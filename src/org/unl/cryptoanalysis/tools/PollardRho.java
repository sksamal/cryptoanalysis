/* Pollard-Rho Implementation */
package org.unl.cryptoanalysis.tools;

import java.io.File;
import java.io.PrintWriter;
import java.math.BigInteger;

public class PollardRho {

	private BigInteger n;
	private BigInteger c;
	private BigInteger start;

	public PollardRho(BigInteger n, int c) {
		this.n = n;
		this.c = new BigInteger(String.valueOf(c));
	}

	public PollardRho(String moduli) {
		this(moduli, "1");
	}

	public PollardRho(String n, String c, String start) {
		this.n = new BigInteger(n);
		this.c = new BigInteger(c);
		this.start = new BigInteger(start);
	}

	public PollardRho(String n, String c) {
		this.n = new BigInteger(n);
		this.c = new BigInteger(c);
		this.start = this.sqrt(this.n);
	}

	public PollardRho(BigInteger moduli) {
		this.n = moduli;
		this.c = BigInteger.ONE;
		this.start = this.sqrt(this.n);
	}

	public BigInteger sqrt(BigInteger n) {
		BigInteger a = BigInteger.ONE;
		BigInteger b = new BigInteger(n.shiftRight(5).add(new BigInteger("8")).toString());
		while (b.compareTo(a) >= 0) {
			BigInteger mid = new BigInteger(a.add(b).shiftRight(1).toString());
			if (mid.multiply(mid).compareTo(n) > 0)
				b = mid.subtract(BigInteger.ONE);
			else
				a = mid.add(BigInteger.ONE);
		}
		return a.subtract(BigInteger.ONE);
	}

	public BigInteger getGcd(BigInteger b_num) {

		/* Step 0 - For modulo 0 and below, not possible */
		if (n.compareTo(BigInteger.ONE) < 0)
			return new BigInteger("-1");

		/*
		 * Step 1 - If num > moduli, reduce it to less than moduli
		 */
		if (b_num.compareTo(n) >= 0)
			b_num = b_num.mod(n);

		// Step 2 - If num is 0 or 1 return as-it is
		if (b_num.compareTo(BigInteger.ZERO) == 0 || b_num.compareTo(BigInteger.ONE) == 0)
			return b_num;

		BigInteger dividend = n;
		BigInteger divisor = null;
		BigInteger remainder = b_num;

		do {
			divisor = remainder;
			remainder = dividend.mod(divisor);
			dividend = divisor;
		} while (remainder.compareTo(BigInteger.ZERO) > 0);

		return divisor;

	}

	public BigInteger getGcd(String b_num) {
		return getGcd(new BigInteger(b_num));
	}

	public BigInteger[] factorize() {
		BigInteger x = this.start;
		BigInteger y = new BigInteger(x.toString());
		BigInteger d = new BigInteger("1");
		int count = 0;
		
		try {
		PrintWriter pw = new PrintWriter(new File("data/pr_"+ c + "_" + start +".txt"));
		while (d.compareTo(new BigInteger("1")) == 0) {
			x = x.multiply(x).add(c).mod(n);
			BigInteger t = y.multiply(y).add(c).mod(n);
			y = t.multiply(t).add(c).mod(n);
			pw.println( x + "," + y);
			// d = new ExtendedEuclidean(n).getGcd(x.subtract(y).abs());
			d = getGcd(x.subtract(y).abs());
			++count;
			if (count % 1000000 == 0)
				System.out.print(count + ".");
		}
		System.out.println("Iterations:" + count);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		BigInteger[] arr = new BigInteger[2];
		arr[0] = d; arr[1] = n.divide(d);
		return arr;
	}

	public void setStart(String string) {
		this.start = new BigInteger(string);
	}
	
	public static void main(String args[]) {
		if(args.length < 1){
			System.out.println("Usage: java PollardRho <n> [c] [startNum]");
			System.exit(1);
		}
		String x = args[0];
		String c = "1", startNum="2" ;

		if(args.length > 1)
			c = args[1];
		if(args.length > 2)
			startNum = args[2];
		
		PollardRho pr1 = new PollardRho(x,c,startNum);
		BigInteger pq[] = pr1.factorize();
		System.out.println("Factorization of " + x + ": " + pq[0] + " * " + pq[1]);
	}

}
