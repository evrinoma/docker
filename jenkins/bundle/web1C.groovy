node {
    def mailRecipients = "1c@ite-ng.ru,nikolns@ite-ng.ru"
    try {
        def gitUser = 'user'
        def gitPass = 'pass'
        def checkDir = '/opt/WWW/container.ite-ng.ru/projects/web1C'
        def gitHeadLocal = ''
        def gitHeadRemote = ''
        def gitRemote="http://${gitUser}:${gitPass}@git.ite-ng.ru/root/web1C.git"
        def remote = [:]
        remote.name = 'bundle'
        remote.host = '172.20.1.11'
        remote.user = 'root'
        remote.port = 12000
        remote.password = '1234'
        remote.allowAnyHosts = true
        stage('Remote SSH') {
            sshCommand remote: remote, command: "if [ -d \"${checkDir}\" ]; then true; else false; fi;"
        }
        stage('Check Git') {
            gitHeadLocal = sshCommand remote: remote, command: "cd ${checkDir} && git rev-parse HEAD"
            gitHeadRemote = sshCommand remote: remote, command: "cd ${checkDir} && git ls-remote ${gitRemote} HEAD | awk '{print \$1}'"
            print gitHeadLocal
            print gitHeadRemote
        }
        if (gitHeadLocal!=gitHeadRemote) {
            stage('Git Pull') {
                sshCommand remote: remote, command: "cd ${checkDir} && git pull ${gitRemote}"
            }
            stage('Public') {
                sshCommand remote: remote, command: "/usr/local/bin/public"
            }
            stage('restart Httpd') {
                sshCommand remote: remote, command: "/usr/local/bin/restartHttpd"
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