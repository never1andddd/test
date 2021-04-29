import edu.princeton.cs.algs4.Queue;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     * <p>
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param q1 A Queue in sorted order from least to greatest.
     * @param q2 A Queue in sorted order from least to greatest.
     * @return The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /**
     * Returns a queue of queues that each contain one item from items.
     */
    private static <Item extends Comparable> Queue<Queue<Item>>
    makeSingleItemQueues(Queue<Item> items) {
        // Your code here!
        Queue<Queue<Item>> returnQ = new Queue<Queue<Item>>();
        int size = items.size();
        for (int i = 0; i < size; i++) {
            Item first = items.dequeue();
            Queue<Item> singleQ = new Queue<Item>();
            singleQ.enqueue(first);
            returnQ.enqueue(singleQ);
        }
        return returnQ;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     * <p>
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param q1 A Queue in sorted order from least to greatest.
     * @param q2 A Queue in sorted order from least to greatest.
     * @return A Queue containing all of the q1 and q2 in sorted order, from least to
     * greatest.
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        // Your code here!
        //System.out.println("q1: " + q1.toString());
        //System.out.println("q2: " + q2.toString());
        int totalN = q1.size() + q2.size();
        Queue<Item> combineQ = new Queue<Item>();
        for (int i = 0; i < totalN; i++) {
            Item min = getMin(q1, q2);
            combineQ.enqueue(min);
            //System.out.println("combined Q: " + combineQ.toString());
        }
        return combineQ;
    }

    /**
     * Returns a Queue that contains the given items sorted from least to greatest.
     */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        // Your code here!
        if (items.size()<= 1) {
            return items;
        }
        Queue<Queue<Item>> singleItemQ = makeSingleItemQueues(items);
        Queue<Item> left = new Queue<>();
        Queue<Item> right = new Queue<>();
        int leftSize = singleItemQ.size() / 2;
        for (int i = 0; i < leftSize; i++) {
            Item temp = singleItemQ.dequeue().dequeue();
            left.enqueue(temp);
        }
        int rightSize = singleItemQ.size();
        for (int i = 0; i < rightSize; i++) {
            Item temp = singleItemQ.dequeue().dequeue();
            right.enqueue(temp);
        }
        Queue<Item> q1 = mergeSort(left);
        Queue<Item> q2 = mergeSort(right);
        //System.out.println("q1: " + q1.toString());
        //System.out.println("q2: " + q1.toString());
        items = mergeSortedQueues(q1, q2);
        return items;
    }

    public static void main(String[] args){
        Queue<String> students = new Queue<String>();
        students.enqueue("Alice");
        students.enqueue("Vanessa");
        students.enqueue("Ethan");
        System.out.println(students.toString());
        System.out.println(mergeSort(students).toString());

        Queue<Integer> numbers = new Queue<Integer>();
        numbers.enqueue(9);
        numbers.enqueue(9);
        numbers.enqueue(2);
        numbers.enqueue(4);
        numbers.enqueue(6);
        numbers.enqueue(5);
        numbers.enqueue(0);
        numbers.enqueue(1);
        numbers.enqueue(6);
        numbers.enqueue(9);
        System.out.println(numbers.toString());
        System.out.println(mergeSort(numbers).toString());
    }
}
