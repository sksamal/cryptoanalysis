package org.unl.cryptoanalysis.hw3;

import java.io.File;
import java.io.PrintWriter;
import java.math.BigInteger;

import javax.xml.bind.DatatypeConverter;

import org.unl.cryptoanalysis.tools.ExtendedEuclidean;
import org.unl.cryptoanalysis.tools.QuickModuloExponentiation;

public class Prob6 {

	public static void main(String args[]) {

		String outFile = "data/prob6-output.txt";

		try {
			PrintWriter pw = new PrintWriter(new File(outFile));
			System.out.println("Output at " + outFile);
		/* RSA Parameters */
		System.out.println("**********RSA Parameters*******************************");
		pw.println("**********RSA Parameters*******************************");

		BigInteger p = new BigInteger("12288506286091804108262645407658709962803358186316309871205769703371233115856772"
				+ "658236824631092740403057127271928820363983819544292950195585905303695015971");
		BigInteger q = new BigInteger("13144131834269512219260941993714669605006625743172006030529504645527800951523697"
				+ "620149903055663251854220067020503783524785523675819158836547734770656069477");
		BigInteger n = p.multiply(q);
		System.out.print(" Size of p = " + p.toString(16).length()*4);
		System.out.print(", Size of q = " + q.toString(16).length()*4);
		System.out.println(", Size of n = " + n.toString(16).length()*4);
		
		pw.print(" Size of p = " + p.toString(16).length()*4);
		pw.print(", Size of q = " + q.toString(16).length()*4);
		pw.println(", Size of n = " + n.toString(16).length()*4);

		BigInteger phi_n = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		BigInteger e = new BigInteger("389263491409977986099492708162379040578860053202851003849443831378701");
		BigInteger d = new ExtendedEuclidean(phi_n).getInverse(e);
		System.out.println("p=" + p);
		System.out.println("q=" + q);
		System.out.println("n=" + n);
		System.out.println("phi_n=" + phi_n);
		System.out.println("e=" + e);
		System.out.println("d=" + d);

		pw.println("p=" + p);
		pw.println("q=" + q);
		pw.println("n=" + n);
		pw.println("phi_n=" + phi_n);
		pw.println("e=" + e);
		pw.println("d=" + d);

		System.out.println("********************Messages****************************************");
		pw.println("********************Messages****************************************");
		
		/* Two messages, msg1= 71 chars and msg2 = 137 chars */
		String msg1 = "MARS is the fourth planet closest to SUN after Mercury, Venus and Earth";
		String msg2 = "MARS is also known as the red planet. The length of day on MARS is 24 hours and 35 minutes. MARS takes 587 days"
				+ "to revolve around the sun.";
		
		System.out.println("Msg1(" + msg1.length() + " chars): " + msg1);
		System.out.println("Msg2(" + msg2.length() + " chars): " + msg2);

//		System.out.println("ed mod(phi_n)" + e.multiply(d).mod(phi_n));
		String msg1_hex = DatatypeConverter.printHexBinary(msg1.getBytes());
		String msg2_hex = DatatypeConverter.printHexBinary(msg2.getBytes());
		System.out.println("Msg1[in hex](" + msg1_hex.length()*4 + " bits) :" + msg1_hex);
		System.out.println("Msg2[in hex](" + msg2_hex.length()*4 + " bits) :" + msg2_hex);

		BigInteger msg1_val = new BigInteger(msg1_hex,16);
		BigInteger msg2_val = new BigInteger(msg2_hex,16);

		BigInteger cip1_val = new QuickModuloExponentiation(msg1_val,e,n).getBigValue();
		BigInteger cip2_val = new QuickModuloExponentiation(msg2_val,e,n).getBigValue();
		
		System.out.println("********************Encrypted Messages****************************************");

		System.out.println("Cipher1[in hex]("+cip1_val.toString(16).length()*4 + " bits):" + cip1_val.toString(16));
		System.out.println("Cipher2[in hex]("+cip2_val.toString(16).length()*4 + " bits):" + cip2_val.toString(16));

		pw.println("********************Encrypted Messages****************************************");

		pw.println("Cipher1[in hex]("+cip1_val.toString(16).length()*4 + " bits):" + cip1_val.toString(16));
		pw.println("Cipher2[in hex]("+cip2_val.toString(16).length()*4 + " bits):" + cip2_val.toString(16));

		BigInteger plain1 = new QuickModuloExponentiation(cip1_val,d,n).getBigValue();
		BigInteger plain2 = new QuickModuloExponentiation(cip2_val,d,n).getBigValue();

		System.out.println("********************Decrypted Messages****************************************");
		pw.println("********************Decrypted Messages****************************************");

		String plain1_hex = plain1.toString(16);
		if(plain1_hex.length()%2!=0)
			plain1_hex = "0"+plain1_hex;
		String plain2_hex = plain2.toString(16);
		if(plain2_hex.length()%2!=0)
			plain2_hex = "0"+plain2_hex;
		
		System.out.println("Msg1[in hex]("+plain1_hex.length()*4 + " bits):" + plain1_hex);
		System.out.println("Msg2[in hex]("+plain2_hex.length()*4 + " bits):" + plain2_hex);

		pw.println("Msg1[in hex]("+plain1_hex.length()*4 + " bits):" + plain1_hex);
		pw.println("Msg2[in hex]("+plain2_hex.length()*4 + " bits):" + plain2_hex);

		byte[] s1 = DatatypeConverter.parseHexBinary(plain1_hex);
		byte[] s2 = DatatypeConverter.parseHexBinary(plain2_hex);
	    
		String dmsg1 =  new String(s1);
		String dmsg2 =  new String(s2);
	    System.out.println("Msg1("+ dmsg1.length() + " chars):" + dmsg1);
	    System.out.println("Msg2("+ dmsg2.length() + " chars):" + dmsg2);
	    pw.println("Msg1("+ dmsg1.length() + " chars):" + dmsg1);
	    pw.println("Msg2("+ dmsg2.length() + " chars):" + dmsg2);
	    pw.close();
	    
		System.out.println("Output written to: " + outFile);

	}

		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}


