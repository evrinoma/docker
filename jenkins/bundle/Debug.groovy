node {
    def mailRecipients = "nikolns@ite-ng.ru, kay@ite-ng.ru"
    try {
        def dumpSqlDir = '/tmp'
        def settings = [:]
        settings.userSrc = 'cont'
        settings.passSrc = 'cont'
        settings.hostSrc = '172.20.1.160'
        settings.baseSrc = 'cont'
        settings.userDst = 'cont'
        settings.passDst = 'cont'
        settings.hostDst = '172.20.1.161'
        settings.baseDst = 'cont_debug'
        def remoteSql = [:]
        remoteSql.name = 'backup'
        remoteSql.host = '172.20.1.168'
        remoteSql.user = 'root'
        remoteSql.password = '1234'
        remoteSql.allowAnyHosts = true
        
        def contDir = '/opt/WWW/container.ite-ng.ru/projects/cont/debug'
        def gitHeadLocal = ''
        def gitHeadRemote = ''
        def gitRemote='http://grishvv:oin50x@git.ite-ng.ru/root/cont.git'
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
            baseDeploy = input(
                id: 'baseDeploy', message: 'DBase Deploy', parameters: [
                    [$class: 'ChoiceParameterDefinition', description: 'database deploy choices', name:'baseDeploy', choices: "false\ntrue"],
            ])
            emailReport = input(
                   id: 'email_report_id', message: 'email_report', parameters: [
                   [$class: 'TextParameterDefinition', defaultValue: '', description: 'Input email name', name: 'email_report']
            ])
            branchName = (branchName)? branchName:'master'
            mailRecipients += (emailReport)? ', '+emailReport:''
            baseDeploy = (baseDeploy)? baseDeploy:'false'
            echo ("Env: ["+branchName+"]")
            echo ("Env: ["+emailReport+"]")
            echo ("Env: ["+baseDeploy+"]")
        }
        stage('Remote SSH') {
            sshCommand remote: remote, command: "if [ -d \"${contDir}\" ]; then true; else false; fi;"
        }
        stage('Git Checkout') {
            sshCommand remote: remote, command: "cd ${contDir} && git config remote.origin.url  ${gitRemote}"
             try  {
                sshCommand remote: remote, command: "cd ${contDir} && git pull ${gitRemote} ${branchName}:${branchName}"
            }catch(Exception e) {
                 echo "Exception already on branch"
            }
            sshCommand remote: remote, command: "cd ${contDir} && git checkout ${branchName}"
        }
        stage('Git Pull') {
            sshCommand remote: remote, command: "cd ${contDir} && git pull origin ${branchName}"
        }
        if (baseDeploy == 'true' ) {
            stage('Clean Base') {
                sshCommand remote: remoteSql, command: "echo 'SET FOREIGN_KEY_CHECKS = 0;' > ${dumpSqlDir}/clean.sql"
            try {
                sshCommand remote: remoteSql, command: "mysqldump --add-drop-table --no-data -u${settings.userDst} -h${settings.hostDst} -p${settings.passDst} ${settings.baseDst} | grep 'DROP TABLE' >> ${dumpSqlDir}/clean.sql"
            } catch(Exception e) {
                echo "some error was detected"  
            }
                sshCommand remote: remoteSql, command: "echo 'SET FOREIGN_KEY_CHECKS = 1;' >> ${dumpSqlDir}/clean.sql"
                sshCommand remote: remoteSql, command: "mysql -u${settings.userDst} -h${settings.hostDst} -p${settings.passDst} ${settings.baseDst} < ${dumpSqlDir}/clean.sql" 
                sshCommand remote: remoteSql, command: "rm -f ${dumpSqlDir}/clean.sql"
            }
            stage('Make Dump') {
             sshCommand remote: remoteSql, command: "mysqldump -u${settings.userSrc} -h${settings.hostSrc} -p${settings.passSrc} ${settings.baseSrc} > ${dumpSqlDir}/${settings.baseSrc}.sql"
            }
            stage('Fill Dump') {
                sshCommand remote: remoteSql, command: "mysql -u${settings.userDst} -h${settings.hostDst} -p${settings.passDst} ${settings.baseDst} < ${dumpSqlDir}/${settings.baseSrc}.sql"
                sshCommand remote: remoteSql, command: "rm -f ${dumpSqlDir}/${settings.baseSrc}.sql"
            }
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
