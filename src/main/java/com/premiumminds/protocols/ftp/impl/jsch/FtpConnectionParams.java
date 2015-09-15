package com.premiumminds.protocols.ftp.impl.jsch;

public class FtpConnectionParams {

	private final String host;
	private final int port;
	private final String username;
	private final String password;
	private final int connectTimeout;

	public FtpConnectionParams(String host, int port, String username,
			String password, int connectTimeout) {
		super();
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.connectTimeout = connectTimeout;
	}

	public String getUsername() {
		return username;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getPassword() {
		return password;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

}
