import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dan Saunders
 * 28 October 2015
 * QuadFormGenerator.java
 * This class will randomly generate n quadratic characters 10000 times, compute the function given by their sum,
 * and create a HashMap from the support of each of the 10000 functions to their count out of 10000
 */

public class QuadCharacterGenerator {
	public static void main (String[] args) throws InterruptedException {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter 2-weight n: ");
		int n = sc.nextInt();
		sc.close();
		
		Map<Integer, Integer> supportMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> onesMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> twosMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> formSupportMap = new HashMap<Integer, Integer>();

		for (int i = 0; i < 10000; i++) {
			List<QuadCharacter> quadCharacters = new ArrayList<QuadCharacter>();
			for (int j = 0; j < n; j++) {
				QuadCharacter quadCharacter = new QuadCharacter();
				quadCharacters.add(quadCharacter);
				int numOnes = quadCharacter.ones.cardinality();
				int numTwos = quadCharacter.twos.cardinality();
				if (onesMap.containsKey(numOnes)) {
					onesMap.put(numOnes, onesMap.get(numOnes) + 1);
				} else {
					onesMap.put(numOnes, 1);
				}
				if (twosMap.containsKey(numTwos)) {
					twosMap.put(numTwos, twosMap.get(numTwos) + 1);
				} else {
					twosMap.put(numTwos, 1);
				}
				int formSupport = quadCharacter.quadForm.getSupport();
				if (formSupportMap.containsKey(formSupport)) {
					formSupportMap.put(formSupport, formSupportMap.get(formSupport) + 1);
				} else {
					formSupportMap.put(formSupport, 1);
				}
			}
			Function f = new Function(quadCharacters);
			int support = f.support;
			if (supportMap.containsKey(support)) {
				supportMap.put(support, supportMap.get(support) + 1);
			}
			else {
				supportMap.put(support, 1);
			}
		}
		System.out.println(supportMap);
		System.out.println("Ones: " + onesMap);
		System.out.println("Twos: " + twosMap);
		System.out.println("Form Supports: " + formSupportMap);
	}
}
