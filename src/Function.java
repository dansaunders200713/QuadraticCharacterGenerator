import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

/**
 * @author Dan Saunders
 * 27 October 2015
 * Function.java
 * This class represents a character over a field, namely quadratic characters over the field Z_3
 * except for special cases on linear characters or constants.
 */

public class Function {
	BitSet zeros = new BitSet(64), ones = new BitSet(64), twos = new BitSet(64);
	Random rand = new Random();
	QuadCharacter quadCharacter;
	int support;
	
	/**
	 * This constructor will return a function from (Z / 2Z)^6 to (Z / 3Z),
	 * or from {0,1}^6 to Z_3, based on the quadCharacter input.
	 * @param quadCharacter - the quadratic character to generate the function from.
	 */
	public Function(QuadCharacter quadCharacter) {
		this.quadCharacter = quadCharacter;
		populateFunctionVectors();
	}
	
	/**
	 * This constructor takes in a list of quadratic characters, and returns its function from
	 * (Z / 2Z)^6 to (Z / 3Z).
	 * @param quadCharacters - the quadratic characters to generate the function from.
	 */
	public Function(List<QuadCharacter> quadCharacters) {
		List<Function> functions = new ArrayList<Function>();
		for (QuadCharacter quadCharacter : quadCharacters) {
			Function f = new Function(quadCharacter);
			functions.add(f);
		}
		populateFunctionVectors(functions);
		calcSupport();
	}
	
	/**
	 * Method that uses the quadratic character in this instance to compute its function
	 * into Z3.
	 */
	public void populateFunctionVectors() {
		for (int x1 = 0; x1 < 2; x1++) {
			for (int x2 = 0; x2 < 2; x2++) {
				for (int x3 = 0; x3 < 2; x3++) {
					for (int x4 = 0; x4 < 2; x4++) {
						for (int x5 = 0; x5 < 2; x5++) {
							for (int x6 = 0; x6 < 2; x6++) {
								int[] bits = {x1, x2, x3, x4, x5, x6};
								if (this.quadCharacter.quadForm.evalToOne(bits)) {
									twos.set(bits[0]*32+bits[1]*16+bits[2]*8+bits[3]*4+bits[4]*2+bits[5]);
								}
								else {
									ones.set(bits[0]*32+bits[1]*16+bits[2]*8+bits[3]*4+bits[4]*2+bits[5]);
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Method that uses a list of functions to create a new function
	 * @param functions - a list of functions to merged into one new function
	 */
	public void populateFunctionVectors(List<Function> functions) {
		for (int i = 0; i < 64; i++) {
			int sum = 0;
			for (Function function : functions) {
				if (function.ones.get(i)) {
					sum += 1;
				} else if (function.twos.get(i)) {
					sum += 2;
				}
			}
			if (sum % 3 == 0) {
				zeros.set(i);
			} else if (sum % 3 == 1) {
				ones.set(i);
			} else {
				twos.set(i);
			}
		}
	}
	
	/**
	 * Method that calculates the number of inputs on which this functions is non-zero on
	 * @return - integer for support
	 */
	public void calcSupport() {
		int s = 0;
		for (int i = 0; i < 64; i++) {
			if (ones.get(i) || twos.get(i)) {
				s++;
			}
		}
		support = s;
	}
	
	/**
	 * Generic toString method for Function. Returns both ones, twos BitSets
	 */
	public String toString() {
		return "Ones: " + ones.toString() + "\nTwos: " + twos.toString();
	}
}
