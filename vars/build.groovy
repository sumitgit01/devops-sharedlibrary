def call(){
    pipeline {
        agent go4gst-sit
        stages {
            stage('build'){
                steps{
                    script{
                        sh """
                            echo "building the code"
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