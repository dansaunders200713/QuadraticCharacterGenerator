/**
 * @author Dan Saunders
 * 27 October 2015
 * QuadForm.java
 * For our experiment, these are quadratic forms over the integers mod 2.
 */

import java.util.Random;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class QuadForm {
	private Random rand = new Random();
	private BitSet monomials = new BitSet(22); // need to impose ordering... x1x2, x1x3, x1x4, x1x5, x1x5, x2x3, x2x4, x2x5, x2x6,
											   // x3x4, x3x5, x3x6, x4x5, x4x6, x5x6 correspond to bits 1-15. x1, x2, x3, x4, x5, x6
											   // correspond to bits 16 - 21, and the constant 1 corresponds to bit 22
	private BitSet wittDecompMonomials = new BitSet(22);
	static final Map<Integer, Tuple> bitMap;
	static {
		bitMap = new HashMap<Integer, Tuple>();
		bitMap.put(0, new Tuple(1, 2)); bitMap.put(1, new Tuple(1, 3)); bitMap.put(2, new Tuple(1, 4)); bitMap.put(3, new Tuple(1, 5)); 
		bitMap.put(4, new Tuple(1, 6)); bitMap.put(5, new Tuple(2, 3)); bitMap.put(6, new Tuple(2, 4)); bitMap.put(7, new Tuple(2, 5)); 
		bitMap.put(8, new Tuple(2, 6)); bitMap.put(9, new Tuple(3, 4)); bitMap.put(10, new Tuple(3, 5)); bitMap.put(11, new Tuple(3, 6)); 
		bitMap.put(12, new Tuple(4, 5)); bitMap.put(13, new Tuple(4, 6)); bitMap.put(14, new Tuple(5, 6)); bitMap.put(15, new Tuple(1, 1)); 
		bitMap.put(16, new Tuple(2, 2)); bitMap.put(17, new Tuple(3, 3)); bitMap.put(18, new Tuple(4, 4)); bitMap.put(19, new Tuple(5, 5)); 
		bitMap.put(20, new Tuple(6, 6)); bitMap.put(21, new Tuple(0, 0)); 
	}
	static final Map<Integer, Integer> reverseBitMap;
	static {
		reverseBitMap = new HashMap<Integer, Integer>();
		for (Map.Entry<Integer, Tuple> entry : bitMap.entrySet()) {
			reverseBitMap.put((int)Math.pow(entry.getValue().getX(), 2) + (int)Math.pow(entry.getValue().getY(), 2), entry.getKey());
		}
	}
	private int rank, support;
	
	/**
	 * This constructor creates a random quadratic form.
	 */
	public QuadForm () {}
	
	public void generateRandomQuadForm () {
		for (int i = 0; i < 22; i++) {
			if (rand.nextInt(2) == 1) {
				this.monomials.set(i);
			}
		}
		wittDecomp();
		calcSupport();
	}
	
	public QuadForm (int key) {
		monomials.clear();
		switch (key) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			monomials.set(21);
			break;
		case 3:
			monomials.set(15);
			break;
		case 4:
			monomials.set(15);
			monomials.set(21);
			break;
		case 5:
			monomials.set(0);
			break;
		case 6:
			monomials.set(0);
			monomials.set(21);
			break;
		case 7:
			monomials.set(0);
			monomials.set(17);
			break;
		case 8:
			monomials.set(0);
			monomials.set(17);
			monomials.set(21);
			break;
		case 9:
			monomials.set(0);
			monomials.set(9);
			break;
		case 10:
			monomials.set(0);
			monomials.set(9);
			monomials.set(21);
			break;
		case 11:
			monomials.set(0);
			monomials.set(9);
			monomials.set(19);
			break;
		case 12:
			monomials.set(0);
			monomials.set(9);
			monomials.set(19);
			monomials.set(21);
			break;
		case 13:
			monomials.set(0);
			monomials.set(9);
			monomials.set(14);
			break;
		case 14:
			monomials.set(0);
			monomials.set(9);
			monomials.set(14);
			monomials.set(21);
			break;
		}
		wittDecomp();
		calcSupport();
	}
	
	/**
	 * getter method for the rank of this quadratic form.
	 * @return - rank.
	 */
	public int getRank() {
		return rank;
	}
	
	/**
	 * getter method for the support of this quadratic form.
	 * @return - support.
	 */
	public int getSupport() {
		return support;
	}
	
	/**
	 * getter method for the BitSet representation of monomials in the quadratic form.
	 * @return - monomials.
	 */
	public BitSet getMonomials() {
		return monomials;
	}
	
	/**
	 * setter methdo for the BitSet representation of monomials in the quadratic form.
	 * @param monomials
	 */
	public void setMonomials(BitSet monomials) {
		this.monomials = monomials;
	}
	
	/**
	 * helper method for wittDecomp. performs a single step in the decomposition.
	 */
	public void wittDecompStep(int k) {
		rank++;
		Tuple quadTerm = bitMap.get(k);
		BitSet l1 = new BitSet(22), l2 = new BitSet(22);
		l1.set(reverseBitMap.get(2*((int)Math.pow(quadTerm.getX(), 2))));
		l2.set(reverseBitMap.get(2*((int)Math.pow(quadTerm.getY(), 2))));
		for (int j = 0; j < wittDecompMonomials.length(); j++) { // for each monomial in the quadratic form
			if (bitMap.get(j).equals(quadTerm) || bitMap.get(j).isOne()) { // if the monomial is either the chosen one for decomposition, or the constant 1
				continue;
			} else {
				if (wittDecompMonomials.get(j) && bitMap.get(j).contains(quadTerm.getX())) { // if the monomial exists in the form and contains the first chosen decomp term
					if (bitMap.get(j).getX() == bitMap.get(j).getY()) { // if the monomial is linear
						l2.set(reverseBitMap.get(0)); // flip on the constant term in l2
					}
					else { // if the term is quadratic
						if (bitMap.get(j).getX() == quadTerm.getX()) { // if the left term of the monomial is equal to the left term of the decomp monomial
							l2.set(reverseBitMap.get(2*((int)Math.pow(bitMap.get(j).getY(), 2)))); // flip the bit corresponding to that linear variable
						}
						else { // if the right term of the monomial is equal to the left term of the decomp term
							l2.set(reverseBitMap.get(2*((int)Math.pow(bitMap.get(j).getX(), 2)))); // flip the bit corresponding to that linear variable
						}
					}
				}
				if (wittDecompMonomials.get(j) && bitMap.get(j).contains(quadTerm.getY())) { // if the monomial exists in the form and contains the second chosen decomp term
					if (bitMap.get(j).getX() == bitMap.get(j).getY()) { // if the monomial is linear
						l1.set(reverseBitMap.get(0)); // flip on the constant term in l1
					}
					else { // if the term is quadratic
						if (bitMap.get(j).getX() == quadTerm.getY()) { // if the left term of the monomial is equal to the right term of the decomp monomial
							l1.set(reverseBitMap.get(2*((int)Math.pow(bitMap.get(j).getY(), 2)))); // flip the bit corresponding to that linear variable
						}
						else { // if the right term of the monomial is equal to the right term of the decomp monomial
							l1.set(reverseBitMap.get(2*((int)Math.pow(bitMap.get(j).getX(), 2)))); // flip the bit corresponding to that linear variable
	
						}
					}
				}
			}
		}
		quadFormSubtract(quadFormMultiply(l1, l2)); // alter the monomial class variable, setting it to (monomial - l1*l2)
	}
	
	/**
	 * This method performs the Witt decomposition algorithm on the randomly 
	 * generated quadratic form. The decomposition will create at most 2/n pairs of 
	 * linearly independent products. The algorithm returns the Witt rank of the 
	 * quadratic form, defined by the number of pairs of linearly independent linear forms.
	 * @return r, the Witt rank of the randomly generated quadratic form.
	 */
	public void wittDecomp() {
		for (int i = 0; i < 22; i++) {
			if (monomials.get(i)) {
				wittDecompMonomials.set(i);
			}
		}
		while (!isLinear()) {
			for (int k = 0; k < 15; k++) {
				if (wittDecompMonomials.get(k)) {
					wittDecompStep(k);
					break; // break out of the loop and start again from the first possible quadratic term
				}
			}
		}
	}
	
	/**
	 * This function takes in a Bitset to subtract from the class level quadratic form.
	 * @param product - this is a BitSet of L1 * L2.
	 */
	public void quadFormSubtract(BitSet product) {
		for (int i = 0; i < 22; i++) { // for each of the possible terms in the BitSets
			if (this.wittDecompMonomials.get(i) && product.get(i)) { // if x_i is on for both 
				this.wittDecompMonomials.clear(i); // x_i - x_i = 0
			}
			else if (!this.wittDecompMonomials.get(i) && product.get(i)) { // if x_i is on for only the product L1 * L2 
				this.wittDecompMonomials.set(i); // 0 - x_i = x_i
			} 
			// other cases: both off, x_i stays off. monomials on, product off, x_i stays on
		}
	}

	/**
	 * This function takes in two BitSets and returns their product.
	 * @param l1 - BitSet 1.
	 * @param l2 - BitSet 2.
	 * @return - the product of the BitSets.
	 */
	public BitSet quadFormMultiply(BitSet l1, BitSet l2) {
		BitSet l1l2 = new BitSet(22);
		for (int i = 15; i < 22; i++) { // for each possible linear / constant term
			for (int j = 15; j < 22; j++) { // for each possible linear / constant term
				if (l1.get(i) && l2.get(j)) { // if both on
					if (i == j) { // x_i = x_j
						if (!l1l2.get(i)) {
							l1l2.set(i); // x * x = x (in our algebraic system)			
						} else {
							l1l2.clear(i); // already set... x + x = 0
						}
					}
					else if (i == 21) { // x_21 the constant term
						if (!l1l2.get(j)) {
							l1l2.set(j); // x_j * 1 = x_i		
						} else {
							l1l2.clear(j); // x_j + x_j = 0
						}

					}
					else if (j == 21) { // x_21 the constant term
						if (!l1l2.get(i)) {
							l1l2.set(i); // x_i * 1 = x_i
						} else {
							l1l2.clear(i); // x_i + x_i = 0
						}
					}
					else { // i, j != 21, i != j
						if (!l1l2.get(reverseBitMap.get((int)Math.pow(bitMap.get(i).getX(), 2) + (int)Math.pow(bitMap.get(j).getX(), 2))))  {
							l1l2.set(reverseBitMap.get((int)Math.pow(bitMap.get(i).getX(), 2) + (int)Math.pow(bitMap.get(j).getX(), 2))); // turn on bit x_i*x_j
						} else {
							l1l2.clear(reverseBitMap.get((int)Math.pow(bitMap.get(i).getX(), 2) + (int)Math.pow(bitMap.get(j).getX(), 2))); // x_i*x_j + x_i*x_j = 0
						}
					}
				}
			}
		}
		return l1l2;
	}

	/**
	 * A simple helper method to test linearity of this quadratic form. This is a crucial
	 * part of the implementation of the Witt decomposition algorithm.
	 * @return - a boolean that specifies whether or not the form is linear.
	 */
	private boolean isLinear() { 
		for (int i = 0; i < 15; i++) { // check each quadratic term
			if (wittDecompMonomials.get(i)) { // if any quadratic term is on
				return false; 
			}
		}
		return true;
	}
	
	/**
	 * A method that calculates the support of the function specified by this quadratic
	 * form, where the support is the number of inputs on which the function is non-zero. Since
	 * this quadratic form is over Z2, the support is the number of inputs on which the function 
	 * evaluates to 1. This method relies on the helper method evalToOne().
	 * @return - support, an integer specifying the above.
	 */
	public void calcSupport() {
		int support = 0;
		for (int x1 = 0; x1 < 2; x1++) {
			for (int x2 = 0; x2 < 2; x2++) {
				for (int x3 = 0; x3 < 2; x3++) {
					for (int x4 = 0; x4 < 2; x4++) {
						for (int x5 = 0; x5 < 2; x5++) {
							for (int x6 = 0; x6 < 2; x6++) {
								int[] bits = {x1, x2, x3, x4, x5, x6};
								if (evalToOne(bits)) {
									support++;
								}
							}
						}
					}
				}
			}
		}
		this.support = support;
	}
	
	/**
	 * This function takes in a setting of the six bits x1, ..., x6, and returns their value
	 * evaluated on the function specified by this quadratic form.
	 * @param bits - x1, ..., x6.
	 * @return eval - value of function on Z2 on the input bit vector.
	 */
	public boolean evalToOne(int[] bits) {
		boolean eval = false; // start out 0
		for (int i = 0; i < 15; i++) { // for each possible quadratic monomial
			if (monomials.get(i) && bits[bitMap.get(i).getX()-1] == 1 && bits[bitMap.get(i).getY()-1] == 1) { // if this monomial exists and their bits are on
				eval = !eval; // add 1 to the evaluation of the form
			}
		}
		for (int i = 15; i < 21; i++) { // for each possible linear monomial
			if (monomials.get(i) && bits[bitMap.get(i).getX()-1] == 1) { // if this monomial exists and its bit is on
				eval = !eval; // add 1 to the evaluation of the form
			}
		}
		if (monomials.get(21)) { // if the constant monomial 1 is on
			eval = !eval; // add 1 to the evaluation of the form
		}
		return eval;
	}
	
	/**
	 * Generic toString method for a quadratic form.
	 */
	public String toString() {
		return monomials.toString() + ", rank: " + rank + ", support: " + support;
	}
}
