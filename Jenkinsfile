pipeline {
  agent {
    docker {
      args '-v /root/.m2:/root/.m2'
      image 'maven:3.6.0-jdk-11-slim'
    }
    node {
      label 'docker'
    }

  }
  stages {
    stage ('Verify Tool'){
      steps {
          parallel (
            docker: { sh "docker -v" }
          )
      }
    }
    stage('Build') {
      steps {
        echo 'Starting Build Step'
        sh 'mvn -B -DskipTests clean package'
        echo 'Build step complete'
      }
    }

  }
}