package security2_others;

public class LFSR12 {

	/**Array waarin de coefficient van deze LFSR wordt bijgehouden
	 * op index 0 staat coefficient X0 etc. 
	 **/
	private int[] coefficients;
	private int[] afterShiftVI;
	private int[] afterXORVI;
	private int output = -1;
	
	/**
	 * 
	 * @param coefficients: geeft aan welke onderdelen geXORd worden.
	 * @param input: binaire weergave van een getal met een lengte van twaalf cijfers
	 * @requires input.length = 12
	 */
	public LFSR12(int[] coefficients, int[] input){
		if(coefficients.length == 12){
			this.coefficients = coefficients;
			afterShiftVI = input;
			afterXORVI = input;
		} else {
			System.out.println("coefficients is niet van de juiste lengte");
		}
	}
	
	public void setCoefficients(int[] coefficients){
		if(coefficients.length == 12){
			this.coefficients = coefficients;
		} else {
			System.out.println("coefficients heeft niet de juiste lengte");
		}
	}
	
	public void setInput(int[] input){
		if(input.length == 12){
			this.afterShiftVI = input;
			this.afterXORVI = input;
		} else {
			System.out.println("input heeft niet de juiste lengte");
		}
	}
	
	public int[] generateOutput(int length){
		int[] result = new int[length];
		for(int i = 0; i < length; i++){
			shift();
			XOR();
			result[i] = output();
		}
		return result;
	}
	
	public void shift(){
		output = afterXORVI[11];
		for(int i = (afterShiftVI.length - 1); i > 0; i--){
			afterShiftVI[i] = afterXORVI[i-1];
		}
		afterShiftVI[0] = 0;
	}
	
	public int output(){
		return output;
	}
	
	public void XOR(){
		for(int i = (coefficients.length - 1); i >= 0; i--){
			if(coefficients[i] == 1 && output == 1){
				afterXORVI[i] = ((afterShiftVI[i] + 1) % 2);
			}
		}
	}
	
	public static String toString(int[] VI){
		String result = "";
		for(int i = (VI.length - 1); i >= 0; i--){
			result = result + VI[i];
		}
		return result;
	}
	
	public int step(){
		shift();
		XOR();
		return output();
	}
	
//	public static void main(String[] args){
//		int[] input = {0,0,0,0,0,0,0,0,1,0,0,0};
//		int[] coefficients = {1,1,0,0,1,0,1,0,0,0,0,0};
//		LFSR12 one = new LFSR12(coefficients, input);
//		System.out.println("" + toString(input));
//		int[] output = one.generateOutput(32);
//		System.out.println("" + toString(output));
//		System.out.println(output[3]);
//		
//	}
}
