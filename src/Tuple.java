/**
 * @author Dan Saunderes
 * 30 October 2015
 * Tuple.java
 * A class that represents a pair of integers.
 */
public class Tuple {
	private final Integer x;
	private final Integer y;
	
	public Tuple(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}
	
	public Integer getX() {
		return x;
	}
	
	public Integer getY() {
		return y;
	}
	
	public String toString() {
		return 	"(" + x + ", " + y + ")";
	}
	
	public boolean contains(Integer x) {
		if (this.x == x || this.y == x) {
			return true;
		}
		return false;
	}
	
	public boolean equals(Object o) {
		Tuple other = (Tuple)o;
		if (this.getX() == other.getX() && this.getY() == other.getY()) 
			return true;
		return false;
	}
	
	public boolean isOne() {
		return (x == 0 && y == 0);
	}
}
