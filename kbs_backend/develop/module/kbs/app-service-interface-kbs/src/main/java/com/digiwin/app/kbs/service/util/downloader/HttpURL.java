/**
 * Copyright @www.drx-home.com
 */
package com.digiwin.app.kbs.service.util.downloader;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.*;

/**
 * @author Kevin
 * @version 1.0
 */
final class HttpURL {
	private static final String method = "GET";
	private static final String CRLF = "\r\n";
	
	private static final String charset = "utf-8";
	private final String host;
	private final int port;
	private final String path;
	private final String queryString;
	private final String protocol;
	private final Map<String, List<String>> requestHeaderMap;
	private Map<String, List<String>> responseHeaderMap = null;
	private Socket client = null;
	private HttpInputStream inputStream;
	private HttpOutputStream outputStream;
	
	public HttpURL(String url) throws MalformedURLException {
		URL address = new URL(url);
		this.host = address.getHost();
		this.port = address.getPort() == -1 ? address.getDefaultPort() : address.getPort();
		this.path = address.getPath();
		this.queryString = address.getQuery();
		this.protocol = String.format("%s/1.1", address.getProtocol()).toUpperCase();
		this.requestHeaderMap = new LinkedHashMap<String, List<String>>();
		this.initRequestHeader();
	}
	
	private void initRequestHeader() {
		addRequestProperty("Host", host);
		addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
		addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		addRequestProperty("Accept-Encoding", "gzip, deflate");
		addRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
		addRequestProperty("Connection", "keep-alive");
	}
	
	private String validateKey(String key) {
		if(key == null || key.length() == 0) {
			return null;
		}
		key = key.trim();
		if(key.length() == 0) {
			return null;
		}
		return key;
	}
	
	public void setRequestProperty(String key, String value) {
		if((key = validateKey(key)) == null) {
			return ;
		}
		List<String> vallist = null;
		for(Iterator<String> iterator = requestHeaderMap.keySet().iterator(); iterator.hasNext(); ) {
			String k = iterator.next();
			if(k.equalsIgnoreCase(key)) {
				vallist = requestHeaderMap.get(k);
				break;
			}
		}
		if(vallist == null) {
			vallist = new ArrayList<String>();
			requestHeaderMap.put(key, vallist);
			vallist.add(value);
		}
		else {
			vallist.clear();
			vallist.add(value);
		}
	}

	public void addRequestProperty(String key, String value) {
		if((key = validateKey(key)) == null) {
			return ;
		}
		List<String> vallist = null;
		for(Iterator<String> iterator = requestHeaderMap.keySet().iterator(); iterator.hasNext(); ) {
			String k = iterator.next();
			if(k.equalsIgnoreCase(key)) {
				vallist = requestHeaderMap.get(k);
				break;
			}
		}
		if(vallist == null) {
			vallist = new ArrayList<String>();
			requestHeaderMap.put(key, vallist);
		}
		vallist.add(value);
	}
	
	public synchronized HttpURL connect() throws IOException {
		if(client != null) {
			return this;
		}
		this.responseHeaderMap = new LinkedHashMap<String, List<String>>();
		client = new Socket();
		client.connect(new InetSocketAddress(host, port));
		inputStream = new HttpInputStream(client.getInputStream(), charset);
		outputStream = new HttpOutputStream(client.getOutputStream(), charset);
		writeRequestHeader();
		return this;
	}
	
	private synchronized void readResponseHeader() throws IOException {
		synchronized (responseHeaderMap) {
			if(responseHeaderMap.size() > 0) {
				return ;
			}
			
			String firstline = inputStream.readLine();
			if(firstline == null) {
				return ;
			}
			firstline = firstline.trim();
			String[] sections = firstline.split(" ");
			if(sections.length < 3) {
				System.err.println("Bad response header." + firstline);
				return ;
			}
			//System.out.println(String.format("response code: %s", sections[1]));
			String line = null;
			while((line = inputStream.readLine()) != null) {
				line = line.trim();
				if(line.length() == 0) {
					break;
				}
				addResponseHeaderLine(line);
			}
		}
	}

	private void addResponseHeaderLine(String line) {
		int pos = line.indexOf(':');
		if(pos == -1) {
			return ;
		}
		String key = line.substring(0, pos);
		String value = line.substring(pos + 1).trim();
		List<String> vallist = null;
		for(Iterator<String> iterator = responseHeaderMap.keySet().iterator(); iterator.hasNext(); ) {
			String k = iterator.next();
			if(k.equalsIgnoreCase(key)) {
				vallist = responseHeaderMap.get(k);
				break;
			}
		}
		if(vallist == null) {
			vallist = new ArrayList<String>();
			responseHeaderMap.put(key, vallist);
		}
		vallist.add(value);
	}

	private void writeRequestHeader() throws IOException {
		StringBuffer requestBuffer = new StringBuffer(); 
		String requrl = null;
		if(queryString != null) {
			requrl = String.format("%s?%s", path, queryString);
		}
		else {
			requrl = String.format("%s", path);
		}
		requestBuffer.append(String.format("%s %s %s%s", method, requrl, protocol, CRLF));
		for(Iterator<String> iterator = requestHeaderMap.keySet().iterator(); iterator.hasNext(); ) {
			String key = iterator.next();
			List<String> vallist = requestHeaderMap.get(key);
			for(String value : vallist) {
				requestBuffer.append(String.format("%s: %s%s", key, value, CRLF));
			}
		}
		requestBuffer.append(CRLF);
		//System.out.println(requestBuffer);
		outputStream.write(requestBuffer.toString().getBytes(charset));
	}

	public String getHeader(String key) {
		try {
			readResponseHeader();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		List<String> vallist = null;
		for(Iterator<String> iterator = responseHeaderMap.keySet().iterator(); iterator.hasNext(); ) {
			String k = iterator.next();
			if(k.equalsIgnoreCase(key)) {
				vallist = responseHeaderMap.get(k);
				break;
			}
		}
		if(vallist != null) {
			return vallist.get(0);
		}
		return null;
	}

	public String[] getHeaders(String key) {
		try {
			readResponseHeader();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		List<String> vallist = null;
		for(Iterator<String> iterator = responseHeaderMap.keySet().iterator(); iterator.hasNext(); ) {
			String k = iterator.next();
			if(k.equalsIgnoreCase(key)) {
				vallist = responseHeaderMap.get(k);
				break;
			}
		}
		if(vallist != null) {
			return vallist.toArray(new String[0]);
		}
		return null;
	}
	
	public Enumeration<String> getHeaderKeys() {
		try {
			readResponseHeader();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return Collections.enumeration(responseHeaderMap.keySet());
	}
	
	public HttpInputStream getInputStream() {
		try {
			readResponseHeader();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return inputStream;
	}
	
	public void disconnect() throws IOException {
		if(client != null) {
			client.close();
		}
	}
}
