package es.uma.khaos.docking_service.model.dlg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import es.uma.khaos.docking_service.exception.DlgParseException;

public class Reference extends Conformation {

	public Reference(List<Atom> atoms) {
		super(atoms);
	}

	public Reference(File pdbqtFile) throws DlgParseException {
		super(pdbqtFile);
	}

	public Reference(File dpfFile, String pdbqtFolder) throws DlgParseException {
		super(getPdbqtFile(dpfFile, pdbqtFolder));
	}

	@Override
	protected List<Atom> readAtomsFromFile(File file) throws DlgParseException {
		try {
			List<String> prefixes = Arrays.asList("HETATM", "ATOM");
			BufferedReader br = new BufferedReader(new FileReader(file));
			List<Atom> atoms = readAtomsFromReader(br, prefixes, null);
			br.close();
			return atoms;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DlgParseException(
					"Fallo al crear un objeto de la clase Reference del fichero:\n"
							+ file.getAbsolutePath());
		}
	}

	private static File getPdbqtFile(File dpfFile, String pdbqtFolder)
			throws DlgParseException {
		String pdbqtName = getPdbqtFileName(dpfFile);
		return new File(pdbqtFolder + pdbqtName);
	}

	private static String getPdbqtFileName(File dpfFile) throws DlgParseException {
		String name = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(dpfFile));
			String line;
			while ((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line);
				if ("move".equals(st.nextToken())) {
					if (name == null) {
						name = st.nextToken();
					} else {
						br.close();
						throw new DlgParseException(
								"Dos nombres encontrados en el DPF.");
					}
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new DlgParseException("Fallo al leer el fichero: "
					+ dpfFile.getAbsolutePath());
		}
		return name;
	}

}