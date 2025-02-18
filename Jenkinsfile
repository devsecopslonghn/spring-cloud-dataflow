pipeline {
    agent any

    parameters {
        choice(name: 'skipptest', choices: ['yes', 'no'], description: 'Skip tests?')
        choice(name: 'ismvn', choices: ['yes', 'no'], description: 'Use mvn or mvnw?')
    }

    stages {
        stage('Prepare Environment') {
            steps {
                script {
                    MVN_CMD = params.ismvn == 'yes' ? 'mvn' : './mvnw'
                    SKIP_TESTS = params.skipptest == 'yes' ? '-DskipTests' : ''
                    MAVEN_OPTS = "-Xmx512m -Xms256m"
                    MVN_THREADS = "-T 0.5C"

                    echo "Using Maven Command: ${MVN_CMD}"
                    echo "Skip Tests: ${SKIP_TESTS}"
                    echo "Maven Memory Limit: ${MAVEN_OPTS}"
                    echo "Using Threads: ${MVN_THREADS}"
                }
            }
        }


        stage('Build Project') {
            steps {
                script {
                    sh "export MAVEN_OPTS='${MAVEN_OPTS}' && ${MVN_CMD} install -s .settings.xml ${SKIP_TESTS} ${MVN_THREADS} -am -pl :spring-cloud-dataflow-server,:spring-cloud-skipper-server,:spring-cloud-dataflow-composed-task-runner"
                }
            }
        }
    }

    post {
        success {
            echo '✅ Build completed successfully!'
        }
        failure {
            echo '❌ Build failed. Check the logs for details.'
        }
        always {
            echo "🧹 Cleaning up workspace..."
            cleanWs()
        }
    }
}
