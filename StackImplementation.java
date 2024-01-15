package oy.interact.tira.student;

import oy.interact.tira.util.StackInterface;

public class StackImplementation<E> implements StackInterface<E> {
    private Object [] itemArray;
    private static final int DEFAULT_STACK_SIZE = 10;
    private int count = -1;


    public StackImplementation() {
        itemArray = new Object[DEFAULT_STACK_SIZE];
    }
    public StackImplementation(int size) throws NullPointerException {
        if (size > 0) {
            itemArray = new Object[size];
        }
    }

    @Override
    public int capacity() {
        return itemArray.length;
    }

    @Override
    public void push(E element) throws OutOfMemoryError, NullPointerException {
        if(element == null) {
            throw new NullPointerException();
         }  
          
            if (count >= itemArray.length - 1) {
                reallocate(itemArray.length * 2);
                
            }
                count++;
                itemArray[count] = element;
                       
          
    }

    @SuppressWarnings("unchecked")
    @Override
    public E pop() throws IllegalStateException {
       if(count < 0) {
        throw new IllegalStateException();
       }
        
            Object element = itemArray[count];
            itemArray[count] = null;
            count--;
            
            if ((E)element == null) {
                throw new IllegalStateException();
            }
            return (E)element;
                   
      
    }

    @SuppressWarnings("unchecked")
    @Override
    public E peek() throws IllegalStateException{
        try {
            return (E)itemArray[count];
        } catch(ArrayIndexOutOfBoundsException e) {
        throw new IllegalStateException();
       }
        
    }

    @Override
    public int size() {
        return count + 1;
    }

    @Override
    public boolean isEmpty() {
        if(count == -1) {
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
       itemArray = new Object[DEFAULT_STACK_SIZE];
       count = -1;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int counter = 0;
        builder.append("[");
        for (Object item : itemArray) {
            if (item != null) {
                 builder.append(item);
                 counter++;
                if(counter < size()) {
                builder.append(", ");
                }
            }
           
            
        }
        builder.append("]");

        return builder.toString();
    }

    private void reallocate(int newSize) throws OutOfMemoryError {
		Object [] newArray = new Object[newSize];
		for (int index = 0; index <= count; index++) {
			newArray[index] = itemArray[index];
		}
		itemArray = newArray;
		
	}



}
