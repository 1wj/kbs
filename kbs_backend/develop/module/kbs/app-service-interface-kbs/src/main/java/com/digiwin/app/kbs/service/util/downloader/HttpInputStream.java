/**
 * Copyright @www.drx-home.com
 */
package com.digiwin.app.kbs.service.util.downloader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Kevin
 * @version 1.0
 */
public class HttpInputStream extends InputStream {
	private final InputStream inputStream;
	private final String charset;
	
	public HttpInputStream(InputStream inputStream, String charset) {
		this.inputStream = inputStream;
		this.charset = charset;
	}
	
	public HttpInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
		this.charset = "utf-8";
	}
	
	public int read() throws IOException {
		return inputStream.read();
	}
	
	public String readLine() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while(true) {
			int b = read();
			baos.write(b);
			if(b == '\n') {
				break;
			}
		}
		return new String(baos.toByteArray(), charset);
	}
	
}
