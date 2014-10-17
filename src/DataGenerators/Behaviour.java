package DataGenerators;


import java.util.List;
import java.util.Vector;


public class Behaviour {

	double noveltyMetric;
	
	
	public Behaviour(){
	//TODO
	}
	
	
	public void setNoveltyMetric(TestSuite testsuite,Vector<TestSuite> TestSequence,Vector<TestSuite> Archive) {
		
		noveltyMetric=getDistFromPopulation(testsuite,TestSequence)+getDistFromArchive(testsuite,Archive);
		
	}
	
	public double getDistFromArchive(TestSuite testsuite,Vector<TestSuite> Archive) {
		double distFromArchive=0;
		double dist;
		
		for (int i = 0; i < Archive.size(); i++) {
			dist= distanceTestSuites(testsuite,Archive.elementAt(i));
			distFromArchive+=dist;
		}
		
		return distFromArchive;
	}
	
	public double getNoveltyMetric(){
		return noveltyMetric;
	}
	
	public  double getDistFromPopulation(TestSuite testsuite,Vector<TestSuite> TestSequence) {
		double distFromPopulation=0;
		double dist;
		
		for (int i = 0; i < TestSequence.size(); i++) {
			dist= distanceTestSuites(testsuite,TestSequence.elementAt(i));
			distFromPopulation+=dist;
		}
		
		return distFromPopulation;
	}
	
	public double distanceTestSuites(TestSuite testsuite1,TestSuite testsuite2) {
		double distanceTestSuites=0;
		double dist;
		
		for (int i = 0; i < testsuite1.getTestCaseSize(); i++) {
			dist= distanceTestCases(testsuite1.getTestCase(i),testsuite2.getTestCase(i));
			distanceTestSuites+=dist;
		}
		
		return distanceTestSuites;
	}
	
	public double distanceTestCases(TestCase testcase1,TestCase testcase2) {
		double distanceNumbers = 0;
		double distanceChar = 0;
		double distanceStrings = 0;
		
		double distanceTestcases;
		
		Class<?>[] types=testcase1.getMethod().getParameterTypes();
		Object[] data1=testcase1.getData();
		Object[] data2=testcase2.getData();
		

		for (int i=0;i<types.length;i++){
			String t=types[i].getName();
			 
			if (t.equals("int") ||t.equals("float")|| t.equals("long")){
				
				distanceNumbers=distanceNumbers(data1[i], data2[i]);
			}
			
			if (t.equals("char")){
				
				distanceChar=distanceChar(data1[i], data2[i]);
			}
		
			if (t.equals("java.lang.String")){
				
				distanceStrings=distanceStrings(data1[i], data2[i]);
			}
		}
		
		distanceTestcases= distanceNumbers+distanceChar+distanceStrings;
		
		return distanceTestcases;
	}
	
	
    public  int distanceStrings(Object a, Object b) {
    	//Levenshtein Distance
    	String a1=a.toString().toLowerCase();
    	String b1=b.toString().toLowerCase();

        // i == 0
        int [] costs = new int [b1.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a1.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b1.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a1.charAt(i - 1) == b1.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b1.length()];
    }
    
    public int distanceChar(Object a, Object b){
    	char a1=a.toString().charAt(0);
    	char b1=b.toString().charAt(0);
    	int diff = Character.toLowerCase(a1) - Character.toLowerCase(b1);
        return diff;
    }
    
    public double distanceNumbers(Object a, Object b){

    	double diff = Double.parseDouble(a.toString())-Double.parseDouble(b.toString());
        return diff;
    }


}
