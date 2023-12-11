package de.dicos.springboot.repairservice.restful.error;

import org.springframework.http.HttpStatus;

public class DicosBadRequestException extends DomainServiceException {
    private static final long serialVersionUID = 1L;
    
    public DicosBadRequestException(String message) {
        super(message);
        super.setHttpStatus(HttpStatus.BAD_REQUEST);
    }

    public DicosBadRequestException(String message, Throwable throwable) {
        super(message, throwable);
        super.setHttpStatus(HttpStatus.BAD_REQUEST);
    }

}
