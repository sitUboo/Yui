pipeline {
    agent any
    options {
        timeout(time: 25, unit: 'SECONDS') 
    }
    stages {
      stage('checkout'){
              checkout([$class: 'GitSCM', doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'PathRestriction', excludedRegions: 'Jenkinsfile']], gitTool: 'Default', submoduleCfg: [], userRemoteConfigs: [[credentialsId: '4cd42b1e-4f9c-4997-9901-658bb830a3ef',url: 'git@github.com:sitUboo/Yui.git']]])
           }
      }

      stage('build'){
           sh "echo I am building.. all the while our api limit has been reached...slowly... "
    }
  
    post {
       success {
        setBuildStatus("Build succeeded", "SUCCESS");
       }
       failure {
        setBuildStatus("Build failed", "FAILURE");
       }
    }
  }
}
