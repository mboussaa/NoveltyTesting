package DataGenerators;

import haxe.root.Functions;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import javax.print.DocFlavor.STRING;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import Main.Commons;


public class NoveltyGeneration {

	//a test sequence (population)is a set of test suites 
	//a test suite (individual) is a set of test cases 
	//a test case is a random call to a service with random data
	
	Vector<TestSuite> bestTestSuites= new Vector<TestSuite>();
	Vector<TestSuite> relevantTestSuites= new Vector<TestSuite>();
	
	Vector<TestSuite> TestSequence= new Vector<TestSuite>();
	
	Vector<TestSuite> newTestSequence=new Vector<TestSuite>();
	
	Method[] methods;
	
	
	String javainterfaceClass;
	String jsinterfaceClass;
	String interfaceClass; 
	
	//call the different services with random data for each version
	public void startInvoke(String javainterfaceClass,String jsinterfaceClass,String interfaceName) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, FileNotFoundException, ScriptException, NoSuchMethodException, InstantiationException{

		this.interfaceClass=javainterfaceClass;
		this.jsinterfaceClass=jsinterfaceClass;
		this.javainterfaceClass=javainterfaceClass;
		
		//get all services from the given interface
		methods=getMethods(interfaceName);
		
		//a test sequence represent a population (a set of test suites)
		
	
		TestSequence=newPopulation();
		
		//start generations
    	for (int k=0;k<CommonParameters.GENERATION_SIZE-1;k++){
 
    	
    	if (relevantTestSuites.size()==0){
    		TestSequence.clear();
    		TestSequence=newPopulation();
    		
    	}else{
    	  	//apply genetic operators + NS for next generation in case we detect some bugs
    	newTestSequence=generateNextPopulation(TestSequence);
    	//display all the generated test test suites
		displayTestSequence(newTestSequence);
		
		//catch the best test suites
		updateBestTestSuites(newTestSequence);

		//display the best test suites
		displayBestTestSuites(newTestSequence);
    	}
  	
    	}//end generation
    	
    	System.out.println("nb bugs: "+bestTestSuites.size());
		

	}
	
	public Vector<TestSuite> generateNextPopulation(Vector<TestSuite> TestSequence) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, FileNotFoundException, ClassNotFoundException, InstantiationException, NoSuchMethodException, ScriptException{
		
		GeneticOperators gp= new GeneticOperators(TestSequence,javainterfaceClass,jsinterfaceClass);
		gp.selection();
		gp.crossover();
		newTestSequence=gp.mutation();
		return newTestSequence;
	}
	
	public Vector<TestSuite> newPopulation() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, FileNotFoundException, ScriptException{
		Vector<TestSuite> TestSequence= new Vector<TestSuite>();
		TestSuite RandomTestSuite= new TestSuite();
		//start population
		for (int i=0;i<CommonParameters.POPULATION_SIZE;i++){

			//generate one solution = a random test suite
			RandomTestSuite=generateRandomTestSuite();				
			
			//calculate the test suite fitness through the fitness values of its test cases
			RandomTestSuite.calculateTestSuiteFitness();
			
			//add the test suite to the global test sequence 
			TestSequence.add(RandomTestSuite);
			
		}//end population
		
		//display all the generated test test suites
		displayTestSequence(TestSequence);
		
		//catch the best test suites
		updateBestTestSuites(TestSequence);

		//display the best test suites
		displayBestTestSuites(TestSequence);
		
		return TestSequence;
	}
	
	public TestSuite generateRandomTestSuite() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, FileNotFoundException, ScriptException{
		
		TestSuite testSuite= new TestSuite();
		Vector<Object> outputs=null;
		int posMethod=0;
		Object[] data= null;
		//define an instance and a test suite size 
		int testSuiteSize=methods.length;

		for (int j=0;j<testSuiteSize;j++){
		
		//posMethod=(int) (Math.random() * methods.length);
		data=new RandomData().getDataGenerated(methods[j]);
	    outputs=new Vector<Object>();
		Object o =methods[j].invoke(javaScriptEngine(), data);
	    Object o1=jsScriptEngine().invokeFunction(methods[j].getName() , data);
	    
	  
	    //don't get methods with void return value
	    if(o!=null&&o1!=null){
	    outputs.add(o);
	    outputs.add(o1);}
	    
	    //instantiate the new test case
	    TestCase tc=new TestCase (methods[j],data,outputs);
	    tc.calculateTestCaseFitness();
	    //add the test case to the current test suite
		testSuite.addTestCase(tc);
		}
		return testSuite;				
		
		
	}
	
	//ScriptEngine to handle the JS versions of code
	public Invocable jsScriptEngine() throws FileNotFoundException, ScriptException{
		
				ScriptEngineManager manager = new ScriptEngineManager();
		        ScriptEngine engine = manager.getEngineByName("JavaScript");
		        engine.eval( new java.io.FileReader(jsinterfaceClass));
		        Invocable inv = (Invocable) engine;
		        return inv;
	}
	
	public Object javaScriptEngine() throws FileNotFoundException, ScriptException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		Class<?> c = Class.forName(javainterfaceClass);
		Object t = c.newInstance();
        return t;
}
	
	//get all services per interface
	public Method[] getMethods(String interfaceName) throws ClassNotFoundException{
		Class c = Class.forName(interfaceName);
		Method[] methods = c.getDeclaredMethods();
		return methods;
	}
	
	//display all the generated test test suites of one sequence (population)
	public void displayTestSequence(Vector<TestSuite> TestSequence){
		System.out.print("\n********************** all test suites **********************");
		for (int i = 0; i < TestSequence.size() ;i++) {
			System.out.println("\n");
			System.out.println("Sequence Num : "+(i+1));
			System.out.println("\nTest Suite size : "+TestSequence.elementAt(i).tc.size());
			for (int j = 0; j < TestSequence.elementAt(i).tc.size(); j++) {
				System.out.println("");	
				TestSequence.elementAt(i).tc.elementAt(j).displayTestCase();
			}
			TestSequence.elementAt(i).displayFitnessTestSuite();
		}
			
	}
	
	//display the best test suites (sets of test cases) with fitness <1
	public void displayBestTestSuites(Vector<TestSuite> TestSequence){
		System.out.println("\n********************** best test suites **********************");
		System.out.println("\nTotal of best test suites : "+bestTestSuites.size()+"/"+TestSequence.size() );
		for (int i = 0; i < bestTestSuites.size() ;i++) {
			System.out.println("\n");
			System.out.println("Sequence Num : "+(i+1));
			System.out.println("\nTest Suite size : "+bestTestSuites.elementAt(i).tc.size());
			for (int j = 0; j < bestTestSuites.elementAt(i).tc.size(); j++) {
				System.out.println("");	
				bestTestSuites.elementAt(i).tc.elementAt(j).displayTestCase();
			}
			bestTestSuites.elementAt(i).displayFitnessTestSuite();
		}
		System.out.println("");		
	}
	
	//catch the best test suites over generations
	public void updateBestTestSuites(Vector<TestSuite> TestSequence){
		relevantTestSuites= new Vector<TestSuite>();
		for (int i = 0; i < TestSequence.size(); i++) {
			
			if(TestSequence.elementAt(i).getTestSuiteFitnessValue()<1){
			bestTestSuites.add(TestSequence.elementAt(i));
			relevantTestSuites.add(TestSequence.elementAt(i));
			 
			}
			Collections.sort(bestTestSuites);
		}
		
	}

}
