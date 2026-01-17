def call(){
    pipeline {
        agent go4gst-sit
        stages {
            stage('build'){
                steps{
                    script{
                        sh """
                            echo "building the code"
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
                        docker build -t seh-students:0.0.1-RELEASE
                        docker tag seh-students:0.0.1-RELEASE summitjoshi/seh-students:0.0.1
                        docker push summitjoshi/seh-students:0.0.1
                    }
                }
            }
            stage('push helm packages to artifactory'){
                steps{
                    script{
                        println "push Helm packages to artifactory"
                     }
                }
            }
            
        }
    }
}