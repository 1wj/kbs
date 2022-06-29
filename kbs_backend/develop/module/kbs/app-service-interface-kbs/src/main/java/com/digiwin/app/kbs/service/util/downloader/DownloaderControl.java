/**
 * Copyright @www.drx-home.com
 */
package com.digiwin.app.kbs.service.util.downloader;

import java.io.*;
import java.net.URL;
import java.util.Vector;

/**
 * @author Kevin
 * @version 2.0
 */
final class DownloaderControl implements Downloader {
	/**
	 * thread download part size
	 */
	private static final int thread_part_size = 1024 * 1024 * 10;//for 10MB
	
	/**
	 * default download thread
	 */
	private static final int default_thread_count = 5;
	
	/**
	 * source to be download
	 */
	private final String srcurl;
	
	/**
	 * save data path
	 */
	private final String savedir;
	
	/**
	 * download thread count
	 */
	private int threadCount;
	
	/**
	 * request content length
	 */
	private long contentLength = -1;
	
	/**
	 * check if support for multi thread download
	 */
	private boolean supportMiltThreadDownload = false;
	
	/**
	 * save file for locale
	 */
	private File savefile = null;
	
	/**
	 * down part info list
	 */
	private Vector<Multiparter> multiList = null;
	
	/**
	 * download thread pool
	 */
	private Vector<DownWorker> workers = null;
	
	private long beginTime = 0L;
	
	private boolean success = false;
	
	private PipedInputStream pis = null;
	private PipedOutputStream pos = null;
	
	public DownloaderControl(String srcurl, String savedir) {
		this.srcurl = srcurl;
		this.savedir = savedir;
	}

	public boolean download() {
		return download(default_thread_count);
	}

	public boolean download(int threadCount) {
		this.threadCount = threadCount;
		process();
		//use pipe stream for method blocking...
		pis = new PipedInputStream();
		pos = new PipedOutputStream();
		try {
			pis.connect(pos);
			pis.read();
		}
		catch(Exception e) {
		}
		
		int timelong = (int)((System.currentTimeMillis() - beginTime) / 1000);
		System.out.println(String.format("download speed: %s byte/s", contentLength / timelong));
		return success;
	}
	
	/**
	 * begin download action
	 * @return
	 */
	private boolean process() {
		beginTime = System.currentTimeMillis();
		prepareDownload();
		if(contentLength > 0) {
			if(savefile == null) {
				return false;
			}
			if(!savefile.getParentFile().exists()) {
				savefile.getParentFile().mkdirs();
			}
			try {
				RandomAccessFile raf = new RandomAccessFile(savefile, "rw");
				raf.setLength(0L);
				raf.setLength(contentLength);
				raf.close();
				raf = null;
			}
			catch(Exception e) {
				System.err.println(String.format("create file error: %s", e.getMessage()));
				return false;
			}
			checkSupportMultiPartDownload();
			if(supportMiltThreadDownload) {
				//down in multi threads
				createMultiparter();
				startMultiparterThreads();
			}
			else {
				//down in one thread
				downloadUseContentLength();
			}
		}
		else {
			downloadFromUnknownContentLength();
		}
		return success;
	}
	
	private void downloadFromUnknownContentLength() {
		byte[] bytes = new byte[4096];
		int len = 0;
		try {
			HttpURL httpURL = new HttpURL(srcurl);
			InputStream inputStream = httpURL.getInputStream();
			RandomAccessFile raf = new RandomAccessFile(savefile, "rw");
			long recv = 0L;
			while((len = inputStream.read(bytes)) > 0) {
				recv += len;
				raf.write(bytes, 0, len);
				recv(len);
			}
			httpURL.disconnect();
		}
		catch(Exception e) {
		}
	}
	
	private void downloadUseContentLength() {
		byte[] bytes = new byte[4096];
		int len = 0;
		try {
			HttpURL httpURL = new HttpURL(srcurl);
			InputStream inputStream = httpURL.getInputStream();
			RandomAccessFile raf = new RandomAccessFile(savefile, "rw");
			long recv = 0L;
			while(true) {
				if(contentLength > recv + bytes.length) {
					len = inputStream.read(bytes);
					if(len > 0) {
						recv += len;
						raf.write(bytes, 0, len);
						recv(len);
					}
				}
				else {
					len = (int)(contentLength - recv);
					len = inputStream.read(bytes, 0, len);
					if(len > 0) {
						recv += len;
						raf.write(bytes, 0, len);
						recv(len);
					}
					//TODO use >= or == ? check this code!!
					if(recv >= contentLength) {
						raf.close();
						raf = null;
						break;
					}
				}
			}
			httpURL.disconnect();
		}
		catch(Exception e) {
		}
	}
	
