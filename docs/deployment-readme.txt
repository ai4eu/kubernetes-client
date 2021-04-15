===================================================================================
    Copyright (C) 2019 Fraunhofer Gesellschaft. All rights reserved. 
===================================================================================
This Acumos software file is distributed by Fraunhofer Gesellschaft
under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
      http://www.apache.org/licenses/LICENSE-2.0
This file is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and limitations under the License.
===============LICENSE_END==========================================================

                    Documentation of Deployment of Kubernetes Client

The downloaded solution.zip folder contain one directory named microservices which contain .proto file 
corresponding container of a composite pipeline. Main directory contain dockerinfo.json file which is used for orchestration. 
This folder contain a kubernetes-client-script.py file which is responsible for all the deployments and it also create one extra 
service for each node of a pipeline “named_webui” for web interface. This python script first find the free ports and then assign 
as a node-port to each service then update the node port in dockerinfo.json file. 

Step 1: 
Extract the downloaded solution.zip folder. 

Step 2:
make sure python yaml is installed
Now run the python script kubernetes-client-script.py. You have to provide namespace as parameter to the script.

Step 3:
You need to verify that deployments and services are deployed successfully. 
                             kubectl -n namespace get all
After running this command on terminal, you have to take a look that corresponding pods are running successfully in given namespace.
    

Moreover in the next versions we are considering the persistance volume and also resource limitations. 
Security stuff also taken into account. 
