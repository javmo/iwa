pipeline {
  agent {
    docker {
      image 'maven:3.8.1-adoptopenjdk-11'
      args '-v /root/.m2:/root/.m2'
    }

  }
stage('Build and Deploy') {
        withMaven(maven: 'M3') {
            // skip deploy to nexus to ease tests, don't require a maven settings file with nexus credentials
            // sh "mvn clean install"

            sh "mvn clean install"
        }
    }

  }
}