/**
 * Copyright @www.drx-home.com
 */
package com.digiwin.app.kbs.service.util.downloader;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Kevin
 * @version 1.0
 */
public class HttpOutputStream extends OutputStream {
	private final String charset;
	private final OutputStream outputStream;
	
	public HttpOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
		this.charset = "utf-8";
	}
	
	public HttpOutputStream(OutputStream outputStream, String charset) {
		this.outputStream = outputStream;
		this.charset = charset;
	}
	
	public void write(int b) throws IOException {
		outputStream.write(b);
	}
	
	public int write(String value) throws IOException {
		if(value == null || value.length() == 0) {
			return 0;
		}
		byte[] bytes = value.getBytes(charset);
		int len = bytes.length; 
		write(bytes, 0, len);
		return len;
	}
}
