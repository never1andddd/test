/** Performs some basic linked list tests. */
public class ArrayDeque<T> {
		private T[] items;
		private int size;
		private int nextFirst;
		private int nextLast;
		private static int RFACTOR = 2;

	/** Creates an empty list. */
	public ArrayDeque() {
		size = 0;
		nextFirst = 4;
		nextLast = 5;
		items = (T[]) new Object[8];
	}
	/** Node forward;
	 *
	 */
	private  int onePlus(int index){
		if (index==items.length-1){
			return 0;
		}else return index + 1;
	}

	private  int oneMinus(int index){
		if (index == 0){
			return items.length - 1;
		}else {
			return index - 1;
		}
	}
	/** Resize our backing array so that it is
	 * of the given capacity. */
	private void resize(int capacity) {
		T[] a = (T[]) new Object[capacity];
		int currentFirst = onePlus(nextFirst);
		int currentLast = oneMinus(nextLast);

		if (currentFirst >= currentLast) {
			System.arraycopy(items, 0, a, 0, currentLast + 1);
			System.arraycopy(items, currentFirst, a, items.length + currentFirst, items.length - currentFirst);
			nextFirst = items.length + currentLast;
			items = a;
		}else{
			int currentLength = currentLast - currentFirst + 1;
			System.arraycopy(items, currentFirst, a, 0, currentLength);
			items = a;
			nextFirst = items.length - 1;
			nextLast = currentLast;
		}
	}

	/** Adds an item of type T to the front of the deque. */
	public void addFirst(T item){
		items[nextFirst] = item;
		nextFirst = oneMinus(nextFirst);
		size += 1;
		if (size == items.length) {
			resize(size * RFACTOR);
		}
	}

	/** Adds an item of type T to the back of the deque. */
	public void addLast(T item){
		items[nextLast] = item;
		size += 1;
		nextLast = onePlus(nextLast);
		if (size == items.length) {
			resize(size * RFACTOR);
		}
	}

	/** Returns true if deque is empty, false otherwise. */
	public boolean isEmpty(){
		if (size == 0){
				return Boolean.TRUE;
			}else {
			return Boolean.FALSE;
		}
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
		T itemToReturn = items[onePlus(nextFirst)];
		/* setting to zero not strictly necessary, but
		 * it's a good habit (we'll see why soon) */
		items[onePlus(nextFirst)] = null;
		nextFirst = onePlus(nextFirst);
		size -= 1;
		if (items.length > 16 && size<items.length*0.25){
			resize(size /RFACTOR);
		}
		return itemToReturn;
	}
	/** Removes and returns the item at the back of the deque. If no such item exists, returns null. */
	public T removeLast(){
		if (this.isEmpty()){
			return null;
		}
		T itemToReturn = items[oneMinus(nextLast)];
		/* setting to zero not strictly necessary, but
		 * it's a good habit (we'll see why soon) */
		items[oneMinus(nextLast)] = null;
		nextLast = oneMinus(nextLast);
		size -= 1;
		if (items.length > 16 && size<items.length*0.25){
			resize(size /RFACTOR);
		}
		return itemToReturn;
	}
	/** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
	 If no such item exists, returns null. Must not alter the deque! */
	public T get(int index){
		if (index >= items.length){
			return null;
		}
		if (nextFirst + 1 + index > items.length){
			return items[nextFirst + 1 + index - items.length];
		}else {
			return items[nextFirst + index + 1];
		}
	}
/**
	public static void main(String[] args) {
		ArrayDeque<Integer> test1 = new ArrayDeque<Integer>();

		test1.addLast(0);
		test1.addLast(1);
		test1.addLast(2);
		test1.addLast(3);
		test1.addLast(4);
		test1.addLast(5);
		System.out.println("Index 5 " + test1.get(5));
		test1.printDeque();
		System.out.println("");
		test1.addLast(6);
		test1.addLast(7);
		test1.removeFirst();
		test1.addLast(9);
		test1.addLast(10);
		test1.removeFirst();

		test1.printDeque();
		System.out.println("");
		System.out.println("size is " + test1.size());


		 test1.addFirst(0);
		 test1.addFirst(1);
		 test1.addFirst(2);
		 test1.addFirst(3);
		 test1.removeFirst();
		 test1.printDeque();
		 System.out.println("");
		 test1.addFirst(4);
		 test1.addFirst(5);
		 test1.printDeque();
		 System.out.println("nextLast is " + test1.nextLast);
		 System.out.println("nextFirst is " + test1.nextFirst);
		 test1.removeFirst();
		 test1.printDeque();
		 System.out.println("");
		 System.out.println("size is " + test1.size());
 }**/

}
