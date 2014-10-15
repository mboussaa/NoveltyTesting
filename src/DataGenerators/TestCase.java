package DataGenerators;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.Vector;

public class TestCase {

	Method m;
	Object[] data;
	Vector<Object> outputs;
	float testCaseFitnessValue;
	Boolean defect=false;
	
	public TestCase (Method m,Object[] data,Vector<Object> outputs){
		
		this.m=m;
		this.data=data;
		this.outputs=outputs;

	}
	
	//calculate the fitness value of one test case : intersection
	public void calculateTestCaseFitness(){
		int nb=1;
		float fitness;

		Object value;
		int frequence, i, j, counter ; 
		frequence = 0; 
		for (i=0; i < outputs.size(); i++){
			counter = 0 ; 
		for (j = 0 ; j < outputs.size(); j++){
		 if ( outputs.elementAt(i).toString().equals(outputs.elementAt(j).toString()) || (Double.parseDouble(outputs.elementAt(i).toString())==(Double.parseDouble(outputs.elementAt(j).toString()))) ) {
			 counter = counter + 1 ;
		}
		} 
		if  (counter > frequence) {
		frequence = counter ; 
		value = outputs.elementAt(i) ;
		}
		} 
		
		fitness=frequence/outputs.size();
		
		this.testCaseFitnessValue=fitness;
		
		if (fitness<1){
			defect=true;
		}
	}
	
	//display details about the current test case : Method name, inputs, outputs and fitness value
	public void displayTestCase(){
		System.out.println(" Method : "+this.m.getName());
		for (int i = 0; i < data.length; i++) {
			System.out.println(" Input : "+data[i] );
		}
		for (int i = 0; i < outputs.size(); i++) {
			System.out.println(" Output "+(i+1)+" : "+this.outputs.elementAt(i));
		}
		System.out.println(" Fitness value : "+this.testCaseFitnessValue );

		
	}
	
	//get test case fitness value
	public float getTestCaseFitnessValue(){
		return testCaseFitnessValue;
	}
	
	//get a method
	public Method getMethod(){
		return m;
	}
	
	//get the generated data
	public Object[] getData(){
		return data;
	}
	
	//set a new method call
	public void setMethod(Method m){
		this.m=m;
	}
	
	//set new generated data
	public void setData(Object[] data){
		this.data=data;
	}
	
	//compare two objects (the attributes)
	@Override
    public boolean equals(Object obj) {
        // V�rification de l'�galit� des r�f�rences
        if (obj==this) {
            return true;
        }
 
        // V�rification du type du param�tre
        if (obj instanceof TestCase) {
            // V�rification des valeurs des attributs
        	TestCase other = (TestCase) obj;
 
            // Pour les attributs de type primitif
            // on compare directement les valeurs :
//            if (this.attribut1 != other.attribut1) {
//                return false; // les attributs sont diff�rents 
//            }
 
            // Pour les attributs de type objets 
            // on compare dans un premier temps les r�f�rences 
//            if (this.attribut2 != other.attribut2) {
//                // Si les r�f�rences ne sont pas identiques
//                // on doit en plus utiliser equals()
//                if (this.attribut2 == null || !this.attribut2.equals(other.attribut2)) {
//                    return false; // les attributs sont diff�rents 
//                }
//            }
 
            // Si on arrive ici c'est que tous les attributs sont �gaux :
            return true;
        }
 
        return false;
    }
	
	//mutate the inputs 
	public void testCaseMutation(){
		Object[] mutatedData;
		RandomData R = new RandomData();
		mutatedData=R.getMutationDataGenerated(m, data);	
	}
	
	public boolean isInteger(String chaine) {
		try {
			Integer.parseInt(chaine);
		} catch (NumberFormatException e){
			return false;
		}
 
		return true;
	}
	
	public boolean isFloat(String chaine) {
		try {
			Float.parseFloat(chaine);
		} catch (NumberFormatException e){
			return false;
		}
 
		return true;
	}
	
	public boolean isLong(String chaine) {
		try {
			Long.parseLong(chaine);
		} catch (NumberFormatException e){
			return false;
		}
 
		return true;
	}
	
	public boolean isDouble(String chaine) {
		try {
			Double.parseDouble(chaine);
		} catch (NumberFormatException e){
			return false;
		}
 
		return true;
	}
	
	//generate random int between 0 and x
		public int random(int x) {		
		      return (int) (Math.random() * x );
		    }
}
