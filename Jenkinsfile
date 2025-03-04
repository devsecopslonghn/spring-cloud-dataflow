pipeline {
    agent any

    stages {
        stage('Build Project in Docker') {
            steps {
                script {
                    
                    // Kiểm tra Docker trước khi build
                    sh 'docker version'

                    // Chạy Maven bên trong container Docker
                    withDockerContainer(image: 'maven:3.9.6-eclipse-temurin-17', args: '-u root:root -v ${WORKSPACE}:/workspace -v /root/.m2:/root/.m2') {
                        sh '''
                            echo '📂 Checking mounted files in container:'
                            ls -lah /workspace

                            cd /workspace 
                            mvn install -s .settings.xml -DskipTests -am -pl :spring-cloud-dataflow-server,:spring-cloud-dataflow-composed-task-runner
                        '''
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Kiểm tra Docker trước khi build
                    sh 'docker version'

                    // Build Docker Image
                    sh '''
                        cd spring-cloud-dataflow-server
                        docker build -t my-app:latest .
                    '''
                }
            }
        }
    }

    post {
        success {
            echo '✅ Build & Docker image created successfully!'
            cleanWs()
        }
        failure {
            echo '❌ Build or Docker image creation failed!'
        }
    }
}
