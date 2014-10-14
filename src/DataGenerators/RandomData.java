package DataGenerators;

import java.lang.reflect.Method;
import java.util.Random;
import java.util.Vector;

public class RandomData {

	
	public Object[] dataGenerated;
	public Object[] dataGeneratedPostMutation;
	
	
	//constructor used to generate random data for each method given as a parameter
	public RandomData(){
		
	}
	
	public Object[] getMutationDataGenerated(Method m, Object[] dataGenerated){
		//the datatypes
		Class<?>[] types=m.getParameterTypes();
		Object[] mutatedDataGenerated=new Object[types.length];
		
		//dataGeneratedPostMutation=new Object[types.length];
		//int pos=random(dataGenerated.length);
		String t=""; 
		//int pos;
		
		//System.out.println(types.length+" fin "+mutatedDataGenerated.length);
		//System.exit(0);
		
		for (int i=0;i<types.length;i++){
			
			//pos=random(dataGenerated.length);
			t=types[i].getName();
			
			if (t.equals("int")){
				mutatedDataGenerated[i]=mutateInt(dataGenerated[i]);			
			}
			if (t.equals("long")){
				dataGenerated[i]=mutateLong(dataGenerated[i]);		
			}
			if (t.equals("boolean")){
				dataGenerated[i]=mutateBoolean(dataGenerated[i]);			
			}
			if (t.equals("char")){
				dataGenerated[i]=mutateChar(dataGenerated[i]);			
			}
			if (t.equals("float")){
				dataGenerated[i]=mutateFloat(dataGenerated[i]);			
			}
			if (t.equals("java.lang.String")){
				dataGenerated[i]=mutateString(dataGenerated[i]);			
			}
		}
		
		
		return mutatedDataGenerated;
	}
	
	//get the generated data
	public Object[] getDataGenerated(Method m){
		Class<?>[] types=m.getParameterTypes();
		dataGenerated=new Object[types.length];
		String t=""; 
		for (int i=0;i<types.length;i++){
			t=types[i].getName();
			
			if (t.equals("int")){
				dataGenerated[i]=generateInt();			
			}
			if (t.equals("long")){
				dataGenerated[i]=generateLong();		
			}
			if (t.equals("boolean")){
				dataGenerated[i]=generateBoolean();			
			}
			if (t.equals("char")){
				dataGenerated[i]=generateChar();			
			}
			if (t.equals("float")){
				dataGenerated[i]=generateFloat();			
			}
			if (t.equals("java.lang.String")){
				dataGenerated[i]=generateString();			
			}
		}
		return dataGenerated;
	}
	
	//Generate random string
    public String generateString() {
		Random r=new Random();
		int length = r.nextInt(CommonParameters.MAX_STRING_LENGTH);
    	String chars = "abcdefghijklmnopqrstuvwxyz1234567890";
    	//String caracteres ="²12345MWXCVBN67890°+&é'(-è_azertyuiçà)=~#{[|`\\opqsdf^@]}¤¨£%µghjklm§/.?<>AZERTwxcYUIOPQSDvbnFGHJKL";
    	int charLength = chars.length();
        StringBuilder  pass = new StringBuilder (charLength);
        for (int x = 0; x < length; x++) {
            int i = (int) (Math.random() * charLength);
            pass.append(chars.charAt(i));
        }
        return pass.toString();
    }
    
    //Generate random Char
    public char generateChar() {
    	
    	String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    	//String caracteres ="²12345MWXCVBN67890°+&é'(-è_azertyuiçà)=~#{[|`\\opqsdf^@]}¤¨£%µghjklm§/.?<>AZERTwxcYUIOPQSDvbnFGHJKL";
    	char pass;
    
         int i = (int) (Math.random() * chars.length());
         pass=chars.charAt(i);
        
        return pass;
    }
	
	//Generate random Int
	public int generateInt() {
		int n;
		Random rand = new Random(); 
		
		n=rand.nextInt();
		
      return n;
    }
	
	//Generate random Float
	public float generateFloat() {
	
		float min = Float.MIN_VALUE;
		float max =  Float.MAX_VALUE;

		Random ra = new Random();

		float finalX = min + ra.nextFloat() * (max - min);
		
	  return finalX;
    }

	//Generate random Long
	public long generateLong() {

		Random ra = new Random();

		double m = Math.random();
		long l = ra.nextLong();

		long finalX =  (long)(m* l);
		
	  return finalX;
    }
	
	//Generate random Boolean
	public Boolean generateBoolean() {
	
		Random rand = new Random(); 
		Boolean n=rand.nextBoolean();
		
      return n;
    }
	
	//Generate random Double
	public double generateDouble() {
		
		double min = Double.MIN_VALUE;
		double max =  Double.MAX_VALUE;

		Random ra = new Random();

		double finalX = min + ra.nextDouble() * (max - min);
		
	  return finalX;
    }
	
	//generate random int between 0 and x
	public int random(int x) {		
	      return (int) (Math.random() * x );
	    }
	
	
	//*************Mutation*************
	
	//Generate random string
    public String mutateString(Object dataGenerated) {
		Random r=new Random();
		int length = r.nextInt(CommonParameters.MAX_STRING_LENGTH);
    	String chars = "abcdefghijklmnopqrstuvwxyz1234567890";
    	//String caracteres ="²12345MWXCVBN67890°+&é'(-è_azertyuiçà)=~#{[|`\\opqsdf^@]}¤¨£%µghjklm§/.?<>AZERTwxcYUIOPQSDvbnFGHJKL";
    	int charLength = chars.length();
        StringBuilder  pass = new StringBuilder (charLength);
        for (int x = 0; x < length; x++) {
            int i = (int) (Math.random() * charLength);
            pass.append(chars.charAt(i));
        }
        return pass.toString();
    }
    
	//Generate random Char
    public char mutateChar(Object dataGenerated) {
    	
    	String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    	//String caracteres ="²12345MWXCVBN67890°+&é'(-è_azertyuiçà)=~#{[|`\\opqsdf^@]}¤¨£%µghjklm§/.?<>AZERTwxcYUIOPQSDvbnFGHJKL";
    	char pass;
    
         int i = (int) (Math.random() * chars.length());
         pass=chars.charAt(i);
        
        return pass;
    }
	
	//Generate random Int
	public int mutateInt(Object dataGenerated) {
		int n;
		Random rand = new Random(); 
		
		n=rand.nextInt();
		
      return n;
    }
	
	//Generate random Float
	public float mutateFloat(Object dataGenerated) {
	
		float min = Float.MIN_VALUE;
		float max =  Float.MAX_VALUE;

		Random ra = new Random();

		float finalX = min + ra.nextFloat() * (max - min);
		
	  return finalX;
    }

	//Generate random Long
	public long mutateLong(Object dataGenerated) {

		Random ra = new Random();

		double m = Math.random();
		long l = ra.nextLong();

		long finalX =  (long)(m* l);
		
	  return finalX;
    }
	
	//Generate random Boolean
	public Boolean mutateBoolean(Object dataGenerated) {
	
		Random rand = new Random(); 
		Boolean n=rand.nextBoolean();
		
      return n;
    }
	
	//Generate random Double
	public double mutateDouble(Object dataGenerated) {
		
		double min = Double.MIN_VALUE;
		double max =  Double.MAX_VALUE;

		Random ra = new Random();

		double finalX = min + ra.nextDouble() * (max - min);
		
	  return finalX;
    }
	
	
}
