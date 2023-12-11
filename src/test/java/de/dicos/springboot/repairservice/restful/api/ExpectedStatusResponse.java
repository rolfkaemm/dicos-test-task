/**
 * (c) DICOS GmbH, 2020
 *
 * $Id$
 */

package de.dicos.springboot.repairservice.restful.api;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

/**
 *
 * @author sth
 */
public class ExpectedStatusResponse
	extends
	RuntimeException
{
	// /////////////////////////////////////////////////////////
	// Class Members
	// /////////////////////////////////////////////////////////
	/** */
	private static Logger log = LoggerFactory.getLogger(ExpectedStatusResponse.class);

	/** */
	private static final long serialVersionUID = 1L;

	/** */
	private ClientHttpResponse response;

	// /////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////
	/**
	 * @param message
	 * @param cause
	 */
	public ExpectedStatusResponse(ClientHttpResponse response)
	{
		super("got error status in response: " + extractStatusCode(response));

		this.response = response;
	}

	// /////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////
	/**
	 * 
	 */
	private static HttpStatus extractStatusCode(ClientHttpResponse response)
	{
		try {
			return response.getStatusCode();
		} catch (IOException x) {
			log.error("cannot extract status", x);
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}

	/**
	 * @return the response
	 */
	public ClientHttpResponse getResponse()
	{
		return response;
	}

	// /////////////////////////////////////////////////////////
	// Inner Classes
	// /////////////////////////////////////////////////////////


}
