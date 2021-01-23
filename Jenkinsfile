pipeline {
    agent any
    environment {
            PASSWORD = 'CoolCanvas19.'
            SELENIUM_PASSWORD = 'CoolCanvas19.'
            USERNAME = 'user8'
            BASE_URL = 'https://jira.codecool.codecanvas.hu'
            GRID_URL = 'https://selenium:CoolCanvas19.@seleniumhub.codecool.codecanvas.hu/wd/hub'
            WAIT = 10
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
                         sh 'mvn test'
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
                         sh 'mvn test'
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