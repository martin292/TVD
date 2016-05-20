package transmision;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class PATScanner extends Scanner{
	
	protected InputStream input;
	

	public PATScanner(String i){
		input = getInputStream(i);
	}
	
	/**
	 * Recorre el input en busca de las PMT
	 * @return
	 * @throws Exception
	 */
	public List<PMT> retPMTs() throws Exception{
		byte[] paquete = new byte[188];		
		List<PMT> pmts = new ArrayList<PMT>();
		boolean encontroPMTs = false;
		
		while(leerPaquete(input, paquete) && !encontroPMTs){
			if(esPAT(pid(paquete))){
				agregarPMTs(paquete, pmts);
				encontroPMTs = true;
			}
		}
		
		return pmts;
	}

	/**
	 * Agrega las pmt con pid != 0 a la lista
	 * @param paquete
	 * @param pmts
	 * @throws Exception
	 */
	private void agregarPMTs(byte[] paquete, List<PMT> pmts) throws Exception {	
		int n = (getSectionLength(paquete, 6) - 9)/4;
		
		int x = 13;
		
		for(int i = 0; i<n; i++){
			if(getProgramNumber(paquete, x) != 0x0000){
				PMT pmt = new PMT(getProgramNumber(paquete, x), getPidPMT(paquete, x+2));
				
				pmts.add(pmt);
			}	
			x = x+4;
			
		}
	}
	
	
	
	/**
	 * Retorna el pid de la pmt
	 * @param paquete
	 * @param i <---Donde se encuentra
	 * @return
	 */
	private int getPidPMT(byte[] paquete, int i) {
		return retNum(paquete, i) & 0x0fff;
	}

	/**
	 * Retorna el program number
	 * @param paquete
	 * @param x <---Donde se encuentra
	 * @return
	 */
	private int getProgramNumber(byte[] paquete, int x) {
		return retNum(paquete, x);
	}

	/**
	 * Retorna true si el pid es PAT
	 * @param pid
	 * @return
	 */
	private boolean esPAT(int pid) {
		return pid == 0x0000;
	}
	
	

	
	
}
