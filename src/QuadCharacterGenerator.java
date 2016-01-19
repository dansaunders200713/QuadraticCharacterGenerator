import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dan Saunders
 * 28 October 2015
 * QuadFormGenerator.java
 * This class will randomly generate n quadratic characters 10000 times, compute the function given by their sum,
 * and create a HashMap from the support of each of the 10000 functions to their count out of 10000
 */

public class QuadCharacterGenerator {
	public static void main (String[] args) throws InterruptedException, FileNotFoundException, UnsupportedEncodingException {
		
		PrintWriter writer = new PrintWriter("2weight3generation.txt", "UTF-8");

		BigInteger[][] totalSupportTable = new BigInteger[65][65]; // this will hold the distribution of 1's and 2's vectors for all functions we generate
		for (int k = 0; k < 65; k++) {
			for (int j = 0; j < 65; j++) {
				totalSupportTable[k][j] = BigInteger.valueOf(0); // set to zero to avoid null pointer exceptions
			}
		}
		int count = 0; // counting the number of functions generated: should be 58720256
		QuadCharacter one = new QuadCharacter(0); // the quadratic character that represents 1, or 2^0
		for (int i = 1; i < 15; i++) { // for each Witt Normal form for n = 6
			BigInteger[][] supportTable = new BigInteger[65][65]; // the distribution of 1's and 2's vectors for all functions of this particular Witt Normal form
			Map<Integer, Integer> supportMap = new HashMap<Integer, Integer>(); // the distribution of support for all functions of this particular Witt Normal form 
			for (int k = 0; k < 65; k++) {
				for (int j = 0; j < 65; j++) {
					supportTable[k][j] = BigInteger.valueOf(0); // set to zero to avoid null pointer exceptions
				}
			}
			QuadCharacter u = new QuadCharacter(i); // this represents the quadratic character with the current Witt Normal form as its quadratic form, or 2^u
			
			for (int x1 = 0; x1 < 2; x1++) {
			for (int x2 = 0; x2 < 2; x2++) {
			for (int x3 = 0; x3 < 2; x3++) {
			for (int x4 = 0; x4 < 2; x4++) {
			for (int x5 = 0; x5 < 2; x5++) {
			for (int x6 = 0; x6 < 2; x6++) {
			for (int x7 = 0; x7 < 2; x7++) {
			for (int x8 = 0; x8 < 2; x8++) {
			for (int x9 = 0; x9 < 2; x9++) {
			for (int x10 = 0; x10 < 2; x10++) {
			for (int x11 = 0; x11 < 2; x11++) {
			for (int x12 = 0; x12 < 2; x12++) {
			for (int x13 = 0; x13 < 2; x13++) {
			for (int x14 = 0; x14 < 2; x14++) {
			for (int x15 = 0; x15 < 2; x15++) {
			for (int x16 = 0; x16 < 2; x16++) {
			for (int x17 = 0; x17 < 2; x17++) {
			for (int x18 = 0; x18 < 2; x18++) {
			for (int x19 = 0; x19 < 2; x19++) {
			for (int x20 = 0; x20 < 2; x20++) {
			for (int x21 = 0; x21 < 2; x21++) {
			for (int x22 = 0; x22 < 2; x22++) { // setting each of the possible 22 variables exhaustively...
				count++; // count each function
				
				int bits[] = new int[] {x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14, x15, x16, x17, x18, x19, x20, x21, x22};
				QuadCharacter v = createQuadCharacter(bits); // create the quadratic character 2^v
				
				List<QuadCharacter> quadCharacters = new ArrayList<QuadCharacter>(); // create the list of the 3 quadratic characters
				quadCharacters.add(one);
				quadCharacters.add(u);
				quadCharacters.add(v); // add the 3 quadratic characters to the list
				
				Function f = new Function(quadCharacters); // combine the 3 characters to create a 2-weight 3 function
				int support = f.support; // get its total support
				if (supportMap.containsKey(support)) { // this logic increments the supportMap based on the function
					supportMap.put(support, supportMap.get(support) + 1);
				}
				else {
					supportMap.put(support, 1);	
				}
				
				int numOnes = f.ones.cardinality(); // get the number of 1's of the function
				int numTwos = f.twos.cardinality(); // get the number of 2's of the function
				
				if (numOnes == 0 && numTwos == 48) {
					System.out.println("NF form: " + u + "Other form: " + v);
				}
				
				supportTable[numOnes][numTwos] = supportTable[numOnes][numTwos].add(BigInteger.valueOf(1)); // add one to the 1's, 2's distribution at the correct location
			}}}}}}}}}}}}}}}}}}}}}}
			
			System.out.println("WNF: " + i);
			System.out.println(supportMap);
			
			BigInteger s = BigInteger.valueOf(0);
			
			writer.println("WNF: " + i);
			for (int k = 0; k < 65; k++) {
				writer.println(Arrays.toString(supportTable[k]));
				for (int j = 0; j < 65; j++) {
					s = s.add(supportTable[k][j]); // summing all values in the local support table
				}
			}
			writer.println();
			System.out.println("This value should be equal to 4194304: " + s); // check to make sure we generated the correct number of functions
			
			totalSupportTable = weightedSum(totalSupportTable, supportTable, i); // add the local support table to the global one, which accounts for all 14 WNF functions
		}
		BigInteger s = BigInteger.valueOf(0);
				
		System.out.println("Number of 2-weight 3 functions generated: " + count);
		for (int i = 0; i < 65; i++) {
			writer.println(Arrays.toString(totalSupportTable[i])); // write global support table to file for inspection 
			System.out.println(Arrays.toString(totalSupportTable[i]));
			for (int j = 0; j < 65; j++) {
				s = s.add(totalSupportTable[i][j]);
			}
		}
		System.out.println("This value should be equal to 58720256: " + s);
		writer.close();
	}
	
