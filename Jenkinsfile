stage('checkout'){
   node {
      properties([pipelineTriggers([[$class: 'GitHubPushTrigger']])])
      checkout([$class: 'GitSCM', doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'PathRestriction', excludedRegions: 'Jenkinsfile']], gitTool: 'Default', submoduleCfg: [], userRemoteConfigs: [[credentialsId: '4cd42b1e-4f9c-4997-9901-658bb830a3ef',url: 'git@github.com:sitUboo/Yui.git']]])
   }
}

stage('build'){
   timeout(time: 1, unit: 'MINUTES') {
       node {
           sh "echo I am building...slowly... "
       }
   }
}
