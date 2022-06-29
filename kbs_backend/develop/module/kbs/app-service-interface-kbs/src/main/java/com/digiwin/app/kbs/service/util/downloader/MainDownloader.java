/**
 * Copyright @www.drx-home.com
 */
package com.digiwin.app.kbs.service.util.downloader;

/**
 * @author Kevin
 * @version 1.0
 */
public class MainDownloader {
	public static Downloader getDownloader(String srcurl, String savedir) {
		return new DownloaderControl(srcurl, savedir);
	}
}
