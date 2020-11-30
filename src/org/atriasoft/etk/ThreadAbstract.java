package org.atriasoft.etk;

public abstract class ThreadAbstract {
	// thread section:
	private boolean threadStopRequested = false;
	private Thread threadInstance = null;
	private final String threadName;
	public ThreadAbstract(String name) {
		this.threadName = name;
	}

	public void threadStart() {
		System.out.println("INFO: Start the thread : " + this.threadName);
		if (threadInstance != null) {
			threadStop();
		}
		threadStopRequested = false;
		threadInstance = new Thread() {
			public void run() {
				threadRun();
			}
		};
		threadInstance.setName(threadName);
		threadInstance.start();
	}
	
	private void threadRun() {
		System.out.println("INFO: Thread Start: " + threadName);
		birth();
		while (threadStopRequested == false) {
	        try {
	        	runPeriodic();
	        } catch (Exception eee) {
	        	eee.printStackTrace();
	        }
		}
		death();
		System.out.println("INFO: Thread Stop: " + threadName);
	}
	protected abstract void birth();
	protected abstract void runPeriodic();
	protected abstract void death();

	public void threadStop() {
		if (threadStopRequested == true) {
			return;
		}
		threadStopRequested = true;
		if (threadInstance == null) {
			return;
		}
		threadInstance.interrupt();
		try {
			threadInstance.join();
		} catch (InterruptedException eee) {
			// nothing to do
		}
		threadInstance = null;
	}

}
