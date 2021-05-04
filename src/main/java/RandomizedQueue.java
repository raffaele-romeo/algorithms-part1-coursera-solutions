import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int N = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        s = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
      return N;
    }

    // add the item
    public void enqueue(Item item) {
        if(item == null){
            throw new IllegalArgumentException();
        }
        if (N == s.length) resize(2 * s.length);
        s[N++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        if (N == 1){
            Item item = s[0];
            s[0] = null;
            return item;
        } else {
            int random = StdRandom.uniform(N);
            Item item = s[random];
            this.s = this.removeTheElement(s, random);
            this.N--;
            return item;
        }
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        int random = StdRandom.uniform(N);

        return s[random];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private int[] randomElem = StdRandom.permutation(N);
        private int n = 0;

        public boolean hasNext() {
            return n < N;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if(n == N){
                throw new NoSuchElementException();
            }
            Item item = s[randomElem[n]];
            n++;
            return item;
        }
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        if (N >= 0) System.arraycopy(s, 0, copy, 0, N);
        s = copy;
    }

    private Item[] removeTheElement(Item[] arr, int index) {
        Item[] anotherArray = (Item[]) new Object[arr.length - 1];

        for (int i = 0, k = 0; i < arr.length; i++) {
            if (i == index) {
                continue;
            }
            anotherArray[k++] = arr[i];
        }

        return anotherArray;
    }

    // unit testing (required)
    public static void main(String[] args){
        RandomizedQueue rqueue = new RandomizedQueue();
        rqueue.enqueue("ciao");
        rqueue.enqueue("ciao1");
        rqueue.enqueue("ciao2");
        rqueue.enqueue("ciao4");
        rqueue.enqueue("ciao3");

        rqueue.dequeue();

        Iterator iter = rqueue.iterator();
        while (iter.hasNext()){
            System.out.println(iter.next());
        }

        Iterator iter2 = rqueue.iterator();
        while (iter2.hasNext()){
            System.out.println(iter2.next());
        }
        System.out.println(rqueue.size());

        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(813);
        rq.dequeue();
        rq.enqueue(5);
    }

}