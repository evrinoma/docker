node {
    try {
        def toolsDir = '/opt/WWW/container.ite-ng.ru/projects/tools'
        def gitHeadLocal = ''
        def gitHeadRemote = ''
        def remote = [:]
        remote.name = 'tools'
        remote.host = '172.16.45.5'
        remote.user = 'root'
        remote.port = 12000
        remote.password = '1234'
        remote.allowAnyHosts = true
        stage('Remote SSH') {
            sshCommand remote: remote, command: "if [ -d \"${toolsDir}\" ]; then true; else false; fi;"
        }
        stage('Check Git') {
            gitHeadLocal = sshCommand remote: remote, command: "cd ${toolsDir} && git rev-parse HEAD"
            gitHeadRemote = sshCommand remote: remote, command: "cd ${toolsDir} && git ls-remote origin -h refs/heads/master | awk '{print \$1}'"
            print gitHeadLocal
            print gitHeadRemote
        }
        if (gitHeadLocal!=gitHeadRemote) {
            stage('Git Pull') {
                sshCommand remote: remote, command: "cd ${toolsDir} && git pull"
            }
            stage('Composer update') {
                sshCommand remote: remote, command: " cd ${toolsDir} && composer update evrinoma/shell-bundle evrinoma/dashboard-bundle evrinoma/utils-bundle evrinoma/dto-bundle evrinoma/settings-bundle evrinoma/delta8-bundle evrinoma/exim-bundle evrinoma/livevideo-bundle evrinoma/menu-bundle"
            }
            stage('Migration') {
                sshCommand remote: remote, command: " /usr/bin/php ${toolsDir}/bin/console --no-interaction doctrine:migrations:migrate"
            }
            stage('Assets') {
                sshCommand remote: remote, command: " /usr/bin/php ${toolsDir}/bin/console assets:install --symlink --env=prod"
            }
            stage('JsRoutes') {
                sshCommand remote: remote, command: "cd ${toolsDir} && php bin/console fos:js-routing:dump --format=json --target=public/js/fos_js_routes.json"
            }
            stage('WebPack') {
                sshCommand remote: remote, command: "cd ${toolsDir} && webpack --env=prod"
            }
            stage('Cache') {
                sshCommand remote: remote, command: " /usr/bin/php ${toolsDir}/bin/console cache:clear --env=prod"
            }
            stage('Permission') {
                sshCommand remote: remote, command: "chown -R apache.apache ${toolsDir} "
            }
            stage('Send email') {
                def mailRecipients = "nikolns@ite-ng.ru"
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
            def mailRecipients = "nikolns@ite-ng.ru"
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