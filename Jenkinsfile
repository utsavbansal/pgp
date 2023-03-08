pipeline {
    agent any
	
	  tools
    {
       maven "Maven"
    }
 stages {
      stage('checkout') {
           steps {
             
                git branch: 'master', url: 'https://github.com/utsavbansal/pgp.git'
             
          }
        }
        
       stage('Docker Build and Tag') {
           steps {
              dir('server/SAIAFarming') {
                sh 'docker build -t spring-SAIAFarming .'
                sh 'docker docker compose up' 
              //  sh 'docker tag samplewebapp utsavbansal/samplewebapp:latest'
                //sh 'docker tag samplewebapp nikhilnidhi/samplewebapp:$BUILD_NUMBER'
                }
               
          }
        }

	 stage('Execute Maven') {
	 steps {
			 dir('SAIAFarm/') {
 		   		// some block
 			
             			sh 'mvn clean'
                		sh 'mvn package'
                		sh 'mvn install'             
          			}
			
	 	dir('server/SAIAFarming') {
 		  
             			sh 'mvn clean'
                		sh 'mvn package'
                		sh 'mvn install'             
          		}
          	}
			
		
           
        }
        
     
 // stage('Publish image to Docker Hub') {
          
   //         steps {
    //    withDockerRegistry([ credentialsId: "dockerHub", url: "" ]) {
      //    sh  'docker push utsavbansal/samplewebapp:latest'
        //  sh  'docker push nikhilnidhi/samplewebapp:$BUILD_NUMBER' 
       // }
                  
         // }
       // }
     
    //  stage('Run Docker container on Jenkins Agent') {
             
      //      steps 
		//	{
        //        sh "docker run -d -p 8003:8080 utsavbansal/samplewebapp"
 
          //  }
        //}
// stage('Run Docker container on remote hosts') {
  //           
      //      steps {
    //            sh "docker -H ssh://jenkins@192.168.135.241 run -d -p 8003:8080 utsavbansal/samplewebapp"
 
        //    }
        //}
    }
	}
    
