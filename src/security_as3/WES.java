package security_as3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class WES {

	public static int[] sbox = new int[256];
	public static final int MAX_KEY = ((int) Math.pow(2,24))-1;
	public static final int MIN_KEY = 0;
	
	private int key;
	
	
	public static void main(String[] args) throws FileNotFoundException{
		generateInverses();
		
		List<Integer> input = new ArrayList<Integer>();
		Scanner scanner = (new Scanner(new File("assignment_3.txt"))).useDelimiter("\n");
		while(scanner.hasNext()){
			input.add(Integer.parseInt(scanner.next().trim()));
		}
		Integer[] inputArr = input.toArray(new Integer[0]);
		System.out.println(Arrays.toString(input.toArray(new Integer[0])));
		
		WES wes = new WES(15782830);
		System.out.println(Arrays.toString(wes.decrypt(wes.encrypt(inputArr))));
		
		for(int i=MIN_KEY;i<=MAX_KEY;i++){
			wes.setKey(i);
			String decrypted = toASCII(wes.decrypt(inputArr));
			if(decrypted.contains("the")){
				System.out.println(decrypted);
				System.out.println(String.format("%24s",Integer.toBinaryString(i)).replace(' ', '0'));
			}
		}
	}
	
	public WES(int key){
		setKey(key);
	}
	
	public void setKey(int key){
		//System.out.println("Using key: "+String.format("%24s",Integer.toBinaryString(key)).replace(' ', '0'));
		this.key = key;
	}
	
	public int inverse(int b){
		return sbox[b];
	}
	
	public int encrypt(int b){
		int cipherText = b;
		for(int i=0;i<3;i++){
			cipherText = addKey(shiftEncrypt(inverse(cipherText)),(key >> (8*i)));
		}
		return cipherText;
	}
	
	public int[] encrypt(Integer[] inputArr){
		int[] output = new int[inputArr.length];
		for(int i=0;i<inputArr.length;i++){
			output[i] = encrypt(inputArr[i]);
		}
		return output;
	}
	public int[] encrypt(int[] inputArr){
		int[] output = new int[inputArr.length];
		for(int i=0;i<inputArr.length;i++){
			output[i] = encrypt(inputArr[i]);
		}
		return output;
	}
	
	public int decrypt(int b){
		int cipherText = b;
		for(int i=2;i>=0;i--){
			cipherText = inverse(shiftDecrypt(addKey(cipherText,(key >> 8*i))));
		}
		return cipherText;
	}
	
	public int[] decrypt(Integer[] inputArr){
		int[] output = new int[inputArr.length];
		for(int i=0;i<inputArr.length;i++){
			output[i] = decrypt(inputArr[i]);
		}
		return output;
	}
	
	public int[] decrypt(int[] inputArr){
		int[] output = new int[inputArr.length];
		for(int i=0;i<inputArr.length;i++){
			output[i] = decrypt(inputArr[i]);
		}
		return output;
	}
	
	
	public static int gmul(int a, int b){
		//Source: http://en.wikipedia.org/wiki/Finite_field_arithmetic#C_programming_example
		int p = 0;
		int counter;
		for(counter = 0;counter<8;counter++){
			if((b & 1)==1){
				p ^= a;
			}
			a <<= 1;
			if((a & 256) == 256){
				a ^= 283;
			}
			b >>= 1;
		}
		return p;
	}
	
	public static void generateInverses(){
		for(int a=0;a<256;a++){
			for(int b=0;b<256;b++){
				int i = gmul(a,b);
				if(i==1){
					sbox[a] = b;
					b = 256; //Skip rest of loop;
				}
			}
		}
	}
	
	public static int shiftEncrypt(int b){
		int msb = b >> 7;
		int out = ((b << 1) & 255) | msb;
		return out;
	}
	
	public static int shiftDecrypt(int b){
		int lsb = b & 1;
		int out = ((b >> 1) & 255) | lsb << 7;
		return out;
	}
	
	public static int addKey(int b, int key){
		return (b ^ (key & 255));
	}
	
	
	public static String toASCII(int[] input){
		String out = "";
		for(int i=0;i<input.length;i++){
			out += (char) input[i];
		}
		return out;
	}
}
