node {
    def mailRecipients = "grishvv@ite-ng.ru,nikolns@ite-ng.ru"
    try {
        def contDir = '/opt/WWW/container.ite-ng.ru/projects/cont/prod'
        def gitHeadLocal = ''
        def gitHeadRemote = ''
        def gitRemote='http://user:pass@git.ite-ng.ru/root/cont.git'
        def remote = [:]
        remote.name = 'sftp'
        remote.host = '172.20.1.165'
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
            stage('Migration') {
                sshCommand remote: remote, command: "/usr/bin/php ${contDir}/bin/console --no-interaction doctrine:migrations:migrate --env=prod"
            }
            stage('JsRoutes') {
                sshCommand remote: remote, command: "cd ${contDir} && php bin/console fos:js-routing:dump --env=prod"
            }
            stage('WebPack') {
                sshCommand remote: remote, command: "cd ${contDir} && yarn && webpack --env=prod"
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
