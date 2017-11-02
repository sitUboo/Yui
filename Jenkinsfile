println "The build is " + env.BUILD_NUMBER

stage('checkout'){
   //  checkout code
   node {
           properties([pipelineTriggers([[$class: 'GitHubPushTrigger']])])
##           git credentialsId: '3ef19ff4-e2c3-42b3-9e6c-4d0940bb6921', url: 'git@github.com:sitUboo/Yui.git', branch: env.BRANCH_NAME 
           println "The build is using branch " + env.BRANCH_NAME
           sh "echo running build..."
//           [$class: 'GitHubCommitStatusSetter',
//             commitShaSource: [$class: 'ManuallyEnteredShaSource', sha: gitSha],
//             reposSource: [$class: 'ManuallyEnteredRepositorySource', url: 'https://github.com/cloudbees/customers' ],
//             statusBackrefSource: [ $class: 'BuildRefBackrefSource' ],
//             contextSource: [$class: 'ManuallyEnteredCommitContextSource', context: 'Quality Checks Passed' ],
//             statusResultSource: [ $class: 'ConditionalStatusResultSource', results: [[$class: 'AnyBuildResult', message: 'Succeeded', state: 'SUCCESS']] ]
      //  ]
   }
}

stage('build'){

   node {
      parallel (
        phase1: { sh "echo p1; sleep 20s; echo phase1" },
//        phase2: { sh "echo p2; sleep 40s; echo phase2" }
      )
      println "The build is " + env.BUILD_NUMBER
      sh "echo run this after both phases complete"
//      step([$class: 'GitHubSetCommitStatusBuilder', statusMessage: [state: 'success', content: 'Compile Passed']])
   }
}
