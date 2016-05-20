package transmision;

import java.io.InputStream;

public abstract class Scanner {

	protected boolean leerPaquete(InputStream input, byte[] paquete) throws Exception {
		int bytes = 0;
		
		while (bytes < paquete.length) {
			int lastReaded = input.read(paquete, bytes, paquete.length - bytes);
			
			if (lastReaded == -1) {
				return false;
			}
			
			bytes = bytes + lastReaded;
		}
		
		return true;
	}
	
	/**
	 * Retorna el input
	 * @param ts
	 * @return
	 */
	protected InputStream getInputStream(String ts){        
        return Scanner.class.getClassLoader().getResourceAsStream(ts);    
    }
	
	/**
	 * Junta y retorna 2 bytes
	 * @param paquete
	 * @param x
	 * @return
	 */
	protected static int retNum(byte[] paquete, int x){
		int num = 0x0000;
		int byteA = (paquete[x] & 0xff)<<8;
		int byteB = (paquete[x+1] & 0xff)<<0;
		
		num = num | byteA;
		num = num | byteB;
		
		return num;
	}
	
	/**
	 * Retorna el pid del paquete
	 * @param paquete
	 * @return
	 */
	protected int pid(byte[] paquete){
		return retNum(paquete, 1) & 0x1fff;
	}
	
	/**
	 * Retorna el section length
	 * @param paquete
	 * @param x
	 * @return
	 */
	protected static int getSectionLength(byte[] paquete, int x) {		
		return retNum(paquete, x) & 0x0fff;
	}
	
}
