pipeline{
    agent any
  stages{
    stage("build"){
      steps{
        sh './gradlew build'
      }
    }
    stage("Sonar Analysis") {
            tools {
               jdk 'java-11'
            }
            environment {
                scannerHome= tool 'SonarQube Scanner 4.8.0.2856'
                projectName= "sampleSCM"
            }
            steps {
                withSonarQubeEnv('Sonar_12') {
                   
                    sh '''${scannerHome}/bin/sonar-scanner -Dsonar.java.binaries=build/classes/java \
                    -Dsonar.projectKey=$projectName -Dsonar.sources=.'''
                }
            }
        }
   }
}
