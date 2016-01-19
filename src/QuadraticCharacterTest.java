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
	public void testWittNormalForm() {
		QuadForm u1 = new QuadForm(1);
		QuadForm u2 = new QuadForm(2);
		QuadForm u3 = new QuadForm(3);
		QuadForm u4 = new QuadForm(4);
		QuadForm u5 = new QuadForm(5);
		QuadForm u6 = new QuadForm(6);
		QuadForm u7 = new QuadForm(7);
		QuadForm u8 = new QuadForm(8);
		QuadForm u9 = new QuadForm(9);
		QuadForm u10 = new QuadForm(10);
		QuadForm u11 = new QuadForm(11);
		QuadForm u12 = new QuadForm(12);
		QuadForm u13 = new QuadForm(13);
		QuadForm u14 = new QuadForm(14);
		System.out.println("{} " + u1);
		System.out.println("{21} " + u2);
		System.out.println("{15} " + u3);
		System.out.println("{15, 21} " + u4);
		System.out.println("{0} " + u5);
		System.out.println("{0, 21} " + u6);
		System.out.println("{0, 17} " + u7);
		System.out.println("{0, 17, 21} " + u8);
		System.out.println("{0, 9} " + u9);
		System.out.println("{0, 9, 21} " + u10);
		System.out.println("{0, 9, 19} " + u11);
		System.out.println("{0, 9, 19, 21} " + u12);
		System.out.println("{0, 9, 14} " + u13);
		System.out.println("{0, 9, 14, 21} " + u14);
	}
	
	@Test
	public void testWittDecompStep() {
		QuadForm q = new QuadForm();
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
