package es.uma.khaos.docking_service.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "results")
public class Results {

    List<Solution> resultList;

	public Results() {
		super();
	}

	public List<Solution> getResultList() {
		return resultList;
	}

	public void setResultList(List<Solution> resultList) {
		this.resultList = resultList;
	}

}