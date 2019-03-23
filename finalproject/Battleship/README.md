## CSCI2020U Final Project
Repository Link: https://github.com/ahmed-nadeem/csci2020u_HotSauce/tree/master/finalproject/Battleship

### Group Members:
Ahmed Nadeem, Adunife Kizito Okoye, & Rutvik Desai

*Each group member contributed equally to the project*

### Battleship

#### Description
This application replicates 'Battleship', a two-player guessing game. Two users connect to one server. Communication is based on sockets, and the GUI is made with JavaFX. 


#### Code organization
1. Client - client code
2. Server - server code
3. Shared - shared part of code, data model and communication protocol

The project was created as a gradle multi-project build, and the code was written in IntelliJ IDE.
  
#### How to run
* Download/clone the repository, and build the project using gradle to create jar files for the server and client
* Execute the server first, and then the client (create two instances of the client, for the two players)
* Enter a port number for the server (a number after 1024), e.g. 4444, and then click "Start Server"
* Enter the same host address for each client and the port number used in the server, e.g. 127.0.0.1 & 4444, and then click "Connect"
* If succesful, players can start setting up their ships and playing against each other until someone wins

##### Server
    ./gradlew buildServerJar
    java -jar  ./Server/build/jfx/app/BattleshipServer.jar
    
##### Client
    ./gradlew buildClientJar
    java -jar  ./Client/build/jfx/app/BattleshipClient.jar


