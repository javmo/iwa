#!groovy
pipeline{
  agentnonestages{
    stage('Maven Install'){
      agent{
        docker{
          image'maven:3.5.0'
        }
      }steps{
        sh'mvn clean install'
      }
    }stage('Docker Build'){
      agentanysteps{
        sh'docker build -t shanem/spring-petclinic:latest .'
      }
    }
  }
}