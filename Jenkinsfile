pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean'
            }
        }

        stage('Parallel tests') {
            parallel {
                stage('run with chrome') {
                    when {
                         expression { params.browserToRun == 'both' || params.browserToRun == 'chrome' }
                         }
                    steps {
                        script {withCredentials([usernamePassword(
                              credentialsId: 'jira2-admin',
                              passwordVariable: 'pass',
                              usernameVariable: 'user'),
                              usernamePassword(
                              credentialsId: 'jira-user6-credentials',
                              passwordVariable: 'sel_pass',
                              usernameVariable: 'username')]) {
                                   echo 'Test phase with chrome: '
                                   sh "mvn test -DUSER=$user -DPASS=$pass -DSEL_PASS=$sel_pass"
                              }
                        }
                    }
                    post {
                        always {
                            junit allowEmptyResults: true,
                            testResults: 'target/surefire-reports/*.xml'
                        }
                    }
                }
                stage('run with firefox') {
                    when {
                         expression { params.browserToRun == 'both' || params.browserToRun == 'firefox' }
                         }
                    steps {
                        script {withCredentials([usernamePassword(
                              credentialsId: 'jira2-admin',
                              passwordVariable: 'pass',
                              usernameVariable: 'user'),
                              usernamePassword(
                              credentialsId: 'jira-user6-credentials',
                              passwordVariable: 'sel_pass',
                              usernameVariable: 'username')]) {
                                   echo 'Test phase with firefox: '
                                   sh "mvn test -DUSER=$user -DPASS=$pass -DSEL_PASS=$sel_pass"
                              }
                        }
                    }
                    post {
                        always {
                            junit allowEmptyResults: true,
                            testResults: 'target/surefire-reports/*.xml'
                        }
                    }
                }
            }
        }
    post {
        always {
            echo 'Cleanup phase: '
            cleanWs()
        }
    }
}