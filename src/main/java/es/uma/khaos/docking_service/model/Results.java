package es.uma.khaos.docking_service.model;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="Results",
description = "Results that include all the results that were obtained for a given problem")


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

	@ApiModelProperty(value = "List of Result")
	public List<Result> getResultList() {
		return resultList;
	}

	public void setResultList(List<Result> resultList) {
		this.resultList = resultList;
	}

}