/*
 * Name: Nang Chen
 * Login: cs12saw
 * PID: A14205066
 * Date: 05/16/2017
 * File: dHeap.java
 * 
 * This is a class that implements the heap structure with 
 * multiple branching factors.
 */
package hw6;

import java.util.*;

public class dHeap<T extends Comparable<? super T>> implements dHeapInterface<T> {

	private T[] heap; //heap array
	private int d; //branching factor
	private int nelems;
	private boolean isMaxHeap; //boolean to indicate whether heap is max or min

	/**
	 * Initializes a binary max heap with capacity = 7
	 */
	public dHeap() {
		this.heap = (T[]) new Comparable[7];
		this.d = 2;
		this.nelems = 0;
		isMaxHeap = true;
	}

	/**
	 * Initializes a binary max heap with a given initial capacity.
	 * 
	 * @param heapSize
	 *            The initial capacity of the heap.
	 */
	public dHeap(int heapSize) {
		this.heap = (T[]) new Comparable[heapSize];
		this.d = 2;
		this.nelems = 0;
		isMaxHeap = true;
	}

	/**
	 * Initializes a d-ary heap (with a given value for d), with a given initial
	 * capacity.
	 * 
	 * @param d
	 *            The number of child nodes each node in the heap should have.
	 * @param heapSize
	 *            The initial capacity of the heap.
	 * @param isMaxHeap
	 *            indicates whether the heap should be max or min
	 * @throws IllegalArgumentException
	 *             if d is less than one.
	 */

	public dHeap(int d, int heapSize, boolean isMaxHeap) throws IllegalArgumentException {
		if (d < 1) {
			throw new IllegalArgumentException();
		} else {
			this.d = d;
			this.nelems = 0;
			this.heap = (T[]) new Comparable[heapSize];
			this.isMaxHeap = isMaxHeap;
		}
	}

	/**
	 * Returns the number of elements stored in the heap.
	 * 
	 * @return The number of elements stored in the heap.
	 */
	@Override
	public int size() {
		return nelems;
	}

	/**
	 * Adds the specified element to the heap; o cannot be null. Resizes the
	 * storage if full.
	 * 
	 * @param o
	 *            The element to add.
	 * @throws NullPointerException
	 *             if o is null.
	 */
	@Override
	public void add(T data) throws NullPointerException {
		if (data == null) {
			throw new NullPointerException();
		} else {
			if (heap.length == size()) {
				heap = this.resize();
			}
			heap[nelems] = data;
			nelems++;
			bubbleUp(nelems);
		}
	}

	/**
	 * Removes and returns the element at the root. If the heap is empty, then
	 * this method throws a NoSuchElementException.
	 * 
	 * @return The element at the root stored in the heap.
	 * @throws NoSuchElementException
	 *             if the heap is empty
	 */
	@Override
	public T remove() throws NoSuchElementException {
		if (size() == 0) {
			throw new NoSuchElementException();
		}
		T heapTemp = heap[0];
		heap[0] = heap[nelems - 1];
		heap[nelems - 1] = null;
		trickleDown(0);
		nelems--;
		return heapTemp;
	}

	/**
	 * Clears all the items in the heap Heap will be empty after this call
	 * returns
	 */
	public void clear() {
		for (int i = 0; i < heap.length; i++) {
			heap[i] = null;
		}
		nelems = 0;
	}

	/**
	 * Retrieves, but does not remove, the element at the root.
	 * 
	 * @return item at the root of the heap
	 * @throws NoSuchElementException
	 *             - if this heap is empty
	 */
	public T element() throws NoSuchElementException {
		if (size() == 0) {
			throw new NoSuchElementException();
		} else {
			return heap[0];
		}
	}

	/**
	 * A helper method that tries to trickle down target elements to the target
	 * position when removing until the heap follows its rule.
	 * 
	 * @param index
	 */
	private void trickleDown(int index) {
		// if size is smaller or equal to 1
		if (nelems <= 1) {
			return;
		}
		// if the largest child is larger or equal to length of heap
		if (getChildLarge(index) >= heap.length) {
			return;
		}
		// if the largest child does not exist
		if (heap[getChildLarge(index)] == null) {
			return;
		}
		// case of max heap
		if (isMaxHeap) {
			int largest = getChildLarge(index);
			// if the largest child is larger than parent
			if (heap[parent(largest)].compareTo(heap[largest]) < 0) {
				swap(heap, parent(largest), largest);
				trickleDown(largest);
			}
			// case of min heap
		} else {
			int smallest = getChildSmall(index);
			// if the smallest child is smaller than parent
			if (heap[parent(smallest)].compareTo(heap[smallest]) > 0) {
				swap(heap, smallest, parent(smallest));
				trickleDown(smallest);
			}
		}
	}

