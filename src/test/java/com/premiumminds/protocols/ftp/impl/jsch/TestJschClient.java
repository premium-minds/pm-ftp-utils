package com.premiumminds.protocols.ftp.impl.jsch;

import java.util.Collection;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.JSch;
import com.premiumminds.protocols.ftp.api.FtpClientSession;
import com.premiumminds.protocols.ftp.api.FtpConnection;
import com.premiumminds.test.junit.rules.JunitRulePropertiesLoad;

public class TestJschClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);


	@ClassRule
	public final static JunitRulePropertiesLoad pRule = JunitRulePropertiesLoad.fromJavaProperty("p", TestJschClient.class.getResource("test.properties")); 

	public TestJschClient() { return; }

	@Before
	public void verifyIfEnabled() {
		org.junit.Assume.assumeTrue(pRule.getPropertyBoolean("enabled", false));
	}
	
	@Test
	public void testConnectAndLs() {

		JSch jsch = new JSch();
		FtpConnectionParams params = newConnectionParams();
		FtpClientFacadeJschImpl underTest = new FtpClientFacadeJschImpl(jsch, LOGGER, params);

		underTest.doWork(new FtpClientSession<Void,RuntimeException>() {
			public Void execute(FtpConnection api) {
				Iterable<String > actual = api.ls(".");
				Assert.assertNotNull(actual);
				if ( (actual instanceof Collection<?>) ) {
					Collection<?> collection = (Collection<?>) actual;
					Assert.assertThat(collection.size(), Matchers.greaterThan(0));
				}
				if ( LOGGER.isTraceEnabled() ) {
					for ( String it : actual ) {
						LOGGER.trace("item: \"{}\"", it);
					}
				}
				return null;
			}
		});
		return;
	}

	private FtpConnectionParams newConnectionParams() {
		String host = pRule.getProperty("host");
		int port = pRule.getPropertyInt("port");
		String user = pRule.getProperty("user");
		String password = pRule.getProperty("pass");
		int timeout = pRule.getPropertyInt("timeout");
		return new FtpConnectionParams(host, port, user, password, timeout);
	}
}
