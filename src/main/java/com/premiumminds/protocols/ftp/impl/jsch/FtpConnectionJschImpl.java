package com.premiumminds.protocols.ftp.impl.jsch;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Vector;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.premiumminds.protocols.ftp.api.FtpClientRuntimeExceptionWrapping;
import com.premiumminds.protocols.ftp.api.FtpConnection;

public class FtpConnectionJschImpl implements FtpConnection {

	private final ChannelSftp channel;

	public FtpConnectionJschImpl(Session session, ChannelSftp channel) {
		this.channel = channel;
		return;
	}

	public void get(String from, OutputStream to) {
		try {
			channel.get(from, to);
		}
		catch (SftpException e) {
			throw new FtpClientRuntimeExceptionWrapping(e);
		}
	}

	public void close() {
		if ( null != channel ) {
			channel.disconnect();
		}
	}

	public Iterable<String> ls(String path) {
		ArrayList<String> rvalue;
		try {
			Vector<?> tmp = channel.ls(path);
			rvalue = new ArrayList<String>(tmp.size());
			for ( Object item : tmp ) {
				LsEntry entry = (LsEntry) item;
				rvalue.add(entry.getFilename());
			}
			return rvalue;
		}
		catch (SftpException e) {
			throw new FtpClientRuntimeExceptionWrapping(e);
		}
	}
}
