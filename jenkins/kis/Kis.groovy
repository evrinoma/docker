node {
    try {
        def gitUser = 'user'
        def gitPass = 'pass'
        def kisDir = '/opt/WWW/container.ite-ng.ru/projects/httpd/kis.ite-ng.ru/content'
        def gitHeadLocal = ''
        def gitHeadRemote = ''
        def gitRemote="http://${gitUser}:${gitPass}@git.ite-ng.ru/root/kis_content.git"
        def remote = [:]
        remote.name = 'kis'
        remote.host = '172.20.1.9'
        remote.user = 'user'
        remote.port = 22
        remote.password = 'pass'
        remote.allowAnyHosts = true
        stage('Remote SSH') {
            sshCommand remote: remote, command: "if [ -d \"${kisDir}\" ]; then true; else false; fi;"
        }
        stage('Check Git') {
            gitHeadLocal = sshCommand remote: remote, command: "cd ${kisDir} && git rev-parse HEAD"
            gitHeadRemote = sshCommand remote: remote, command: "cd ${kisDir} && git ls-remote ${gitRemote} HEAD | awk '{print \$1}'"
            print gitHeadLocal
            print gitHeadRemote
        }
        if (gitHeadLocal!=gitHeadRemote) {
            stage('Git Pull') {
                sshCommand remote: remote, command: "cd ${kisDir} && git pull ${gitRemote}"
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

