node {
    try {
        stage('run exim query') {
            def dockerName = 'exim query'
            def dockerRootDir = '/opt/exim'
            def dockerScriptDir = "${dockerRootDir}/bin"
            def remote = [:]
            remote.name = dockerName
            remote.host = '172.16.45.5'
            remote.user = 'root'
            remote.port = 12001
            remote.password = '1234'
            remote.allowAnyHosts = true
            sshCommand remote: remote, command: "cd ${dockerScriptDir} && ./exim -q -v"
        }
        stage('Send email') {
            def mailRecipients = "nikolns@ite-ng.ru"
            def jobName = currentBuild.fullDisplayName
            def subject = "[Jenkins] ${jobName}"
            def body = "EXIM QUERY BUILD: SUCCESS"
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
            def body = "EXIM QUERY DOCKER: FAILED"
            mail(bcc: '', cc: '', charset: 'UTF-8', mimeType: 'text/html', replyTo: '',
            body: body,
            subject: subject,
            to: mailRecipients
            )
        }
    }
}