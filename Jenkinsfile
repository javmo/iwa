#!/usr/bin/env groovy
pipeline { 
  agent { 
    node { 
      label 'docker'
    }
    node {
      label 'maven'
        docker {
          args '-v /root/.m2:/root/.m2'
          image 'maven:3.6.0-jdk-11-slim'
        }
    } 
  }
  stages {
    stage ('Checkout Code') {
      steps {
        checkout scm
      }
    }
    stage ('Verify Tools'){
      steps {
        parallel (
          node: { sh "mvn -v" },
          docker: { sh "docker -v" }
        )
      }
    }
    stage ('Build app') {
      steps {
        sh "mvn -B -DskipTests clean package"
      }
    }
    stage ('Test'){
      steps {
        sh "mvn test"
      }
    }
 
    stage ('Build container') {
      steps {
        sh "docker build -t javmo94/iwa:latest ."
        sh "docker tag javmo94/iwa:latest javmo94/iwa:v${env.BUILD_ID}"
      }
    }
    stage ('Deploy') {
      steps {
        input "Ready to deploy?"
        sh "docker stack rm iwa"
        sh "docker stack deploy iwa --compose-file docker-compose.yml"
        sh "docker service update iwa_server --image javmo94/iwa:v${env.BUILD_ID}"
      }
    }
    stage ('Verify') {
      steps {
        input "Everything good?"
      }
    }
    stage ('Clean') {
      steps {
        sh "mvn clean"
      }
    }
  }
}