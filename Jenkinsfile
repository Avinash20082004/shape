pipeline {
    agent any

    tools {
        jdk 'JDK-17'
        gradle 'Gradle9'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'gradlew.bat clean bootJar'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    withCredentials([
                        string(credentialsId: 'sonar-token-authentication', variable: 'SONAR_TOKEN')
                    ]) {
                        bat '''
                        gradlew.bat sonar ^
                         -Dsonar.projectKey=shape ^
                         -Dsonar.host.url=http://localhost:9000 ^
                         -Dsonar.token=%SONAR_TOKEN% ^
                         -Dsonar.gradle.skipCompile=true
                        '''
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 30, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }

    post {
        always {
            echo "Pipeline finished."
        }
        success {
            echo "Pipeline succeeded!"
        }
        failure {
            echo "Pipeline failed!"
        }
    }
}
