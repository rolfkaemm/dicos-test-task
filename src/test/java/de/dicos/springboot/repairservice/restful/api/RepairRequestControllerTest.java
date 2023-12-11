/**
 * (c) DICOS GmbH, 2020
 *
 * $Id$
 */

package de.dicos.springboot.repairservice.restful.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import ch.qos.logback.classic.Level;
import de.dicos.springboot.repairservice.restful.error.DomainServiceException;
import de.dicos.springboot.repairservice.restful.error.ErrorResponseTO;
import lombok.NoArgsConstructor;

/**
 *
 * @author rk
 */
@NoArgsConstructor
public class RepairRequestControllerTest
	extends
	ControllerTestBase
{
	// /////////////////////////////////////////////////////////
	// Class Members
	// /////////////////////////////////////////////////////////

	private static Logger log = LoggerFactory.getLogger(RepairRequestControllerTest.class);

	// /////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////


	// /////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////

	@BeforeAll
	public static void initialSetup()
	{
		setLogLevel("", Level.DEBUG);
	}

	@BeforeEach
	public void cleanupBeforeTest()
	{
		super.cleanupBeforeTest();
		
		restTemplate.getRestTemplate().setErrorHandler(new ResponseErrorHandler() {

			@Override
			public boolean hasError(ClientHttpResponse response)
				throws IOException
			{
				return !response.getStatusCode().is2xxSuccessful();
			}

			@Override
			public void handleError(ClientHttpResponse response)
				throws IOException
			{
				if (response.getStatusCode() == expectedStatus) {
					log.info("Got expected non-success status " + expectedStatus);
				} else {
					log.error("got error status in response: " + response.getStatusCode());
				}
			}
		});

	}

	@Test
	public void postKostenvoranschlag_shouldSucceed()
	{
		log.info("#### " + new Exception().getStackTrace()[0].getMethodName());
		
		KostenvoranschlagPostTO postTO = new KostenvoranschlagPostTO();
		postTO.setCarModel("Audi A3");
		List<String> list = Arrays.asList(new String[]{"Bremsbelagwechsel", "Reifenrotation", "Getriebeölwechsel"});
		postTO.setRepairOperations(list);

        HttpHeaders headers = new HttpHeaders();  
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<KostenvoranschlagPostTO> request = new HttpEntity<KostenvoranschlagPostTO>(
                postTO, headers);
        
		Double value = restTemplate.postForObject(restBase + "cost", request, Double.class);
        assertThat(value).isEqualTo(Double.parseDouble("987.66"));
	}

	@Test
	public void postKostenvoranschlag_shouldFail_carModelMissing()
	{
		log.info("#### " + new Exception().getStackTrace()[0].getMethodName());
		
		KostenvoranschlagPostTO postTO = new KostenvoranschlagPostTO();
		List<String> list = Arrays.asList(new String[]{"Bremsbelagwechsel", "Reifenrotation"});
		postTO.setRepairOperations(list);

        HttpHeaders headers = new HttpHeaders();  
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<KostenvoranschlagPostTO> request = new HttpEntity<KostenvoranschlagPostTO>(
                postTO, headers);
        
		var errorResponseTO = restTemplate.postForObject(restBase + "cost", request, ErrorResponseTO.class);
        assertThat(errorResponseTO.getStatus()).isEqualTo("BAD_REQUEST");
        assertThat(errorResponseTO.getMessage()).contains("carModel: darf nicht null sein");
	}
	
	@Test
	public void postTerminwunsch_shouldSucceed()
	{
		log.info("#### " + new Exception().getStackTrace()[0].getMethodName());
		
		TerminwunschPostTO postTO = new TerminwunschPostTO();
		postTO.setCustomerNumber(4711);
		postTO.setNumberPlate("778844");
		postTO.setPreferredDate("31.01.2024");
		postTO.setCarModel("Audi A3");
		List<String> list = Arrays.asList(new String[]{"Bremsbelagwechsel", "Reifenrotation", "Getriebeölwechsel"});
		postTO.setRepairOperations(list);

        HttpHeaders headers = new HttpHeaders();  
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TerminwunschPostTO> request = new HttpEntity<TerminwunschPostTO>(
                postTO, headers);
        
        ResponseEntity<String> restServiceResult = restTemplate.postForEntity(restBase + "date", request, String.class);
		if (HttpStatus.OK.equals(restServiceResult.getStatusCode())) {
			assertThat(restServiceResult.getBody()).isEqualTo("OK");
		} else {
			log.error(restServiceResult.getStatusCode() + ": " + restServiceResult.getBody());
		}
	}
	
	
	// /////////////////////////////////////////////////////////
	// Inner Classes
	// /////////////////////////////////////////////////////////

}
