pipeline {
    agent any
    environment {
        PW = 'CoolCanvas19.'
        USER_NAME = 'user8'
        BASE_URL = 'https://jira.codecool.codecanvas.hu'
        GRID_URL = 'https://selenium:CoolCanvas19.@seleniumhub.codecool.codecanvas.hu/wd/hub'
        WAIT = 10
    }
    stages {
        stage('Clean up') {
            steps {
                cleanWs()
            }
        }
        stage('Set up Git') {
            steps {
                git branch: 'master',
                    url: 'https://github.com/mpanka/JiraRegressionProject'
            }
        }
        stage('Parallel tests') {
            parallel {
                stage('run with chrome') {
                    environment {
                        BROWSER = 'chrome'
                    }
                    steps {
                        sh 'mvn test'
                    }
                    post {
                        always {
                            junit allowEmptyResults: true,
                            testResults: '**/target/surefire-reports/*.xml'
                        }
                    }
                }
                stage('run with firefox') {
                    environment {
                        BROWSER = 'firefox'
                    }
                    steps {
                        sh 'mvn test'
                    }
                    post {
                        always {
                            junit allowEmptyResults: true,
                            testResults: '**/target/surefire-reports/*.xml'
                        }
                    }
                }
            }
        }
    }
}