package de.dicos.springboot.repairservice.restful.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import de.dicos.springboot.repairservice.restful.adapter.VerwaltungssystemAdapter;
import de.dicos.springboot.repairservice.restful.api.KostenvoranschlagPostTO;
import de.dicos.springboot.repairservice.restful.api.TerminwunschPostTO;
import de.dicos.springboot.repairservice.restful.data.PriceEstimation;
import de.dicos.springboot.repairservice.restful.data.PriceEstimations;

@Validated
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RepairRequestService {
	
	@Autowired
	private PriceEstimations priceEstimations;
	
	@Autowired
	private VerwaltungssystemAdapter verwaltungssystemAdapter;

	/**
	 * Ermittelt für ein Car Model und eine Reparaturliste den Kostenvoranschlag.
	 *  
	 * @param request KostenvoranschlagPostTO
	 * @return double expected cost estimate 
	 * @throws RuntimeException wenn das Model oder eine Reparatur nicht bekannt sind.  
	 */
	public double calculateExpectedCosts(@NotNull @Valid final KostenvoranschlagPostTO request) {
		BigDecimal[] estimatedPrices = {BigDecimal.valueOf(0)};
		List<PriceEstimation> priceList = findPriceEstimations(request.getCarModel(), request.getRepairOperations());
		priceList.forEach(pe -> {
			BigDecimal currentPrice = BigDecimal.valueOf(pe.getPriceEstimation());
			estimatedPrices[0] = estimatedPrices[0].add(currentPrice);
		});
		
		return estimatedPrices[0].doubleValue();
	}

	/**
	 * Einen Reparatur Wunschtermin abgeben 
	 * 
	 * @param request Terminwunsch
	 * @return void
	 */
	public void proposeRepairDate(@NotNull @Valid final TerminwunschPostTO request) {
		List<PriceEstimation> priceList = new ArrayList<>();
		request.getRepairOperations().forEach(ro -> {
			PriceEstimation pe = priceEstimations.findPreisEstimation(request.getCarModel(), ro);
			priceList.add(pe);
		});
		
		verwaltungssystemAdapter.callVerwaltungssystem(request, priceList);
	}
	
	private List<PriceEstimation> findPriceEstimations(String carModel, List<String> repairOperations) {
		List<PriceEstimation> priceList = new ArrayList<>();
		repairOperations.forEach(ro -> {
			priceList.add(priceEstimations.findPreisEstimation(carModel, ro));
	    });
		
		return priceList;
	}
}
