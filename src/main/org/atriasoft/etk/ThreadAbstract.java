package org.atriasoft.etk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract base class for managed threads with lifecycle methods.
 *
 * <p>Provides a framework for creating threads with well-defined initialization, execution,
 * and cleanup phases. Subclasses implement the birth(), runPeriodic(), and death() methods
 * to define thread behavior.</p>
 *
 * @author Edouard DUPIN
 * @since 0.1.0
 */
public abstract class ThreadAbstract {
	final static Logger LOGGER = LoggerFactory.getLogger(ThreadAbstract.class);
	// thread section:
	private boolean threadStopRequested = false;
	private Thread threadInstance = null;
	private final String threadName;
	
	/**
	 * Constructs a new managed thread with the specified name.
	 *
	 * @param name Name for the thread (used for debugging and logging)
	 */
	public ThreadAbstract(final String name) {
		this.threadName = name;
	}
	
	/**
	 * Initialization method called once when the thread starts.
	 *
	 * <p>Override this method to perform thread initialization logic.</p>
	 */
	protected abstract void birth();
	
	/**
	 * Cleanup method called once when the thread terminates.
	 *
	 * <p>Override this method to perform cleanup and resource release.</p>
	 */
	protected abstract void death();
	
	/**
	 * Main execution method called repeatedly while the thread is running.
	 *
	 * <p>Override this method to implement the thread's main logic. This method
	 * is called in a loop until threadStop() is called.</p>
	 */
	protected abstract void runPeriodic();
	
	private void threadRun() {
		System.out.println("INFO: Thread Start: " + this.threadName);
		birth();
		while (!this.threadStopRequested) {
			try {
				runPeriodic();
			} catch (Exception eee) {
				eee.printStackTrace();
			}
		}
		death();
		System.out.println("INFO: Thread Stop: " + this.threadName);
	}
	
	/**
	 * Starts the thread execution.
	 *
	 * <p>Creates and starts a new thread. If a thread is already running, it will be stopped first.</p>
	 */
	public void threadStart() {
		System.out.println("INFO: Start the thread : " + this.threadName);
		if (this.threadInstance != null) {
			threadStop();
		}
		this.threadStopRequested = false;
		this.threadInstance = new Thread() {
			@Override
			public void run() {
				threadRun();
			}
		};
		this.threadInstance.setName(this.threadName);
		this.threadInstance.start();
	}
	
	/**
	 * Stops the thread execution.
	 *
	 * <p>Requests the thread to stop and waits for it to terminate gracefully.</p>
	 */
	public void threadStop() {
		if (this.threadStopRequested) {
			return;
		}
		this.threadStopRequested = true;
		if (this.threadInstance == null) {
			return;
		}
		this.threadInstance.interrupt();
		try {
			this.threadInstance.join();
		} catch (InterruptedException eee) {
			// nothing to do
		}
		this.threadInstance = null;
	}
	
}
