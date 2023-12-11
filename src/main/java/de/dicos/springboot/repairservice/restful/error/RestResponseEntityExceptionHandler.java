package de.dicos.springboot.repairservice.restful.error;

import java.util.StringJoiner;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {
	
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponseTO> handleException(final Exception givenThrowable) {
        final ErrorResponseTO response;
        final HttpStatus httpStatus;
        if (DomainServiceException.class.isAssignableFrom(givenThrowable.getClass())) {
        	DomainServiceException dsex = (DomainServiceException) givenThrowable;
        	httpStatus = dsex.getHttpStatus();
        	response = new ErrorResponseTO(dsex.getHttpStatus().name(), dsex.getMessage());
        } else if (ConstraintViolationException.class.isAssignableFrom(givenThrowable.getClass())) {
        	httpStatus = HttpStatus.BAD_REQUEST;
            response = createConstraintViolationResponse(givenThrowable);
        } else {
        	httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        	response = new ErrorResponseTO(HttpStatus.INTERNAL_SERVER_ERROR.name(), givenThrowable.getMessage());
        }

        return new ResponseEntity<>(response, httpStatus);
    }

	/**
	 * Create an ErrorResponseTO for a ContraintViolationExcpetion.
	 *
	 * @param curThrowable Throwable with the information.
	 * @return ErrorResponseTO
	 */
	private ErrorResponseTO createConstraintViolationResponse(Throwable curThrowable) {
		final ErrorResponseTO response;
		var sj = new StringJoiner("; ");
		var ve = (ConstraintViolationException) curThrowable;
		var constraintValidations = ve.getConstraintViolations();

		if (constraintValidations != null) {
			for (ConstraintViolation<?> violation : constraintValidations) {
				var path = violation.getPropertyPath();
				var pathString = path == null ? "" : (path + ": ");
				sj.add(pathString + violation.getMessage());
			}
		}
		response = new ErrorResponseTO(HttpStatus.BAD_REQUEST.name(), sj.toString());
		return response;
	}

}
