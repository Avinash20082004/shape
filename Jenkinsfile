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
                // Clean and build your Spring Boot project
                bat 'gradlew.bat clean bootJar'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // Inject SonarQube environment variables
                withSonarQubeEnv('SonarQube') {
                    withCredentials([
                        string(credentialsId: 'sonar-token-authentication', variable: 'SONAR_TOKEN')
                    ]) {
                        // Run SonarQube Gradle task with proper server URL and token
                        bat "gradlew.bat sonarqube -Dsonar.host.url=http://192.168.1.3:9000 -Dsonar.token=%SONAR_TOKEN% -Dsonar.gradle.skipCompile=true -Dsonar.projectKey=shape"
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                // Wait for SonarQube Quality Gate result
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

