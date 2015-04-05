package security2_others;

public class LFSR {

	public LFSR() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		int[] input = new int[]{0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0};
		
		int[] pol1 = new int[]{0,5,7,10,11};
		int[] pol2 = new int[]{0,3,8,9,11};
		int[] pol3 = new int[]{0,5,6,8,11};
		
		lfsr(input, pol1);
		lfsr(input, pol2);
		lfsr(input, pol3);
	}
	// shift all bits one position to the left
	public static int[] shiftLeft(int[] input) {
		int[] register = new int[input.length+1];
		
		for(int i=0; i<input.length; i++){
			register[i] = input[i];
			System.out.println("Input:    "+ input[0] + input[1] + input[2] + input[3] + input[4] + input[5] + input[6] + input[7] + input[8] + input[9] + input[10] + input[11] + input[12] + input[13] + input[14] + input[15]);
			System.out.println("Register: "+ register[0] + register[1] + register[2] + register[3] + register[4] + register[5] + register[6] + register[7] + register[8] + register[9] + register[10] + register[11] + register[12]+ register[13]+ register[14]+ register[15]+ register[16]);
			System.out.println("----------");
		}
		register[input.length] = 0;
		
		return register;
	}
	
	//The first lfsr
	public static int[] lfsr(int[] input, int[] polynome){
		
		int[] register = shiftLeft(input);
		System.out.println("Polynomial: "+ polynome[0] +","+ polynome[1] +","+ polynome[2] +","+ polynome[3] +","+ polynome[4]);
		System.out.println("Register: "+ register[0] + register[1] + register[2] + register[3] + register[4] + register[5] + register[6] + register[7] + register[8] + register[9] + register[10] + register[11]);
		register[polynome[0]] = (input[polynome[0]] ^ input[0]);
		register[polynome[1]] = (input[polynome[1]] ^ input[0]);
		register[polynome[2]] = (input[polynome[2]] ^ input[0]);
		register[polynome[3]] = (input[polynome[3]] ^ input[0]);
		register[polynome[4]] = (0 ^ input[0]);
		
		System.out.println("Register: "+ register[0] + register[1] + register[2] + register[3] + register[4] + register[5] + register[6] + register[7] + register[8] + register[9] + register[10] + register[11]);
		System.out.println("--------");
		
		return register;
	}
	
	

}