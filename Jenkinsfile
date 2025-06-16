pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build with Maven') {
            steps {
                echo "🔨 Running mvn clean install..."
                sh "mvn clean install -B -pl \\!spring-cloud-dataflow-common-test-docker-junit5 -am"
            }
        }

        stage('List generated JARs') {
            steps {
                echo "📦 Danh sách file JAR được tạo:"
                // Tìm tất cả .jar trong workspace và in ra
                sh '''
                  find . -type f -name "*.jar" | sed "s|^| - |"
                '''
            }
        }
    }

    post {
        success {
            echo "✅ Build và liệt kê JAR hoàn tất."
        }
        failure {
            echo "❌ Build thất bại!"
        }
    }
}
