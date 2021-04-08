node {
    def mailRecipients = "nikolns@ite-ng.ru, kay@ite-ng.ru"
    try {
        def gitUser = 'gitUser'
        def gitPass = 'gitPass'
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
        def contDir = '/opt/WWW/container.ite-ng.ru/projects/httpd/cont/debug'
        def contApiDir = '/opt/WWW/container.ite-ng.ru/projects/httpd/cont.api/debug'
        def gitHeadLocal = ''
        def gitHeadRemote = ''
        def gitRemote="http://${gitUser}:${gitPass}@git.ite-ng.ru/root/cont.git"
        def gitApiRemote="http://${gitUser}:${gitPass}@git.ite-ng.ru/root/cont.api.git"
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
                   [$class: 'TextParameterDefinition', defaultValue: '', description: 'Input branch name CONT default (master)', name: 'branch_name']
            ])
            branchNameAPI = input(
                   id: 'branch_name_api_id', message: 'branch_name_api', parameters: [
                   [$class: 'TextParameterDefinition', defaultValue: '', description: 'Input branch name CONT.API default (master)', name: 'branch_name_api']
            ])
            baseDeploy = input(
                id: 'baseDeploy', message: 'DBase Deploy', parameters: [
                    [$class: 'ChoiceParameterDefinition', description: 'database deploy choices', name:'baseDeploy', choices: "false\ntrue"],
            ])
            skipMigration = input(
                id: 'skipMigration', message: 'DBase skip Migrations', parameters: [
                    [$class: 'ChoiceParameterDefinition', description: 'database skip Migrations choices', name:'skipMigration', choices: "false\ntrue"],
            ])
            emailReport = input(
                   id: 'email_report_id', message: 'email_report', parameters: [
                   [$class: 'TextParameterDefinition', defaultValue: '', description: 'Input email name', name: 'email_report']
            ])
            branchName = (branchName)? branchName:'master'
            branchNameAPI = (branchNameAPI)? branchNameAPI:'master'
            mailRecipients += (emailReport)? ', '+emailReport:''
            baseDeploy = (baseDeploy)? baseDeploy:'false'
            skipMigration = (skipMigration)? skipMigration:'false'
            echo ("Env: ["+branchName+"]")
            echo ("Env: ["+branchNameAPI+"]")
            echo ("Env: ["+emailReport+"]")
            echo ("Env: ["+baseDeploy+"]")
            echo ("Env: ["+skipMigration+"]")
        }
        stage('Remote SSH') {
            sshCommand remote: remote, command: "if [ -d \"${contDir}\" ]; then true; else false; fi;"
        }
        stage('Composer Init') {
            sshCommand remote: remote, command: "rm -rf /root/.composer && mkdir -p /root/.composer && echo -e '{\n    \"http-basic\": {\n        \"git.ite-ng.ru\": {\n            \"username\": \"${gitUser}\",\n            \"password\": \"${gitPass}\"\n        }\n    }\n}'> /root/.composer/auth.json"
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
        stage('Cont') {
            stage('Git Checkout') {
                sshCommand remote: remote, command: "cd ${contDir} && git config remote.origin.url  ${gitRemote}"
                 try  {
                    sshCommand remote: remote, command: "cd ${contDir} && git add . && git stash && git stash clear && git pull ${gitRemote} ${branchName}:${branchName}"
                }catch(Exception e) {
                     echo "Exception already on branch"
                }
                sshCommand remote: remote, command: "cd ${contDir} && git checkout ${branchName}"
            }
            stage('Git Pull') {
                sshCommand remote: remote, command: "cd ${contDir} && git pull origin ${branchName}"
            }
            stage('Composer') {
                sshCommand remote: remote, command: "cd ${contDir} && composer install"
            }
            if (skipMigration == 'false' ) {
                stage('Migration') {
                    sshCommand remote: remote, command: "/usr/bin/php ${contDir}/bin/console --no-interaction doctrine:migrations:migrate --env=dev"
                }
            }
            stage('JsRoutes') {
                sshCommand remote: remote, command: "cd ${contDir} && php bin/console fos:js-routing:dump --env=dev"
            }
            stage('Assets') {
                sshCommand remote: remote, command: "/usr/bin/php ${contDir}/bin/console assets:install --symlink --env=dev"
            }
            stage('WebPack') {
                sshCommand remote: remote, command: "cd ${contDir} && yarn && webpack --env=dev"
            }
            stage('Remove Cache') {
                sshCommand remote: remote, command: "rm -rf ${contDir}/var/cache/*"
            }
            stage('Cache') {
                sshCommand remote: remote, command: "/usr/bin/php ${contDir}/bin/console cache:clear --env=dev"
            }
            stage('Permission') {
                sshCommand remote: remote, command: "chown -R apache.apache ${contDir} "
            }
        }
        stage('Cont.Api') {
            stage('Git Checkout') {
                sshCommand remote: remote, command: "cd ${contApiDir} && git config remote.origin.url  ${gitApiRemote}"
                 try  {
                    sshCommand remote: remote, command: "cd ${contApiDir} && git add . && git stash && git stash clear && git pull ${gitApiRemote} ${branchNameAPI}:${branchNameAPI}"
                }catch(Exception e) {
                     echo "Exception already on branch"
                }
                sshCommand remote: remote, command: "cd ${contApiDir} && git checkout ${branchNameAPI}"
            }
            stage('Git Pull') {
                sshCommand remote: remote, command: "cd ${contApiDir} && git pull origin ${branchNameAPI}"
            }
            stage('Composer') {
                sshCommand remote: remote, command: "cd ${contApiDir} && composer install"
            }
            stage('Assets') {
                sshCommand remote: remote, command: "/usr/bin/php ${contApiDir}/bin/console assets:install --symlink --env=dev"
            }
            if (skipMigration == 'false' ) {
                stage('Migration') {
                    sshCommand remote: remote, command: "/usr/bin/php ${contApiDir}/bin/console --no-interaction doctrine:migrations:migrate --env=dev"
                }
            }
            stage('JsRoutes') {
                sshCommand remote: remote, command: "cd ${contApiDir} && php bin/console fos:js-routing:dump --format=json --target=public/js/fos_js_routes.json --env=dev"
            }
            stage('WebPack') {
                sshCommand remote: remote, command: "cd ${contApiDir} && yarn && webpack --env=dev"
            }
            stage('Remove Cache') {
                sshCommand remote: remote, command: "rm -rf ${contApiDir}/var/cache/*"
            }
            stage('Cache') {
                sshCommand remote: remote, command: "/usr/bin/php ${contApiDir}/bin/console cache:clear --env=dev"
            }
            stage('Permission') {
                sshCommand remote: remote, command: "chown -R apache.apache ${contApiDir} "
            }
        }
        stage('Send email') {
            def jobName = currentBuild.fullDisplayName
            def subject = "[Jenkins] ${jobName} checkout to branch ${branchName} and checkout to branch ${branchNameAPI} "
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