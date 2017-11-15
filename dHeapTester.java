/*
 * Name: Nang Chen
 * Login: cs12saw
 * PID: A14205066
 * Date: 05/16/2017
 * File: dHeapTester.java
 * 
 * This is a tester class that tests the methods in dHeap.java.
 */
package hw6;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class dHeapTester {
	private dHeap<Integer> max;
	private dHeap<Integer> min;

	@Before
	public void setUp() {
		max = new dHeap(2, 7, true);
		min = new dHeap(3, 10, false);
	}

	@Test
	public void testAdd() {
		max.add(9);
		max.add(10);
		max.add(4);
		max.add(2);
		max.add(7);
		max.add(6);
		max.add(3);
		assertEquals(7, max.size());
		assertEquals(new Integer(10), max.element());
		min.add(8);
		min.add(2);
		min.add(7);
		min.add(6);
		min.add(3);
		assertEquals(5, min.size());
		assertEquals(new Integer(2), min.element());
	}

	@Test
	public void testRemove() {
		max.add(9);
		max.add(10);
		max.add(4);
		max.add(11);
		max.add(7);
		max.add(6);
		max.add(3);
		max.add(5);
		max.add(4);
		max.add(2);
		max.add(1);
		max.remove();
		max.remove();
		max.remove();
		max.remove();
		max.remove();

		assertEquals(new Integer(5), max.remove());
		assertEquals(5, max.size());

		min.add(9);
		min.add(10);
		min.add(4);
		min.add(11);
		min.add(7);
		min.add(6);
		min.add(3);
		min.add(5);
		min.add(4);
		min.add(2);
		min.add(1);
		min.remove();
		min.remove();
		min.remove();
		min.remove();
		min.remove();
		assertEquals(new Integer(5), min.remove());
		assertEquals(5, min.size());
	}

	@Test
	public void testClear() {
		max.add(9);
		max.add(10);
		max.add(4);
		max.clear();
		assertEquals(0, max.size());
	}

	@Test
	public void testElement() {
		max.add(9);
		max.add(10);
		max.add(4);
		max.add(2);
		max.add(7);
		max.add(6);
		max.add(3);
		assertEquals(new Integer(10), max.element());
		min.add(9);
		min.add(10);
		min.add(4);
		min.add(2);
		min.add(7);
		min.add(6);
		min.add(3);
		assertEquals(new Integer(2), min.element());
	}

	@Test
	public void testSize() {
		max.add(7);
		max.add(6);
		max.add(3);
		assertEquals(3, max.size());
		assertEquals(0, min.size());
	}
}
