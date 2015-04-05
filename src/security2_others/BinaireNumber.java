package security2_others;

public class BinaireNumber {

	private int[] number;
	
	public BinaireNumber(int length){
		number = new int[length];
	}
	
	public int[] currentNumber(){
		return number;
	}
	
	public int[] nextNumber(){
		boolean finished = false;
		int i = 0;
		while(!finished && i < number.length){
			if(number[i] == 0){
				number[i] = 1;
				i--;
				while(i>=0){
					number[i] = 0;
					i--;
				}
				finished = true;
			}
			if(!finished && number[i] == 1){
				i++;
			}
		}
		return number;
	}
	
//	public static void main(String[] args){
//		BinaireNumber bin = new BinaireNumber(12);
//		int i = 0;
//		while(i < 300){
//			System.out.println("" + BinaireNumber.toString(bin.currentNumber()));
//			bin.nextNumber();
//			i++;
//		}
//	}
//	
//	public static String toString(int[] VI){
//		String result = "";
//		for(int i = (VI.length - 1); i >= 0; i--){
//			result = result + VI[i];
//		}
//		return result;
//	}
}
