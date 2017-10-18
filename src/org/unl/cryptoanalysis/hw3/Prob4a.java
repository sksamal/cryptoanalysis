package org.unl.cryptoanalysis.hw3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;

import org.unl.cryptoanalysis.tools.ExtendedEuclidean;
import org.unl.cryptoanalysis.tools.QuickModuloExponentiation;

public class Prob4a {
	
	public static void main(String args[]) {
		String fileName = "data/cipher001.txt";
		String outFile = "data/plain001.txt";

		try {
			Scanner s = new Scanner(new File(fileName));
			PrintWriter pw = new PrintWriter(new File(outFile));
			s.nextLine();
			String n_string = s.nextLine().split("=")[1].trim();
			BigInteger n = new BigInteger(n_string);
			s.nextLine();
			String e_string = s.nextLine().split("=")[1].trim();
			BigInteger e = new BigInteger(e_string);

			System.out.println("Input:" + fileName);
			System.out.println("Output:" + outFile);
			System.out.println("e :" + e);
			pw.println("e :" + e);
			System.out.println("n :" + n);
			pw.println("n :" + n);

			BigInteger p = naiveFactorize(n); 
			BigInteger q = n.divide(p);
			System.out.println("p :" + p);
			pw.println("p :" + p);
			System.out.println("q :" + q);
			pw.println("q :" + q);
			BigInteger phi_n = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
			BigInteger d = new ExtendedEuclidean(phi_n).getInverse(e);
			System.out.println("d :" + d);
			pw.println("d :" + d + "\n");
			System.out.println("e*d (mod phi_n) :" + e.multiply(d).mod(phi_n) + "\n\n");
//			pw.println("e*d (mod phi_n) :" + e.multiply(d).mod(phi_n) + "\n\n");
			
			s.nextLine();
			s.nextLine();
			s.nextLine();

			String plainText = "";
			
			while (s.hasNext()) {
				String c_string_hex = s.nextLine().trim();

				BigInteger c = new BigInteger(c_string_hex, 16);
				System.out.print("c :" + c);
	//			pw.print("c :" + c);
				System.out.println("\t c(hex) :" + c.toString(16));
	//			pw.println("\t c(hex) :" + c.toString(16));

				BigInteger m = new QuickModuloExponentiation(c, d, n).getBigValue();
				System.out.print("m :" + m.toString());
	//			pw.print("m :" + m.toString());
				System.out.print("\tm(hex) :" + m.toString(16));
	//			pw.print("\t m(hex) :" + m.toString(16));
				
//				BigInteger c1 = new QuickModuloExponentiation(m, e, n).getBigValue();
//				System.out.println("\t c'(hex) :" + c1.toString(16));
//				pw.println("\t c'(hex) :" + c1.toString(16));

				String m_hex = m.toString(16);
				if(m_hex.length()%2!=0)
					m_hex = "0"+m_hex;
				byte[] s1 = DatatypeConverter.parseHexBinary(m_hex);
			    System.out.println("\t m(char) :" + new String(s1));
//				pw.println("\t m(char) :" + new String(s1));
				plainText+=new String(s1);			
			}			
			pw.println("PlainText:\n");
			pw.println(plainText);
			pw.close();
			s.close();
			System.out.print("Output written to "+outFile);
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found:" + fileName);
			e.printStackTrace();
		}

	}

	
	/* Uses naive method to factorize, start from approximate square root of n and
	 * decrease checking each odd number
	 */
	private static BigInteger naiveFactorize(BigInteger n) {

		BigInteger a = sqrt(n).add(BigInteger.ONE);
		if (a.mod(new BigInteger("2")).compareTo(BigInteger.ZERO) == 0)
			a = a.subtract(BigInteger.ONE);

		BigInteger two = new BigInteger("2");
		while (n.mod(a).compareTo(BigInteger.ZERO) != 0) {
			a = a.subtract(two);
		}

		return a;
	}


	/* Returns approximate square root of n */
	public static BigInteger sqrt(BigInteger n) {
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

}
