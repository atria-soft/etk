package org.atriasoft.etk;

public abstract class ThreadAbstract {
	// thread section:
	private boolean threadStopRequested = false;
	private Thread threadInstance = null;
	private final String threadName;
	
	public ThreadAbstract(final String name) {
		this.threadName = name;
	}
	
	protected abstract void birth();
	
	protected abstract void death();
	
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
