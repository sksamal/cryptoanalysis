 /* Uses the Extended Euclidean method to find inverse
 * and uses the Euclidean method to find gcd
 */

package org.unl.cryptoanalysis.tools;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ExtendedEuclidean {

		private BigInteger b_moduli;
		
		
		public ExtendedEuclidean(BigInteger moduli) {
			this.b_moduli = moduli;
		}
		
		public ExtendedEuclidean(String moduli) {
			this.b_moduli = new BigInteger(moduli);
		}


/* Compute Inverse using Extended Euclidean Algorithm */
public BigInteger getInverse(BigInteger b_num) {
			
			/* Step 0 - For modulo 0 and below, not possible */
			if(b_moduli.compareTo(BigInteger.ONE) < 0)
				return new BigInteger("-1");
			
			/* Step 1 - If num > moduli, reduce it to less than moduli
			 */
			if(b_num.compareTo(b_moduli) >=0 )
				b_num=b_num.mod(b_moduli);
			
			// Step 2 - If num is 0 or 1 return as-it is
			if(b_num.compareTo(BigInteger.ZERO) == 0 || b_num.compareTo(BigInteger.ONE)==0)
				return b_num;
			
			// Step 3 - Compute gcd and store results in temporary storage
			List<BigInteger> quotients = new ArrayList<BigInteger>();
			BigInteger dividend = b_moduli;
			BigInteger divisor = b_num;
			BigInteger remainder;
			do {
				BigInteger quotient = dividend.divide(divisor);
				quotients.add(quotient);
				remainder = dividend.mod(divisor);
				if(remainder.compareTo(BigInteger.ZERO) == 0) 
					return new BigInteger("-1");		//Inverse doesnot exist
				dividend = divisor;
				divisor = remainder;
	//			System.out.println(quotient);

			} while(remainder.compareTo(BigInteger.ONE) > 0);
			
//			System.out.println("Divison Done");
			
			// Step 4 - Start from x0=0, x1 =1 and build the inverse
			BigInteger x0=BigInteger.ZERO,x1=BigInteger.ONE;
			for(BigInteger q : quotients) {
				BigInteger x2 = q.multiply(new BigInteger("-1").multiply(x1)).add(x0);
				x0 = x1;
				x1 = x2;
			}
			
			// Step 4 - Bring x1 to modulo moduli
			if(x1.compareTo(BigInteger.ZERO) < 0)
				x1 = x1.add(b_moduli);
			return x1;
		}
		
public BigInteger getInverse(String b_num) {
	return getInverse(new BigInteger(b_num));
}

/* Return gcd of (b_num,b_moduli) */
public BigInteger getGcd(BigInteger b_num) {
	
	/* Step 0 - For modulo 0 and below, not possible */
	if(b_moduli.compareTo(BigInteger.ONE) < 0)
		return new BigInteger("-1");
	
	/* Step 1 - If num > moduli, reduce it to less than moduli
	 */
	if(b_num.compareTo(b_moduli) >=0 )
		b_num=b_num.mod(b_moduli);
	
	// Step 2 - If num is 0 or 1 return as-it is
	if(b_num.compareTo(BigInteger.ZERO) == 0 || b_num.compareTo(BigInteger.ONE)==0)
		return b_num;

	BigInteger dividend = b_moduli;
	BigInteger divisor = null;
	BigInteger remainder = b_num;

	do {
		divisor = remainder;
		remainder = dividend.mod(divisor);
		dividend = divisor;
	} while(remainder.compareTo(BigInteger.ZERO) > 0);

	return divisor;

}
	
public BigInteger getGcd(String b_num) {
	return getGcd(new BigInteger(b_num));
}


/*Test methods */
public static void main(String args[]) {
			
			System.out.println("gcd of (256,128) is "+new ExtendedEuclidean("256").getGcd("128"));
			System.out.println("Inverse of 65537 modulo 579837292 is "+new ExtendedEuclidean("579837292").getInverse("65537"));
			System.out.println("Inverse of 11483 modulo 799976616 is "+new ExtendedEuclidean("799976616").getInverse("11483"));
			System.out.println("Inverse of 11085 modulo 72172 is "+new ExtendedEuclidean("72172").getInverse("11085"));


		}
		
		
}
