node {
    def mailRecipients = "nikolns@ite-ng.ru"
    try {
        stage('Build KIS Docker') {
            def dockerName = 'kis'
            def dockerRootDir = '/opt/docker'
            def dockerScriptDir = "${dockerRootDir}/scripts"
            def remote = [:]
            remote.name = dockerName
            remote.host = '172.20.1.9'
            remote.user = 'user'
            remote.port = 749
            remote.password = 'pass'
            remote.allowAnyHosts = true
            sshCommand remote: remote, command: "echo -e \"${remote.password}\" | sudo -S bash -c \"cd ${dockerScriptDir} && ./build.prod\""
        }
        stage('Send email') {
            def jobName = currentBuild.fullDisplayName
            def subject = "[Jenkins] ${jobName}"
            def body = "GLOBAL DOCKER BUILD: SUCCESS"
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
            def jobName = currentBuild.fullDisplayName
            def subject = "[Jenkins][FAILED] ${jobName}"
            def body = "GLOBAL BUILD DOCKER: FAILED"
            mail(bcc: '', cc: '', charset: 'UTF-8', mimeType: 'text/html', replyTo: '',
            body: body,
            subject: subject,
            to: mailRecipients
            )
        }
    }
}