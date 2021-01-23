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
                    //credentialsId:'2f24bb349e96511a7b431f54d86626aea32bb71f',
                    url: 'https://github.com/mpanka/JiraRegressionProject'
            }
        }
        stage('Parallel tests') {
            parallel {
                stage('Chrome') {
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
                stage('Firefox') {
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