package transmision;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;



public class PMTScanner extends Scanner{
	
	protected InputStream input;
	protected List<PMT> pmts = new ArrayList<PMT>();
	protected List<ElementaryStream> elementaryPIDs = new ArrayList<ElementaryStream>();
	//
	
	
	public PMTScanner(String i) throws Exception{
		input = getInputStream(i);
		pmts = new PATScanner(i).retPMTs();
	}
	
	/**
	 * Recorre el input y retorna los elementaryStreams
	 * @return
	 * @throws Exception
	 */
	protected List<ElementaryStream> retElementaryPIDs() throws Exception{
		byte[] paquete = new byte[188];
		
		while(leerPaquete(input, paquete)){
			procesar(paquete);
		}
		
		return elementaryPIDs;
	}
	
	/**
	 * Si el paquete es pmt agrega el elementaryPID a la lista
	 * @param paquete
	 * @throws Exception
	 */
	protected void procesar(byte[] paquete) throws Exception {
		if(esPMT(paquete)){
			int inicio = obtenerInicio(paquete);//puntero al for de la pmt
			int fin = obtenerFin(paquete);//fin del for de la pmt
			
			agregarElementaryPIDs(paquete, inicio, fin);
		}	
	}
	
	/**
	 * Recorre el for y agrega a la lista los elementary pid que sean del tipo 0x11 o 0x1B
	 * @param paquete
	 * @param i
	 * @param fin
	 * @throws Exception
	 */
	protected void agregarElementaryPIDs(byte[] paquete, int i, int fin) throws Exception{		
		while(i < fin){
			if(paquete[i] == 0x11 || paquete[i] == 0x1b){//paquete[i] es el Stream Type
				ElementaryStream es = new ElementaryStream(paquete[i], getElementaryPID(paquete, i+1));
				elementaryPIDs.add(es);		
			}
			i = i+3;
			i = saltearDescriptor(paquete, i);
		}
		
	}
	
	/**
	 * Retorna el inicio del for
	 * @param paquete
	 * @return
	 */
	protected int obtenerInicio(byte[] paquete){
		return 17 + getDescriptorLength(paquete, 15); 
	}
	/**
	 * Retorna el fin del for
	 * @param paquete
	 * @return
	 */
	protected int obtenerFin(byte[] paquete){
		return 7 + getSectionLength(paquete, 6) - 4;
	}

	/**
	 * Retorna TRUE si el paquete tiene pid de una pmt
	 * @param paquete
	 * @return
	 * @throws Exception
	 */
	protected boolean esPMT(byte[] paquete) throws Exception {
		boolean ret = false;
		for (PMT pmt : pmts) {
			ret = ret || pmt.igual(pid(paquete));
		}
		return ret;
	}	
		
	/**
	 * Busca en la lista pmts una PMT con pid = al parametro 
	 * @param pid
	 * @return
	 */
	protected PMT getPMT(int pid){
		for (PMT pmt : pmts) {
			if(pmt.pid == pid){return pmt;}
		}
		return null;
	}
	
	
	/**
	 * Saltea el descriptor
	 * puntero + longitud del for del descriptor
	 * @param paquete
	 * @param x
	 * @return
	 */
	protected int saltearDescriptor(byte[] paquete, int x) {
		return x + (retNum(paquete, x) & 0x0fff) + 2;
	}
	
	/**
	 * Retorna el descriptor length
	 * @param paquete
	 * @param x
	 * @return
	 */
	protected static int getDescriptorLength(byte[] paquete, int x) {
		return retNum(paquete, x) & 0x0fff;
	}
		
	/**
	 * Retorna el elementary pid del paquete
	 * @param paquete
	 * @param x
	 * @return
	 */
	protected int getElementaryPID(byte[] paquete, int x) {
		return retNum(paquete, x) & 0x1fff;
	}
		

}
