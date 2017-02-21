package es.uma.khaos.docking_service.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Results {

    List<Result> resultList;
    
    public Results() {
    	super();
    }
    
	public Results(List<Result> resultList) {
		super();
		this.resultList = resultList;
	}

	public List<Result> getResultList() {
		return resultList;
	}

	public void setResultList(List<Result> resultList) {
		this.resultList = resultList;
	}

}