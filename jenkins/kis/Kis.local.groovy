node {
    def mailRecipients = "grishvv@ite-ng.ru,nikolns@ite-ng.ru"
    try {
        def gitUser = 'user'
        def gitPass = 'pass'
        def kisLocalDir = '/opt/WWW/container.ite-ng.ru/projects/httpd/kis.ite-ng.ru/content_new'
        def gitHeadLocal = ''
        def gitHeadRemote = ''
        def gitRemote="http://${gitUser}:${gitPass}@git.ite-ng.ru/root/kis_content_new.git"
        def remote = [:]
        remote.name = 'kis'
        remote.host = '172.20.1.179'
        remote.user = 'user'
        remote.port = 22
        remote.password = 'pass'
        remote.allowAnyHosts = true
        stage('Remote SSH') {
            sshCommand remote: remote, command: "if [ -d \"${kisLocalDir}\" ]; then true; else false; fi;"
        }
        stage('Check Git') {
            gitHeadLocal = sshCommand remote: remote, command: "cd ${kisLocalDir} && git rev-parse HEAD"
            gitHeadRemote = sshCommand remote: remote, command: "cd ${kisLocalDir} && git ls-remote ${gitRemote} HEAD | awk '{print \$1}'"
            print gitHeadLocal
            print gitHeadRemote
        }
        if (gitHeadLocal!=gitHeadRemote) {
            stage('Git Stash') {
                sshCommand remote: remote, command: "cd ${kisLocalDir} && git add . && git stash"
            }
            stage('Git Pull') {
                sshCommand remote: remote, command: "cd ${kisLocalDir} && git pull ${gitRemote} master"
            }
            stage('Composer') {
                sshCommand remote: remote, command: "rm -rf /root/.composer && mkdir -p /root/.composer && echo -e '{\n    \"http-basic\": {\n        \"git.ite-ng.ru\": {\n            \"username\": \"${gitUser}\",\n            \"password\": \"${gitPass}\"\n        }\n    }\n}'> /root/.composer/auth.json"
                sshCommand remote: remote, command: "cd ${kisLocalDir} && composer install"
            }
            stage('JsRoutes') {
                sshCommand remote: remote, command: "cd ${kisLocalDir} && php bin/console fos:js-routing:dump --env=prod"
            }
            stage('Npm WebPack') {
                sshCommand remote: remote, command: "cd ${kisLocalDir} && npm run prod"
            }
            stage('Assets') {
                sshCommand remote: remote, command: "/usr/bin/php ${kisLocalDir}/bin/console assets:install --symlink --env=prod"
            }
            stage('Remove Cache') {
                sshCommand remote: remote, command: "rm -rf ${kisLocalDir}/var/cache/*"
            }
            stage('Cache') {
                sshCommand remote: remote, command: "/usr/bin/php ${kisLocalDir}/bin/console cache:clear --env=prod"
            }
            stage('Permission') {
                sshCommand remote: remote, command: "chown -R apache.apache ${kisLocalDir} "
            }
            stage('Send email') {
                def jobName = currentBuild.fullDisplayName
                def subject = "[Jenkins] ${jobName}"
                def body = "GIT COMMIT:${gitHeadRemote} BUILD: SUCCESS DEPLOY: SUCCESS"
                mail(bcc: '', cc: '', charset: 'UTF-8', mimeType: 'text/html', replyTo: '',
                body: body,
                subject: subject,
                to: mailRecipients
                )
            }
        } 
        currentBuild.result = 'SUCCESS'
        echo "RESULT: ${currentBuild.result}"
    } catch(Exception e) {
        stage('Report') {
            currentBuild.result = 'SUCCESS'
            echo "RESULT: ${currentBuild.result}"
        }
        stage('Send email') {
            def jobName = currentBuild.fullDisplayName
            def subject = "[Jenkins][FAILED] ${jobName}"
            def body = "BUILD: ${currentBuild.result} RESULT:FAILED"
            mail(bcc: '', cc: '', charset: 'UTF-8', mimeType: 'text/html', replyTo: '',
            body: body,
            subject: subject,
            to: mailRecipients
            )
        }
    }
}

