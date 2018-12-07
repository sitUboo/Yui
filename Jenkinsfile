pipeline {
    agent any
    options {
        timeout(time: 25, unit: 'SECONDS') 
    }
    stages {
      stage('checkout'){
          steps{
              checkout([$class: 'GitSCM', doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'PathRestriction', excludedRegions: 'Jenkinsfile']], gitTool: 'Default', submoduleCfg: [], userRemoteConfigs: [[credentialsId: '4cd42b1e-4f9c-4997-9901-658bb830a3ef',url: 'git@github.com:sitUboo/Yui.git']]])
           }
      }
      stage('build'){
          steps{
             sh "echo I am building.. all the while our api limit has been reached...slowly... "
          }
      }
    }

    post {
         success {
            step([
              $class: "GitHubCommitStatusSetter",
               reposSource: [$class: "ManuallyEnteredRepositorySource", url: "https://github.com/sitUboo/Yui"],
               contextSource: [$class: "ManuallyEnteredCommitContextSource", context: "ci/jenkins/build-status"],
               errorHandlers: [[$class: "ChangingBuildStatusErrorHandler", result: "UNSTABLE"]],
               statusResultSource: [ $class: "ConditionalStatusResultSource", results: [[$class: "AnyBuildResult", message: "Build succeeded", state: "SUCCESS"]] ]
            ]);
         }
    }
}
