pipeline {
  agent any
  stages {
    stage('BUILD') {
      agent any
      steps {
        script {
          echo 'Skipping DEV Restore'
        }

        script {
          sh "mvn clean install -f root/object-criteria-factor/pom.xml"
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