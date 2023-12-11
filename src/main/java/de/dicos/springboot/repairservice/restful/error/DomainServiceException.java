package de.dicos.springboot.repairservice.restful.error;

import org.springframework.http.HttpStatus;

public class DomainServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
    protected HttpStatus httpStatus = HttpStatus.NOT_IMPLEMENTED;

    public DomainServiceException(String message) {
        super(message);
    }

    public DomainServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

}
