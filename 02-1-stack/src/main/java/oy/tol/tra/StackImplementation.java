package oy.tol.tra;

/**
 * An implementation of the StackInterface.
 * <p>
 * TODO: Students, implement this so that the tests pass.
 *
 * Note that you need to implement construtor(s) for your concrete StackImplementation, which
 * allocates the internal Object array for the Stack:
 * - a default constructor, calling the StackImplementation(int size) with value of 10.
 * - StackImplementation(int size), which allocates an array of Object's with size.
 *  -- remember to maintain the capacity and/or currentIndex when the stack is manipulated.
 */

public class StackImplementation<E> implements StackInterface<E> {
   private Object[] itemArray;
   private int capacity;
   private int currentIndex = -1; // 初始状态下栈是空的
   private static final int DEFAULT_STACK_SIZE = 10;

   public StackImplementation() throws StackAllocationException {
      this(DEFAULT_STACK_SIZE); // 调用另一个构造函数
   }

   public StackImplementation(int capacity) throws StackAllocationException {
      if (capacity < 2) {
         throw new StackAllocationException("Capacity must be at least 2.");
      }
      this.capacity = capacity;
      this.itemArray = new Object[capacity];
   }

   @Override
   public int capacity() {
      return capacity;
   }

   @Override
   public void push(E element) throws StackAllocationException, NullPointerException {
      checkAndGrow();
      if (element == null) {
         throw new NullPointerException("Element cannot be null.");
      }

      itemArray[++currentIndex] = element; // 先增加currentIndex，然后添加元素
   }

   @SuppressWarnings("unchecked")
   @Override
   public E pop() throws StackIsEmptyException {
      if (isEmpty()) {
         throw new StackIsEmptyException("Stack is empty.");
      }
      E element = (E) itemArray[currentIndex];
      itemArray[currentIndex--] = null; // 移除元素，并减少currentIndex
      return element;
   }

   @Override
   public int size() {
      return currentIndex + 1;
   }

   @SuppressWarnings("unchecked")
   @Override
   public E peek() throws StackIsEmptyException {
      if (isEmpty()) {
         throw new StackIsEmptyException("Stack is empty.");
      }
      return (E) itemArray[currentIndex];
   }

   @Override
   public void clear() {
      // 重置栈到初始状态
      currentIndex = -1; // 重置currentIndex
   }

   @Override
   public boolean isEmpty() {
      return currentIndex < 0;
   }

   private void checkAndGrow() {
      if (currentIndex + 1 == capacity) { // 如果栈已满
         int newCapacity = capacity + (capacity >> 1); // 增加50%容量
         Object[] newArray = new Object[newCapacity];
         System.arraycopy(itemArray, 0, newArray, 0, capacity);
         itemArray = newArray;
         capacity = newCapacity; // 更新容量
      }
   }
   @Override
   public String toString() {
      //时间复杂度为n
      StringBuilder builder = new StringBuilder("[");
      for (var index = 0; index <= currentIndex; index++) {
         builder.append(itemArray[index].toString());
         if (index < currentIndex) {
            builder.append(", ");
         }
      }
      builder.append("]");
      return builder.toString();
   }
}