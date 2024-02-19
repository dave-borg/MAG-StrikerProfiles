pipeline {
    agent any
    environment {
        GITHUB_CREDENTIALS = credentials('GitHub-ssh-un-privatekey')
        DOCKER_IMAGE_NAME = 'dave-borg/mag-striker-profiles'
    }
    

    stages {
        
        stage('Checkout') {
            steps {
                git credentialsId: 'GitHub-ssh-un-privatekey', url: 'git@github.com:dave-borg/MAG-StrikerProfiles.git', branch: 'main'
            }
        }

        stage('Build Maven Project') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build(env.DOCKER_IMAGE_NAME)
                }
            }
        }

        
    }
}