/* Uses quick exponentiation method to compute 
 * exponentiation 
 */
package org.unl.cryptoanalysis.tools;

import java.math.BigInteger;

public class QuickModuloExponentiation {

		private int base;
		private int exponent;
		private int modulous;
		
		private BigInteger b_base;
		private BigInteger b_exponent;
		private BigInteger b_modulous;
		
		
		public QuickModuloExponentiation(int base,int exponent,int modulous) {
			this.base = base;
			this.exponent = exponent;
			this.modulous = modulous;
		}
		
		public QuickModuloExponentiation(BigInteger base,BigInteger exponent,BigInteger modulous) {
			this.b_base = base;
			this.b_exponent = exponent;
			this.b_modulous = modulous;
		}
		
		public QuickModuloExponentiation(String base,String exponent,String modulous) {
			this.b_base = new BigInteger(base);
			this.b_exponent = new BigInteger(exponent);
			this.b_modulous = new BigInteger(modulous);
		}

		public QuickModuloExponentiation(BigInteger base,BigInteger exponent) {
			this.b_base = base;
			this.b_exponent = exponent;
			this.b_modulous = null;
		}
		
		public QuickModuloExponentiation(String base,String exponent) {
			this.b_base = new BigInteger(base);
			this.b_exponent = new BigInteger(exponent);
			this.b_modulous = null;
		}

		public int getModulous() {
			return modulous;
		}

		public void setModulous(int modulous) {
			this.modulous = modulous;
		}

		public int getBase() {
			return base;
		}
		public void setBase(int base) {
			this.base = base;
		}
		
		public int getExponent() {
			return exponent;
		}
		public void setExponent(int exponent) {
			this.exponent = exponent;
		}
		
		/* For normal Integers */
		public long getValue() {
			
			/* Step 0 - For exponent 0 and below, not possible or trivial */
			if(this.exponent<0)
				return -1;
			
			if(this.exponent==0)
				return 1;
			
			/* Step 1 - Convert exponent to binary and compute */
			String bits = Integer.toBinaryString(this.exponent);
			long value = 1;
			for(char bit : bits.toCharArray()) {
				value = value*value;
				if(bit=='1')
					value = value*base;
				value=value%modulous;
			}
				return value;
		}

		/* For BigIntegers */
		public BigInteger getBigValue() {
			
			/* Step 0 - For exponent 0 and below, not possible or trivial */
			if(this.b_exponent.compareTo(BigInteger.ZERO) < 0)
				return new BigInteger("-1");
			
			if(this.b_exponent.compareTo(BigInteger.ZERO) == 0)
				return BigInteger.ONE;
			
			
			/* Step 1 - Convert exponent to binary and compute */
			String bits = this.b_exponent.toString(2);			
			BigInteger value = BigInteger.ONE;
			for(char bit : bits.toCharArray()) {
				value = value.multiply(value);
				if(bit=='1')
					value = value.multiply(b_base);
				
				if(b_modulous!=null)
					value=value.mod(b_modulous);
				
			}
				return value;
		}
		
		
		/* Test methods */
		public static void main(String args[]) {
			
			long value = new QuickModuloExponentiation(5,4,30000).getValue();
			BigInteger b_value = new QuickModuloExponentiation("5","4","30000").getBigValue();
			System.out.println("5 raised to power 4 (mod 30000) is " + value);
			System.out.println("5 raised to power 4 (mod 30000) is " + b_value);

			System.out.println("123 raised to power 54 (mod 678) is " + new QuickModuloExponentiation(123,54,678).getValue());
			System.out.println("123 raised to power 32 (mod 678) is " + new QuickModuloExponentiation(123,32,678).getValue());

			System.out.println("123 raised to power 54 (mod 678) is " + new QuickModuloExponentiation("123","54","678").getBigValue());
			System.out.println("2 raised to power 10 is " + new QuickModuloExponentiation("2","64").getBigValue());

			System.out.println("23456 raised to power 59489 (mod 72173) is " + new QuickModuloExponentiation("23456","59489","72173").getBigValue());
			System.out.println("39671 raised to power 11085 (mod 72173) is " + new QuickModuloExponentiation("39671","11085","72173").getBigValue());

		}
		
		
}
