import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static final int N_START = 5;
	public static final int N_MAX = 10;
	public static String ENCRYPTED_TEXT;
	public static final String LOOK_FOR = "security";

	
	public static void main(String[] args) throws FileNotFoundException {

		ENCRYPTED_TEXT = (new Scanner(new File("encrypted.txt"))).useDelimiter("\\A").next();
		/*System.out.println(applyCiphers(new int[]{1,2,3},1,"aaaaaaaa"));
		System.out.println(applyCiphers(new int[]{1,2,3},1,0,"aaaaaaaa"));
		System.out.println(applyCiphers(new int[]{1,2,3},1,2,"aaaaaaaa"));
		System.out.println(applyCiphers(new int[]{1,2,3},1,1,"aaaaaaaa"));
		
		System.out.println(applyCiphers(new int[]{0},1,0,"aaa"));
		*/
		for (int n = N_START; n <= Math.min(N_MAX,LOOK_FOR.length()); n++) {
			System.out.println("Starting N: "+n);
			tryCipherLength(n);
		}
		System.out.println("Done.");
	}

	public static void tryCipherLength(int n) {
		int[] ciphers = new int[n];
		while(ciphers!=null){
			String attempt = applyCiphers(ciphers,1, LOOK_FOR);
			checkDecrypted(ciphers,attempt);
			ciphers = nextCiphers(ciphers);
		}
	}
	
	public static void checkDecrypted(int[] ciphers, String attempt){
		if(ENCRYPTED_TEXT.contains(attempt)){
			int index = ENCRYPTED_TEXT.indexOf(attempt);
			//System.out.println("Attempt = "+attempt);
			System.out.println(Arrays.toString(ciphers));
			//System.out.println("Index = "+index);
			//System.out.println("CipherOffset = "+((ciphers.length - (index % ciphers.length)) % ciphers.length));
			//System.out.println(ENCRYPTED_TEXT);
			System.out.println(applyCiphers(ciphers,-1, index, ENCRYPTED_TEXT));
			System.out.println();
		}
	}

	public static int[] nextCiphers(int[] ciphers) {
		int index = 0;
		while (index <ciphers.length && ciphers[index] == 25) {
			ciphers[index] = 0;
			index++;
		}

		if (index < ciphers.length) {
			ciphers[index] = ciphers[index] + 1;
			return ciphers;
		}
		return null;
	}

	public static String applyCiphers(int[] cipher, int direction, int offset, String text) {
		String output = "";
		int cipherOffset = (cipher.length - (offset % cipher.length)) % cipher.length;
		for (int i = 0; i < text.length(); i++) {
			output = output
					+ applyCipher(cipher[(i+cipherOffset) % cipher.length],direction, text.charAt(i));
		}

		return output;
	}
	
	public static String applyCiphers(int[] cipher, int direction, String text) {
		return applyCiphers(cipher, direction, 0, text);
	}

	public static char applyCipher(int cipher, int direction, char text) {
		return (char) ('a' + ((((text - 'a') + cipher*Math.signum(direction) + 26) % 26)));
	}
}