	private void startMultiparterThreads() {
		//TODO add code here
		workers = new Vector<DownWorker>();
		for(int i = 0; i < threadCount; i++) {
			try {
				DownWorker worker = new DownWorker(this, savefile);
				workers.add(worker);
				worker.start();
			}
			catch(Exception e) {
				String errmsg = String.format("create download worker error: %s", e.getMessage());
				System.err.println(errmsg);
			}
		}
	}

	private void createMultiparter() {
		multiList = new Vector<Multiparter>();
		long index = 0;
		int num = 0;
		while(true) {
			if(index +  thread_part_size < contentLength) {
				long start = index;
				index += thread_part_size;
				long end = index - 1;
				Multiparter multiparter = new Multiparter(this, num++, srcurl, start, end, contentLength);
				System.out.println(String.format("prepare for download -> %s", multiparter));
				multiList.add(multiparter);
			}
			else {
				long start = index;
				long end = contentLength - 1;
				index = end;
				Multiparter multiparter = new Multiparter(this, num++, srcurl, start, end, contentLength);
				System.out.println(String.format("prepare for download -> %s", multiparter));
				multiList.add(multiparter);
				break;
			}
		}
	}

	/**
	 * read conent length
	 */
	private void prepareDownload() {
		try {
			//open url
			HttpURL httpURL = new HttpURL(srcurl).connect();
			String contentLength = httpURL.getHeader("Content-Length");
			if(contentLength != null && contentLength.length() > 0) {
				this.contentLength = Long.parseLong(contentLength);
			}
			String contentDisposition = httpURL.getHeader("Content-Disposition");
			if(contentDisposition != null && contentDisposition.length() > 0) {
				contentDisposition = contentDisposition.toLowerCase();
				if(contentDisposition.startsWith("attachment")) {
					int pos = contentDisposition.indexOf("filename=");
					if(pos != -1) {
						String filename = null;
						try {
							filename = contentDisposition.substring(pos + "filename=".length() + 1);
							filename = new String(filename.getBytes("iso-8859-1"), "utf-8");
						}
						catch(Exception e) {
						}
						if(filename != null) {
							savefile = new File(savedir, filename);
						}
					}
				}
			}
			if(savefile == null) {
				URL url = new URL(srcurl);
				String path = url.getPath();
				if(path.endsWith("/")) {
					savefile = new File(savedir, "unknown");
				}
				else {
					savefile = new File(savedir, new File(path).getName());
				}
			}
			httpURL.disconnect();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * check if can support range request
	 */
	private void checkSupportMultiPartDownload() {
		try {
			//open url
			HttpURL httpURL = new HttpURL(srcurl);
			if(contentLength > thread_part_size) {
				httpURL.addRequestProperty("Range", "bytes=1024-2048");
			}
			httpURL.connect();
			if(httpURL.getHeader("Content-Range") != null) {
				supportMiltThreadDownload = true;
			}
			httpURL.disconnect();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public Multiparter findMultiparter() {
		synchronized (multiList) {
			for(Multiparter multiparter : multiList) {
				if(!multiparter.isActive()) {
					System.out.println("download part: " + multiparter);
					multiparter.active();
					return multiparter;
				}
			}
		}
		return null;
	}

	public void freeWorker(DownWorker worker) {
		workers.remove(worker);
		if(workers.size() == 0) {
			try {
				pos.write(1);
			}
			catch(Exception e) {
			}
		}
	}
	
	private long contentReceived = 0L;
	private String tmplabel = null;

	public void recv(int length) {
		contentReceived += length;
		if(contentLength > 0) {
			String str = String.format("%02d%s\r", Long.valueOf(contentReceived * 100 / contentLength), "%");
			if(!str.equals(tmplabel)) {
				tmplabel = str;
				System.out.print(tmplabel);
			}
		}
		else {
			String str = String.format("recved %d bytes\r", contentReceived);
			if(!str.equals(tmplabel)) {
				tmplabel = str;
				System.out.print(tmplabel);
			}
		}
	}
}
