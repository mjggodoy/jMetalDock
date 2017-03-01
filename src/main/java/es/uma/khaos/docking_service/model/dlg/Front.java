package es.uma.khaos.docking_service.model.dlg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Front extends ArrayList<AutoDockSolution> {
	
	private static final long serialVersionUID = 1L;
	
	public Front() {
		super();
	}
	
	public String toString() {
		String res = "Front (" + this.size() + " solutions)\n";
		int idx = 1;
		for (AutoDockSolution as : this) {
			res += "(" + idx + "): " + as;
			idx++;
		}
		return res;
	}

	public void printToTxt(String filePath) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new File(filePath));
		for (AutoDockSolution sol : this) {
			pw.println(sol.getTotalEnergy()+"\t"+sol.getEnergy1()+"\t"+sol.getEnergy2()
					+"\t"+sol.getRmsd()+"\t"+sol.getKi());
		}
		pw.close();
	}
	
}
