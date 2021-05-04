import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int N = 0;
    private class Node{
        Item item;
        Node next;
        Node previous;
    }
    //TODO add checks
    private Node first, last = null;

    // construct an empty deque
    public Deque(){
    }

    // is the deque empty?
    public boolean isEmpty(){
        return first == null || last == null;
    }

    // return the number of items on the deque
    public int size(){
        return N;
    }

    // add the item to the front
    public void addFirst(Item item){
        if (item == null) {
            throw new IllegalArgumentException();
        }
        boolean isFirstItem = isEmpty();
        Node second = first;
        first = new Node();
        first.item = item;
        first.next = second;
        first.previous = null;
        if (isFirstItem) last = first;
        else second.previous = first;
        N++;
    }

    // add the item to the back
    public void addLast(Item item){
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.previous = null;
        if (isEmpty()) first = last;
        else {
            last.previous = oldLast;
            oldLast.next = last;
        }
        N++;
    }

    // remove and return the item from the front
    public Item removeFirst(){
        if(this.isEmpty()){
            throw new NoSuchElementException();
        }

        Item item = first.item;
        first = first.next;
        if (isEmpty()) last = null;
        N--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast(){
        if(this.isEmpty()){
            throw new NoSuchElementException();
        }
        Item item = last.item;
        last = last.previous;
        if (N != 1) last.next = null;
        if(isEmpty()) first = null;
        N--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args){
        Deque deque = new Deque<String>();
        deque.addLast("ciao1");
        deque.addLast("ciao1");
        deque.addLast("ciao1");
        deque.removeLast();
        deque.removeLast();
        deque.isEmpty();
        //deque.removeLast();
       // deque.addFirst("ciao2");


//        deque.removeLast();
//        deque.removeLast();
//        deque.removeLast();

        Iterator iter = deque.iterator();

        while (iter.hasNext()){
            System.out.println(iter.next());
        }

        System.out.println(deque.size());



    }

}