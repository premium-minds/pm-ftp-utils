package com.premiumminds.protocols.ftp.impl.jsch;

import org.slf4j.Logger;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.premiumminds.protocols.ftp.api.FtpClientFacade;
import com.premiumminds.protocols.ftp.api.FtpClientRuntimeExceptionWrapping;
import com.premiumminds.protocols.ftp.api.FtpClientSession;

public class FtpClientFacadeJschImpl implements FtpClientFacade {

	private final JSch jsch;
	private final Logger logger;
	private final FtpConnectionParams params;

	public FtpClientFacadeJschImpl(JSch jsch, Logger logger, FtpConnectionParams params){
		this.jsch = jsch;
		this.logger = logger;
		this.params = params;
		return;
	}


	public <T, E extends Exception> T doWork(FtpClientSession<T, E> ftpSession) throws E {
		
		Session session = null;
		T rvalue;
		try {
			session = jsch.getSession(params.getUsername(), params.getHost(), params.getPort());
			session.setConfig(JschConfiguration.STRICT_HOST_VERIFY, JschConfiguration.FALSE);
			session.setPassword(params.getPassword());
			session.connect(params.getConnectTimeout());
			ChannelSftp channel = (ChannelSftp)session.openChannel("sftp");
			channel.connect(params.getConnectTimeout());
			rvalue = ftpSession.execute(new FtpConnectionJschImpl(session, channel));
		}
		catch (JSchException e) {
			throw new FtpClientRuntimeExceptionWrapping(e);
		}
		finally {
			if ( null != session ) {
				try {
					session.disconnect();
				}
				catch ( RuntimeException e ) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return rvalue;
	}

}
