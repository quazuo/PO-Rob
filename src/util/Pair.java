package zad1.util;

public class Pair<U, V> {
    private final U first;
    private final V second;

    public U getFirst() {
        return first;
    }
    public V getSecond() {
        return second;
    }

    public Pair(U first, V second) {
        this.first = first;
        this.second = second;
    }
}
