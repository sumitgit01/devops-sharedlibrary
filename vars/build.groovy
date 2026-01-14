pipeline {
    agent any
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
        
    }
}