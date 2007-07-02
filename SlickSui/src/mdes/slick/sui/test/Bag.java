/*
 * Bag.java
 *
 * Created on May 2, 2007, 5:34 PM
 */

package mdes.slick.sui.test;

import java.util.HashMap;

/**
 * 
 * @author oNyx, touched up by davedes
 */
public class Bag<T> {
    
    private int capacity;
    private T[] data;
    private int size=0;
    
    public Bag() {
        this(10);
    }
    
    public Bag(int capacity) {
        this.capacity=capacity;
        this.data = (T[])new Object[capacity];
    }
    
    public T remove(int i) {
        T o = data[i];
        data[i] = data[--size];
        data[size] = null;
        return o;
    }
    
    public boolean remove(T object) {
        int i = indexOf(object);
        if (i >= 0) {
            remove(i);
            return true;
        } else {
            return false;
        }
    }
    
    public T set(int i, T t) {
        T o = data[i];
        data[i] = t;
        return o;
    }
    
    public boolean contains(T t) {
        return indexOf(t) != -1;
    }
    
    public int indexOf(T t) {
        for (int i=0; i<size; i++) {
            if (data[i].equals(t))
                return i;
        }
        return -1;
    }
    
    public void add(T t) {
        size++;
        if (size>capacity) {
            capacity = (capacity*3)/2 + 1;
            T[] oldData = data;
            data = (T[])new Object[capacity];
            System.arraycopy(oldData, 0, data, 0, oldData.length);
        }
        data[size-1] = t;
    }
    
    public int size() {
        return size;
    }
    
    public T get(int index) {
        return data[index];
    }

    public int getCapacity() {
        return capacity;
    }
    
    public void ensureCapacity(int minCapacity) {
        int oldCapacity = this.capacity;
	if (minCapacity > oldCapacity) {
	    T[] oldData = data;
	    int newCapacity = (oldCapacity*3)/2 + 1;
    	    if (newCapacity < minCapacity)
		newCapacity = minCapacity;
	    data = (T[])new Object[newCapacity];
	    System.arraycopy(oldData, 0, data, 0, size);
            this.capacity = newCapacity;
	}
    }
    
    public boolean addAll(Bag b) {
        int numNew = b.size();
	ensureCapacity(size + numNew); 
        System.arraycopy(b.data, 0, data, size, numNew);
        size += numNew;
	return numNew != 0;
    }
    
    public void clear() {
        for (int i=0; i<size; i++) {
            data[i] = null;
        }
        size = 0;
    }
    
    public void fastClear() {
        this.data = (T[])new Object[capacity];
        size = 0;
    }
    
    public static void main(String[] args) {
        A a = new B();
        System.out.println(A.id());
        System.out.println(B.id());
        System.out.println(C.id());
        System.out.println(a.id());
        
    }
    
    static class A {
        public static int id() { return 1; }
    }
    
    static class B extends A {
        public static int id() { return 2; }
    }
    
    static class C extends B {
    }
}