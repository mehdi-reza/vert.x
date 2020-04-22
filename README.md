#### Run the App

- ``mvn clean package exec:java@run  ``  
Utilitizes maven dependencies  


- ``mvn clean package exec:exec@fat  ``  
This runs generated fat jar using maven shade plugin  


- ``mvn clean package && java -jar target/learn-0.0.1-SNAPSHOT-fat.jar``  
For this you dont need maven after packaged  


- `` Launch java class learn.VertxVerticle file from IDE``  


Endpoint "/echo" accepts post json body, writes it to event bus and receives reply from queue handler.

``curl http://localhost:8080/echo -d'{"hello":"world"}'`` 