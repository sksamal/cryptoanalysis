/* MD5 Implementation */
package org.unl.cryptoanalysis.tools;

import javax.xml.bind.DatatypeConverter;

public class MD5 {

	private String n;
	private long A = 0x67452301L;
	private long B = 0xefcdab89L;
	private long C = 0x98badcfeL;
	private long D = 0x10325476L;
	private long[] T = new long[64]; 
	private long[] s = new long[64];
	
	private long F(long x, long y, long z) {
		return ((x&y)|(~x&z));
	}
	
	private long G(long x, long y, long z) {
		return ((x&z)|(y&~z));
	}
	
	private long H(long x, long y, long z) {
		long x_xor_y = (x&~y)|(~x&y);
		return ((x_xor_y&~z)|(~x_xor_y&z));
	}
	
	private long I(long x, long y, long z) {
		long x_v_not_z = (x|~z);
		return ((y&~x_v_not_z)|(~y&x_v_not_z));
	}

	private long operation(long a, int round, long b, long c, long d, int i, long s) {
		int k = 0;
		switch (round) {
		case 1:
			a = a + F(b,c,d);
			k = i;
			break;
		case 2:
			a = a + G(b,c,d);
			k = (5*i + 1)%16;
			break;
		case 3:
			a = a + H(b,c,d);
			k = (3*i + 5)%16;
			break;
		case 4:
			a = a + I(b,c,d);
			k = (7*i + 1)%16;
			break;
		}
	//	a = b + (a + X[k] + T[i]) <<< s);
		
		return a;
	}
	
	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}
	
	public String getDigest() {
				String bMessage = padMessage();
				return null;
			}
	
	private String padMessage() {
		String size = Long.toHexString(n.length()*Character.SIZE);
		while(size.length()<16)
			size="0"+size;
		
		String padded_msg = (n+ (char)0x1000);
		while(padded_msg.length()%32 != 28)
			padded_msg+=(char)0x000;
		
		String hexMessage = "";
		System.out.println(padded_msg.length());
		for(char c : padded_msg.toCharArray()) {
			String c_hex = Integer.toHexString(c);
			System.out.println(c+ "=" + c_hex);
			while(c_hex.length()<4)
				c_hex="0"+c_hex;
			hexMessage+=c_hex;
		}
		
		hexMessage+=size;
		System.out.println("Length=" + hexMessage.length());
		System.out.println("Message=" + hexMessage);
		return hexMessage;
	}

	public MD5(String n) {
		this.n = n;
		double two_pow_32 = Math.pow(2, 32);
		
		for(int i=0;i<T.length;i++)
			T[i] = (long)(Math.abs(Math.sin(i+1)*two_pow_32));
		
		int j = 0;
		int[][] a = {{7,12,17,22}, {5,9,14,20}, {4,11,16,23},{6,10,15,21}};
		for(int i=0;i<64;i=i+16){
			s[i]=s[i+4]=s[i+8]=s[i+12] = a[j][0];
			s[i+1]=s[i+5]=s[i+9]=s[i+13] = a[j][1];
			s[i+2]=s[i+6]=s[i+10]=s[i+14] = a[j][2];
			s[i+3]=s[i+7]=s[i+11]=s[i+15] = a[j][3];
			j++;
		}
	}

		
	public static void main(String args[])  {
//		if(args.length < 1){
//			System.out.println("Usage: java PollardRho <n> [c] [startNum]");
//			System.exit(1);
//		}
				
		new MD5("asdsd").getDigest();
	}

}
