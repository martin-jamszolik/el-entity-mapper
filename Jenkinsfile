pipeline {
  agent {
    docker {
      image 'maven:3-alpine'
    }

  }
  stages {
    stage('BUILD') {
      steps {
        script {
          echo 'Skipping DEV Restore'
        }

        script {
          sh "mvn clean install -f root/object-criteria-factory/pom.xml"
        }

      }
    }

    stage('Finished') {
      steps {
        echo 'Complete'
      }
    }

  }
}