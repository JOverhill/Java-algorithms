package oy.interact.tira.student;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;



import oy.interact.tira.util.Pair;
import oy.interact.tira.util.TIRAKeyedOrderedContainer;
import oy.interact.tira.util.Visitor;

public class BinarySearchTreeContainer<K extends Comparable<K>, V> implements TIRAKeyedOrderedContainer<K, V> {
    private TreeNode<K, V> root;
    int size;
    int maxDepth;
    int currentIndex;
    private Comparator<K> comparator;
    
    public BinarySearchTreeContainer(Comparator<K> comparator) {
        root = null;
        this.comparator=comparator;
        
    }
    @Override
    public void add(K key, V value) throws OutOfMemoryError, IllegalArgumentException {
        if(key == null || value == null) {
            throw new IllegalArgumentException("Key or value is null");
        }
        TreeNode<K, V> node = new TreeNode<K, V>(key, value, comparator);
        if(root == null) {
            root = node;
            size++;
            maxDepth = 1;
        } else{
            
            if(root.insert(node)){
                maxDepth = Math.max(maxDepth, root.count);
                size++;             
            }
        }
    }

    @Override
    public V get(K key) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException("Key is null");
        }
        if (root == null)  {
            return  null;
        }
        return root.find(key);
    }

    @Override
    public V remove(K key) throws IllegalArgumentException {
         // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public V find(Predicate<V> searcher) {
        return findP(root, searcher);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int capacity() {
         return Integer.MAX_VALUE;
    }

    @Override
    public void ensureCapacity(int capacity) throws OutOfMemoryError, IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ensureCapacity'");
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Pair<K, V>[] toArray() throws Exception {
        Pair<K, V> [] array = (Pair<K, V>[]) new Pair[size];
        if(root == null) {
            return array;
        }
        AtomicInteger arrayIndex = new AtomicInteger();
        root.toArray(array, arrayIndex);
        return array;
    }

    @Override
    public int indexOf(K itemKey) {
       
        if(root == null || itemKey == null) {
            return -1;
        }
            TreeNode<K, V> current = root;
            if (current.left != null) {
                currentIndex = current.left.count +1;
            } else {
                currentIndex = 0;
        }

            while (current != null) {
                int cmp = comparator.compare(itemKey, current.key);
                if (cmp == 0) {
                    return currentIndex;
                } else if (cmp < 0) {
                    current = current.left;
                    currentIndex--;
                    if(current.right != null) {
                    currentIndex -= (current.right.count + 1);
                }
                } else if(cmp > 0) {
                    current = current.right;
                    currentIndex++;
                    if(current.left != null) {
                        currentIndex += (current.left.count + 1);
                    }
                }
            }
            return -1;
    }

    @Override
    public Pair<K, V> getIndex(int index) throws IndexOutOfBoundsException { 
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }   
        TreeNode<K, V> current = root;
        
        if (current.left != null) {
            currentIndex = current.left.count +1;
        } else {
            currentIndex = 0;
        }
        
        while (current != null) {
            if (currentIndex == index) {
                    return new Pair<>(current.key, current.value);  
            } else if (index < currentIndex) {
                current = current.left;
                
                currentIndex--;
                if(current.right != null) {
                    currentIndex = currentIndex - (current.right.count + 1);
                }
            } else if (index > currentIndex){
                current = current.right;
                currentIndex++;
                if(current.left != null) {
                    currentIndex = currentIndex + (current.left.count + 1);
                }
            }
        
        }
    return null; // Index out of range
    
    }

    @Override
    public int findIndex(Predicate<V> searcher) {
        AtomicInteger index = new AtomicInteger(-1);
        return inOrderSearch(root, searcher, index);
    }
    
     private int inOrderSearch(TreeNode<K, V> node, Predicate<V> searcher, AtomicInteger index) {
        if (node == null) {
            return -1;  
        }

        int leftResult = inOrderSearch(node.left, searcher, index);

        if (leftResult != -1) {
            return leftResult;  
        }

        index.incrementAndGet();  

        if (searcher.test(node.value)) {
            return index.get();  
        }

        int rightResult = inOrderSearch(node.right, searcher, index);

        if (rightResult != -1) {
            return rightResult; 
        }

        return -1;  
    }

    @Override
    public void accept(Visitor<K, V> visitor) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'accept'");
    }
    
    public V findP(TreeNode<K, V> node, Predicate<V> searcher) {
            if (node == null) {
                return null;
            }
            if(searcher.test(node.value)) {
                return node.value;
            }
            V leftResult = findP(node.left, searcher);
            if (leftResult != null) {
                return leftResult;
            }
            return findP(node.right, searcher); 

         }
      


    private class TreeNode<K extends Comparable<K>, V>  {
         private K key;
         private V value;
         public int count;
         public int currentIndex;
          TreeNode<K, V> left;
          TreeNode<K, V> right;
         private Comparator<K> comparator;
        

         public TreeNode(K key, V value, Comparator<K> comparator) {
            this.key = key;
            this.value = value;
            this.comparator = comparator;
            this.left = null;
            this.right = null;
         }

         public boolean insert(TreeNode<K, V> node) {
            if(node == null) {
                return false;
            }
            if(node.value.equals(value)) {
                this.key = node.key;
                this.value = node.value;
                return false;
            }
            boolean result = false;
            if(comparator.compare(node.key, this.key) <= 0) {
                if(left == null) {
                    left = node;
                    count += 1;
                    result = true;
                } else {
                    if (result = left.insert(node)) {
                        count++;
                    } 
                }
            } else {
                if(right == null) {
                    right = node;
                    count += 1;
                    result = true;
                } else {
                    if (result = right.insert(node)){
                        count++;
                    }
                }
          
            }   
             return result;
         }
        


         public V find(K key) {
            
            if (this.key.equals(key)) {  
                return this.value; 
            } else if(comparator.compare(key, this.key) < 0) {
                if(left != null) {  
                    return left.find(key);
                }
            } else {
                if(right != null) {     
                    return right.find(key);
                }
            }
            return null;
         }
         
         public int findIndexP(TreeNode<K, V> node, Predicate<V> predicate, int currentIndex) {
            if (node == null) {
            return -1;
            }
            if (predicate.test(node.value)) {
                return currentIndex + countNodes(node.left); 
            }
            int leftIndex = findIndexP(node.left, predicate, currentIndex);
            if (leftIndex != -1) {
                return leftIndex; 
            }
            return findIndexP(node.right, predicate, currentIndex + countNodes(node.left) + 1);
        }
        
       
        private int countNodes(TreeNode<K, V> node) {
            if (node == null || (node.left==null && node.right==null)) {
                return 0;
            }
            return 1 + countNodes(node.left) + countNodes(node.right);
        }
    

         public int findIndexByKey(K key) {
            
            if (this.key.equals(key)) {
                return currentIndex;
            } else if(comparator.compare(key, this.key) < 0) {
                currentIndex--;   
                return left.findIndexByKey(key);
            } else {
                currentIndex++;
                return right.findIndexByKey(key);
            }
          
         }
         
         public Pair<K, V> findIndex(int index) {
            Pair<K, V> result = null;
            if (index == currentIndex) {
                result = new Pair<K, V>(this.key, this.value);
                return result;
            }
            if (index < currentIndex) {
                    currentIndex = left.count +1;
                    return left.findIndex(index);   
            } else {
                currentIndex = right.count -1;
                currentIndex++;
                return right.findIndex(index);
            }
        }
            
         

         public void toArray(Pair<K,V> [] array, AtomicInteger index) {
            if (left != null) {
                left.toArray(array, index);
            }
            array[index.getAndIncrement()] = new Pair<K, V>(key, value);
            if(right != null) {
                right.toArray(array, index);
            }
         }
    }


}


