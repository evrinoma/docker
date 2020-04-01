node {
    def mailRecipients = "nikolns@ite-ng.ru"
    try {
        def contDir = '/opt/WWW/container.ite-ng.ru/projects/cont/debug'
        def gitHeadLocal = ''
        def gitHeadRemote = ''
        def gitRemote='http://user:pass@git.ite-ng.ru/root/cont.git'
        def remote = [:]
        def projectCode = ''
        def project = ''
        def sumscheckoff = 'false'
        def param = ''
        remote.name = 'bundle'
        remote.host = '172.20.1.175'
        remote.user = 'root'
        remote.port = 22
        remote.password = '1234'
        remote.allowAnyHosts = true
        stage('Input Env Value') {
            branchName = input(
                   id: 'branch_name_id', message: 'branch_name', parameters: [
                   [$class: 'TextParameterDefinition', defaultValue: '', description: 'Input branch name', name: 'branch_name']
            ])
            emailReport = input(
                   id: 'email_report_id', message: 'email_report', parameters: [
                   [$class: 'TextParameterDefinition', defaultValue: '', description: 'Input email name', name: 'email_report']
            ])
            branchName = (branchName)? branchName:'master'
            mailRecipients += (emailReport)? ', '+emailReport:''
            echo ("Env: ["+branchName+"]")
            echo ("Env: ["+emailReport+"]")
        }
        stage('Remote SSH') {
            sshCommand remote: remote, command: "if [ -d \"${contDir}\" ]; then true; else false; fi;"
        }
        stage('Git Checkout') {
            sshCommand remote: remote, command: "cd ${contDir} && git pull ${gitRemote}"
            sshCommand remote: remote, command: "cd ${contDir} && git checkout ${branchName}"
        }
        stage('Git Pull') {
            sshCommand remote: remote, command: "cd ${contDir} && git pull ${gitRemote}"
        }
        stage('Migration') {
            sshCommand remote: remote, command: " /usr/bin/php ${contDir}/bin/console --no-interaction doctrine:migrations:migrate"
        }
        stage('WebPack') {
            sshCommand remote: remote, command: "cd ${contDir} && yarn && webpack --env=prod"
        }
        stage('Assets') {
            sshCommand remote: remote, command: " /usr/bin/php ${contDir}/bin/console assets:install --symlink --env=prod"
        }
        stage('Cache') {
            sshCommand remote: remote, command: " /usr/bin/php ${contDir}/bin/console cache:clear --env=prod"
        }
        stage('Permission') {
            sshCommand remote: remote, command: "chown -R apache.apache ${contDir} "
        }
        stage('Send email') {
            def jobName = currentBuild.fullDisplayName
            def subject = "[Jenkins] ${jobName} checkout to branch ${branchName}"
            def body = "GIT COMMIT:${gitHeadRemote} BUILD: SUCCESS DEPLOY: SUCCESS"
            mail(bcc: '', cc: '', charset: 'UTF-8', mimeType: 'text/html', replyTo: '',
            body: body,
            subject: subject,
            to: mailRecipients
            )
        }
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