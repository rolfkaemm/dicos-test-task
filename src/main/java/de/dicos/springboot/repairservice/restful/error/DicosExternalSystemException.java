package de.dicos.springboot.repairservice.restful.error;

import org.springframework.http.HttpStatus;

public class DicosExternalSystemException extends DomainServiceException {
	private static final long serialVersionUID = 1L;

	public DicosExternalSystemException(String message) {
		super(message);
		super.setHttpStatus(HttpStatus.SERVICE_UNAVAILABLE);
	}

	public DicosExternalSystemException(String message, Throwable throwable) {
		super(message, throwable);
		super.setHttpStatus(HttpStatus.SERVICE_UNAVAILABLE);
	}

}
