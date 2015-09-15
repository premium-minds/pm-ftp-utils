package com.premiumminds.protocols.ftp.api;

import java.io.Closeable;
import java.io.OutputStream;

public interface FtpConnection extends Closeable {

	void get(String from, OutputStream osTo);

	Iterable<String> ls(String path);

}
