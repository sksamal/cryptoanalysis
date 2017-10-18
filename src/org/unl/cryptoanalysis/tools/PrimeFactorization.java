package org.unl.cryptoanalysis.tools;

import java.math.BigInteger;
import java.util.ArrayList;
public class PrimeFactorization {

	public static void main(String args[]) {
				
	//	for(long i = 2;i<Long.MAX_VALUE && (i >1) ;i= i*i-(long)(Math.random()*(i*i-2)))
		System.out.println(factorizePollardRho(new BigInteger("11312582241969660023")));
		
	}
	
	public static void primeFactors(long n)
	{
		System.out.print("\nn="+n + " : ");
	    // Print the number of 2s that divide n

	    while (n%2 == 0)
	    {
	        System.out.print(2 + " ");
	    	n = n/2;
	    }
	 
	    // n must be odd at this point.  So we can skip one element (Note i = i +2)
	    for (int i = 3; i <= Math.sqrt(n); i = i+2)
	    {
	        // While i divides n, print i and divide n
	        while (n%i == 0)
	        {
	        	System.out.print(i + " ");
	            n = n/i;
	        }
	    }
	 
	    // This condition is to handle the case when n is a prime number
	    // greater than 2
	    if (n > 2)
	    	System.out.print(n + " ");
	}
	
	public static boolean ifprime(long n)
	{
	    // Print the number of 2s that divide n
	    boolean broken= false;

	    while (n%2 == 0)
	    {
	    	broken = true;
	    	n = n/2;
	    }
	 
	    // n must be odd at this point.  So we can skip one element (Note i = i +2)
	    for (int i = 3; !broken && i <= Math.sqrt(n); i = i+2)
	    {
	        while (n%i == 0)
	        {
	        	broken = true;
	        	break;
	        }
	    }
	 
	    // This condition is to handle the case whien n is a prime number
	    // greater than 2
	    if (n > 2 && !broken) {
	    	System.out.println(n + " is prime");
	    	return true;
	    }
	    return false;
	}
	
	public static boolean ifprime(BigInteger n)
	{
	    // Print the number of 2s that divide n
	    boolean broken= false;
	    BigInteger two = new BigInteger("2");

	    while (n.mod(two).intValue() == 0)
	    {
	    	broken = true;
	    	n = n.divide(two);
	    }
	 
	    // n must be odd at this point.  So we can skip one element (Note i = i +2)
	    for (BigInteger i = new BigInteger("3"); !broken && (i.multiply(i).compareTo(n) < 0); i=i.add(two))
	    {
	        if (n.mod(i).compareTo(new BigInteger("0")) == 0)
	        	broken = true;
	    }
	 
	    // This condition is to handle the case whien n is a prime number
	    // greater than 2
	    if (!broken) {
	    	System.out.println(n + " is prime");
	    	return true;
	    }
	    return false;
	}

	public static BigInteger sqrt(BigInteger n) {
		  BigInteger a = BigInteger.ONE;
		  BigInteger b = new BigInteger(n.shiftRight(5).add(new BigInteger("8")).toString());
		  while(b.compareTo(a) >= 0) {
		    BigInteger mid = new BigInteger(a.add(b).shiftRight(1).toString());
		    if(mid.multiply(mid).compareTo(n) > 0) b = mid.subtract(BigInteger.ONE);
		    else a = mid.add(BigInteger.ONE);
		  }
		  return a.subtract(BigInteger.ONE);
		}

	private static BigInteger factorizePollardRho(BigInteger n) {
		BigInteger x = sqrt(n);
		BigInteger y = new BigInteger(x.toString());
		BigInteger d = new BigInteger("1");
		int count = 0;
		ArrayList<BigInteger> trail_x = new ArrayList<BigInteger>();
		ArrayList<BigInteger> trail_y = new ArrayList<BigInteger>();

		while(d.compareTo(new BigInteger("1"))==0) {
			x=x.multiply(x).add(d).mod(n);
			trail_x.add(x);
			BigInteger t = y.multiply(y).add(d).mod(n);
			y=t.multiply(t).add(d).mod(n);
			trail_y.add(y);
//			d = new ExtendedEuclidean(n).getGcd(x.subtract(y).abs());
			d = n.gcd(x.subtract(y).abs());
			++count;
			if(count%1000000==0)
				System.out.print(count+".");
		}
		System.out.println("T(x)" + trail_x);
		System.out.println("T(y)" + trail_y);

		return d;
	}
}
