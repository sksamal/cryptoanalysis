package org.unl.cryptoanalysis.hw3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.unl.cryptoanalysis.tools.ExtendedEuclidean;

public class Prob7 {

	public static void main(String args[]) {
		String fileName = "data/badKeys.txt";
		Map<String, BigInteger> cMap = read(fileName);		
		Map<String, BigInteger[]> factorMap = new HashMap<String, BigInteger[]>();
		
		String outFile = "data/prob7-output.txt";

		try {
			PrintWriter pw = new PrintWriter(new File(outFile));
		
		System.out.println("Output at " + outFile);
		System.out.println("\n********************Pairwise comparison****************************************");
		pw.println("\n********************Pairwise comparison****************************************");

		/* Compare pairwise to find factors due to weak random generator */
		for(String key1 : cMap.keySet()) {
			for(String key2 : cMap.keySet()) {
				if(key1.equals(key2)) continue;
				if(factorMap.get(key1) != null && factorMap.get(key2) != null) continue;
				BigInteger gcd = new ExtendedEuclidean(cMap.get(key1)).getGcd(cMap.get(key2));
				if(gcd.compareTo(new BigInteger("1")) != 0)  {
					BigInteger pq1[] = new BigInteger[2];
					BigInteger pq2[] = new BigInteger[2];
					pq1[0] = gcd; pq1[1] = cMap.get(key1).divide(gcd); 
					pq2[1] = gcd; pq2[0] = cMap.get(key2).divide(gcd); 
					factorMap.put(key1,pq1);
					factorMap.put(key2,pq2);
					System.out.println("[" + key1 + " and " + key2 + " share same factor]--> \n\tgcd =" + gcd);
					pw.println("[" + key1 + " and " + key2 + " share same factor]--> \n\tgcd =" + gcd);
				}
			}
		}
		System.out.println("\n********************Broken Keys****************************************");
		pw.println("\n********************Broken Keys****************************************");
		for(String key : factorMap.keySet()) {
			BigInteger pq[] = factorMap.get(key);
			System.out.println(key + ": \n\tp = " + pq[0] + "\n\tq = " + pq[1]);
			pw.println(key + ": \n\tp = " + pq[0] + "\n\tq = " + pq[1]);
		}
		pw.close();
		System.out.println("Output written to: " + outFile);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* Read keys from badKeys.txt */
	public static Map<String, BigInteger> read(String fileName) {
		Map<String, BigInteger> ciphersMap = new HashMap<String, BigInteger>();
		try {
			Scanner s = new Scanner(new File(fileName));
			while (s.hasNext()) {
				String text[] = s.nextLine().split(":");
				ciphersMap.put(text[0], new BigInteger(text[1].trim(),16));
			}
			s.close();
		} catch (FileNotFoundException e) {
			System.out.println("File " + fileName + " not found. Make sure it exists and " + "try again");
			e.printStackTrace();
		}
		return ciphersMap;
	}

}


