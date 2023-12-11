package de.dicos.springboot.repairservice.restful.error;

import org.springframework.http.HttpStatus;

public class DicosInternalServerErrorException extends DomainServiceException {
    private static final long serialVersionUID = 1L;
    
    public DicosInternalServerErrorException(String message) {
        super(message);
        super.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public DicosInternalServerErrorException(String message, Throwable throwable) {
        super(message, throwable);
        super.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
