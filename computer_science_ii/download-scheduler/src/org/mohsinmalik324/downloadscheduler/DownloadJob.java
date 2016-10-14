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
	
	/**
	 * Returns an instance of DownloadJob.
	 * 
	 * @param downloadSize The download size of this job.
	 * 
	 * @param timeRequested When the job was requested.
	 * 
	 * @param isPremium If the job is premium.
	 * 
	 * @param id The ID of the download job.
	 */
	public DownloadJob(int downloadSize, int timeRequested, boolean isPremium,
	  int id) {
		this.downloadSize = downloadSize;
		downloadSizeRemaining = downloadSize;
		this.timeRequested = timeRequested;
		this.isPremium = isPremium;
		this.id = id;
	}

	/**
	 * Returns the download size.
	 * 
	 * @return The download size.
	 */
	public int getDownloadSize() {
		return downloadSize;
	}

	/**
	 * Returns the download size remaining.
	 * 
	 * @return The download size remaining.
	 */
	public int getDownloadSizeRemaining() {
		return downloadSizeRemaining;
	}

	/**
	 * Sets the download size remaining.
	 * 
	 * @param downloadSizeRemaining The new download size remaining.
	 */
	public void setDownloadSizeRemaining(int downloadSizeRemaining) {
		this.downloadSizeRemaining = downloadSizeRemaining;
	}

	/**
	 * Returns the time requested.
	 * 
	 * @return The time requested.
	 */
	public int getTimeRequested() {
		return timeRequested;
	}

	/**
	 * Returns if the download is premium.
	 * 
	 * @return If the download is premium.
	 */
	public boolean isPremium() {
		return isPremium;
	}

	/**
	 * Returns the ID of this download.
	 * 
	 * @return The ID of this download.
	 */
	public int getId() {
		return id;
	}
	
}