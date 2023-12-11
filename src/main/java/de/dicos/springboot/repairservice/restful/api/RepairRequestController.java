/**
 * (c) DICOS GmbH, 2023
 *
 * $Id$
 */

package de.dicos.springboot.repairservice.restful.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.dicos.springboot.repairservice.restful.business.RepairRequestService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author jtibke
 */
@NoArgsConstructor
@Slf4j
@RestController
@RequestMapping(path = "/rest",
    consumes = MediaType.APPLICATION_JSON_VALUE
)
public class RepairRequestController
{
	// /////////////////////////////////////////////////////////
	// Class Members
	// /////////////////////////////////////////////////////////

//	@Autowired
//	private AdministrationService administrationService;

	@Autowired
	private RepairRequestService repairRequestService;

	// /////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////


	// /////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////

//	@GetMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
//	public void sendRepairRequest(@ParameterObject RepairRequest request)
//		throws Exception
//	{
//		log.info("sending new repair request to administration");
//
//		administrationService.postRepairRequest(request);
//	}

	
	@PostMapping(value = "/cost", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Double> calculateExpectedCosts(@RequestBody KostenvoranschlagPostTO request)
		throws Exception
	{
		log.info("Kotenvoranschlag berechnen: " + request);

		double price = repairRequestService.calculateExpectedCosts(request);
		
		return ResponseEntity.ok(price);
	}

	@PostMapping(value = "/date", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> proposeRepairDate(@RequestBody TerminwunschPostTO request)
		throws Exception
	{
		log.info("Terminwunsch senden");

		repairRequestService.proposeRepairDate(request);
		
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}

	
	// /////////////////////////////////////////////////////////
	// Inner Classes
	// /////////////////////////////////////////////////////////


}
