package hw6;

public class MyPriorityQueue<T extends Comparable<? super T>> {

	private dHeap<T> d;

	public MyPriorityQueue(int initialSize) {
		d = new dHeap(3, initialSize, true);
	}

	/**
	 * Inserts an element into the Priority Queue. The element received cannot
	 * be null.
	 *
	 * @param element
	 *            Element to be inserted.
	 * @throws NullPointerException
	 *             if the element received is null.
	 * @return returns true
	 */
	public boolean offer(T element) throws NullPointerException {
		if (element == null) {
			throw new NullPointerException();
		} else {
			d.add(element);
			return true;
		}
	}

	/**
	 * Retrieves the head of this Priority Queue (largest element), or null if
	 * the queue is empty.
	 *
	 * @return The head of the queue (largest element), or null if queue is
	 *         empty.
	 */
	public T poll() {
		if (d.size() == 0) {
			return null;
		} else {
			return d.remove();
		}
	}

	/**
	 * Clears the contents of the queue
	 */
	public void clear() {
		d.clear();
	}

	/**
	 * Retrieves, but does not remove, the head of this queue, or returns null
	 * if this queue is empty.
	 * 
	 * @return the next item to be removed, null if the queue is empty
	 */
	public T peek() {
		if (d.size() == 0) {
			return null;
		} else {
			return d.element();
		}
	}

}
