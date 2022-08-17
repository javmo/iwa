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
        sh 'mvn clean install'
      }
    }

    stage('Docker Build') {
      agent any
      steps {
        sh 'docker build -t shanem/spring-petclinic:latest .'
      }
    }

  }
}