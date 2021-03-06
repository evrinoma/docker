node {
    def mailRecipients = "nikolns@ite-ng.ru"
    try {
        def gitHeadLocal = ''
        def gitHeadRemote = ''
        def remote = [:]
        remote.name = 'backup'
        remote.host = '172.20.1.168'
        remote.user = 'root'
        remote.password = '1234'
        remote.allowAnyHosts = true
        stage('Remote SSH') {
            sshCommand remote: remote, command: "ls -la /opt/WWW/backup/sqldump"
        }
        stage('Check Cron') {
            sshCommand remote: remote, command: "cat /opt/WWW/container.ite-ng.ru/conf/backup/crontab.root"
            sshCommand remote: remote, command: "ps aux | grep -v grep | grep cron"
        }
        stage('Run BackUp') {
            sshCommand remote: remote, command: "/usr/local/bin/backupexec"
        }
        stage('Analize BackUp') {
            def lastDir = sshCommand remote: remote, command: "ls -td -- /opt/WWW/backup/sqldump/* | head -n 1"
            sshCommand remote: remote, command: "ls -la ${lastDir}"
            sshCommand remote: remote, command: "jq -c '.[]' ${lastDir}/servers.json | while read i; do base=\$(echo \"\$i\" | jq -r '.base'); if [ \"\$(ls -la | grep \$base.sql | awk {' print \$5'})\" != \"0\" ]; then true; else false; fi; done;"
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


