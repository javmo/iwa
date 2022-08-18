pipeline {
  agent {
    docker {
      image 'maven:3-alpine'
      args '-v /root/.m2:/root/.m2'
    }

  }
  stages {
    stage('Build') {
      steps {
        echo 'Starting Build Step'
        sh 'mvn -B -DskipTests clean package'
        echo 'Build step complete'
      }
    }

    stage('Docker Build') {
      steps {
        sh 'docker build -t javmo94/iwa:latest .'
      }
    }

  }
}