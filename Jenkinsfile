pipeline {
    agent any
    parameters {
                string(name: 'browserToRun', defaultValue: 'both', description: 'Browsers to run: Both, Chrome, Firefox')
                string(name: 'chrome', defaultValue: 'chrome', description: 'Chrome browser')
                string(name: 'firefox', defaultValue: 'firefox', description: 'Firefox browser')
    }
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
                         script {withCredentials([
                               usernamePassword(
                               credentialsId: 'jira-user8-credentials',
                               passwordVariable: 'pass',
                               usernameVariable: 'username')]) {
                                    echo 'Test phase with chrome: '
                                    sh "mvn test -DjiraUsername=$username -DjiraPassword=$pass -DseleniumPassword=$pass"
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
                         script {withCredentials([
                               usernamePassword(
                               credentialsId: 'jira-user8-credentials',
                               passwordVariable: 'pass',
                               usernameVariable: 'username')]) {
                                    echo 'Test phase with chrome: '
                                    sh "mvn test -DjiraUsername=$username -DjiraPassword=$pass -DseleniumPassword=$pass"
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
    }
    post {
        always {
            echo 'Cleanup phase: '
            cleanWs()
        }
    }
}