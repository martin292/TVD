package transmision;

public class PMT {

	public int programNumber;
	public int pid;
	
	
	public PMT(int pn, int p) throws Exception{
		programNumber = pn;
		pid = p;
	}
	
	public boolean igual(int p) {
		return pid == p;
	}
	
}
