package SystemUnderTest;

import java.util.Collections;
import java.util.LinkedList;

import Interfaces.SystemSample_INTERFACE;

//SUT
public class SystemSample_JAVA implements SystemSample_INTERFACE {
	

	//Encode a string
	public String encode(String enc, int offset) {
		offset = offset % 26 + 26;
		StringBuilder encoded = new StringBuilder();
		for (char i : enc.toLowerCase().toCharArray()) {
			if (Character.isLetter(i)) {
				int j = (i - 'a' + offset) % 26;
				encoded.append((char) (j + 'a'));
			} else {
				encoded.append(i);
			}
		}
		return encoded.toString();
	}
	
	
	//Longest common subsequence
	public String lcs(String a, String b){
		
	    int[][] lengths = new int[a.length()+1][b.length()+1];
	    
	    // row 0 and column 0 are initialized to 0 already	 
	    for (int i = 0; i < a.length(); i++)
	        for (int j = 0; j < b.length(); j++)
	            if (a.charAt(i) == b.charAt(j))
	                lengths[i+1][j+1] = lengths[i][j] + 1;
	            else
	                lengths[i+1][j+1] =
	                    Math.max(lengths[i+1][j], lengths[i][j+1]);
	 
	    // read the substring out from the matrix
	    StringBuffer sb = new StringBuffer();
	    for (int x = a.length(), y = b.length();
	         x != 0 && y != 0; ) {
	        if (lengths[x][y] == lengths[x-1][y])
	            x--;
	        else if (lengths[x][y] == lengths[x][y-1])
	            y--;
	        else {
	            assert a.charAt(x-1) == b.charAt(y-1);
	            sb.append(a.charAt(x-1));
	            x--;
	            y--;
	        }
	    }
	 
	    return sb.reverse().toString();
	    
	}
	
	
	//Greatest common divisor
	public float gcd(long u ,long v)
	{
		  long t, k;
		  
		  if (v == 0) return u;
		 
		  u = Math.abs(u);
		  v = Math.abs(v); 
		  if (u < v){
		    t = u;
		    u = v;
		    v = t;
		  }
		 
		  for(k = 1; (u & 1) == 0 && (v & 1) == 0; k <<= 1){
		    u >>= 1; v >>= 1;
		  }
		 
		  t = (u & 1) != 0 ? -v : u;
		  while (t != 0){
		    while ((t & 1) == 0) t >>= 1;
		 
		    if (t > 0)
		      u = t;
		    else
		      v = -t;
		 
		    t = u - v;
		  }
		  return u * k;
	}

}
