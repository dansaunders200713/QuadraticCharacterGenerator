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
		this.quadForm.generateRandomQuadForm();
		f = new Function(this);
		f.populateFunctionVectors();
		ones = f.ones;
		twos = f.twos;
	}
	
	public QuadCharacter (int key) {
		this.quadForm = new QuadForm(key);
		f = new Function(this);
		f.populateFunctionVectors();
		ones = f.ones;
		twos = f.twos;
	}
	
	public QuadCharacter (BitSet monomials) {
		this.quadForm = new QuadForm();
		this.quadForm.setMonomials(monomials);
		f = new Function(this);
		f.populateFunctionVectors();
		ones = f.ones;
		twos = f.twos;
	}
	
	public String toString() {
		return "\nQuadratic Form: " + quadForm.toString() + "\nOnes: " + ones + "\nTwos: " + twos;
	}
	
	public boolean equals(Object o) {
		QuadCharacter other = (QuadCharacter)o;
		return this.ones.equals(other.ones) && this.twos.equals(other.twos);
	}
}
