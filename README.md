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
To do so, the method newPopulation() generated random test suites and add them to a test sequence which represents the whole population.<br><br>
A test suite (solution) is generated and exectued like this way : <br>
-We define a random size for a test suite<br>

```
testSuiteSize=(int) (Math.random() * CommonParameters.MAX_SEQUENCE )+1;
  ```
  <br>
-We generate random data taking into account the datatypes of the service inputs. The randomData class is responsible for this generation<br>

```
data=new RandomData().getDataGenerated(methods[posMethod]);
```
<br>
 -We exectute the generated data on top of different generated versions Js & Java<br>
 
 ``` 
outputs=new Vector<Object>();
Object o =methods[posMethod].invoke(javaScriptEngine(), data);
Object o1=jsScriptEngine().invokeFunction(methods[posMethod].getName() , data);
```
<br>
jsScriptEngine() & javaScriptEngine() are two methods that use the reflection API of Java to invoke a method with its inputs automatically. <br><br>
-We get the different outputs

 ``` 
 //don't get methods with void return value
	    if(o!=null&&o1!=null){
	    outputs.add(o);
	    outputs.add(o1);}
``` 

  3- We save the new test case with its relative outputs + the updated fitness value

 ``` 
TestCase tc=new TestCase (methods[posMethod],data,outputs);
 ``` 
 -We save in the same way test suite in the current population (sequence)
 
  ``` 
 TestSequence.add(RandomTestSuite);
  ``` 
  4- We display all the generated test test suites and the best ones those that have generated bugs
  
     
       //display all the generated test test suites
		displayTestSequence(TestSequence);
		
		//catch the best test suites
		updateBestTestSuites(TestSequence);

		//display the best test suites
		displayBestTestSuites(TestSequence);
		 