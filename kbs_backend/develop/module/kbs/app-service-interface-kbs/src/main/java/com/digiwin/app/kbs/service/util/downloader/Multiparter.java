/**
 * Copyright @www.drx-home.com
 */
package com.digiwin.app.kbs.service.util.downloader;

/**
 * @author Kevin
 * @version 2.0
 */
final class Multiparter {
	private final DownloaderControl control;
	private final int index;
	private final String url;
	private final long start;
	private final long end;
	private final long partLength;
	private final long contentLength;
	private long recved = 0L;
	private boolean finished = false;
	private boolean active = false;
	
	public Multiparter(DownloaderControl control, int index, String url, long start, long end, long contentLength) {
		this.control = control;
		this.index = index;
		this.url = url;
		this.start = start;
		this.end = end;
		this.partLength = end - start + 1;
		this.contentLength = contentLength;
	}
	
	public String getUrl() {
		return url;
	}
	
	public long getStart() {
		return start;
	}
	
	public long getEnd() {
		return end;
	}
	
	public long getContentLength() {
		return contentLength;
	}
	
	void up(int length) {
		recved += length;
		control.recv(length);
		//System.out.println(String.format("%d: %02d%s", index, recved * 100 / partLength, "%"));
		if(recved == partLength) {
			finished = true;
		}
	}

	boolean isFinished() {
		return finished;
	}
	
	boolean isActive() {
		return active;
	}
	
	public long getMore() {
		return partLength - recved;
	}
	
	void active() {
		active = true;
	}
	
	public String toString() {
		return String.format("%02d->%d/%d-%d/%d", index, partLength, start, end, contentLength);
	}

	public int getIndex() {
		return index;
	}

	public void reset() {
		this.recved = 0;
		this.active = false;
		this.finished = false;
	}
}
