package com.premiumminds.protocols.ftp.api;

public interface FtpClientSession<T, E extends Exception> {

	T execute(FtpConnection api) throws E;
}
