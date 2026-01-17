def call(){
    pipeline {
        agent any
        stages {
            stage('build'){
                steps{
                    script{
                        sh """
                            echo "building the code"
                            java -version
                            mvn -version
                            #export JAVA_HOME="/datadisk/soft/jdk-21.0.9/bin/"
                            #echo "${JAVA_HOME}"
                            mvn clean install
                        """
                    }
                }
            }
            stage('static code analysis'){
                steps{
                    script{
                        println "static code analysis using Sonar"
                    }
                }
            }
            stage('build docker image and push to artifactory'){
                steps{
                    script{
                        println "build docker image and push"
                        sh """
                            docker build -t seh-students:0.0.1-RELEASE
                            docker tag seh-students:0.0.1-RELEASE summitjoshi/seh-students:0.0.1
                            docker push summitjoshi/seh-students:0.0.1
                        """
                    }
                }
            }
            stage('push helm packages to artifactory'){
                steps{
                    script{
                        println "push Helm packages to artifactory"
                        withCredentials([usernamePassword(credentialsId: 'artifactory-creds', usernameVariable: 'ART_USER', passwordVariable: 'ART_PASS')]) {
                        sh """
                            # login to Artifactory Helm registry
                            helm registry login -u $ART_USER -p $ART_PASS trialp04su6.jfrog.io
                            tar -czvf seh-students-0.0.1.tgz manifestbuild
                            curl -u $ART_USER:$ART_PASS \
                            -T seh-students-0.0.1.tgz \
                            "https://trialp04su6.jfrog.io/artifactory/seh-helm/seh-students/release/0.0.1/seh-students-0.0.1.tgz"
                        """
                        }
                    }
                }
            }  
        }
    }
}