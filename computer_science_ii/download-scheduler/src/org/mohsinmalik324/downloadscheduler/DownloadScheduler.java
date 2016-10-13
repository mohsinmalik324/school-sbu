package org.mohsinmalik324.downloadscheduler;

/**
 * TODO: Write this.
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
public class DownloadScheduler {
	
	private DownloadQueue regularQ;
	private DownloadQueue premiumQ;
	private int currentTime;
	private int simulationEndTime;
	private DownloadRandomizer randomizer;
	private DownloadJob[] currentJobs;
	private int downloadSpeed;
	
	/**
	 * Constructor for <code>DownloadScheduler</code>.
	 * 
	 * @param simulationEndTime When the simulation should end.
	 * @param downloadSpeed The download speed.
	 * @param servers The amount of servers.
	 * @param probPremium The probability of a new premium job in a timestep.
	 * @param probRegular The probability of a new regular job in a timestep.
	 * 
	 * @throws IllegalArgumentException
	 *    simulationEndTime is less than 0, downloadSpeed is less than 1,
	 *    servers is less than 1, probPremium is less than 0 or greater than
	 *    1, or probRegular is less than 0 or greater than 1.
	 */
	public DownloadScheduler(int simulationEndTime, int downloadSpeed,
	  int servers, double probPremium, double probRegular) throws IllegalArgumentException {
		
		this.simulationEndTime = simulationEndTime;
		this.downloadSpeed = downloadSpeed;
		currentTime = 0;
		regularQ = new DownloadQueue();
		premiumQ = new DownloadQueue();
		randomizer = new DownloadRandomizer(probPremium, probRegular);
	}
	
	/**
	 * Returns a String which represents the download simulation.
	 * 
	 * @return A String which represents the download simulation.
	 */
	public String simulate() {
		return "";
	}
	
}