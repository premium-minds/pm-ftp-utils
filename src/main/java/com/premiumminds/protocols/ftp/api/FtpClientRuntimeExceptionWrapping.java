package com.premiumminds.protocols.ftp.api;

public class FtpClientRuntimeExceptionWrapping extends RuntimeException {
	private static final long serialVersionUID = 1L;

	
	public FtpClientRuntimeExceptionWrapping(Throwable cause) {
		super(cause.getMessage(), cause);
		return;
	}

	

}
