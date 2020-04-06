node {
    def mailRecipients = "nikolns@ite-ng.ru"
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

