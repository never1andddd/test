public class LinkedListDeque<T> implements Deque<T> {
	private class Node{
		private T item;
		private Node next;
		private Node prev;

		Node(T i, Node p, Node n) {
			this.item = i;
			this.next = n;
			this.prev = p;
		}

	}
	private Node sentinel;
	private int size;

	/**creates an empty list.*/
	public LinkedListDeque() {
		size = 0;
		sentinel = new Node(null,null, null);
		sentinel.next = sentinel;
		sentinel.prev = sentinel;
	}

	@Override
	/** Adds an item of type T to the front of the deque. */
	public void addFirst(T item) {
		Node oldFrontNode = sentinel.next;
		Node newNode = new Node(item, sentinel, oldFrontNode);
		sentinel.next = newNode;
		sentinel.next.next.prev = newNode;
		size += 1;
	}

	@Override
	/** Adds an item of type T to the back of the deque. */
	public void addLast(T item) {
		size += 1;
		Node oldLastNode = sentinel.prev;
		Node newNode = new Node(item, oldLastNode, sentinel);
		oldLastNode.next = newNode;
		sentinel.prev = newNode;
	}

	@Override
	/** Returns true if deque is empty, false otherwise. */
	public boolean isEmpty() {
		if (sentinel.prev == sentinel) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@Override
	/** Returns the number of items in the deque. */
	public int size() {
		return size;
	}

	@Override
	/** Prints the items in the deque from first to last, separated by a space. */
	public void printDeque() {
		for (Node p = sentinel.next; p != sentinel; p = p.next) {
			System.out.print(p.item + " ");
		}
	}

	@Override
	/** Removes and returns the item at the front of the deque. If no such item exists, returns null. */
	public T removeFirst() {
		Node first = sentinel.next;
		if (first == sentinel) {
			return null;
		}
		sentinel.next = first.next;
		first.next.prev = sentinel;
		size -= 1;
		return first.item;
	}

	@Override
	/** Removes and returns the item at the back of the deque. If no such item exists, returns null. */
	public T removeLast() {
		T back = sentinel.prev.item;
		if (sentinel.prev == sentinel) {
			return null;
		}
		sentinel.prev = sentinel.prev.prev;
		sentinel.prev.next = sentinel;
		size -= 1;
		return back;
	}

	@Override
	/** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
	 If no such item exists, returns null. Must not alter the deque! */
	public T get(int index) {
		if (index == 0) {
			return sentinel.next.item;
		}
		Node currentNode = sentinel.next.next;
		while (index > 1 && currentNode.next != sentinel) {
			index -= 1;
			currentNode = currentNode.next;
		}
		if (index != 1){
			return null;
		}
		return currentNode.item;
	}

	/** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
	 If no such item exists, returns null. Must not alter the deque! */

	private T getRecursiveHelper(Node currentNode, int index) {
		if (index == 0) {
			return currentNode.item;
		}
		return getRecursiveHelper(currentNode.next, index - 1);
	}

	public T getRecursive(int index) {
		if (index >= size) {
			return null;
		}
		return getRecursiveHelper(sentinel.next, index);
	}
}

