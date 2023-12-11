package de.dicos.springboot.repairservice.restful.data;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"carModel", "repairAction", "priceEstimation"})
public class PriceEstimation {
	
	private String carModel;
	private String repairAction;
	private Double priceEstimation;
	
	public PriceEstimation() {
		// no argument constructor required by Jackson
	}
	
	public PriceEstimation(String carModel, String repairAction, Double priceEstimation) {
		super();
		this.carModel = carModel;
		this.repairAction = repairAction;
		this.priceEstimation = priceEstimation;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public String getRepairAction() {
		return repairAction;
	}

	public void setRepairAction(String repairAction) {
		this.repairAction = repairAction;
	}

	public Double getPriceEstimation() {
		return priceEstimation;
	}

	public void setPriceEstimation(Double priceEstimation) {
		this.priceEstimation = priceEstimation;
	}

	@Override
	public String toString() {
		return "PriceEstimation [carModel=" + carModel + ", repairAction=" + repairAction + ", priceEstimation="
				+ priceEstimation + "]";
	}

}
