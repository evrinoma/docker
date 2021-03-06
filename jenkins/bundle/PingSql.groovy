node {
    def mailRecipients = "nikolns@ite-ng.ru"
    try {
        def checkDir = '/opt/WWW/container.ite-ng.ru/conf/backup'
        def remote = [:]
        remote.name = 'bundle'
        remote.host = '172.20.1.161'
        remote.user = 'root'
        remote.port = 22
        remote.password = '1234'
        remote.allowAnyHosts = true
        stage('check web') {
            currentBuild.result = sshCommand remote: remote, command: "/usr/bin/php ${checkDir}/ping.php"
        }
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
