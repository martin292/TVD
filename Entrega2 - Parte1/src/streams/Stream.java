package streams;

import java.io.InputStream;

public abstract class Stream {

	protected static InputStream getInputStream(String ts) throws Exception {        
        return Stream.class.getClassLoader().getResourceAsStream(ts);    
    }
	
	protected static boolean leerPaquete(InputStream input, byte[] buffer) throws Exception {
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
	
	protected static int retNum(byte[] buffer, int x){
		int num = 0x0000;
		int byteA = (buffer[x] & 0xff)<<8;
		int byteB = (buffer[x+1] & 0xff)<<0;
		
		num = num | byteA;
		num = num | byteB;
		
		return num;
	}
	
	protected static int pid(byte[] buffer) {		
		return retNum(buffer, 1) & 0x1fff;
	}
	
	protected static boolean esPAT(int pid) {
		return pid == 0x0000;
	}

	
	
}
