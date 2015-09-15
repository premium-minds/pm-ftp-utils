package com.premiumminds.protocols.ftp.api;

public interface FtpClientFacade {

	<T, E extends Exception> T doWork(FtpClientSession<T, E> ftpSession) throws E;

}
