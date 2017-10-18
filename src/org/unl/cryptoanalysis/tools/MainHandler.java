package org.unl.cryptoanalysis.tools;

import org.unl.cryptoanalysis.hw3.Prob4a;
import org.unl.cryptoanalysis.hw3.Prob4b;
import org.unl.cryptoanalysis.hw3.Prob5;
import org.unl.cryptoanalysis.hw3.Prob6;
import org.unl.cryptoanalysis.hw3.Prob7;

public class MainHandler {

		public static void main(String args[])
		{
			try{
			if(args.length<1) 
				usage();
			else
			{
				switch(Integer.parseInt(args[0])) {
				case 1:
					System.out.println("##################################");
					System.out.println("Problem 4a - Cipher 1");
					System.out.println("##################################");
					Prob4a.main(null);
					break;
				case 2:
					System.out.println("##################################");
					System.out.println("Problem 4b - Cipher 2");
					System.out.println("##################################");
					Prob4b.main(null);
					break;
				case 3:
					System.out.println("##################################");
					System.out.println("Problem 5 - Cipher 3");
					System.out.println("##################################");
					Prob5.main(null);
					break;
				case 4:
					System.out.println("##################################");
					System.out.println("Problem 6 - (Garbled decryption)");
					System.out.println("##################################");
					Prob6.main(null);
					break;
				case 5:
					System.out.println("##################################");
					System.out.println("Problem 7 - (Bad Keys)");
					System.out.println("##################################");
					Prob7.main(null);
					break;
				default:
					usage();
				}
			}
			}
			catch (Exception e) {
				usage();
			}
		}
		
		public static void usage() {
			System.out.println("Usage:");
			System.out.println("	java -jar cryptoanalysis.jar <i>");
			System.out.println(" where i = \n 	1. CipherText001 (Problem 4a)"
					+ "\n 	2. CipherText002 (Problem 4b)"
					+ "\n 	3. CipherText003 (Problem 5)"
					+ "\n 	4. Garbled Decryption (Problem 6)"
					+ "\n 	5. badKeys.txt (Problem 7)");
			System.out.println("Example:\n	java cryptoanalysis.jar 1");
			System.exit(1);
		}
}