	public static BigInteger[][] weightedSum(BigInteger[][] totalSupportTable, BigInteger[][] supportTable, int i) {
		switch (i) {
		case 1:
			for (int k = 0; k < 65; k++) {
				for (int j = 0; j < 65; j++) {
					totalSupportTable[k][j] = totalSupportTable[k][j].add(supportTable[k][j].multiply(BigInteger.valueOf(1)));
				}
			}
			break;
		case 2:
			for (int k = 0; k < 65; k++) {
				for (int j = 0; j < 65; j++) {
					totalSupportTable[k][j] = totalSupportTable[k][j].add(supportTable[k][j].multiply(BigInteger.valueOf(1)));
				}
			}
			break;
		case 3:
			for (int k = 0; k < 65; k++) {
				for (int j = 0; j < 65; j++) {
					totalSupportTable[k][j] = totalSupportTable[k][j].add(supportTable[k][j].multiply(BigInteger.valueOf(63)));
				}
			}
			break;
		case 4:
			for (int k = 0; k < 65; k++) {
				for (int j = 0; j < 65; j++) {
					totalSupportTable[k][j] = totalSupportTable[k][j].add(supportTable[k][j].multiply(BigInteger.valueOf(63)));
				}
			}
			break;
		case 5:
			for (int k = 0; k < 65; k++) {
				for (int j = 0; j < 65; j++) {
					totalSupportTable[k][j] = totalSupportTable[k][j].add(supportTable[k][j].multiply(BigInteger.valueOf(2604)));
				}
			}
			break;
		case 6:
			for (int k = 0; k < 65; k++) {
				for (int j = 0; j < 65; j++) {
					totalSupportTable[k][j] = totalSupportTable[k][j].add(supportTable[k][j].multiply(BigInteger.valueOf(2604)));
				}
			}
			break;
		case 7:
			for (int k = 0; k < 65; k++) {
				for (int j = 0; j < 65; j++) {
					totalSupportTable[k][j] = totalSupportTable[k][j].add(supportTable[k][j].multiply(BigInteger.valueOf(39060)));
				}
			}
			break;
		case 8:
			for (int k = 0; k < 65; k++) {
				for (int j = 0; j < 65; j++) {
					totalSupportTable[k][j] = totalSupportTable[k][j].add(supportTable[k][j].multiply(BigInteger.valueOf(39060)));
				}
			}
			break;
		case 9:
			for (int k = 0; k < 65; k++) {
				for (int j = 0; j < 65; j++) {
					totalSupportTable[k][j] = totalSupportTable[k][j].add(supportTable[k][j].multiply(BigInteger.valueOf(291648)));
				}
			}
			break;
		case 10:
			for (int k = 0; k < 65; k++) {
				for (int j = 0; j < 65; j++) {
					totalSupportTable[k][j] = totalSupportTable[k][j].add(supportTable[k][j].multiply(BigInteger.valueOf(291648)));
				}
			}
			break;
		case 11:
			for (int k = 0; k < 65; k++) {
				for (int j = 0; j < 65; j++) {
					totalSupportTable[k][j] = totalSupportTable[k][j].add(supportTable[k][j].multiply(BigInteger.valueOf(874944)));
				}
			}
			break;
		case 12:
			for (int k = 0; k < 65; k++) {
				for (int j = 0; j < 65; j++) {
					totalSupportTable[k][j] = totalSupportTable[k][j].add(supportTable[k][j].multiply(BigInteger.valueOf(874944)));
				}
			}
			break;
		case 13:
			for (int k = 0; k < 65; k++) {
				for (int j = 0; j < 65; j++) {
					totalSupportTable[k][j] = totalSupportTable[k][j].add(supportTable[k][j].multiply(BigInteger.valueOf(888832)));
				}
			}
			break;
		case 14:
			for (int k = 0; k < 65; k++) {
				for (int j = 0; j < 65; j++) {
					totalSupportTable[k][j] = totalSupportTable[k][j].add(supportTable[k][j].multiply(BigInteger.valueOf(888832)));
				}
			}
			break;
		}
		return totalSupportTable;
	}
	
	public static QuadCharacter createQuadCharacter(int[] bits) {
		BitSet b = new BitSet(22);
		for (int i = 0; i < bits.length; i++) {
			if (bits[i] == 1) {
				b.set(i);
			}
		}
		return new QuadCharacter(b);
	}
}
