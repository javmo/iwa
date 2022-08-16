pipeline {
  agent {
    dockerfile {
      filename 'https://bitbucket.org/javmo94/iwa/src/master/Dockerfile'
    }

  }
  stages {
    stage('Build') {
      agent any
      steps {
        sh 'mvn -B'
      }
    }

  }
}