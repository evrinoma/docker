pipeline {
    agent any
    environment {
        PASS = 'pass'
        USER = 'user'
        HOST = '172.20.1.161'
        SCRIPT = '/opt/docker/scripts/build.deferred.prod'
    }
   stages {
      stage('Run') {
         steps {
             sh 'sshpass -p ${PASS} ssh -o "StrictHostKeyChecking no" -p 749 -n ${USER}@${HOST} "echo -e \'${PASS}\' | sudo -S bash -c \'nohup ${SCRIPT} > /dev/null 2>&1 &\'\"'
         }
      }
   }
   post {
        always {
             mail to: 'nikolns@ite-ng.ru',
             subject: "[Jenkins] ${env.JOB_BASE_NAME}",
             body: "GLOBAL DOCKER BUILD: SUCCESS"
        }
    }
}

