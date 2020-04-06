node {
    def mailRecipients = "nikolns@ite-ng.ru"
    try {
        def dumpSqlDir = '/tmp'
        def gitHeadLocal = ''
        def gitHeadRemote = ''
        def sftpRemote = [:]
        sftpRemote.user = 'jenkins'
        sftpRemote.pass = 'l\\;tyrbyc'
        sftpRemote.host = 'cloud.ite-ng.ru'
        def settings = [:]
        def remote = [:]
        remote.name = 'backup'
        remote.host = '172.20.1.168'
        remote.user = 'root'
        remote.password = '1234'
        remote.allowAnyHosts = true
        stage('Input Env Value') {
            baseName = input(
                id: 'baseChoice', message: 'DBase selector', parameters: [
                    [$class: 'ChoiceParameterDefinition', description: 'database choices', name:'baseChoice', choices: "cont\nmh3itgr_nek_ng\nmh3itgr_ng"],
            ])

            emailReport = input(
                  id: 'email_report_id', message: 'email_report', parameters: [
                  [$class: 'TextParameterDefinition', defaultValue: '', description: 'Input email name', name: 'email_report']
            ])

            baseName = (baseName)? baseName:''
            mailRecipients += (emailReport)? ', '+emailReport:''
            echo ("Env: ["+baseName+"]")
            echo ("Env: ["+emailReport+"]")
        }
        stage('Prepare') {
            switch(baseName) {
              case 'cont':
                    settings.user = 'cont'
                    settings.pass = 'cont'
                    settings.host = '172.20.1.160'
                  break;
              case 'mh3itgr_nek_ng':
                    settings.user = 'mh3itgr_ngdb'
                    settings.pass = '147Fort21'
                    settings.host = '172.20.1.160'
                  break;
              case 'mh3itgr_ng':
                    settings.user = 'mh3itgr_ngdb'
                    settings.pass = '147Fort21'
                    settings.host = '172.20.1.9'
                  break;
              default:
                  throw new Exception("Base is not selected")
            }
             echo ("Env: ["+baseName+"]")
        }
        stage('Make Dump') {
            echo ("mysqldump -u"+settings.user+"-h"+settings.host+" -p"+settings.pass+" "+baseName+" > "+dumpSqlDir+"/"+baseName+".sql")
            sshCommand remote: remote, command: "mysqldump -u${settings.user} -h${settings.host} -p${settings.pass} ${baseName} > ${dumpSqlDir}/${baseName}.sql"
        }
        stage('Make Zip') {
            sshCommand remote: remote, command: "zip ${dumpSqlDir}/${baseName}.sql.zip  -j ${dumpSqlDir}/${baseName}.sql "
        }
        stage('Sftp Copy') {
            def date = new Date().format( 'yyyy-MM-dd.h.m.s' )
            try {
                sshCommand remote: remote, command: "lftp -c 'open -u ${sftpRemote.user},${sftpRemote.pass} sftp://${sftpRemote.host}; cd disk/dump/; mkdir ${baseName};'"
            } catch(Exception e) {
                echo "Direcotry disk/dump/${baseName} already exist"
            }
            try {
                sshCommand remote: remote, command: "lftp -c 'open -u ${sftpRemote.user},${sftpRemote.pass} sftp://${sftpRemote.host}; cd disk/dump/${baseName}; mkdir ${date};'"
            } catch(Exception e) {
                echo "Direcotry disk/dump/${baseName}/${date} already exist"
            }
            sshCommand remote: remote, command: "lftp -c 'open -u ${sftpRemote.user},${sftpRemote.pass} sftp://${sftpRemote.host}; cd disk/dump/${baseName}/${date};put ${dumpSqlDir}/${baseName}.sql.zip'"
        }
        stage('clean') {
          sshCommand remote: remote, command: "rm -rf ${dumpSqlDir}/${baseName}.sql"
          sshCommand remote: remote, command: "rm -rf ${dumpSqlDir}/${baseName}.sql.zip"
        }
        currentBuild.result = 'SUCCESS'
        echo "RESULT: ${currentBuild.result}"
        stage('Send email') {
            def jobName = currentBuild.fullDisplayName
            def subject = "[Jenkins] ${jobName}"
            def body = "GIT COMMIT:${gitHeadRemote} BUILD: SUCCESS DEPLOY: SUCCESS <br> PASS: <b>dump</b> <a href='https://cloud.ite-ng.ru/index.php/s/AGuyVggGJ0QqRJV'>click</a>"
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
