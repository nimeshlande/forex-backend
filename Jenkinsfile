pipeline{
    agent any
    tools{maven 'MAVEN_HOME'}
    stages{
        stage('Checkout'){
            steps{
                git branch:'main',url:'https://github.com/nimeshlande/forex-backend.git'
            }
        }
        stage('Build'){
            steps{
                bat 'mvn compile'
            }
        }
        stage('Test'){
            steps{
                bat 'mvn test'
            }
        }
        stage('Package'){
            steps{
                bat 'mvn package'
            }
        }
        stage('Deploy'){
            steps{
                echo 'deployed...'
            }
        }  
    }
}
