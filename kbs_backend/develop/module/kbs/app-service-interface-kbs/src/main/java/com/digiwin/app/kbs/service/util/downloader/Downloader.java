/**
 * Copyright @www.drx-home.com
 */
package com.digiwin.app.kbs.service.util.downloader;

/**
 * @author Kevin
 * @version 1.0
 */
public interface Downloader {

	boolean download();

	boolean download(int threadCount);

}
