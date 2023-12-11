package de.dicos.springboot.repairservice.restful.api;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Repair Service Kunde Terminwunsch - Transfer Object")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TerminwunschPostTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private Integer customerNumber;
    
    @NotNull
    private String carModel;
    
    @NotNull
    private String numberPlate;
    
    @NotNull
    private String preferredDate;
    
    @NotNull
    @Size(min=1)
    private List<String> repairOperations;

	public Integer getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(Integer customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public String getNumberPlate() {
		return numberPlate;
	}

	public void setNumberPlate(String numberPlate) {
		this.numberPlate = numberPlate;
	}

	public String getPreferredDate() {
		return preferredDate;
	}

	public void setPreferredDate(String preferredDate) {
		this.preferredDate = preferredDate;
	}

	public List<String> getRepairOperations() {
		return repairOperations;
	}

	public void setRepairOperations(List<String> repairOperations) {
		this.repairOperations = repairOperations;
	}

	@Override
	public String toString() {
		return "TerminwunschPostTO [customerNumber=" + customerNumber + ", carModel=" + carModel + ", numberPlate="
				+ numberPlate + ", preferredDate=" + preferredDate + ", repairOperations=" + repairOperations + "]";
	}

}
