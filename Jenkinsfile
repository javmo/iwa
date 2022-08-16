pipeline {
  agent {
    docker {
      image 'adoptopenjdk/openjdk11'
      args 'JAR_FILE=target/*.jar'
    }

  }
  stages {
    stage('Build') {
      steps {
        sh 'mvn -B -DskipTests clean package'
      }
    }

  }
}