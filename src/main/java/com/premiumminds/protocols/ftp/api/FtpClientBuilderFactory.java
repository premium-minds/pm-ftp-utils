package com.premiumminds.protocols.ftp.api;

import java.net.MalformedURLException;
import java.net.URI;

import com.premiumminds.protocols.ftp.impl.jsch.FtpClientFacadeBuilderJsch;

public class FtpClientBuilderFactory {

	private static final Object SFTP = "sftp";


	public FtpClientBuilderFactory() {
		return;
	}

	public FtpClientFacadeBuilder getClientFacadeBuilder(URI uri) {
		final FtpClientFacadeBuilderJsch rvalue;
		final String scheme = uri.getScheme();
		if ( SFTP.equals(scheme) ) {
			try {
				rvalue = new FtpClientFacadeBuilderJsch(uri.toURL());
			}
			catch (MalformedURLException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		else {
			throw new RuntimeException("unhandled scheme: "+scheme);
		}
		return rvalue;
	}
}
