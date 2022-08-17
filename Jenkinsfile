pipeline {
  agent {
    node {
      label 'agent1'
    }

  }
  stages {
    stage('Clone') {
      steps {
        git(url: 'https://bitbucket.org/javmo94/iwa', branch: 'master')
      }
    }
    stage('Build') {
      parallel {
        stage('Build') {
          steps {
            sh 'mvn -B -DskipTests clean package'
          }
        }
        stage('P1') {
          steps {
            sh '''date
echo run parallel!!'''
          }
        }
        stage('P2') {
          steps {
            sh '''date
echo run parallel!!'''
          }
        }
      }
    }
    stage('Packaging') {
      steps {
        sh '''pwd
cd ./docker
docker build -t javmo94/iwa .
docker images
'''
      }
    }

    }
  }
}