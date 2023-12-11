package de.dicos.springboot.repairservice.restful.api;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Repair Service Kostenvoranschlag anfragen - Transfer Object")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class KostenvoranschlagPostTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private String carModel; 
    
    @NotNull
    @Size(min=1)
    private List<String> repairOperations;

    
	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public List<String> getRepairOperations() {
		return repairOperations;
	}

	public void setRepairOperations(List<String> repairOperations) {
		this.repairOperations = repairOperations;
	}

	@Override
	public String toString() {
		return "KostenvoranschlagPostTO [carModel=" + carModel + ", repairOperations=" + repairOperations + "]";
	}

}
