package com.premiumminds.protocols.ftp.api;


public interface FtpClientFacadeBuilder  {

	FtpClientFacade newFtpClient();
	FtpClientFacadeBuilder setCredentials(String username, String password);
	FtpClientFacadeBuilder setConnectTimeout(int timeoutMs);
}
