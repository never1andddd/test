/** Performs some basic linked list tests. */
public class ArrayDeque<T> implements Deque<T>{
		private T[] items;
		private int size;
		private int nextFirst;
		private int nextLast;
		private static int RFACTOR=2;

	/** Creates an empty list. */
	public ArrayDeque() {
		size = 0;
		nextFirst = 4;
		nextLast =  5;
		items = (T[]) new Object[8];
	}

	/** Resize our backing array so that it is
	 * of the given capacity. */
	private void resize(int capacity) {
		T[] a = (T[]) new Object[capacity];
		nextFirst = a.length/4;
		nextLast = a.length*3/4;
		System.arraycopy(items, 0, a, nextFirst, size);
		items = a;
	}

	/** Adds an item of type T to the front of the deque. */
	public void addFirst(T item){
		if (size == items.length) {
			resize(size * RFACTOR);
		}
		items[nextFirst] = item;
		nextFirst -= 1;
		if (nextFirst == -1){
			nextFirst = items.length-1;
		}
		size += 1;
	}

	/** Adds an item of type T to the back of the deque. */
	public void addLast(T item){
		if (size == items.length) {
			resize(size * RFACTOR);
		}
		items[nextLast] = item;
		nextLast += 1;
		if (nextLast == items.length){
			nextLast = 0;
		}
		size += 1;
	}

	/** Returns true if deque is empty, false otherwise. */
	public boolean isEmpty(){
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null){
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}

	/** Returns the number of items in the deque. */
	public int size(){
		return size;
	}
	/** Prints the items in the deque from first to last, separated by a space. */
	public void printDeque(){
		for (int i = 0; i<items.length; i++) {
			System.out.print(items[i] + " ");
		}
	}
	/** Removes and returns the item at the front of the deque. If no such item exists, returns null. */
	public T removeFirst(){
		if (this.isEmpty()){
			return null;
		}
		T itemToReturn = items[nextFirst+1];
		/* setting to zero not strictly necessary, but
		 * it's a good habit (we'll see why soon) */
		items[nextFirst + 1] = null;
		nextFirst += 1;
		size -= 1;
		if (items.length>16 && size<items.length*0.25){
			resize(size /RFACTOR);
		}
		return itemToReturn;
	}
	/** Removes and returns the item at the back of the deque. If no such item exists, returns null. */
	public T removeLast(){
		if (this.isEmpty()){
			return null;
		}
		T itemToReturn = items[nextLast-1];
		/* setting to zero not strictly necessary, but
		 * it's a good habit (we'll see why soon) */
		items[nextLast - 1] = null;
		nextLast -= 1;
		size -= 1;
		if (items.length>16 && size<items.length*0.25){
			resize(size /RFACTOR);
		}
		return itemToReturn;
	}
	/** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
	 If no such item exists, returns null. Must not alter the deque! */
	public T get(int index){
		return items[index];
	}


} 