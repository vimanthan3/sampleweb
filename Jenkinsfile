pipeline{
    agent any
  stages{
    stage("build"){
      steps{
        sh './gradlew build'
      }
    }
    stage('Docker image'){
      steps{
        sh 'docker build -t sample:1.0.1 .'
          }
      }
   }
}
