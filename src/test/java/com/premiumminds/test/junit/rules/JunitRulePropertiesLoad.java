package com.premiumminds.test.junit.rules;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class JunitRulePropertiesLoad implements TestRule {

	private final URL url;

	private Properties properties;

	public JunitRulePropertiesLoad(String name) {
		this(JunitRulePropertiesLoad.class.getClassLoader(), name);
		return;
	}
	public JunitRulePropertiesLoad(ClassLoader cl, String name) {
		this(cl.getResource(name));
	}
	public JunitRulePropertiesLoad(URL url) {
		if ( null == url ) {
			throw new RuntimeException("no config file given");
		}
		this.url = url;
		return;
	}

	public static JunitRulePropertiesLoad fromJavaProperty(String propertyName, URL defaultValue) {
		String tmp = System.getProperty(propertyName);
		URL url;
		if ( tmp == null ) {
			url = defaultValue;
		}
		else {
			try {
				url = new URL(tmp);
			}
			catch (MalformedURLException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		return new JunitRulePropertiesLoad(url);
	}

	public Statement apply(final Statement base, Description description) {
		return new Statement() {
			
			@Override
			public void evaluate() throws Throwable {
				InputStream is = url.openStream();
				Properties tmp = new Properties();
				try {
					tmp.load(is);
				}
				catch (IOException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
				try {
					properties = tmp;
					base.evaluate();
				}
				finally {
					properties = null;
				}
				return;
			}
		};
	}

	public String getProperty(String key)  {
		String rvalue = properties.getProperty(key);
		if ( rvalue == null ) {
			throw newKeyNotFound(key);
		}
		return rvalue;
	}
	public String getProperty(String key, String defaultValue)  {
		String rvalue, tmp = properties.getProperty(key);
		if ( tmp == null ) {
			rvalue = defaultValue;
		}
		else {
			rvalue = tmp;
		}
		return rvalue;
	}
	public int getPropertyInt(String key) {
		String tmp = getProperty(key);
		if ( tmp == null ) {
			throw newKeyNotFound(key);
		}
		return Integer.valueOf(tmp).intValue();
	}
	public boolean getPropertyBoolean(String key, boolean defaultValue) {
		boolean rvalue;
		String tmp = getProperty(key, null);
		if ( tmp == null ) {
			rvalue = false;
		}
		else {
			rvalue = Boolean.valueOf(tmp).booleanValue();
		}
		return rvalue;
	}

	private static RuntimeException newKeyNotFound(String key) {
		return new NullPointerException("key not found: "+key);
	}
}
