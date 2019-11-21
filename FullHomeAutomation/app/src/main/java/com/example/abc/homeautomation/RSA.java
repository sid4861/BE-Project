package com.example.abc.homeautomation;

public class RSA {

	static int RSA_P = 11, RSA_Q = 13, RSA_E = 7, RSA_D = 0;
	static int RSA_N = 0, RSA_PHI = 0;

	public RSA() {
		RSA_PHI = cal_phi(RSA_P, RSA_Q);
		RSA_N = n_value(RSA_P, RSA_Q);
	}

	/**
	 * 
	 * @param a
	 * @return
	 */
	public static long square(long a) {
		return (a * a);
	}

	public String Decrypt(String message, int privateKey) {
		int n = RSA_N;
		String decipher = "";
		char[] ar = message.toCharArray();
		String dc = "", c = "";
		int i = 0, j = 0;
		for (; i < ar.length - 2; i++) {
			c = "" + ar[i];
			try {
				for (j = (i + 1); ar[j] != '-'; j++) {
					c = c + ar[j];
				}

			} catch (Exception E) {
			}
			i = j;
			dc = dc + (char) RSA.BigMod(Integer.parseInt(c), privateKey, n);
		}
		decipher += dc;
		return decipher;
	}

	public String Encrypt(String message, int publicKey) {
		int n = RSA_N;
		String cipher = "";
		char[] ar = message.toCharArray();
		String c = "";
		for (int i = 0; i < ar.length; i++) {
			if (c.equals("")) {
				c = "" + RSA.BigMod(ar[i], publicKey, n);
			} else {
				c = c + "-" + RSA.BigMod(ar[i], publicKey, n);
			}
		}
		cipher += c;
		return cipher;
	}

	public int getPrivateKey() {
		long RES = 0;
		for (RSA_D = 1;; RSA_D++) {
			RES = (RSA_D * RSA_E) % RSA_PHI;
			if (RES == 1) {
				break;
			}
		}
		return RSA_D;
	}

	public int getPublicKey() {
		return RSA_E;
	}

	/**
	 * 
	 * @param b
	 * @param p
	 * @param m
	 * @return
	 */
	public static long BigMod(int b, int p, int m) // b^p%m=?
	{
		if (p == 0) {
			return 1;
		} else if (p % 2 == 0) {
			return square(BigMod(b, p / 2, m)) % m;
		} else {
			return ((b % m) * BigMod(b, p - 1, m)) % m;
		}
	}

	/**
	 * 
	 * @param prime1
	 * @param prime2
	 * @return
	 */
	public static int n_value(int prime1, int prime2) {
		return (prime1 * prime2);
	}

	/**
	 * 
	 * @param prime1
	 * @param prime2
	 * @return
	 */
	public static int cal_phi(int prime1, int prime2) {
		return ((prime1 - 1) * (prime2 - 1));
	}

	public static void main(String[] args) throws Exception {

		RSA rsa = new RSA();
		String cipher = "", decipher = "";

		String Hex = "SOHAM ";

		cipher = rsa.Encrypt(Hex, rsa.getPublicKey());
		System.out.println("Encrypted String: " + cipher);

		RSA rsa2 = new RSA();
		decipher = rsa.Decrypt(cipher, rsa2.getPrivateKey());
		System.out.println("Decrypted String: " + decipher);

	}

}
