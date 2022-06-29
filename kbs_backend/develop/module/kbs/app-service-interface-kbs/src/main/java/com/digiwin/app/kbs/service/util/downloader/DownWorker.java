/**
 * Copyright @www.drx-home.com
 */
package com.digiwin.app.kbs.service.util.downloader;

import java.io.*;

/**
 * @author Kevin
 * @version 2.0
 */
final class DownWorker extends Thread {
	private final DownloaderControl control;
	private RandomAccessFile raf;
	
	public DownWorker(DownloaderControl control, File savefile) throws IOException {
		this.control = control;
		this.raf = new RandomAccessFile(savefile, "rw");
	}

	public void run() {
		while(true) {
			Multiparter multiparter = control.findMultiparter();
			if(multiparter == null) {
				if(raf != null) {
					try {
						raf.close();
						raf = null;
					}
					catch(Exception e) {
						String errmsg = String.format("close file stream error: %s", e.getMessage());
						System.err.println(errmsg);
					}
				}
				break;
			}
			try {
				raf.seek(multiparter.getStart());
				process(multiparter);
			}
			catch(Exception e) {
				String errmsg = String.format("download error:%s", readException(e));
				System.err.println(errmsg);
			}
		}
		control.freeWorker(this);
	}
	
	private String readException(Exception e) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(baos));
			e.printStackTrace(writer);
			writer.flush();
			writer.close();
			return new String(baos.toByteArray());
		}
		catch(Exception ex) {
		}
		return null;
	}
	
	private void process(Multiparter multiparter) throws IOException {
		HttpURL httpURL = new HttpURL(multiparter.getUrl());
		String range = String.format("bytes=%d-%d", multiparter.getStart(), multiparter.getEnd());
		httpURL.addRequestProperty("Range", range);
		httpURL.connect();
		InputStream inputStream = httpURL.getInputStream();
		byte[] bytes = new byte[4096];
		int len = 0;
		while(true) {
			if(multiparter.getMore() > bytes.length) {
				len = inputStream.read(bytes);
				if(len < 1) {
					System.err.println("error retry for " + multiparter);
					multiparter.reset();
					httpURL.disconnect();
					break;
				}
				raf.write(bytes, 0, len);
				multiparter.up(len);
			}
			else {
				int count = (int)(multiparter.getMore());
				if(count > 0) {
					len = inputStream.read(bytes, 0, count);
					if(len < 1) {
						System.err.println("error retry for " + multiparter);
						multiparter.reset();
						httpURL.disconnect();
						break;
					}
					raf.write(bytes, 0, len);
					multiparter.up(len);
					if(multiparter.isFinished()) {
						//System.out.println(multiparter.getIndex() + "-finished!");
						httpURL.disconnect();
						break;
					}
				}
				else {
					//TODO add bad code here?
					break;
				}
			}
		}
	}
}
