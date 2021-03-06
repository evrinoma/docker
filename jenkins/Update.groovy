node {
    def mailRecipients = "nikolns@ite-ng.ru"
    try {
        stage('Build Meeting Docker') {
            def dockerName = 'meeting'
            def dockerRootDir = '/opt/docker'
            def dockerScriptDir = "${dockerRootDir}/scripts"
            def remote = [:]
            remote.name = dockerName
            remote.host = '172.16.45.4'
            remote.user = 'user'
            remote.port = 749
            remote.password = 'pass'
            remote.allowAnyHosts = true
            sshCommand remote: remote, command: "echo -e \"${remote.password}\" | sudo -S bash -c \"cd ${dockerScriptDir} && ./build.prod\""
        }
        stage('Build SFTP Docker') {
            def dockerName = 'sftp'
            def dockerRootDir = '/opt/docker'
            def dockerScriptDir = "${dockerRootDir}/scripts"
            def remote = [:]
            remote.name = dockerName
            remote.host = '172.20.1.160'
            remote.user = 'user'
            remote.port = 749
            remote.password = 'pass'
            remote.allowAnyHosts = true
            sshCommand remote: remote, command: "echo -e \"${remote.password}\" | sudo -S bash -c \"cd ${dockerScriptDir} && ./build.prod\""
        }
        stage('Build WEB Docker') {
            def dockerName = 'web'
            def dockerRootDir = '/opt/docker'
            def dockerScriptDir = "${dockerRootDir}/scripts"
            def remote = [:]
            remote.name = dockerName
            remote.host = '172.16.45.15'
            remote.user = 'user'
            remote.port = 749
            remote.password = 'pass'
            remote.allowAnyHosts = true
            sshCommand remote: remote, command: "echo -e \"${remote.password}\" | sudo -S bash -c \"cd ${dockerScriptDir} && ./build.prod\""
        }
        stage('Build DOVECOT.EMAIL Docker') {
            def dockerName = 'dovecot.email'
            def dockerRootDir = '/opt/docker'
            def dockerScriptDir = "${dockerRootDir}/scripts"
            def remote = [:]
            remote.name = dockerName
            remote.host = '172.16.45.4'
            remote.user = 'user'
            remote.port = 749
            remote.password = 'pass'
            remote.allowAnyHosts = true
            sshCommand remote: remote, command: "echo -e \"${remote.password}\" | sudo -S bash -c \"cd ${dockerScriptDir} && ./build.prod\""
        }
        stage('Build DEBUG.1C Docker') {
            def dockerName = 'debug1C'
            def dockerRootDir = '/opt/docker'
            def dockerScriptDir = "${dockerRootDir}/scripts"
            def remote = [:]
            remote.name = dockerName
            remote.host = '172.16.45.55'
            remote.user = 'user'
            remote.port = 749
            remote.password = 'pass'
            remote.allowAnyHosts = true
            sshCommand remote: remote, command: "echo -e \"${remote.password}\" | sudo -S bash -c \"cd ${dockerScriptDir} && ./build.prod\""
        }
        stage('Build EXIM.EMAIL Docker') {
            def dockerName = 'exim.email'
            def dockerRootDir = '/opt/docker'
            def dockerScriptDir = "${dockerRootDir}/scripts"
            def remote = [:]
            remote.name = dockerName
            remote.host = '172.16.45.5'
            remote.user = 'user'
            remote.port = 749
            remote.password = 'pass'
            remote.allowAnyHosts = true
            sshCommand remote: remote, command: "echo -e \"${remote.password}\" | sudo -S bash -c \"cd ${dockerScriptDir} && ./build.prod\""
        }
         stage('Build Primavera Docker') {
                    def dockerName = 'primavera'
                    def dockerRootDir = '/opt/docker'
                    def dockerScriptDir = "${dockerRootDir}/scripts"
                    def remote = [:]
                    remote.name = dockerName
                    remote.host = '172.20.1.22'
                    remote.user = 'nikolns'
                    remote.port = 749
                    remote.password = '0631'
                    remote.allowAnyHosts = true
                    sshCommand remote: remote, command: "echo -e \"${remote.password}\" | sudo -S bash -c \"cd ${dockerScriptDir} && ./build.prod\""
                }
        // stage('Build BUNDLE Docker') {
        //     def dockerName = 'bundle'
        //     def dockerRootDir = '/opt/docker'
        //     def dockerScriptDir = "${dockerRootDir}/scripts"
        //     def remote = [:]
        //     remote.name = dockerName
        //     remote.host = '172.20.1.161'
        //     remote.user = 'user'
        //     remote.port = 749
        //     remote.password = 'pass'
        //     remote.allowAnyHosts = true
        //     sshCommand remote: remote, command: "echo -e \"${remote.password}\" | sudo -S bash -c \"cd ${dockerScriptDir} && ./build.prod\""
        // }
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
