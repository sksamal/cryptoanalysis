package org.unl.cryptoanalysis.hw3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;

import org.unl.cryptoanalysis.tools.ExtendedEuclidean;
//import org.unl.cryptoanalysis.tools.PollardRho;
import org.unl.cryptoanalysis.tools.QuickModuloExponentiation;

public class Prob4b {
	
	
	/* Problem 2
	 * factored to 780535675704985143085937429075336653<36> · 870392276127072787672802596403119153<36>
	 * using factordb.com
	 */

	public static void main(String args[]) {
		String fileName = "data/cipher002.txt";
		String outFile = "data/plain002.txt";

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
			
			/* Pollard-Rho didnot work */
//			BigInteger pq[] = new PollardRho(n).factorize();  // Did not work (was taking a long time) 
//			BigInteger p = pq[0];
//			BigInteger q = pq[1];

			/* Used factordb.com */
			BigInteger p = new BigInteger("780535675704985143085937429075336653"); // Factor DB
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
//				pw.print("c :" + c);
				System.out.println("\t c(hex) :" + c.toString(16));
//				pw.println("\t c(hex) :" + c.toString(16));

				BigInteger m = new QuickModuloExponentiation(c, d, n).getBigValue();
				System.out.print("m :" + m.toString());
//				pw.print("m :" + m.toString());
				System.out.print("\tm(hex) :" + m.toString(16));
//				pw.print("\t m(hex) :" + m.toString(16));
	
				
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
			System.out.println("Output written to "+outFile);
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found:" + fileName);
			e.printStackTrace();
		}

	}

}
