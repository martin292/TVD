package streams;

import java.io.InputStream;

public class ProgramScanner extends Stream{

	public static void main(String[] args) throws Exception {
		InputStream input = getInputStream("sampleA.ts");
		byte[] buffer = new byte[188];
		
		while(leerPaquete(input, buffer)){
			if(esPAT(pid(buffer))){
				imprimirPMT(buffer);
			}
		}
		input.close();
	}
	
	private static void imprimirPMT(byte[] buffer){
		int sectionLength = getSectionLength(buffer, 6); //
		
		int n = (sectionLength - 9)/4;
		
		imprimirProgNumYPID(buffer, n);
	}

	private static void imprimirProgNumYPID(byte[] buffer, int n) {
		int x = 13;
		for(int i = 0; i<n; i++){			
			System.out.printf("ProgramNumber: %4X\n", getProgramNumber(buffer, x));
			System.out.printf("Pid: %4X\n", getPidPMT(buffer, x+2));
			System.out.println(" ");
			
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


