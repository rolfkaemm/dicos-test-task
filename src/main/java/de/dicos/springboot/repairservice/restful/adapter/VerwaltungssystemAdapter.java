package de.dicos.springboot.repairservice.restful.adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import de.dicos.springboot.repairservice.gen.model.RepairOperation;
import de.dicos.springboot.repairservice.gen.model.RepairRequest;
import de.dicos.springboot.repairservice.restful.api.TerminwunschPostTO;
import de.dicos.springboot.repairservice.restful.data.PriceEstimation;
import de.dicos.springboot.repairservice.restful.error.DicosBadRequestException;
import de.dicos.springboot.repairservice.restful.error.DicosExternalSystemException;

@Component
public class VerwaltungssystemAdapter {
	
	private static Logger log = LoggerFactory.getLogger(VerwaltungssystemAdapter.class);

    @Autowired
    @Qualifier("verwaltungRT")
    private RestTemplate restTemplate;
    
    @Value("${verwaltung.rest.url}")
    private String url;
    
    @PostConstruct
    public void configure() {
		restTemplate.setErrorHandler(new ResponseErrorHandler() {

			@Override
			public boolean hasError(ClientHttpResponse response)
				throws IOException
			{
				return !response.getStatusCode().is2xxSuccessful();
			}

			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				log.error("got error status in response: " + response.getStatusCode());
				if (response.getStatusCode().is4xxClientError()) {
					throw new DicosBadRequestException("Verwaltungssystem: Bad Request");
				} else {
				throw new DicosExternalSystemException("Verwaltungssystem: statusCode: " + response.getStatusCode());
				}
			}
		});

    }

    public void callVerwaltungssystem(final TerminwunschPostTO twRequest, final List<PriceEstimation> priceList) {
    	
    	RepairRequest request = buildRepairRequest(twRequest, priceList);
    	
        HttpHeaders headers = new HttpHeaders();  
        headers.setContentType(MediaType.APPLICATION_JSON);

		restTemplate.postForEntity(url, new HttpEntity<RepairRequest>(request, headers), Void.class);

    }
    
    private RepairRequest buildRepairRequest(final TerminwunschPostTO twRequest, final List<PriceEstimation> priceList) {
    	RepairRequest repairRequest = new RepairRequest();
    	
    	repairRequest.setCarModel(twRequest.getCarModel());
    	repairRequest.setCustomerNumber(twRequest.getCustomerNumber());
    	repairRequest.setNumberPlate(twRequest.getNumberPlate());
    	repairRequest.setPreferredDate(twRequest.getPreferredDate());
    	List<RepairOperation> repairOperationList = toRepairOperationList(priceList);
    	repairRequest.setRepairOperations(repairOperationList);
    	
    	return repairRequest;
    }
    
    private List<RepairOperation> toRepairOperationList(final List<PriceEstimation> priceList) {
    	List<RepairOperation> repairOperationList = new ArrayList<>();
    	priceList.forEach(pe -> {
    		RepairOperation ro = new RepairOperation();
    		ro.setDescription(pe.getRepairAction());
    		ro.setPriceEstimation(pe.getPriceEstimation());
    	});
    	
    	return repairOperationList;
    }
}
