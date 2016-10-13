package org.mohsinmalik324.downloadscheduler;

/**
 * Represents a download.
 * 
 * @author Mohsin Malik
 *    <dd>Email: mohsin.malik@stonybrook.edu
 *    <dd>Stony Brook ID: 110880864
 *    
 * <dt>More:
 *    <dd>Course: CSE214
 *    <dd>Assignment #: 4
 *    <dd>Recitation #: 4
 *    <dd>TA: Jun Young Kim
 */
public class DownloadJob {
	
	private int downloadSize;
	private int downloadSizeRemaining;
	private int timeRequested;
	private boolean isPremium;
	private int id;

	public int getDownloadSize() {
		return downloadSize;
	}

	public void setDownloadSize(int downloadSize) {
		this.downloadSize = downloadSize;
	}

	public int getDownloadSizeRemaining() {
		return downloadSizeRemaining;
	}

	public void setDownloadSizeRemaining(int downloadSizeRemaining) {
		this.downloadSizeRemaining = downloadSizeRemaining;
	}

	public int getTimeRequested() {
		return timeRequested;
	}

	public void setTimeRequested(int timeRequested) {
		this.timeRequested = timeRequested;
	}

	public boolean isPremium() {
		return isPremium;
	}

	public void setPremium(boolean isPremium) {
		this.isPremium = isPremium;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}