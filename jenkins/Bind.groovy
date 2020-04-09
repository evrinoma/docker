node {
    try {
        def contDir = '/opt/bind'
        def gitHeadLocal = ''
        def gitHeadRemote = ''
        def gitRemote='http://user:pass@git.ite-ng.ru/root/bind.git'
        def remote = [:]
        remote.name = 'bind.slave'  //bind.master
        remote.host = '172.16.45.4' //172.20.1.5
        remote.user = 'root'
        remote.port = 12000
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
            stage('restart Bind') {
                sshCommand remote: remote, command: "/usr/local/bin/restartBind"
            }
            stage('Send email') {
                def mailRecipients = "dmy@ite-ng.ru, nikolns@ite-ng.ru"
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
            def mailRecipients = "dmy@ite-ng.ru, nikolns@ite-ng.ru"
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