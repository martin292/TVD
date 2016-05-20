package transmision;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ElementaryStream {
	
	public int pid;
	public int type;
	public OutputStream output;

	
	public ElementaryStream(int t, int p) throws Exception{
		pid = p;
		type = t;
		output = getOutputStream();
	}
	
	
	/**
	 * Escribe todo un paquete en el archivo output
	 * @param paquete
	 * @throws IOException
	 */
	public void escribir(byte[] paquete) throws IOException {
		for (int i = 0; i < paquete.length; i++) {
			output.write(paquete[i]);
		}
	}
	
	/**
	 * Cierra el output
	 * @throws IOException
	 */
	public void cerrar() throws IOException{
		output.close();
	}
	
	/**
	 * Retorna un output
	 * @return
	 * @throws Exception
	 */
	private OutputStream getOutputStream() throws Exception {
		return new FileOutputStream("ES-" + pid + ".ts");
	}
	
}
