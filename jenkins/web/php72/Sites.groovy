node {
    def mailRecipients = "vasilav@ite-ng.ru,nikolns@ite-ng.ru"
    try {
        def dockerName = 'web'
        def baseDir = "/opt/DISK/projects/httpd/"
        def trackingDocDir = ''
        def gitStatusLocal = ''
        def gitHeadLocal = ''
        def gitHeadRemote = ''
        def remote = [:]
        remote.name = dockerName
        remote.host = 'IP'
        remote.user = 'user'
        remote.port = port
        remote.password = 'pass'
        remote.allowAnyHosts = true
        def list = [
            1: [ headRemote : 'fe726296185f10098fcf7280c3150360c27baeba', target : 'ite-ng.ru' ],
            2: [ headRemote : '0e6501d44c8b7a1978a975ba5bf808d2e48825fc', target : 'ipark45.ru' ],
            3: [ headRemote : 'b05e82e5a4fff6e6b54738605eb6d1e7a62fe4e4', target : 'kpsz.ru' ],
            4: [ headRemote : '23be7603d8492f8499ba5a2389e567dd49afff37', target : 'kzkt45.ru' ],
            5: [ headRemote : '6d4955c0a90ba7ff59314057ac53ccee251e632a', target : 'nekeng.ru' ],
            6: [ headRemote : 'a4e8b6553ceeaefc03175f5a44b3a0be1065255a', target : 'vargashi45.ru' ],
        ]
        stage('Scan') {
            list.each { key, value ->
                trackingDocDir=baseDir+list[key].target
                gitHeadRemote=list[key].headRemote
                stage('Remote SSH') {
                    sshCommand remote: remote, command: "echo -e \"${remote.password}\" | sudo -S bash -c \"if [ -d \"${trackingDocDir}\" ]; then true; else false; fi;\""
                }
                stage('Check Git') {
                    gitHeadLocal = sshCommand remote: remote, command: "echo -e \"${remote.password}\" | sudo -S bash -c \"cd ${trackingDocDir} && git rev-parse HEAD\""
                    print gitHeadLocal
                    print gitHeadRemote
                }
                stage('Check Status')
                {
                    gitStatusLocal = sshCommand remote: remote, command: "echo -e \"${remote.password}\" | sudo -S bash -c 'if [ -z \"\$(cd /opt/DISK/projects/httpd/ite-ng.ru/ && git status | grep 'clean')\" ] ; then  echo \"attack\"; else echo \"clean\"; fi;'"
                    print gitStatusLocal
                }
                if ((gitHeadLocal!=gitHeadRemote)||(gitStatusLocal=='attack')) {
                    gitHeadLocal = sshCommand remote: remote, command: "echo -e \"${remote.password}\" | sudo -S bash -c \"cd ${trackingDocDir} && git reset && git checkout . && git clean -fdx\""
                    currentBuild.result = 'SUCCESS'
                    echo "RESULT: ${currentBuild.result}"
                    stage('Send email') {
                        def jobName = currentBuild.fullDisplayName
                        def subject = "[Jenkins] ${jobName}"
                        def body = "GIT Attack detected ${trackingDocDir}<br>"
                        mail(bcc: '', cc: '', charset: 'UTF-8', mimeType: 'text/html', replyTo: '',
                        body: body,
                        subject: subject,
                        to: mailRecipients
                        )
                    }
                }
            }
        }
    } catch(Exception e) {
        stage('Report') {
            currentBuild.result = 'UNSUCCESS'
            echo "RESULT: ${currentBuild.result}"
        }
        stage('Send email') {
            def jobName = currentBuild.fullDisplayName
            def subject = "[sites check failed][FAILED] ${jobName}"
            def body = "BUILD: ${currentBuild.result} RESULT:FAILED"
            mail(bcc: '', cc: '', charset: 'UTF-8', mimeType: 'text/html', replyTo: '',
            body: body,
            subject: subject,
            to: mailRecipients
            )
        }
    }
}
