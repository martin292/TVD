package streams;

import java.io.InputStream;

public abstract class Stream {

	protected static InputStream getInputStream(String ts) throws Exception {        
        return ProgramScanner.class.getClassLoader().getResourceAsStream(ts);    
    }
	
	protected static boolean hayPaquete(InputStream input, byte[] buffer) throws Exception {
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
		int byteA = buffer[x]<<8;
		int byteB = buffer[x+1]<<0;
		
		num = num | byteA;
		num = num | byteB;
		
		return num;
	}
	
}
