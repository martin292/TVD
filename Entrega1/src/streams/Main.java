package streams;

import java.io.InputStream;


public class Main {

	protected static int paquetes = 0;
	
	public static void main(String[] args) throws Exception {
		//if(args.length != 1) {
		//	System.out.println("Se espera archivo origen");
		//	System.exit(-1);
		//}
		//InputStream input = new FileInputStream(args[0]);
		InputStream input = getInputStream();
        
        byte[] buffer = new byte[188];
        
        while(leerPaquete(input, buffer)){
        	paquetes = paquetes + 1;
        	procesarPaquete(buffer);
        }
        
        System.out.println("Cantidad de paquetes: " + paquetes);
        
        input.close();
	}
	
	private static boolean leerPaquete(InputStream input, byte[] buffer)throws Exception{
		int bytes = 0;
		
		while (bytes < buffer.length) {
			int lastReaded = input.read(buffer, bytes, buffer.length - bytes);
			
			if (lastReaded == -1) {
				return false;
			}
			
			bytes = bytes + lastReaded;
		}
		
		return true;
	}
	
	private static void procesarPaquete(byte[] buffer) {
		int byte1 = buffer[0];
		int byte2 = buffer[1];
		int byte3 = buffer[2];
		int byte4 = buffer[3];
		
		System.out.printf("byte 1 %4X\n", byte1);
		System.out.printf("byte 2 %4X\n", byte2);
		System.out.printf("byte 3 %4X\n", byte3);
		System.out.printf("byte 4 %4X\n", byte4);
		
		System.out.println(" ");
		int valorJunto = 0x00000000;
		int byte1Desplazado = byte1<<24;
		int byte2Desplazado = byte2<<16;
		int byte3Desplazado = byte3<<8;
		int byte4Desplazado = byte4<<0;
		
		valorJunto = valorJunto | byte1Desplazado;
		valorJunto = valorJunto | byte2Desplazado;
		valorJunto = valorJunto | byte3Desplazado;
		valorJunto = valorJunto | byte4Desplazado;
		
		System.out.printf("valor junto %4X\n", valorJunto);
		
		int pid = 0x0000;
		int maskPid = 0x1fff;
		int byte2pid = byte2<<8;
		int byte3pid = byte3<<0;		
		
		pid = pid | byte2pid;
		pid = pid | byte3pid;
		
		pid = pid & maskPid;
		
		System.out.printf("PID %4X\n", pid);
		System.out.println("--------");
	}
		
	private static InputStream getInputStream() throws Exception {        
        return Main.class.getClassLoader().getResourceAsStream("sample1.ts");    
    }
	
}
