pipeline {
  agent any
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

    stage('Docker Build') {
      steps {
        sh 'docker build -t javmo94/iwa:latest .'
      }
    }

    stage('Login') {
      steps {
        withCredentials(bindings: [usernamePassword(credentialsId: "${JENKINS_DOCKER_CREDENTIALS_ID}", usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
          sh 'sh \'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin\''
        }

      }
    }

    stage('Push') {
      steps {
        sh 'sh \'docker push javmo94/iwa:latest\''
        sh 'sh \'docker logout\''
      }
    }

  }
  environment {
    JENKINS_DOCKER_CREDENTIALS_ID = 'dockerhub-javmo94'
  }
}