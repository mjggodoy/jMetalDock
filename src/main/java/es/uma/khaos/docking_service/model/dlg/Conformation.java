package es.uma.khaos.docking_service.model.dlg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import es.uma.khaos.docking_service.exception.PuaException;

public class Conformation {

	protected List<Atom> atoms;

	public Conformation(List<Atom> atoms) {
		this.atoms = atoms;
	}

	public Conformation(File file) throws PuaException {
		this.atoms = readAtomsFromFile(file);
	}

	public Conformation(BufferedReader br, List<String> startPrefixes, String endPrefix)
			throws PuaException {
		this.atoms = readAtomsFromReader(br, startPrefixes, endPrefix);
	}

	public List<Atom> getAtoms() {
		return atoms;
	}

	public void setAtoms(List<Atom> atoms) {
		this.atoms = atoms;
	}

	protected List<Atom> readAtomsFromFile(File file) throws PuaException {
		List<Atom> atoms = new ArrayList<Atom>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "\t");
				double x = Double.parseDouble(st.nextToken());
				double y = Double.parseDouble(st.nextToken());
				double z = Double.parseDouble(st.nextToken());
				String type = st.nextToken();
				atoms.add(new Atom(x, y, z, type));
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new PuaException("Fichero no encontrado: "+file.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
			throw new PuaException("Fallo al leer el fichero: "+file.getAbsolutePath());
		}
		
		return atoms;
	}

	protected List<Atom> readAtomsFromReader(BufferedReader br,
			List<String> startPrefixes, String endPrefix) throws PuaException {
		List<Atom> atoms = new ArrayList<Atom>();
		String line = "", line2 = "";
		int atomsCount = 0;
		try {
			while ((line = br.readLine()) != null) {
				if ((endPrefix != null) && (line.startsWith(endPrefix)))
					break;
				if (lineStartsWithOnePrefix(line, startPrefixes)) {
						atomsCount++;
						line2 = line;
						if (line.startsWith("DOCKED: ")) {
							line2 = line2.substring("DOCKED: ".length());
						}
						int index = Integer.parseInt(line2.substring(8, 11).trim());
						if (atomsCount != index) {
							br.close();
							throw new PuaException(
									"Formato de fichero no reconocido\n" + "Line: "
											+ line + "\n" + "Expected "
											+ atomsCount + " but found " + index);
						} else {
							double x = Double.parseDouble(line2.substring(30, 38)
									.trim());
							double y = Double.parseDouble(line2.substring(38, 46)
									.trim());
							double z = Double.parseDouble(line2.substring(46, 54)
									.trim());
							String type = line2.substring(77).trim();
							atoms.add(new Atom(x, y, z, type));
						}
					
	
				}
			}
		} catch (NumberFormatException e) {
			throw new PuaException("Error en línea leyendo atomo\n"
					+ line);
		} catch (NoSuchElementException e) {
			throw new PuaException("Error en línea leyendo atomo\n"
					+ line);
		} catch (IOException e) {
			e.printStackTrace();
			throw new PuaException("Fallo al leer Conformation de fichero.");
		}
		if (atoms.size() != atomsCount) {
			throw new PuaException("Formato de fichero no reconocido");
		}
		return atoms;
	}
	
	private boolean lineStartsWithOnePrefix(String line, List<String> prefixes) {
		boolean res = false;
		for (String prefix : prefixes) {
			res = res || line.startsWith(prefix);
		}
		return res;
	}

	public double calculateRmsd(Reference reference) throws PuaException {
		return this.calculateRmsd(reference, true);
	}

	public double calculateRmsd(Reference reference, boolean symmetryFlag)
			throws PuaException {
		double sqrSum = 0.0;
		if (this.atoms.size() != reference.getAtoms().size()) {
			throw new PuaException(
					"Conformaciones con diferente número de átomos. Reference (" +
					reference.getAtoms().size() + ") =/= Conformation (" +
					this.atoms.size() + ")");
		}
		for (int i = 0; i < this.atoms.size(); i++) {
			if (symmetryFlag) {
				sqrSum += this.atoms.get(i).getDistance(reference);
			} else {
				Atom atom = reference.getAtoms().get(i);
				sqrSum += this.atoms.get(i).getDistance(atom);
			}
		}
		return Math.sqrt(sqrSum / this.atoms.size());
	}

	public String toString() {
		String res = "";
		for (int i = 1; i <= atoms.size(); i++) {
			Atom a = atoms.get(i - 1);
			res += i + ": " + a + "\n";
		}
		return res;
	}

}
