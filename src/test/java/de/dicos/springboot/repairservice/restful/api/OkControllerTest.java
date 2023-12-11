/**
 * (c) DICOS GmbH, 2020
 *
 * $Id$
 */

package de.dicos.springboot.repairservice.restful.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import lombok.NoArgsConstructor;

/**
 *
 * @author sth
 */
@NoArgsConstructor
public class OkControllerTest
	extends
	ControllerTestBase
{
	// /////////////////////////////////////////////////////////
	// Class Members
	// /////////////////////////////////////////////////////////

	private static Logger log = LoggerFactory.getLogger(OkControllerTest.class);

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
	}

	@Test
	public void testOk()
	{
		log.info("#### " + new Exception().getStackTrace()[0].getMethodName());

		String value = restTemplate.getForObject(restBase + "ok",
			String.class);

		log.debug("Test got " + value);

		Assertions.assertThat(value).isEqualTo("OK");
	}

	// /////////////////////////////////////////////////////////
	// Inner Classes
	// /////////////////////////////////////////////////////////

}
