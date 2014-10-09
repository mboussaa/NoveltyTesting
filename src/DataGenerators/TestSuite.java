package DataGenerators;

import java.util.Vector;

public class TestSuite implements java.lang.Comparable{
	
	Vector<TestCase> tc=null;
	float testSuiteFitnessValue;
	Boolean defect=false;
	
	public TestSuite(){
			 this.tc= new Vector<TestCase>();
	}
	
	//add new test case to the current test suite
	public void addTestCase(TestCase tc){
			 this.tc.add(tc);
	}
	
	//calculate the fitness value for the current test suite
	public void calculateTestSuiteFitness(){
			 float totalTestCaseFitness=0;
			 for (int i = 0; i < tc.size(); i++) {
				 totalTestCaseFitness+=tc.elementAt(i).testCaseFitnessValue;
			}
			 testSuiteFitnessValue=totalTestCaseFitness/tc.size();
			 
				if (testSuiteFitnessValue<1){
					defect=true;
				}
	}
	
	//get the test suite fitness value
	public float getTestSuiteFitnessValue(){
			return testSuiteFitnessValue;
	}
	
	//display the fitness value of the current test suite
	public void displayFitnessTestSuite(){
			System.out.println(" \nTotal Fitness value : "+this.testSuiteFitnessValue );
	}
	
	//use to compare to test suites basing on the fitness value of each one
	public int compareTo(Object other) { 
			  float nb1 = ((TestSuite) other).getTestSuiteFitnessValue(); 
			  float nb2 = this.getTestSuiteFitnessValue(); 
		      if (nb1 > nb2)  return -1; 
		      else if(nb1 == nb2) return 0; 
		      else return 1; 
		   } 
	
	public void testSuiteMutation(){
		
	}
}
