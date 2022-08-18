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
        sh 'docker login'
      }
    }

    stage('Push') {
      steps {
        sh 'docker push javmo94/iwa:latest'
        sh 'docker logout'
      }
    }

  }
}