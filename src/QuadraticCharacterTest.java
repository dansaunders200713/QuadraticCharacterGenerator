import java.util.BitSet;

import org.junit.Test;
import org.junit.Assert;

/**
 * @author Dan Saunders
 * 9 November 2015
 * QuadraticCharacterTest.java
 * A test class for some of the methods in this project.
 */

public class QuadraticCharacterTest {
	 
	@Test
	public void testQuadFormSubtract() {
		QuadForm q = new QuadForm();
		q.getMonomials().clear();
		q.getMonomials().set(0);
		q.getMonomials().set(1);
		BitSet b = new BitSet();
		b.set(0);
		b.set(2);
		q.quadFormSubtract(b);
		Assert.assertTrue(!q.getMonomials().get(0));
		Assert.assertTrue(q.getMonomials().get(1));
		Assert.assertTrue(q.getMonomials().get(2));
	}
	
	@Test
	public void testWittDecompStep() {
		QuadForm q = new QuadForm();
		q.getMonomials().clear();
		q.getMonomials().set(0);
		q.getMonomials().set(2);
		q.getMonomials().set(6);
		q.getMonomials().set(16);
		q.getMonomials().set(17);
		q.getMonomials().set(21);
		q.wittDecompStep(0);
		Assert.assertTrue(q.getMonomials().get(17) && q.getMonomials().get(21));
	}
	
	@Test
	public void testQuadFormMultiply() {
		BitSet l1 = new BitSet();
		l1.set(15,22);
		BitSet l2 = new BitSet();
		l2.set(15,22);
		QuadForm q = new QuadForm();
		BitSet l1l2 = q.quadFormMultiply(l1, l2);
		Assert.assertTrue(l1l2.get(15) && l1l2.get(16) && l1l2.get(17) && l1l2.get(18) && l1l2.get(19) && l1l2.get(20) && l1l2.get(21));
	}
	
	@Test
	public void testIsOne() {
		Assert.assertTrue(QuadForm.bitMap.get(21).isOne());
	}
	
	@Test
	public void testTupleEquals() {
		Tuple t1 = new Tuple(1, 2);
		Tuple t2 = QuadForm.bitMap.get(0);
		Assert.assertTrue(t1.equals(t2));
	}
	
	@Test
	public void testEvalToOne() {
		int[] bits = {1, 1, 0, 0, 0, 0};
		QuadForm q = new QuadForm();
		q.getMonomials().clear();
		q.getMonomials().set(0);
		q.getMonomials().set(15, 17);
		Assert.assertTrue(q.evalToOne(bits));
		q.getMonomials().clear();
		q.getMonomials().set(21);
		Assert.assertTrue(q.evalToOne(bits));
	}
}