	/**
	 * A helper method that tries to bubble up target elements to the target
	 * position when adding until the heap follows its rule.
	 * 
	 * @param index
	 */
	private void bubbleUp(int index) {
		if (nelems <= 1) {
			return;
		}
		if (index > 0) {
			// case of max heap
			if (isMaxHeap) {
				// if parent is smaller than current
				if (heap[index - 1].compareTo(heap[parent(index - 1)]) > 0) {
					swap(heap, index - 1, parent(index - 1));
					bubbleUp(parent(index - 1));
				}
				// case of min heap
			} else {
				// if parent is greater than current
				if (heap[index - 1].compareTo(heap[parent(index - 1)]) < 0) {
					swap(heap, index - 1, parent(index - 1));
					bubbleUp(parent(index - 1));
				}
			}
		}
	}

	/**
	 * A helper method that finds the index of the largest child of a element.
	 * 
	 * @param index
	 */
	private int getChildLarge(int index) {
		// if its left child is larger or equals to the length of heap
		if (getChildOnLeft(index) >= heap.length) {
			return heap.length + 1;
		}
		int maxIndex = getChildOnLeft(index);
		int maxTemp = maxIndex;
		// get the largest child from all the children
		for (int k = maxTemp + 1; k < maxTemp + d; k++) {
			if (heap[k] == null) {
				return maxIndex;
			}
			if (heap[k].compareTo(heap[maxIndex]) > 0) {
				maxIndex = k;
			}
		}
		return maxIndex;
	}

	/**
	 * A helper method that finds the index of the smallest child of a element.
	 * 
	 * @param index
	 */
	private int getChildSmall(int index) {
		// if its left child is larger or equals to the length of heap
		if (getChildOnLeft(index) >= heap.length) {
			return heap.length + 1;
		}
		int minIndex = getChildOnLeft(index);
		int minTemp = minIndex;
		// get the smallest child from all the children
		for (int k = minTemp + 1; k < minTemp + d; k++) {
			if (heap[k] == null) {
				return minIndex;
			}
			if (heap[k].compareTo(heap[minIndex]) < 0) {
				minIndex = k;
			}
		}
		return minIndex;
	}

	/**
	 * A helper method that resizes the heap to twice of its size if it's full.
	 */
	private T[] resize() {
		return Arrays.copyOf(heap, heap.length * 2);
	}

	/**
	 * A helper method that finds the index of parent of a element.
	 * 
	 * @param index
	 */
	private int parent(int index) {
		return (index - 1) / d;
	}

	/**
	 * A helper method that finds the left child of a element.
	 * 
	 * @param index
	 */
	private int getChildOnLeft(int index) {
		return index * d + 1;
	}

	/**
	 * A helper method that swaps a element with another one.
	 * 
	 * @param object,
	 *            index1, index2
	 */
	private void swap(T[] object, int index1, int index2) {
		T temp = object[index1];
		object[index1] = object[index2];
		object[index2] = temp;
	}
	
	/**
	 * A method to check if the value in the heap is larger than k
	 * 
	 * @param k
	 * @return linked list which is added with the values larger than k
	 */
	public LinkedList<T> findGreaterThanK(T k){
		LinkedList<T> list = new LinkedList<T>();
		if(isMaxHeap){
		for(int i = 0; i < list.size();i++){
			if (heap[i].compareTo(k)>0){
				list.add(heap[i]);
			}
		}	
		}
		return list;
	}
	
	public int findSum(int[] a, int num1, int num2){
		dHeap<Integer> min = new dHeap(2, a.length, false);
			for(int i = 0; i < a.length;i++){
				min.add(i);
			}
			for(int j = 1;j < num1; j++){
				min.remove();
			}
			int temp = min.element();
			int x = 0;
			for(int k = num1;k<num2;k++){
				min.remove();
				x = x + min.element();
			}
			return x;
	}
} // End of public class dHeap<T extends Comparable<? super T>>
	// implements dHeapInterface<T>.
