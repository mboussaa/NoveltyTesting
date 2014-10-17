package DataGenerators;


import java.util.List;
import java.util.Vector;


public class Behaviour {

	double noveltyMetric;
	
	
	public Behaviour(){
		this.noveltyMetric=0;
	}
	
	
	public void setNoveltyMetric(TestSuite testsuite,Vector<TestSuite> TestSequence,Vector<TestSuite> Archive) {
		
		noveltyMetric=getDistFromPopulation(testsuite,TestSequence)+getDistFromArchive(testsuite,Archive);
		
	}
	
	public  double getDistFromPopulation(TestSuite testsuite,Vector<TestSuite> TestSequence) {
		
		return 0;
	}
	

	public double getDistFromArchive(TestSuite testsuite,Vector<TestSuite> Archive) {
		
		return 0;
	}
	
	public double distanceTestsuites(TestSuite testsuite1,TestSuite testsuite2) {
		
		return 0;
	}
	
	public double distanceTestcases(TestCase testcase1,TestCase testcase2) {
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
				distanceChar=distanceChar(data1[i].toString().charAt(0), data2[i].toString().charAt(0));
			}
		
			if (t.equals("java.lang.String")){
				distanceStrings=distanceStrings(data1[i].toString(), data2[i].toString());
			}
		}
		
		distanceTestcases= distanceNumbers+distanceChar+distanceStrings;
		return distanceTestcases;
	}
	
	
    public  int distanceStrings(String a, String b) {
    	//Levenshtein Distance
    	
        a = a.toLowerCase();
        b = b.toLowerCase();
        // i == 0
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }
    
    public int distanceChar(char a, char b){

    	int diff = Character.toLowerCase(a) - Character.toLowerCase(b);
        return diff;
    }
    
    public double distanceNumbers(Object a, Object b){

    	double diff = Double.parseDouble(a.toString())-Double.parseDouble(b.toString());
        return diff;
    }


}
