package org.atriasoft.etk.util;

// Pair class
public class Pair<U, V> {
	// Factory method for creating a Typed Pair immutable instance
	public static <U, V> Pair<U, V> of(final U a, final V b) {
		// calls private constructor
		return new Pair<>(a, b);
	}
	
	public final U first; // first field of a Pair
	
	public final V second; // second field of a Pair
	
	// Constructs a new Pair with specified values
	public Pair(final U first, final V second) {
		this.first = first;
		this.second = second;
	}
	
	@Override
	// Checks specified object is "equal to" current object or not
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		
		final Pair<?, ?> pair = (Pair<?, ?>) o;
		
		// call equals() method of the underlying objects
		if (!this.first.equals(pair.first)) {
			return false;
		}
		return this.second.equals(pair.second);
	}
	
	@Override
	// Computes hash code for an object to support hash tables
	public int hashCode() {
		// use hash codes of the underlying objects
		return 31 * this.first.hashCode() + this.second.hashCode();
	}
	
	@Override
	public String toString() {
		return "(" + this.first + ", " + this.second + ")";
	}
	
	public Pair<U, V> withFirst(final U value) {
		return new Pair<>(value, this.second);
	}
	
	public Pair<U, V> withSecond(final V value) {
		return new Pair<>(this.first, value);
	}
	
}

// Program to implement Pair Class in Java
/*
class Main
{
    public static void main(String[] args)
    {
        Pair<String, Integer> p1 = Pair.of("John", 26);
        Pair<String, Integer> p2 = Pair.of("Tom", 30);
        Pair<String, Integer> p3 = Pair.of("John", 26);
 
        List<Pair<String, Integer>> pairs = new ArrayList<>();
        pairs.add(p1);
        pairs.add(p2);
        pairs.add(p3);
 
        System.out.println(pairs);
 
        Set<Pair<String, Integer>> distinctPairs = new HashSet<>(pairs);
        System.out.println(distinctPairs);
    }
}
*/