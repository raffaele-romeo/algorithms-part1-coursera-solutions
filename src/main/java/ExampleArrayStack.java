public class ExampleArrayStack<Item> {
    private Item[] s;
    private int N = 0;

    public ExampleArrayStack() {
        s = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public void push(Item item) {
        if (N == s.length) resize(2 * s.length);
        s[N++] = item;
    }

    public Item pop() {
        Item item = s[--N];
        s[N] = null;  //avoid loitering
        if (N > 0 && N == s.length/4) resize(s.length/2);
        return item;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        if (N >= 0) System.arraycopy(s, 0, copy, 0, N);
        s = copy;
    }
}
