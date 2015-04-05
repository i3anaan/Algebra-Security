package security2_others;

import java.util.ArrayList;
import java.util.List;

public class TripleLFSR12 {

	private LFSR12 one;
	private LFSR12 two;
	private LFSR12 three;
	private int output;
	
	public TripleLFSR12(int[] coefficientsOne, int[] coefficientsTwo, int[] coefficientsThree, int[] keyOne, int[] keyTwo, int[] keyThree){
		one = new LFSR12(coefficientsOne, keyOne);
		two = new LFSR12(coefficientsTwo, keyTwo);
		three = new LFSR12(coefficientsThree, keyThree);
	}
	
	public int output(){
		int result = -1;
		int outputThree = three.step();
		int outputTwo = two.step();
		int outputOne = one.step();
		if(outputThree == 0){
			result = outputOne;
		} else {
			result = outputTwo;
		}
		return result;
	}
	
	public int[] generateOutput(int length){
		int[] generated = new int[length];
		for(int i = 0; i < length; i++){
			generated[i] = this.output();
		}
		return generated;
	}
	
	public static int equalElements(int[] fst, int[] snd){
		int counter = 0;
		if(fst.length != snd.length){
			System.out.println("De te vergelijken arrays hebben niet dezelfde lengte");
		} else {
			for(int i = 0; i < fst.length; i++){
				if(fst[i] == snd[i]){
					counter++;
				}
			}
		}
		return counter;
	}
	
	public static void main(String[] args){
		TripleLFSR12 test;
		int[] cOne =   {1,1,0,0,1,0,1,0,0,0,0,0};
		int[] cTwo =   {1,0,1,1,0,0,0,0,0,1,0,0};
		int[] cThree = {1,0,0,1,0,1,1,0,0,0,0,0};
		
		int[] opdracht = {1,0,0,1,0,1,1,1,0,1,1,1,0,0,0,0,1,1,0,0,1,
				1,0,0,0,1,0,0,0,1,0,0,1,1,0,1,0,1,0,1,
				1,1,0,0,0,1,0,1,1,1,0,0,0,0,1,1,0,1,0,0,1,0,1,1};
		
		//List<int[]> possibleKeysOne = new ArrayList<int[]>();
		List<int[]> possibleKeysTwo = new ArrayList<int[]>();
		
		BinaireNumber binOne = new BinaireNumber(12);
		BinaireNumber binTwo = new BinaireNumber(12);
		BinaireNumber binThree =  new BinaireNumber(12);
		LFSR12 generatorOne;
		TripleLFSR12 generatorTwo;
		TripleLFSR12 generatorThree;
		for(int i = 0; i < 4069; i++){
			generatorOne = new LFSR12(cOne, binOne.currentNumber().clone());
			int[] outputOne = generatorOne.generateOutput(64);
			if(equalElements(outputOne, opdracht) >= 51){
				for(int j = 0; j < 4069; j++){
					generatorTwo = new TripleLFSR12(cOne, cTwo, cThree, binOne.currentNumber().clone(), binTwo.currentNumber().clone(), new int[12]);
					int[] outputTwo = generatorTwo.generateOutput(64);
					if(equalElements(outputTwo, opdracht) >= 51){
						for(int k = 0; k < 4069; k++){
							generatorThree = new TripleLFSR12(cOne, cTwo, cThree, binOne.currentNumber().clone(), binTwo.currentNumber().clone(), binThree.currentNumber().clone());
							int[] outputThree = generatorThree.generateOutput(64);
							if(equalElements(outputThree, opdracht) >= 51){
								System.out.println("equalElements = " + equalElements(outputThree, opdracht));
								System.out.println("Possible key = " + LFSR12.toString(binOne.currentNumber().clone()) + ", " + LFSR12.toString(binTwo.currentNumber().clone()) + ", " + LFSR12.toString(binThree.currentNumber().clone()));
							}
						}
					}
					binThree = new BinaireNumber(12);
					binTwo.nextNumber();
				}
			}
			binTwo = new BinaireNumber(12);
			binOne.nextNumber();
		}
		
	}
}
