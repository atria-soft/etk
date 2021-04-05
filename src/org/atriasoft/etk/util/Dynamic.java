package org.atriasoft.etk.util;

public class Dynamic<T> {
	public T value;
	
	public Dynamic(final T value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "Dynamic<" + super.toString() + ">";
	}
}
