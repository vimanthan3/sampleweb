pipeline{
    agent any
  stages{
    stage("Build"){
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
      stage ('Publish') {
            environment {
                NEXUS_URL = 'http://192.168.1.30:8081'
                NEXUS_REPO_GROUPID = 'com.logicfocus'
                NEXUS_REPO_ARTIFACT = 'sampleSCM'
                NEXUS_REPO = 'sampleSCM'
                CHECKSUM =''
            }
            steps {
                nexusArtifactUploader(
                    nexusVersion: "${nexusVersion}",
                    protocol: 'http',
                    nexusUrl: "${nexusURL}",
                    groupId: "$NEXUS_REPO_GROUPID",
                    version: "1.0.1",
                    repository: "$NEXUS_REPO",
                    credentialsId: 'nexus1',
                    artifacts: [
                        [artifactId: "$NEXUS_REPO_ARTIFACT",
                         classifier: '',
                         file: '/home/logicfocus/.jenkins/workspace/vimanthan/sampleSCM/build/libs/sampleSCM-2.0.jar',
                         type: 'jar']
                    ]
                )
            }
        }
   }
}
