package oy.interact.tira.student;

import java.util.function.Predicate;

import oy.interact.tira.util.Pair;
import oy.interact.tira.util.TIRAKeyedContainer;

public class HashTableContainer<K extends Comparable<K>,V> implements TIRAKeyedContainer<K,V> {
    @SuppressWarnings("unchecked")
    private Pair<K, V> [] array = new Pair[1000];
    
    private int count = 0;
    int collisionCount = 0;
    int maxProbingCount = 0;

    @Override
    public void add(K key, V value) throws OutOfMemoryError, IllegalArgumentException {
        if(key == null || value == null){
            throw new IllegalArgumentException("Key or value null");
        }
        
        int hash = key.hashCode();
        /*Calls for hashCode() function at class K, my solution below:
            public int hashCode() {
            int hash = 31;
            int length = id.length();
            for (int index = 0; index < length; index++) {
                hash = hash * 31 + id.charAt(index);
            }
            return hash;
    } */
        boolean added = false;
        int currentProbingcount = 0;
        int hashmodifier = 0;
        int index;
        
        if( size() >= (int) (capacity() * 0.65) ) {
            ensureCapacity((int) (array.length / 0.65));
            
        }

        Pair<K, V> newPair = new Pair<>(key, value);
        do {  
            index = indexFrom(hash, hashmodifier);
            if (array[index] == null) {
                array[index] = newPair;
                count ++;
                added = true;
            } else if(array[index].getKey().equals(key)) {
                array[index] = newPair;
                added = true;
            } else {
                hashmodifier++;
                collisionCount++;
            }
        } while(!added);
        maxProbingCount = Math.max(maxProbingCount, currentProbingcount);
    }

    private int indexFrom(int hash, int collision) {
        return ((hash + 31 * collision + 31 * collision * collision) & 0x7FFFFFFF) % array.length;
    }

    @Override
    public V get(K key) throws IllegalArgumentException {
        if(key == null) {
            throw new IllegalArgumentException();
        }
        int hash = key.hashCode();
        int hashmodifier = 0;
        int index;
        boolean finished = false;
        V result = null;
        do {  
            index = indexFrom(hash, hashmodifier);
            if (array[index] == null) {
                finished = true;    
            } else if(array[index].getKey().equals(key)) {
                result = array[index].getValue();
                finished = true;
            } else {
                hashmodifier++;
            }
        } while(!finished);
        return result;
    }

    @Override
    public V remove(K key) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public V find(Predicate<V> searcher) {
       for (int i = 0; i < array.length; i++) {
        if(array[i] != null) {
            if(searcher.test(array[i].getValue())) {
            return array[i].getValue();
            }
        }
       }
       return null;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public int capacity() {
        return array.length;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void ensureCapacity(int capacity) throws OutOfMemoryError, IllegalArgumentException {
        if(capacity <= 0 || capacity <= size()) {
            throw new IllegalArgumentException();
        }
        Pair<K, V> [] oldArray = array; 
        array = new Pair[capacity];
        collisionCount = 0;
        maxProbingCount = 0;
        count = 0;
		for (int index = 0; index < oldArray.length; index++) {
            if(oldArray[index] != null) {
                add(oldArray[index].getKey(), oldArray[index].getValue());
            }
		}
        
		
    }

    @Override
    public void clear() {
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }

        count = 0;
    }
   
    @SuppressWarnings("unchecked")
    @Override
    public Pair<K, V>[] toArray() throws Exception {
        Pair<K, V> [] newArray = new Pair[count];
        int newIndex = 0;
        
       for(int index = 0; index < array.length; index++) {
        if (array[index] == null) {
            continue;
        }
        newArray[newIndex++] = array[index];
       
    } 

       return newArray;

    }

    
    

}
