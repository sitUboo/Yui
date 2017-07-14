
stage('checkout'){
   // checkout code
   node {
    git credentialsId: '2da9761c-bd84-4d3c-8a94-350a4897ce72', url: 'git@github.com:sitUboo/notification.git'

    sh 'cat buildInfo.txt'
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
