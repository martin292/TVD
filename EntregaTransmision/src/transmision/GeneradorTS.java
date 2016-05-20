package transmision;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GeneradorTS extends Scanner{
	
	protected InputStream input;
	protected String archivo;
	List<ElementaryStream> elementaryPIDs = new ArrayList<ElementaryStream>();
	
	public GeneradorTS(String in){
		input = getInputStream(in);
		archivo = in;
	}
	
	
	/**
	 * Recorre el input
	 * @throws Exception
	 */
	protected void generarTS() throws Exception{
		PMTScanner p = new PMTScanner(archivo);
		byte[] paquete = new byte[188];
		
		elementaryPIDs = p.retElementaryPIDs();
		
		while(leerPaquete(input, paquete)){
			escribirTS(paquete);
		}
		
		input.close();
		cerrarOutputs();
	}
	
	/**
	 * Si el paquete es un Elementary Stream escribe el output
	 * @param paquete
	 * @throws Exception
	 */
	private void escribirTS(byte[] paquete) throws Exception {
		if(esElementaryStream(paquete)){
			retES(pid(paquete)).escribir(paquete);
		}
	}

	/**
	 * Retorna un ElementaryStream con pid = al parametro
	 * @param pid
	 * @return
	 */
	private ElementaryStream retES(int pid) {
		for (ElementaryStream es : elementaryPIDs) {
			if(es.pid == pid){
				return es;
			}
		}
		return null;
	}

	/**
	 * Retorna true si el paquete es un elementary stream
	 * @param paquete
	 * @return
	 */
	private boolean esElementaryStream(byte[] paquete) {
		for (ElementaryStream es : elementaryPIDs) {
			if(es.pid == pid(paquete)){
				return true;
			}
		}
		return false;
	}
	
	private void cerrarOutputs() throws IOException {
		for (ElementaryStream es : elementaryPIDs) {
			es.cerrar();
		}		
	}

	//-----------------------------------------------------------------------------
	
	public static void main(String[] args) throws Exception {
		if(args.length == 0){
			new GeneradorTS("sampleB.ts").generarTS();//Esto lo uso para testear a mano
		}else{
			new GeneradorTS(args[0]).generarTS();
		}
	}

}
