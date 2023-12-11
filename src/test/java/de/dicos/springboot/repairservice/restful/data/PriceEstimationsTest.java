package de.dicos.springboot.repairservice.restful.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.dicos.springboot.repairservice.restful.api.ControllerTestBase;
import lombok.NoArgsConstructor;

@NoArgsConstructor
class PriceEstimationsTest extends ControllerTestBase {

	@Autowired
	private PriceEstimations priceEstimations;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void find_shouldSucceed() {
		final String carModel = "Audi A3";
		final String repairAction = "Reifenrotation";
		
		PriceEstimation priceEstimation = priceEstimations.findPreisEstimation(carModel, repairAction);
		assertThat(priceEstimation).isNotNull();
		
	}

	@Test
	void find_shouldSucceed_WhenCaseDiffers() {
		final String carModel = "audi a3";
		final String repairAction = "reifenrotation";
		
		PriceEstimation priceEstimation = priceEstimations.findPreisEstimation(carModel, repairAction);
		assertThat(priceEstimation).isNotNull();
	}

	@Test
	void find_shouldFail_WhenNotFound() {
		final String carModel = "Audi 3";
		final String repairAction = "Reifenrotation";
		final String expectedMessage = "Could not find carModel or repairAction";
		
        assertThatThrownBy(() -> priceEstimations.findPreisEstimation(carModel, repairAction))
        .isInstanceOf(RuntimeException.class).hasMessage(expectedMessage);
	}

}
