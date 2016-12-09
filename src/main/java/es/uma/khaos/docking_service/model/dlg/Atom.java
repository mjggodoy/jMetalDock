package es.uma.khaos.docking_service.model.dlg;

public class Atom {
	
	private double x;
	private double y;
	private double z;
	
	private String type;
	
	public Atom(double x, double y, double z, String type) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = type;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public double getDistance(Atom atom) {
		double res = Math.pow(this.x-atom.x, 2)
				+ Math.pow(this.y-atom.y, 2)
				+ Math.pow(this.z-atom.z, 2);
		//return Math.sqrt(res);
		return res;
	}
	
	public double getDistance(Conformation conformation) {
		return this.getDistance(conformation, false);
	}
	
	public double getDistance(Conformation conformation, boolean print) {
		double res = Double.MAX_VALUE;
		int i=0;
		for (Atom atom : conformation.getAtoms()) {
			if (atom.getType().equals(this.type)) {
				if (print) {
					System.out.println("type="+atom.type+"/"+this.type+"      Crd[0][0]="
							+this.x+"   CrdRef["+i+"][0]="+atom.x+"   dc[0]="+(this.x-atom.x));
					System.out.println("type="+atom.type+"/"+this.type+"      Crd[0][1]="
							+this.y+"   CrdRef["+i+"][1]="+atom.y+"   dc[1]="+(this.y-atom.y));
					System.out.println("type="+atom.type+"/"+this.type+"      Crd[0][2]="
							+this.z+"   CrdRef["+i+"][2]="+atom.z+"   dc[2]="+(this.z-atom.z));
				}

				double distance = this.getDistance(atom);
				res = Math.min(distance, res);
			}
			i++;
		}
		return res;
	}

	@Override
	public String toString() {
		return "Atom [x=" + x + ", y=" + y + ", z=" + z + ", type=" + type
				+ "]";
	}
	
}
