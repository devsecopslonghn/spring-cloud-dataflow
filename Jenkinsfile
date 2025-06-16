pipeline {
    agent any

    // (Tuỳ chọn) Cấu hình Maven/JDK nếu bạn đã khai báo tool trong Jenkins
    tools {
        jdk    'jdk21'   // tên JDK bạn đã cấu hình
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build with Maven') {
            steps {
                echo "🔨 Running mvn clean install..."
                sh 'mvn clean install -B'
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
