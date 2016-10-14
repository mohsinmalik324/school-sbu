package org.mohsinmalik324.downloadscheduler;

/**
 * DownloadScheduler simulates the downloads.
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
	  int servers, double probPremium, double probRegular)
	  throws IllegalArgumentException {
		this.simulationEndTime = simulationEndTime;
		this.downloadSpeed = downloadSpeed;
		regularQ = new DownloadQueue();
		premiumQ = new DownloadQueue();
		randomizer = new DownloadRandomizer(probPremium, probRegular);
		currentJobs = new DownloadJob[servers];
	}
	
	/**
	 * Returns a String which represents the download simulation.
	 * 
	 * @return A String which represents the download simulation.
	 */
	public String simulate() {
		String simulation = "";
		int jobsServed = 0;
		int premiumJobsServed = 0;
		int regularJobsServed = 0;
		int dataServed = 0;
		int premiumDataServed = 0;
		int averagePremiumWaitTime = 0;
		int averageRegularWaitTime = 0;
		simulation = append(simulation, "--------------------------"
		  + "Simulation Starting--------------------------\n");
		int id = 1;
		for(currentTime = 0; currentTime <= simulationEndTime; currentTime++) {
			simulation = append(simulation, "Timestep " + currentTime + ":\n");
			int regNewJob = randomizer.getRegular();
			int preNewJob = randomizer.getPremium();
			simulation = append(simulation, "\t\tNew Regular Job: ");
			if(regNewJob == -1) {
				simulation = append(simulation, "n/a\n");
			} else {
				DownloadJob job = new DownloadJob(regNewJob, currentTime,
				  false, id++);
				regularQ.enqueue(job);
				simulation = append(simulation, "Job#" + job.getId() +
				  ": Size: " + job.getDownloadSize() + "Mb\n");
			}
			simulation = append(simulation, "\t\tNew Premium Job: ");
			if(preNewJob == -1) {
				simulation = append(simulation, "n/a\n");
			} else {
				DownloadJob job = new DownloadJob(preNewJob, currentTime,
				  true, id++);
				premiumQ.enqueue(job);
				simulation = append(simulation, "Job#" + job.getId() +
				  ": Size: " + job.getDownloadSize() + "Mb\n");
			}
			String finishedJobs = "";
			for(int i = 0; i < currentJobs.length; i++) {
				DownloadJob job = currentJobs[i];
				if(job != null && job.getDownloadSizeRemaining() == 0) {
					int waitTime = currentTime - job.getTimeRequested();
					finishedJobs += "Job " + job.getId() + " finished, " +
				      (job.isPremium() ? "Premium" : "Regular") + " job. " +
					  job.getDownloadSize() + "Mb served, Total wait time: " +
				      waitTime + '\n';
					jobsServed++;
					dataServed += job.getDownloadSize();
					if(job.isPremium()) {
						premiumJobsServed++;
						premiumDataServed += job.getDownloadSize();
						averagePremiumWaitTime += waitTime;
					} else {
						averageRegularWaitTime += waitTime;
					}
					currentJobs[i] = null;
				}
				if(currentJobs[i] == null) {
					job = null;
					try {
						if(!premiumQ.isEmpty()) {
							job = premiumQ.dequeue();
						} else if(!regularQ.isEmpty()) {
							job = regularQ.dequeue();
						}
					} catch(EmptyQueueException e) {
						job = null;
					}
					currentJobs[i] = job;
				}
			}
			simulation = append(simulation, "\t\tRegularQueue:" +
			  regularQ.toString() + '\n');
			simulation = append(simulation, "\t\tPremiumQueue:" +
			  premiumQ.toString() + '\n');
			for(int i = 1; i <= currentJobs.length; i++) {
				simulation = append(simulation, serverToString(i) + '\n');
				DownloadJob job = currentJobs[i - 1];
				if(job != null) {
					int newDownloadSize = job.getDownloadSizeRemaining() -
					  downloadSpeed;
					if(newDownloadSize <= 0) {
						newDownloadSize = 0;
					}
					job.setDownloadSizeRemaining(newDownloadSize);
				}
			}
			simulation = append(simulation, finishedJobs);
			simulation = append(simulation, "----------------------------"
			  + "-------------------------------------------\n");
		}
		regularJobsServed = jobsServed - premiumJobsServed;
		simulation = append(simulation, "Simulation Ended:\n");
		simulation = append(simulation, "\t\tTotal jobs served: " +
		  jobsServed + '\n');
		simulation = append(simulation, "\t\tTotal Premium Jobs served: " +
		  premiumJobsServed + '\n');
		simulation = append(simulation, "\t\tTotal Regular Jobs served: " +
		  regularJobsServed + '\n');
		simulation = append(simulation, "\t\tTotal Data served: " +
		  dataServed + "Mb\n");
		simulation = append(simulation, "\t\tTotal Premium Data served: " +
		  premiumDataServed + "Mb\n");
		simulation = append(simulation, "\t\tTotal Regular Data served: " +
		  (dataServed - premiumDataServed) + "Mb\n");
		averagePremiumWaitTime /= premiumJobsServed;
		averageRegularWaitTime /= regularJobsServed;
		simulation = append(simulation, "\t\tAverage Premium Wait Time: " +
		  averagePremiumWaitTime + '\n');
		simulation = append(simulation, "\t\tAverage Regular Wait Time: " +
		  averageRegularWaitTime + '\n');
		simulation = append(simulation, "----------------------"
		  + "Thank You For Running the Simulator--------------");
		return simulation;
	}
	
	/**
	 * Returns a String representation of a server.
	 * 
	 * @param server The number ID of the server, starting at 1.
	 * 
	 * @return A String representation of a server.
	 * 
	 * <dt>Precondition:
	 *    <dd>server can't be less than 1.
	 *    <dd>server can't be greater than the number of servers.
	 * 
	 * @throws IllegalArgumentException
	 *    server is less than 1 or server is greater than amount of servers.
	 */
	private String serverToString(int server) throws IllegalArgumentException {
		if(server <= 0) {
			throw new IllegalArgumentException("server is less than 1.");
		}
		if(server > currentJobs.length) {
			throw new IllegalArgumentException("server is greater than "
			  + "amount of servers.");
		}
		DownloadJob job = currentJobs[server - 1];
		if(job == null) {
			return "\t\tServer " + server + ":idle";
		}
		int id = job.getId();
		int downloadSize = job.getDownloadSize();
		int remaining = job.getDownloadSizeRemaining();
		int requestTime = job.getTimeRequested();
		boolean premium = job.isPremium();
		return "\t\tServer " + server + ":[#" + id + ": " + downloadSize +
		  "Mb total, " + remaining + "Mb remaining, Request Time: " +
		  requestTime + ", " + (premium ? "Premium" : "Regular") + "]";
	}
	
	/**
	 * Appends a String to another String and returns it.
	 * 
	 * @param original The String to be appended to.
	 * 
	 * @param toAppend The String to append to original.
	 * 
	 * @return The appended String.
	 */
	private static String append(String original, String toAppend) {
		return original + toAppend;
	}
	
}