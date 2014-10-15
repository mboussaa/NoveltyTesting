package DataGenerators;



import java.io.FileNotFoundException;
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
	Vector<Object> outputs;
	
	
	Vector<TestSuite> TestSequence;
	Vector<TestSuite> selectedTestSequence;
	Vector<TestSuite> newTestSequence= new Vector<TestSuite>();;
	TestSuite testSuite= new TestSuite();
	
	int testSequenceSize;
	int selectedTestSequenceSize;
	
	
	public GeneticOperators(Vector<TestSuite> TestSequence){

		this.TestSequence=TestSequence;	
		this.testSequenceSize=TestSequence.size();
	
	}
	
	
	public double testNovelty(Behaviour b) {
		// System.err.println(b);
		
		archive = new ArrayList<Behaviour>(k);
		addProbability = 0;
		int totalSize = archive.size() + currentPop.size();
		double[] dist = new double[totalSize];
		int i = 0;
		int inArchiveCount = 0;
		for (Behaviour b2 : archive) {
			dist[i] = b.distanceFrom(b2);
			
			if (dist[i] < 0.0000001) inArchiveCount++;
			i++;
		}
		
		for (Behaviour b2 : currentPop) {
			dist[i] = b.distanceFrom(b2);
			
			i++;
		}
		int kTemp = Math.min(totalSize, this.k);
		Arrays.sort(dist);
		double avgDist = 0;
		for (i = 0; i < kTemp; i++) {
			avgDist += dist[i];
		}
		avgDist /= kTemp;
		

		
		if (inArchiveCount < k) {
			
			if (addProbability > 0) {
				
					toArchive.add(b);
			
			} else { // Using threshold archive addition method.
				if (archiveThreshold == 0) {
					archiveThreshold = b.defaultThreshold();
			//		archiveThresholdMin = properties.getDoubleProperty(ARCHIVE_THRESHOLD_MIN, archiveThreshold * 0.05);
	
				}
				
					toArchive.add(b);
				//}
			}
		}
		
		return avgDist;
	}
	
	public void noveltySelection(){

		for (int i = 0; i < TestSequence.size(); i++) {
			
		if(TestSequence.elementAt(i).tc.size()>0){
			
			testNovelty(b);
			}
		}
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
	
	public void mutation(){
		outputs=new Vector<Object>();

				for (int i = 0; i < selectedTestSequence.size() ;i++) {
			
					for (int j = 0; j < selectedTestSequence.elementAt(i).tc.size(); j++) {
						
						Object[] data=new RandomData().getMutationDataGenerated(selectedTestSequence.elementAt(i).tc.get(j).m,selectedTestSequence.elementAt(i).tc.get(j).data);
						//selectedTestSequence.elementAt(i).tc.get(j).setData(data);

						//TestCase tc=new TestCase (selectedTestSequence.elementAt(i).tc.get(j).m,data,null);

						//testSuite.addTestCase(tc);
					}
					//newTestSequence.add(testSuite);
				}
		}

	
	public void crossover(){
		if (selectedTestSequence.size()>0) {
			
			for (int i = 0; i < selectedTestSequence.size() ;i++) {
		
				for (int j = 0; j < selectedTestSequence.elementAt(i).tc.size(); j++) {
					
					Object[] data=new RandomData().getDataGenerated(selectedTestSequence.elementAt(i).tc.get(j).m);
					//System.out.println("");	
					//TestSequence.elementAt(i).tc.elementAt(j).displayTestCase();
				}
				
				//TestSequence.elementAt(i).displayFitnessTestSuite();
			}
			
			
			
		}
	}
	
	public Vector<TestSuite> getNewTestSequence(){
		Vector<TestSuite> t=null;
		return t;
	}
}

	

	
	
	
	
	
	