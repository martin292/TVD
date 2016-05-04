package streams;

import java.io.InputStream;

public class ProgramScanner extends Stream{

	public static void main(String[] args) throws Exception {
		InputStream input = getInputStream("sample533.ts");
		byte[] buffer = new byte[188];
		
		while(hayPaquete(input, buffer)){
			imprimirPMT(buffer);
		}
		
		input.close();
	}
	
	private static void imprimirPMT(byte[] buffer){
		int sectionLength = 0;		
		
		if(buffer[5] == 0x00){			
			sectionLength = getSectionLength(buffer, 7);
		}else{
			sectionLength = getSectionLength(buffer, 6 + buffer[5]);
		}
		
		int n = (sectionLength - 9)/4;
		imprimirProgNumYPID(buffer, n);
	}

	private static void imprimirProgNumYPID(byte[] buffer, int n) {
		int x = 14;
		for(int i = 0; i<n; i++){
			System.out.println("ProgramNumber: " + getProgramNumber(buffer, x) + " -> PID: " + getPidPMT(buffer, x+2));
			x = x+4;				
		}
	}

	private static int getSectionLength(byte[] buffer, int x) {		
		return retNum(buffer, x) & 0x0fff;
	}
	
	private static int getProgramNumber(byte[] buffer, int x) {
		return retNum(buffer, x);
	}
	
	private static int getPidPMT(byte[] buffer, int x) {		
		return retNum(buffer, x) & 0x0fff;
	}	
	
}
