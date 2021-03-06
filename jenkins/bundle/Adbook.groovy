node {
    def mailRecipients = "grishvv@ite-ng.ru,nikolns@ite-ng.ru"
    try {
        def gitUser = 'user'
        def gitPass = 'pass'
        def contDir = '/opt/WWW/container.ite-ng.ru/projects/httpd/adbook'
        def gitHeadLocal = ''
        def gitHeadRemote = ''
        def gitRemote="http://${gitUser}:${gitPass}@git.ite-ng.ru/root/adbook.git"
        def remote = [:]
        remote.name = 'bundle'
        remote.host = '172.20.1.161'
        remote.user = 'root'
        remote.port = 22
        remote.password = '1234'
        remote.allowAnyHosts = true
        stage('Remote SSH') {
            sshCommand remote: remote, command: "if [ -d \"${contDir}\" ]; then true; else false; fi;"
        }
        stage('Check Git') {
            gitHeadLocal = sshCommand remote: remote, command: "cd ${contDir} && git rev-parse HEAD"
            gitHeadRemote = sshCommand remote: remote, command: "cd ${contDir} && git ls-remote ${gitRemote} HEAD | awk '{print \$1}'"
            print gitHeadLocal
            print gitHeadRemote
        }
        if (gitHeadLocal!=gitHeadRemote) {
            stage('Git Pull') {
                sshCommand remote: remote, command: "cd ${contDir} && git pull ${gitRemote}"
            }
            stage('Composer') {
                sshCommand remote: remote, command: "rm -rf /root/.composer && mkdir -p /root/.composer && echo -e '{\n    \"http-basic\": {\n        \"git.ite-ng.ru\": {\n            \"username\": \"${gitUser}\",\n            \"password\": \"${gitPass}\"\n        }\n    }\n}'> /root/.composer/auth.json"
                sshCommand remote: remote, command: "cd ${contDir} && composer install"
            }
            stage('Migration') {
                sshCommand remote: remote, command: "/usr/bin/php ${contDir}/bin/console --no-interaction doctrine:migrations:migrate  --env=prod"
            }
            stage('Encore') {
                sshCommand remote: remote, command: "cd ${contDir} && yarn encore production"
            }
            stage('Assets') {
                sshCommand remote: remote, command: "/usr/bin/php ${contDir}/bin/console assets:install --symlink --env=prod"
            }
            stage('Remove Cache') {
                sshCommand remote: remote, command: "rm -rf ${contDir}/var/cache/*"
            }
            stage('Cache') {
                sshCommand remote: remote, command: "/usr/bin/php ${contDir}/bin/console cache:clear --env=prod"
            }
            stage('Permission') {
                sshCommand remote: remote, command: "chown -R apache.apache ${contDir} "
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
