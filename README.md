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

-More examples and information about the generated code from Haxe can be found here : https://github.com/mboussaa/Haxe_Samples


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
To do so, the method newPopulation() generate random test suites and add them to a test sequence which represents the whole population.<br><br>
A test suite (solution) is generated and exectued like this way : <br>
-We define a random size for a test suite<br>

```
testSuiteSize=(int) (Math.random() * CommonParameters.MAX_SEQUENCE )+1;
  ```
  <br>
-We generate random data taking into account the datatypes of the service inputs. The randomData class is responsible for doing this<br>

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

  3- We save the new test case with its relative outputs + we update the fitness value

 ``` 
TestCase tc=new TestCase (methods[posMethod],data,outputs);
 ``` 
 -We save in the same way this test suite in the current population (sequence)
 
  ``` 
 TestSequence.add(RandomTestSuite);
  ``` 
  4- We repeat these steps until reaching a termination criterion which is the number of generations (Value defined in commons.parameters.java)<br><br>
  
  	
	public class CommonParameters {
	
	//the max size for a test suite (individual)
	public static int MAX_SEQUENCE=5;
	
	//the population size
	public static int POPULATION_SIZE=3;
	
	//the population size
	public static int GENERATION_SIZE=3;
	
	//the max length of a generated string
	public static int MAX_STRING_LENGTH=15;
	
	}
	

  5- We display all the generated test test suites and the best ones those that have generated bugs
  
     
       //display all the generated test test suites
		displayTestSequence(TestSequence);
		
		//catch the best test suites
		updateBestTestSuites(TestSequence);

		//display the best test suites
		displayBestTestSuites(TestSequence);
		
####Novelty search adaptation
Until now, we have described only a random approach that loads basically some services through an interface then generate random data to fulfill an initial population of test data. To generate the next generation we are going to apply the novelty search to explore the huge space of test data.
		 