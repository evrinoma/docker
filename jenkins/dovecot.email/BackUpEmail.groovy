node {
    try {
        stage('Backup Email Domain') {
            def dockerName = 'web'
            def dockerRootDir = '/opt/UTILS'
            def dockerScriptDir = "${dockerRootDir}"
            def remote = [:]
            remote.name = dockerName
            remote.host = '172.16.45.4'
            remote.user = 'user'
            remote.port = pass
            remote.password = '0631'
            remote.allowAnyHosts = true
            sshCommand remote: remote, command: "echo -e \"${remote.password}\" | sudo -S bash -c \"cd ${dockerScriptDir} && ./backupexec_week\""
        }
        stage('Send email') {
            def mailRecipients = "nikolns@ite-ng.ru"
            def jobName = currentBuild.fullDisplayName
            def subject = "[Jenkins] ${jobName}"
            def body = "GLOBAL BUILD BACKUP EMAIL: SUCCESS"
            mail(bcc: '', cc: '', charset: 'UTF-8', mimeType: 'text/html', replyTo: '',
            body: body,
            subject: subject,
            to: mailRecipients
            )
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
            def body = "GLOBAL BUILD BACKUP EMAIL: FAILED"
            mail(bcc: '', cc: '', charset: 'UTF-8', mimeType: 'text/html', replyTo: '',
            body: body,
            subject: subject,
            to: mailRecipients
            )
        }
    }
}
