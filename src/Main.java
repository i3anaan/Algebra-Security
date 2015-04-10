import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static final int N_START = 1;
	public static final int N_MAX = 3;
	public static String ENCRYPTED_TEXT;
	public static final String LOOK_FOR = "encryption";

	
	public static void main(String[] args) throws FileNotFoundException {

		ENCRYPTED_TEXT = (new Scanner(new File("encrypted.txt"))).useDelimiter("\\A").next();
		for (int n = N_START; n <= Math.min(N_MAX,LOOK_FOR.length()); n++) {
			System.out.println("Starting N: "+n);
			tryCipherLength(n); //Bruteforce dictionary attack
			countLetters(n);  //Count letters, assume text only has E's
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
	
	public static void countLetters(int n){
		int[][] count = new int[n][26];
		
		for(int i=0;i<ENCRYPTED_TEXT.length();i++){
			count[i%n][(int)(ENCRYPTED_TEXT.charAt(i)-'a')] = count[i%n][(int)(ENCRYPTED_TEXT.charAt(i)-'a')]+1;  
		}
		
		int[] ciphers = buildCiphersMostOccuring(count);
		
		System.out.println(Arrays.toString(ciphers));
		System.out.println(applyCiphers(ciphers, -1, ENCRYPTED_TEXT));
		System.out.println();
		
	}
	
	public static int[] buildCiphersMostOccuring(int[][] count){
		int[] max = new int[count.length];
		int[] maxIndex = new int[count.length];
		for(int n=0;n<count.length;n++){
			maxIndex[n] = 0;
			max[n] = count[n][0];
			for(int i=1;i<count[0].length;i++){
				if(count[n][i]>max[n]){
					max[n] = count[n][i];
					maxIndex[n] = i;
				}
			}
		}
		
		for(int n=0;n<count.length;n++){
			maxIndex[n] = (maxIndex[n] - ('e'-'a')+26) % 26;
		}
		
		return maxIndex;
	}
	
	public static void checkDecrypted(int[] ciphers, String attempt){
		if(ENCRYPTED_TEXT.contains(attempt)){
			int index = ENCRYPTED_TEXT.indexOf(attempt);
			System.out.println(Arrays.toString(ciphers));
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
