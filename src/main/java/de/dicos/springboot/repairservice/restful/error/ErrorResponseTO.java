package de.dicos.springboot.repairservice.restful.error;

import java.io.Serializable;

public class ErrorResponseTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String status;

    private String message;

    public ErrorResponseTO() {
        // does nothing
    }
    
    public ErrorResponseTO(String status, String message) {
        this.status = status;
        this.message = message;
    }

	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "ErrorResponseTO [status=" + status + ", message=" + message + "]";
	}
	
}
