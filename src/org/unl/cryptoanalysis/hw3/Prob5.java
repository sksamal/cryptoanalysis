package org.unl.cryptoanalysis.hw3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;

import org.unl.cryptoanalysis.tools.QuickModuloExponentiation;

public class Prob5  {
	
	private static String fileName = "data/cipher003.txt";
	
	public static void main(String args[]) {

		int c1;
		if(args.length < 1) {
			c1 = 255;
		}
		else
		c1=Integer.parseInt(args[0]);
		
		/* Generate all pairs if no user specified input */
		if(c1 == 255) {
			for(int c =0;c<c1;c++)
				breakCipher(c,255);
			System.out.println("All pairs generated and matched successfuly.");
			System.out.println("Please merge the outputs from plain003-<i>.txt and sort by "
					+ "line number to get the entire plain text");
		}

		/* Run a smaller version */
		else
		{
			System.out.println("Running smaller version with first character: " + (char)c1);
			breakCipher(c1,255);
		}
		merge();
		
	}
		
	public static void breakCipher(int c1, int c_max) {
		
		String outFile = "data/003/plain003-"+c1+".txt";

		try {
			Scanner s = new Scanner(new File(fileName));
			PrintWriter pw = new PrintWriter(new File(outFile));
			s.nextLine();
			String n_string = s.nextLine().split("=")[1].trim();
			BigInteger n = new BigInteger(n_string);
			s.nextLine();
			String e_string = s.nextLine().split("=")[1].trim();
			BigInteger e = new BigInteger(e_string);

//			System.out.println("e :" + e);
//			pw.println("e :" + e);
//			System.out.println("n :" + n);
//			pw.println("n :" + n);
			
			s.nextLine();
			s.nextLine();
			s.nextLine();

			long millis = System.currentTimeMillis();

			/* Create a hash-table for every pair of plaintext */
			Map<String,String> cMap = new HashMap<String,String>();
				for(int c2 = 0; c2<c_max;c2++) {
					String c1_hex = Integer.toHexString(c1);
					String c2_hex = Integer.toHexString(c2);
					if (c1_hex.length()%2 !=0) c1_hex="0"+c1_hex;
					if (c2_hex.length()%2 !=0) c2_hex="0"+c2_hex;
					BigInteger m = new BigInteger(c1_hex+c2_hex,16);
					BigInteger cip = new QuickModuloExponentiation(m, e, n).getBigValue();
					cMap.put(cip.toString(16), m.toString(16));
				}
				
			System.out.println("Process " + c1 + ": Part-1(hash-table) completed in " + (System.currentTimeMillis() - millis) + "ms");
				
			int lineNum=0;
			while (s.hasNext()) {
				
				lineNum++;
				String c_string_hex = s.nextLine().trim();

				// Brute force attack by comparing entries from 
				// the map with cipher (since the size of input is very small 256*256)
				String m_hex = cMap.get(c_string_hex);
				if(m_hex!=null) {
					System.out.println("Match found for line: " + lineNum + ",m:" + m_hex);
					if(m_hex.length()%2!=0)
						m_hex = "0"+m_hex;
					byte[] s1 = DatatypeConverter.parseHexBinary(m_hex);
				    System.out.println("\t m(char) :" + new String(s1));

					pw.println("Line:, "+lineNum + ",m:, " + m_hex + ",m(char):, " + new String(s1));
				}
			}
				pw.close();
				s.close();
				System.out.println("Output written to "+outFile);
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found:" + fileName);
			e.printStackTrace();
		}
	}
	
	/* Merge outputs from different files */
	public static void merge() {
		
		/* Create a map and put the plain texts from 
		 * different created files
		 */
		Map<Integer,String> plainMap = new HashMap<Integer,String>();
		int max = 0;
		for (int c=0;c<255;c++) {
			String fileName = "data/003/plain003-"+c+".txt";
			try {
				Scanner s = new Scanner(new File(fileName));
				
				while (s.hasNextLine()) {
					String tokens[] = s.nextLine().split(":,");
					String line = (tokens[1].split(","))[0].trim();
					int lineNum = Integer.parseInt(line);
					if (lineNum > max)
						max = lineNum;
					
					if(tokens.length==3 || tokens[3].trim().equals(""))  {
						String nLine = s.hasNextLine() ? s.nextLine() :"";
						plainMap.put(Integer.parseInt(line), "\n"+nLine);
					}
					else
						plainMap.put(Integer.parseInt(line), tokens[3].substring(1));
				}
				s.close();

			}
			catch(Exception e) {
				System.out.println("Unable to open " + fileName);
				e.printStackTrace();
			}
		}

		String plainText = "";
		for(int i=0;i<max;i++) {
			if(plainMap.get(i)!=null)
				plainText+=plainMap.get(i);
			else plainText+="  ";
		}

		String outFile = "data/plain003.txt";

		try{
		PrintWriter pw = new PrintWriter(new File(outFile));
		pw.println("PlainText: \n" + plainText);
		System.out.println("PlainText: \n" + plainText);
		pw.close();
		}
		
		catch (Exception e) {
			System.out.println("File not found:" + outFile);
		}
			
	}
}
