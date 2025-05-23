package org.atriasoft.etk.util;

import java.util.concurrent.locks.Lock;

/**
 * simple use:
 * try (AutoUnlock autoUnlock = AutoUnlock.lock(lock)) {
 * 
 * @author heero
 *
 */
public class AutoUnLock implements AutoCloseable {
	public static AutoUnLock lock(final Lock lock) {
		lock.lock();
		return new AutoUnLock(lock);
	}
	
	public static AutoUnLock tryLock(final Lock lock) throws Exception {
		if (!lock.tryLock()) {
			throw new Exception("wxvwvc");
		}
		return new AutoUnLock(lock);
	}
	
	private final Lock lock;
	
	private AutoUnLock(final Lock lock) {
		this.lock = lock;
	}
	
	@Override
	public void close() {
		this.lock.unlock();
	}
}