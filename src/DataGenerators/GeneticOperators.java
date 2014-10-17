package DataGenerators;



import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class GeneticOperators {


	int k = 30;
	double archiveThreshold = 0;
	double archiveThresholdChangeFactor = 1.0;
	double archiveThresholdMin;
	public List<Behaviour> archive;
	List<Behaviour> currentPop;
	List<Behaviour> toArchive;
	double addProbability;
	DataGenerators.Behaviour b;

	
	String javainterfaceClass;
	String jsinterfaceClass;
	
	Vector<TestSuite> Archive= new Vector<TestSuite>();;
	Vector<TestSuite> TestSequence;
	Vector<TestSuite> selectedTestSequence;
	Vector<TestSuite> newTestSequence= new Vector<TestSuite>();

	
	int testSequenceSize;
	int selectedTestSequenceSize;
	
	
	public GeneticOperators(Vector<TestSuite> TestSequence,String javainterfaceClass,String jsinterfaceClass){
		this.javainterfaceClass=javainterfaceClass;
		this.jsinterfaceClass=jsinterfaceClass;
		
		this.TestSequence=TestSequence;	
		this.testSequenceSize=TestSequence.size();
	
	}
	

	public void noveltySelection(){
		for (int i = 0; i < TestSequence.size(); i++) {
		Behaviour b = new Behaviour() ;
		b.setNoveltyMetric(TestSequence.elementAt(i), TestSequence, Archive);
		}
		
	//selectedTestSequence= new Vector<TestSuite>();
		
		for (int i = 0; i < TestSequence.size(); i++) {
	
		if(TestSequence.elementAt(i).getTestSuiteFitnessValue()<1){
			
			selectedTestSequence.add(TestSequence.elementAt(i));
			
			}
		}
		//selectedTestSequenceSize=selectedTestSequence.size();
	}
	

	public void selection(){
		selectedTestSequence= new Vector<TestSuite>();
		
		for (int i = 0; i < TestSequence.size(); i++) {
	
		if(TestSequence.elementAt(i).getTestSuiteFitnessValue()<1){
			
			selectedTestSequence.add(TestSequence.elementAt(i));
			
			}
		}
		selectedTestSequenceSize=selectedTestSequence.size();
		
		//System.out.println(selectedTestSequenceSize);
		//System.exit(0);
	}
	
	public Vector<TestSuite> mutation() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, FileNotFoundException, ClassNotFoundException, InstantiationException, ScriptException, NoSuchMethodException{
		Vector<Object> outputs=null;
		do{
				for (int i = 0; i < selectedTestSequence.size() ;i++) {
					TestSuite testSuite= new TestSuite();
					for (int j = 0; j < selectedTestSequence.elementAt(i).tc.size(); j++) {
						
						Object[] data=new RandomData().getMutationDataGenerated(selectedTestSequence.elementAt(i).tc.elementAt(j).m,selectedTestSequence.elementAt(i).tc.elementAt(j).data);
						//selectedTestSequence.elementAt(i).tc.get(j).setData(data);

					    outputs=new Vector<Object>();
						Object o =selectedTestSequence.elementAt(i).tc.elementAt(j).m.invoke(javaScriptEngine(), data);
					    Object o1=jsScriptEngine().invokeFunction(selectedTestSequence.elementAt(i).tc.elementAt(j).m.getName() , data);
					    
					  
					    //don't get methods with void return value
					    if(o!=null&&o1!=null){
					    outputs.add(o);
					    outputs.add(o1);}
						
						
						TestCase tc=new TestCase (selectedTestSequence.elementAt(i).tc.elementAt(j).m,data,outputs);

						testSuite.addTestCase(tc);
					}
					newTestSequence.add(testSuite);
				}
			
		}while(newTestSequence.size()!=TestSequence.size());
		
//		System.out.print("\n********************** new **********************");
//		for (int i = 0; i < newTestSequence.size() ;i++) {
//			System.out.println("\n");
//			System.out.println("Sequence Num : "+(i+1));
//			System.out.println("\nTest Suite size : "+newTestSequence.elementAt(i).tc.size());
//			for (int j = 0; j < newTestSequence.elementAt(i).tc.size(); j++) {
//				System.out.println("");	
//				newTestSequence.elementAt(i).tc.elementAt(j).displayTestCase();
//			}
//			newTestSequence.elementAt(i).displayFitnessTestSuite();
//		}System.exit(0);
		return newTestSequence;
	}

	
	public void crossover(){ 
		
		int crossoverPoint = 0;
		int crossoverMethodPosition = 0;
		Object permute=null;
		
		if (selectedTestSequenceSize>1) {
			
			for (int i = 0; i < selectedTestSequenceSize-1 ;i++) {
				
			    //crossoverPoint= 0;
				crossoverMethodPosition= (int) (Math.random() * selectedTestSequence.elementAt(i).tc.size());
				crossoverPoint= (int) (Math.random() * selectedTestSequence.elementAt(i).tc.elementAt(crossoverMethodPosition).data.length);
				
				
				permute=selectedTestSequence.elementAt(i).tc.elementAt(crossoverMethodPosition).data[crossoverPoint];
				selectedTestSequence.elementAt(i).tc.elementAt(crossoverMethodPosition).data[crossoverPoint]=selectedTestSequence.elementAt(i+1).tc.elementAt(crossoverMethodPosition).data[crossoverPoint];
				selectedTestSequence.elementAt(i+1).tc.elementAt(crossoverMethodPosition).data[crossoverPoint]=permute;
				
				
				//System.out.println(selectedTestSequence.elementAt(i).tc.elementAt(crossoverMethodPosition).m.getName());
				//System.out.println(crossoverMethodPosition);
				//System.out.println(crossoverPoint);
				//System.out.println(selectedTestSequence.elementAt(i).tc.elementAt(crossoverMethodPosition).data[crossoverPoint]);
				//System.out.println(selectedTestSequence.elementAt(i+1).tc.elementAt(crossoverMethodPosition).data[crossoverPoint]);
				//System.out.println();
					//Object[] data=new RandomData().getMutationDataGenerated(selectedTestSequence.elementAt(i).tc.get(j).m,selectedTestSequence.elementAt(i).tc.get(j).data);
					//selectedTestSequence.elementAt(i).tc.get(j).setData(data);

					//TestCase tc=new TestCase (selectedTestSequence.elementAt(i).tc.get(j).m,data,null);

					//testSuite.addTestCase(tc);
				
				}
				//newTestSequence.add(testSuite);
			}//System.exit(0);
	
			
		}
	
	
	public Vector<TestSuite> getNewTestSequence(){
		Vector<TestSuite> t=null;
		return t;
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
}

	

	
	
	
	
	
	