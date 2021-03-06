node {
    def mailRecipients = "grishvv@ite-ng.ru,nikolns@ite-ng.ru"
    try {
        def gitUser = 'user'
        def gitPass = 'pass'
        def contDir = '/opt/WWW/container.ite-ng.ru/projects/httpd/cont/prod'
        def gitHeadLocal = ''
        def gitHeadRemote = ''
        def gitRemote='http://${gitUser}:${gitPass}@git.ite-ng.ru/root/cont.git'
        def remote = [:]
        def projectCode = ''
        def project = ''
        def sumscheckoff = 'false'
        def param = ''
        remote.name = 'sftp'
        remote.host = '172.20.1.165'
        remote.user = 'root'
        remote.port = 22
        remote.password = '1234'
        remote.allowAnyHosts = true
        stage('Input Env Value') {
            projectCode = input(
                   id: 'project_code_id', message: 'project_code', parameters: [
                   [$class: 'TextParameterDefinition', defaultValue: '', description: 'Allow set project\'s codes to deliver', name: 'project_code']
            ])
            project = input(
                    id: 'project_id', message: 'project', parameters: [
                    [$class: 'TextParameterDefinition', defaultValue: '', description: 'Allow set project\'s id to delivery', name: 'project']
            ])
            sumscheckoff = input(
                    id: 'sumscheckoff_id', message: 'sumscheckoff', parameters: [
                    [$class: 'ChoiceParameterDefinition', choices: 'false\ntrue', name: 'sumscheckoff', defaultValue: 'false', description: 'Allow to turn off checking of delivery sums differences']
            ])
            echo ("Env: ["+projectCode+"]")
            echo ("Env: ["+project+"]")
            echo ("Env: ["+sumscheckoff+"]")
            param += (project)?'--project=${project}':''
            param += (projectCode)?'--project_code=${projectCode}':''
            param += (sumscheckoff == 'true')?'--sumscheckoff':''
            echo ("Env: ["+param+"]")
        }
        stage('Remote SSH') {
            sshCommand remote: remote, command: "if [ -d \"${contDir}\" ]; then true; else false; fi;"
        }
        stage('Run delivery_load_command') {
            sshCommand remote: remote, command: " /usr/bin/php ${contDir}/bin/console legio:cont:delivery_load_command ${param} --env=prod"
        }
        stage('Permission') {
            sshCommand remote: remote, command: "chown -R apache.apache ${contDir} "
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