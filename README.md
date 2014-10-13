Novelty Search for Test Data Generation
=================================
Goal
-----
Testing of JS & Java code generated from Haxe
Instructions
-----
####System Under Test (Services)
- JS version : SystemUnderTest/generatedjs.js 
- Java version : haxe.root/Functions.java
- Cpp version : ./cpp (not already used)

####Services Interface
- haxe.root/interface.java

####Running
- Commons.java : Contains the SUT & Interface name described above.<br><br>
- Main.java : start exection taking into account the common parameters.<br><br>
- NoveltyGeneration.java :<br>
  
  1- methods=getMethods(interfaceName) : Load the services names from the java interface generated from Haxe<br><br>
  2- TestSequence=newPopulation(): Generate the initial population of test data
