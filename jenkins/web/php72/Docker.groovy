node {
    try {
        def mailRecipients = "nikolns@ite-ng.ru"
        def dockerName = 'web'
        def dockerRootDir = '/opt/docker'
        def dockerComposeDir = "${dockerRootDir}/compose/prod/${dockerName}/php72"
        def gitHeadLocal = ''
        def gitHeadRemote = ''
        def remote = [:]
        remote.name = dockerName
        remote.host = '172.16.45.15'
        remote.user = 'user'
        remote.port = 749
        remote.password = 'pass'
        remote.allowAnyHosts = true
        stage('Remote SSH') {
            sshCommand remote: remote, command: "echo -e \"${remote.password}\" | sudo -S bash -c \"if [ -d \"${dockerRootDir}\" ]; then true; else false; fi;\""
            sshCommand remote: remote, command: "echo -e \"${remote.password}\" | sudo -S bash -c \"if [ -d \"${dockerComposeDir}\" ]; then true; else false; fi;\""
        }
        stage('Check Git') {
            gitHeadLocal = sshCommand remote: remote, command: "echo -e \"${remote.password}\" | sudo -S bash -c \"cd ${dockerRootDir} && git rev-parse HEAD\""
            gitHeadRemote = sshCommand remote: remote, command: "echo -e \"${remote.password}\" | sudo -S bash -c \"cd ${dockerRootDir} && git ls-remote origin -h refs/heads/master | awk {'print \\\$1'}\""
            print gitHeadLocal
            print gitHeadRemote
        }
        if (gitHeadLocal!=gitHeadRemote) {
            stage('Git Pull') {
                sshCommand remote: remote, command: "echo -e \"${remote.password}\" | sudo -S bash -c \"cd ${dockerRootDir} && git pull\""
            }
            stage('PreBuild Docker') {
                sshCommand remote: remote, command: "echo -e \"${remote.password}\" | sudo -S bash -c \"cd ${dockerComposeDir} && ./build\""
            }
            stage('Send email') {
                def jobName = currentBuild.fullDisplayName
                def subject = "[Jenkins] ${jobName}"
                def body = "GIT COMMIT:${gitHeadRemote} DOCKER BUILD: SUCCESS"
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
            def body = "GIT PULL: ${currentBuild.result} RESULT BUILD DOCKER: FAILED"
            mail(bcc: '', cc: '', charset: 'UTF-8', mimeType: 'text/html', replyTo: '',
            body: body,
            subject: subject,
            to: mailRecipients
            )
        }
    }
}
