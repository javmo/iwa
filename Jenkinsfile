pipeline {
  agent none
  stages {
    stage('Maven Install') {
      agent {
        docker {
          image 'maven:3.8.1-adoptopenjdk-11'
          args '-v /root/.m2:/root/.m2'
        }

      }
      steps {
        sh 'mvn -B -DskipTests clean package'
      }
    }

    stage('Docker Build & Push') {
      agent any
      environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-javmo94')
      }
      steps {
        sh 'docker build -t javmo94/iwa:latest .'
        echo 'Build image finished'
        sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
        sh 'docker push javmo94/iwa:latest'
        sh 'docker logout'
      }
    }

  }
}