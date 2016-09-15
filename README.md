# REST-Showcase

This repository contains two main components, the first is REST server and the second is its REST client which demonstrates variety of HTTP requests (i.e. GET, POST, PUT, DELETE) with XML and JSON data format. 

## How does it work?

The REST server is deployed on [Heroku](https://www.heroku.com/) platform which is publicly available in the following URL https://introsde-181499.herokuapp.com/sdelab. It has some resources that can be accessed by clients. All the resources are stored in SQLite database (i.e. healthprofile.sqllite). To get all list of the person in the database for example, you can request the following URI https://introsde-181499.herokuapp.com/sdelab/person. 

The REST client makes some HTPP requests to the REST server with various scenarios, either by varying HTTP requests methods e.g. GET, POST, PUT and DELETE as well as varying the data format, both XML and JSON. In the processes, there are involved marshalling and unmashalling activities as described in [XMLJSON-Marshalling](https://github.com/rudiansen/XMLJSON-Marshalling). All the outputs of the requests will be printed in the terminal and logged into client-server.log file.

## How to run the program?

To test either REST server and its client are running well, let's try to run the REST client. Clone this repository onto your local machine by following below steps:

1. `git clone https://github.com/rudiansen/REST-Showcase.git`
2. `cd REST-Showcase/introsde-rest-client`
3. `ant execute.client`

By executing above commands, you will see the results of the REST invocation both in the terminal and in a new created file client-server.log.

PS: To be able to execute this command, you are expected to have ANT set on your machine.



  