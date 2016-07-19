#Introduction
Grorchestrator is an docker orchestration tool designed using Groovy.It abstracts away interaction with a Docker Daemon to a json configuration file and a few commands.it is designed to be used in CD pipelines.

#Installation.  
1.Download the distro.  
2.Extract the archive.  
3.Set the `$GROR_HOME` env variable to the place where you have extracted the archive.  
4.Adjust the `$PATH` to include this: `export PATH=$PATH:$GROR_HOME/bin`  


#Usage.  
1.Create a `gror.json` file where you want to store the environment configurations.The contents should be as described in the documentation.  
2.To pull a image exec `gror pull <your_instance_name> <tag>`.  
3.To run a container exec `gror run <your_instance_name> <tag>`.  
4.To kill a container exec `gror kill <your_instance_name>`.  


#Knobs  
Set the following environment variables in your gror server shell.  
`registry_username`  : username for the registry.Maps to `DockerHubAuth.username`.  
`registry_email`: email for the registry.Maps to `DockerHubAuth.email`.  
`registry_password` : password for the registry.Maps to `DockerHubAuth.password`.  
`registry_auth` : auth for the registry.Maps to `DockerHubAuth.auth`.Let it default to `""`.   

#Current features:  
1.Port mappings from host to container.  
2.Volume mappings from host to container. 
3.Adding extra hosts to the container.  
4.Pulling an image from docker hub.  
5.Running a container.  
6.Killing a container.  
