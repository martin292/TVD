package streams;

import java.io.InputStream;


public class Mapa extends Stream{

	public static void main(String[] args) throws Exception {
		imprimirMapa("sample533.ts");
	}
	
	private static void imprimirMapa(String ts) throws Exception{
		InputStream input = getInputStream(ts);
		byte[] buffer = new byte[188];
		
		while(hayPaquete(input, buffer)){
			imprimirPaquete(buffer);
		}
		
		input.close();
	}
	
	
	private static void imprimirPaquete(byte[] buffer) {
		if(PaqueteEnSincronia(buffer[0])){
			imprimirPID(buffer);
		}else{
			System.out.println("Paquete no sincronizado ");
		}
	}

	private static void imprimirPID(byte[] buffer) {
		int pid = pid(buffer);
		
		if(esPAT(pid)){
			System.out.println("* ");
		}else if(esNull(pid)){
			System.out.println("| ");
		}else {
			System.out.println(". ");
		}
	}

	private static boolean PaqueteEnSincronia(byte b) {
		return b == 0x0047;
	}
	
	private static boolean esPAT(int pid) {
		return pid == 0x0000;
	}
	
	private static boolean esNull(int pid) {
		return pid == 0x1fff;
	}	

	private static int pid(byte[] buffer) {		
		return retNum(buffer, 1) & 0x1fff;
	}	

}
