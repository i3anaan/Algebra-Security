package security_as2;

public class LFSR {

	
	public int[] register;
	public int[] polynomial;
	
	public static void main(String[] args){
		//Test if LFSR works
		LFSR lfsr1 = new LFSR(fromString("000100000000"),fromString("100001010011"));
		LFSR lfsr2 = new LFSR(fromString("000100000000"),fromString("110010100001"));
		LFSR lfsr3 = new LFSR(fromString("000000001000"),fromString("100001010011"));
		LFSR lfsr4 = new LFSR(fromString("000000001000"),fromString("110010100001"));
		
		LFSR lfsr = new LFSR(fromString("001"),fromString("110"));
		
		System.out.println(doSteps(lfsr1,32));
		//System.out.println(doSteps(lfsr2,32));
		//System.out.println(doSteps(lfsr3,32));
		//System.out.println(doSteps(lfsr4,32));
		System.out.println(doSteps(lfsr,8));
		
		//Output
		//00011011111000011111010111001110
		//00010000010100100001010100100010
		//Correct
	}
	
	public static int[] fromString(String bits){
		int[] out = new int[bits.length()];
		for(int i=0;i<bits.length();i++){
			out[i] = bits.charAt(i)=='0' ? 0 : 1;
		}
		return out;
	}
	
	
	public LFSR(int[] register, int[] polynomial){
		if(register.length!=polynomial.length){
			System.err.println("Register length != polynomial length");
		}
		this.register = register;
		this.polynomial = polynomial;
	}
	
	public int doStep(){
		int[] newRegister = register;
		int newBit = 0;
		//Calculate the bit added at the start of the register
		for(int i=0;i<register.length;i++){
			newBit = newBit + polynomial[i]*register[i];
		}
		newBit = newBit % 2;
		
		//Shift the register
		for(int i=0;i<register.length-1;i++){
			register[i] = register[i+1];
		}
		register[register.length-1] = newBit;
		return getOuputBit();
	}
	
	public int getOuputBit(){
		return register[0];
	}
	
	public String getState(){
		String out = "";
		for(int b : register){
			out = out + (b+"");
		}
		return out;
	}
	
	public static String doSteps(LFSR lfsr, int steps){
		String out = "" + lfsr.getOuputBit();
		for(int i=0;i<steps;i++){
			//System.out.println(lfsr.getState());
			out = out + lfsr.doStep();
		}
		//System.out.println(lfsr.getState());
		return out;
	}
}
