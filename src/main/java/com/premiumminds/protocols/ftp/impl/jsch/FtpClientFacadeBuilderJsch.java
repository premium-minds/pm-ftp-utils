package com.premiumminds.protocols.ftp.impl.jsch;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.JSch;
import com.premiumminds.protocols.ftp.api.FtpClientFacade;
import com.premiumminds.protocols.ftp.api.FtpClientFacadeBuilder;

public class FtpClientFacadeBuilderJsch implements FtpClientFacadeBuilder {


	private static final Logger logger = LoggerFactory.getLogger(FtpClientFacade.class);

	private String username;
	private String password;

	private final URL url;
	private int connectTimeoutMs;

	public FtpClientFacadeBuilderJsch(URL url) {
		this.url = url;
		doReadUserInfo();
		return;
	}

	@Override
	public FtpClientFacadeJschImpl newFtpClient() {
		JSch jsch = new JSch();
		FtpConnectionParams params = new FtpConnectionParams(url.getHost(), url.getPort(), username, password, connectTimeoutMs);
		return new FtpClientFacadeJschImpl(jsch, logger, params);
	}

	@Override
	public FtpClientFacadeBuilder setCredentials(String username, String password) {
		this.username = username;
		this.password = password;
		return this;
	}

	@Override
	public FtpClientFacadeBuilder setConnectTimeout(int timeoutMs) {
		this.connectTimeoutMs = timeoutMs;
		return this;
	}

	private void doReadUserInfo() {
		String tmp = url.getUserInfo();
		if ( tmp != null ) {
			String[] ssTmp = tmp.split(":");
			if ( ssTmp.length >= 1 ) {
				username = ssTmp[0];
			}
			if ( ssTmp.length >= 2 ) {
				password = ssTmp[1];
			}
		}
		return;
	}
}
