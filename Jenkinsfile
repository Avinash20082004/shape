pipeline {
    agent any

    tools {
        jdk 'JDK-17'
        gradle 'Gradle9'
    }

    stages {

        stage('Checkout') {
            steps {
                // Checkout the source code from SCM
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // Build the project
                bat 'gradlew.bat clean bootJar'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // Inject SonarQube environment variables
                withSonarQubeEnv('SonarQube') {
                    // Use token securely from Jenkins credentials
                    withCredentials([
                        string(credentialsId: 'sonar-token-authentication', variable: 'SONAR_TOKEN')
                    ]) {
                        // Run Sonar analysis
                        bat 'gradlew.bat sonar -Dsonar.token=%SONAR_TOKEN% -Dsonar.gradle.skipCompile=true'
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                // Wait for SonarQube Quality Gate result
                timeout(time: 10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: false
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished.'
        }
        success {
            echo 'Build and analysis completed successfully!'
        }
        failure {
            echo 'Pipeline failed. Check logs!'
        }
    }
}
