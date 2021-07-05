node {
    def mailRecipients = "nikolns@ite-ng.ru"
    try {
        def base_uri = 'https://cloud.ite-ng.ru/'
        def API_PATH = 'ocs/v1.php/apps/files_sharing/api/v1/shares'
        def gitUser = 'user'
        def gitPass = 'pass'
        def baseName = 'documentation'
        def trakingDocDir = "/opt/WWW/container.ite-ng.ru/projects/tracking/${baseName}"
        def gitHeadLocal = ''
        def gitHeadRemote = ''
        def gitRemote="http://${gitUser}:${gitPass}@git.ite-ng.ru/kpsz/${baseName}.git"
        def template='<a href=\\\"%s\\\"><img src=\\\"%s\\\" alt=\\\"%s icon\\\" />%s</a>'
        def list = [
            1: [ name : '2020-79481.pdf', pass : '', target : '', response : '', url : '', title : 'Описание типа средств измерений', icon: 'pdf', src: 'http://www.kpsz.ru/wp-content/icons/pdf-icon.png'],
            2: [ name : 'README.md', pass : '', target : '', response : '', url : '', title : 'Введение', icon: 'txt', src: 'http://www.kpsz.ru/wp-content/icons/doc-icon.png']
        ]
        def sqlRemote = [:]
        sqlRemote.user = 'user'
        sqlRemote.pass = 'pass'
        sqlRemote.host = 'IP'
        sqlRemote.db = 'db_name'
        def sftpRemote = [:]
        sftpRemote.user = 'user'
        sftpRemote.pass = 'pass'
        sftpRemote.host = 'cloud.ite-ng.ru'
        def settings = [:]
        def remote = [:]
        remote.name = 'backup'
        remote.host = 'IP'
        remote.user = 'user'
        remote.password = 'pass'
        remote.allowAnyHosts = true
        stage('Remote SSH') {
            sshCommand remote: remote, command: "if [ -d \"${trakingDocDir}\" ]; then true; else false; fi;"
        }
        stage('Check Git') {
            gitHeadLocal = sshCommand remote: remote, command: "cd ${trakingDocDir} && git rev-parse HEAD"
            gitHeadRemote = sshCommand remote: remote, command: "cd ${trakingDocDir} && git ls-remote ${gitRemote} HEAD | awk '{print \$1}'"
            print gitHeadLocal
            print gitHeadRemote
        }
        if (gitHeadLocal!=gitHeadRemote) {
            stage('Git Pull') {
                sshCommand remote: remote, command: "cd ${trakingDocDir} && git pull ${gitRemote}"
            }
            stage('Sftp Copy') {
                def date = new Date().format( 'yyyy-MM-dd.h.m.s' )
                try {
                    sshCommand remote: remote, command: "lftp -c 'open -u ${sftpRemote.user},${sftpRemote.pass} sftp://${sftpRemote.host}; cd disk/kpsz/; mkdir ${baseName};'"
                } catch(Exception e) {
                    echo "Direcotry disk/kzkt/${baseName} already exist"
                }
                try {
                    sshCommand remote: remote, command: "lftp -c 'open -u ${sftpRemote.user},${sftpRemote.pass} sftp://${sftpRemote.host}; cd disk/kpsz/${baseName}; mkdir ${date};'"
                } catch(Exception e) {
                    echo "Direcotry disk/kzkt/${baseName}/${date} already exist"
                }
                list.each { key, value ->
                    list[key].pass = sshCommand remote: remote, command: "cat /dev/urandom | tr -dc 'a-fA-F0-9' | fold -w 8 | head -n 1"
                    list[key].target = "${trakingDocDir}/$value.name"
                    sshCommand remote: remote, command: "lftp -c 'open -u ${sftpRemote.user},${sftpRemote.pass} sftp://${sftpRemote.host}; cd disk/kpsz/${baseName}/${date};put ${list[key].target}'"
                    list[key].response = sshCommand remote: remote, command: "curl --insecure --silent --user ${sftpRemote.user}:${sftpRemote.pass} '$base_uri/$API_PATH' --data 'path=/disk/kpsz/${baseName}/${date}/${list[key].name}' --data 'shareType=3' --data 'permissions=3' --data 'password=${list[key].pass}' --data 'name=${list[key].name}' --data 'attributes[0][scope]=ownCloud&attributes[0][key]=read&attributes[0][value]=true'"
                    list[key].url = list[key].response[(list[key].response.indexOf('<url>')+5)..(list[key].response.indexOf('</url>')-1)]
                }
                stage('Save to site base') {
                    def id = sshCommand remote: remote, command: "mysql --host='${sqlRemote.host}' --user='${sqlRemote.user}' --password='${sqlRemote.pass}' --database='${sqlRemote.db}' --execute='SELECT id FROM kpsz.wpnec_posts where post_name like \"proshivki\" and post_status = \"publish\"' | grep -e '[0-9]'"
                    def query = ''
                    list.each { key, value ->
                        query += String.format(template, list[key].url, list[key].src, list[key].icon, list[key].title)+'\n\n'
                    }
                    if (query != '') {
                        sshCommand remote: remote, command: "mysql --host='${sqlRemote.host}' --user='${sqlRemote.user}' --password='${sqlRemote.pass}' --database='${sqlRemote.db}' --execute='SET NAMES \"utf8\" COLLATE \"utf8_general_ci\";UPDATE `kpsz`.`wpnec_posts` SET `post_content` = \"${query}\" WHERE (`ID` = \"${id}\")'"
                    }
                }
            }
            currentBuild.result = 'SUCCESS'
            echo "RESULT: ${currentBuild.result}"
            stage('Send email') {
                def jobName = currentBuild.fullDisplayName
                def subject = "[Jenkins] ${jobName}"
                def body = "GIT COMMIT:${gitHeadRemote}, BUILD: SUCCESS, DEPLOY: SUCCESS<br>"
                list.each { key, value ->
                    body += "Document with name:<i>${list[key].name}</i> was succesfuly published<br>"
                    body += "Link:<b><a href='${list[key].url}'>click</a></b> PASS: <b>${list[key].pass}</b><br>"
                }
                mail(bcc: '', cc: '', charset: 'UTF-8', mimeType: 'text/html', replyTo: '',
                body: body,
                subject: subject,
                to: mailRecipients
                )
            }
        }
    } catch(Exception e) {
        stage('Report') {
            currentBuild.result = 'UNSUCCESS'
            echo "RESULT: ${currentBuild.result}"
        }
        stage('Send email') {
            def jobName = currentBuild.fullDisplayName
            def subject = "[AutoDeploying failed][FAILED] ${jobName}"
            def body = "BUILD: ${currentBuild.result} RESULT:FAILED"
            mail(bcc: '', cc: '', charset: 'UTF-8', mimeType: 'text/html', replyTo: '',
            body: body,
            subject: subject,
            to: mailRecipients
            )
        }
    }
}
