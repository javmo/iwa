pipeline {
  agent any
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