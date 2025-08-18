pipeline {
    agent {
        kubernetes {
            yaml '''
                apiVersion: v1
                kind: Pod
                spec:
                  serviceAccountName: default
                  containers:
                  - name: build-deploy
                    image: gradle:8-jdk21
                    command:
                    - sleep
                    args:
                    - 99d
                    volumeMounts:
                    - name: docker-sock
                      mountPath: /var/run/docker.sock
                  volumes:
                  - name: docker-sock
                    hostPath:
                      path: /var/run/docker.sock
            '''
        }
    }

    stages {
        stage('Setup') {
            steps {
                container('build-deploy') {
                    sh '''
                        apt-get update && apt-get install -y curl
                        curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/arm64/kubectl"
                        chmod +x kubectl && mv kubectl /usr/local/bin/
                    '''
                }
            }
        }

        stage('Build') {
            steps {
                container('build-deploy') {
                    sh './gradlew clean build jar'
                    archiveArtifacts artifacts: 'build/libs/*.jar'
                }
            }
        }

        stage('Deploy') {
            when { branch 'master' }
            steps {
                container('build-deploy') {
                    sh '''
                        kubectl apply -f k3s-manifests/sl-api-deployment.yaml
                        kubectl rollout restart deployment/sl-api-backend
                        kubectl rollout status deployment/sl-api-backend --timeout=300s
                    '''
                }
            }
        }
    }
}
