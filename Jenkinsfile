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
        stage('Create Build Info') {
    environment {
        NEXUS_URL = 'http://192.168.1.30:8081'
        REPO_NAME = 'sampleSCM'
        GROUP_ID = 'com.logicfocus'
        ARTIFACT_ID = 'sampleSCM'
    } 
    steps {
        script {
            def buildGradleContent = readFile('build.gradle')
            def version = (buildGradleContent =~ /version\s+'([^']+)'/)[0][1] ?: '1.0.1'
            def artifactPath = "/${REPO_NAME}/${GROUP_ID.replace('.', '/')}/${ARTIFACT_ID}/${version}/${ARTIFACT_ID}-${version}.jar"
            def nexusChecksumUrl = "${NEXUS_URL}/service/rest/v1/components?repository=${REPO_NAME}&group=${GROUP_ID.replace('.', '/')}&name=${ARTIFACT_ID}&version=${version}"
            def artifactInfo = sh(script: "curl -s -u admin:lfadmin ${nexusChecksumUrl}", returnStdout: true).trim()
            def checksumValue = readJSON(text: artifactInfo).items[0].assets[0].checksum.sha1

            // Create buildData.json with build information
            sh """
                echo '{' > buildData.json
                echo '  "buildnumber": "${BUILD_NUMBER}",' >> buildData.json
                echo '  "buildurl": "${JOB_URL}",' >> buildData.json
                echo '  "group": "${GROUP_ID}",' >> buildData.json
                echo '  "checksum": "${checksumValue}",' >> buildData.json
                echo '  "artifact": "${ARTIFACT_ID}",' >> buildData.json
                echo '  "version": "${version}",' >> buildData.json
                echo '  "ext": "jar",' >> buildData.json
                echo '}' >> buildData.json
            """

            stash name: 'buildDataStash', includes: 'buildData.json'
                    unstash 'buildDataStash'
                    def buildData = readFile('buildData.json')
                    echo "Build Data: ${buildData}"
                    archiveArtifacts artifacts: 'buildData.json', onlyIfSuccessful: false
            
                }
            }
        }
   }
}
