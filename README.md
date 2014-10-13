Novelty Search for Test Data Generation
=================================
Goal
-----
Testing of JS & Java code generated from Haxe
Instructions
-----
####System Under Test (Services)
- JS generated version : SystemUnderTest/generatedjs.js 
- Java generated version : haxe.root/Functions.java
- Cpp generated version : ./cpp (not already used)

####Services Interface
- Java Generated version : haxe.root/interface.java

####Running
- Commons.java : Contains the SUT & Interface name described above.<br><br>
- Main.java : start exection taking into account the common parameters.<br><br>
- NoveltyGeneration.java :<br>
  
  1- methods=getMethods(interfaceName) : Load the services names from the java interface generated from Haxe<br><br>
  2- TestSequence=newPopulation(): Generate the initial population of test data<br><br>
NB: <br>
A test sequence (population) is a set of test suites <br>
A test suite (individual or solution) is a set of test cases <br>
A test case is a random call to a service with random data<br><br>
To do so, the method newPopulation() generated random test suites and add them to a test sequence which represents the whole population.<br>
A test suite (solution) is generated and exectued like that : 
  
