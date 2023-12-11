package de.dicos.springboot.repairservice.restful.business;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.dicos.springboot.repairservice.restful.api.ControllerTestBase;
import de.dicos.springboot.repairservice.restful.api.KostenvoranschlagPostTO;
import lombok.NoArgsConstructor;

@NoArgsConstructor
class RepairRequestServiceTest extends ControllerTestBase {
	
	@Autowired
	private RepairRequestService repairRequestService;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void postKostenvoranschlag_shouldSucceed() {
		KostenvoranschlagPostTO request = new KostenvoranschlagPostTO();
		request.setCarModel("Audi A3");
		request.setRepairOperations(Collections.singletonList("Bremsbelagwechsel"));
		
		double price = repairRequestService.calculateExpectedCosts(request);
		assertThat(price).isEqualTo(335.21);

	}

	@Test
	void postKostenvoranschlag_shouldSucceed_whenMoreOperations() {
		KostenvoranschlagPostTO request = new KostenvoranschlagPostTO();
		request.setCarModel("Audi A3");
		List<String> list = Arrays.asList(new String[]{"Bremsbelagwechsel", "Reifenrotation"});
		request.setRepairOperations(list);
		
		double price = repairRequestService.calculateExpectedCosts(request);
		// 335.21 + 191.48 = 526.69
		assertThat(price).isEqualTo(526.69);

	}

	@Test
	void postKostenvoranschlag_shouldFail_whenNoOperations() {
		KostenvoranschlagPostTO request = new KostenvoranschlagPostTO();
		request.setCarModel("Audi A3");

		String expectedMessage = "repairOperations: darf nicht null sein";
        assertThatThrownBy(() -> repairRequestService.calculateExpectedCosts(request))
        .isInstanceOf(ConstraintViolationException.class).hasMessageContaining(expectedMessage);
	}

	@Test
	void postKostenvoranschlag_shouldFail_whenNoCarModel() {
		KostenvoranschlagPostTO request = new KostenvoranschlagPostTO();
		List<String> list = Arrays.asList(new String[]{"Bremsbelagwechsel", "Reifenrotation"});
		request.setRepairOperations(list);


		String expectedMessage = "carModel: darf nicht null sein";
        assertThatThrownBy(() -> repairRequestService.calculateExpectedCosts(request))
        .isInstanceOf(ConstraintViolationException.class).hasMessageContaining(expectedMessage);
	}

}
