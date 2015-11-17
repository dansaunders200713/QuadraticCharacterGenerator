/**
 * @author Dan Saunders
 * 2 November 2015
 * QuadractricCharacter.java
 * This class is a representation of a quadractic character over Z3 mod Z2,
 * and are randomly generated when the constructor is called.
 */

import java.util.BitSet;

public class QuadCharacter {
	QuadForm quadForm;
	BitSet ones = new BitSet(64), twos = new BitSet(64);
	Function f;
	
	public QuadCharacter () {
		this.quadForm = new QuadForm();
		f = new Function(this);
		f.populateFunctionVectors();
		ones = f.ones;
		twos = f.twos;
	}
	
	public String toString() {
		return "\nQuadratic Form: " + quadForm.toString() + "\nOnes: " + ones + "\nTwos: " + twos;
	}
}
