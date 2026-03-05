package org.atriasoft.etk.util;

/**
 * Simple mutable wrapper for any object type.
 *
 * <p>Provides a generic container that allows modification of the contained value,
 * useful for passing mutable references or creating modifiable containers.</p>
 *
 * @param <T> Type of the wrapped value
 *
 * @author Edouard DUPIN
 * @since 0.1.0
 */
public class Dynamic<T> {
	public T value;
	
	/**
	 * Creates a new Dynamic wrapper with the specified initial value.
	 *
	 * @param value Initial value to wrap
	 */
	public Dynamic(final T value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "Dynamic<" + super.toString() + ">";
	}
}
