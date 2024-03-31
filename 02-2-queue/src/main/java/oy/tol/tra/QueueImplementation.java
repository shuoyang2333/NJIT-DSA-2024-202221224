package oy.tol.tra;

public class QueueImplementation<E> implements QueueInterface<E> {
    private Object[] itemArray;
    private int capacity;
    private int head = 0; // 头指针
    private int tail = 0; // 尾指针
    private int size = 0; // 数组中的元素个数

    public QueueImplementation() {
    }

    public QueueImplementation(int capacity) {
        this.itemArray = new Object[capacity];
        this.capacity = capacity;
    }

    /**
     * For querying the current capacity of the queue.
     *
     * @return The number of elements the queue can currently hold.
     */
    public int capacity() {
        return capacity;
    }

    /**
     * Add an element to the queue.
     *
     * @param element The element to add, must not be null.
     * @throws QueueAllocationException If the reallocation for the queue failed in case queue needs reallocation.
     * @throws NullPointerException     If the element is null.
     */
    public void enqueue(E element) throws QueueAllocationException, NullPointerException {
        if (size == capacity) {
            checkAndGrow();
        }

        if (element == null) {
            throw new NullPointerException("Element cannot be null!");
        }
        itemArray[tail] = element;
        tail = (tail + 1) % itemArray.length;
        size++;
    }

    /**
     * Removes an element from the queue.
     *
     * @return The element from the head of the queue.
     * @throws QueueIsEmptyException If the queue is empty.
     */
    @Override
    public E dequeue() throws QueueIsEmptyException {
        if (isEmpty()) {
            throw new QueueIsEmptyException("Queue is empty!");
        }
        E element = (E) itemArray[head];
        itemArray[head] = null; // clear the reference to the dequeued element
        head = (head + 1) % itemArray.length;
        size--;
        return element;
    }

    /**
     * Returns the element at the head of the queue, not removing it from the queue.
     *
     * @return The element in the head of the queue.
     * @throws QueueIsEmptyException If the queue is empty.
     */
    @Override
    public E element() throws QueueIsEmptyException {
        if (isEmpty()) {
            throw new QueueIsEmptyException("Queue is empty!");
        }
        E element = (E) itemArray[head];
        return element;
    }

    /**
     * Returns the count of elements currently in the queue.
     *
     * @return Count of elements in the queue.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Can be used to check if the queue is empty.
     *
     * @return True if the queue is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Resets the queue to empty state, removing the objects.
     */
    @Override
    public void clear() {
        // Clearing references to elements
        for (int i = 0; i < size; i++) {
            itemArray[(head + i) % itemArray.length] = null;
        }
        head = tail = size = 0; // Reset pointers and size
    }

    private void checkAndGrow() {
        int newCapacity = capacity + (capacity >> 1); // Increase capacity by 50%
        Object[] newArray = new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newArray[i] = itemArray[(head + i) % itemArray.length];
        }
        itemArray = newArray;
        capacity = newCapacity; // Update capacity
        head = 0; // Reset head pointer as items are shifted to the beginning of the new array
        tail = size; // Update tail pointer to the end of the new array
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            builder.append(itemArray[(head + i) % itemArray.length].toString());
            if (i < size - 1) {
                builder.append(", ");
            }
        }
        builder.append("]");
        return builder.toString();
    }
}

