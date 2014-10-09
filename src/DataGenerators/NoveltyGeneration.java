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
	
	Vector<TestSuite> bestTestSuites;
	Vector<TestSuite> TestSequence;	
	Vector<Object> outputs;

	int testSuiteSize;
	int posMethod;
	Object[] data= null;
	Method[] methods;
	String interfaceName; 
	
	//call the different services with random data for each version
	public void startInvoke(String javainterfaceClass,String jsinterfaceClass,String interfaceName) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, FileNotFoundException, ScriptException, NoSuchMethodException, InstantiationException{

		this.interfaceName=javainterfaceClass;
		bestTestSuites= new Vector<TestSuite>();
		TestSequence=new Vector<TestSuite>();


		//ScriptEngine to handle the JS versions of code
		ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        engine.eval( new java.io.FileReader(jsinterfaceClass));
        Invocable inv = (Invocable) engine;
        
		
		//get all services from an interface
        methods=getMethods(interfaceName);
		
        
        //start generation
    	for (int k=0;k<CommonParameters.GENERATION_SIZE;k++){
        
        //start population
		for (int i=0;i<CommonParameters.POPULATION_SIZE;i++){

			//define an instance and a test suite size 
			testSuiteSize=(int) (Math.random() * CommonParameters.MAX_SEQUENCE )+1;
			TestSuite testSuite= new TestSuite();

			
			for (int j=0;j<testSuiteSize;j++){
					
					//define a vector to put the outputs of different versions
					outputs=new Vector<Object>();
					
					//define a random choice of method
					posMethod=(int) (Math.random() * methods.length);
					
					//define random data
					data=new RandomData().getDataGenerated(methods[posMethod]);
					
					//invoke a random method with random data and get the outputs into a vector
					Class<?> c = Class.forName(javainterfaceClass);
				    Object t = c.newInstance();
			
					Object o =methods[posMethod].invoke(t, data);
				    Object o1=inv.invokeFunction(methods[posMethod].getName() , data);
				    
				    //don't get methods with void return value
				    if(o!=null&&o1!=null){
				    outputs.add(o);
				    outputs.add(o1);}
				    
				    //instantiate the new test case
				    TestCase tc=new TestCase (methods[posMethod],data,outputs);
				    
				    //calculate its fitness value
				    tc.calculateTestCaseFitness();
				    
				    //add the test case to the current test suite
					testSuite.addTestCase(tc);				
					
			}//end test suite (test case)		
			
			//calculate the test suite fitness through the fitness values of its test cases
			testSuite.calculateTestSuiteFitness();
			
			//add the test suite to the global test sequence 
			TestSequence.add(testSuite);
			
			
		 
		}//end population
		
		//display all the generated test test suites
		displayTestSequence();
		
		//catch the best test suites
		updateBestTestSuites(TestSequence);

		//display the best test suites
		displayBestTestSuites();
		
	}//end generation
		
	}
	
	
	//get all services per interface
	public Method[] getMethods(String interfaceName) throws ClassNotFoundException{
		Class c = Class.forName(interfaceName);
		Method[] methods = c.getDeclaredMethods();
		return methods;
	}
	
	//display all the generated test test suites of one sequence (population)
	public void displayTestSequence(){
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
	public void displayBestTestSuites(){
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
		
		for (int i = 0; i < TestSequence.size(); i++) {
			
			if(TestSequence.elementAt(i).getTestSuiteFitnessValue()<1){
			bestTestSuites.add(TestSequence.elementAt(i));
			 
			}
			Collections.sort(bestTestSuites);
		}
		
	}

}
