package gad.simplehash;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Stream;

public class Hashtable<K, V> {
    private List<Pair<K, V>>[] table;
    private int[] a;

    public static void main(String[] args) {
        Hashtable<Integer,Integer> rip = new Hashtable<>(128,new int[]{2,3,4,5,6,7,8,9,10});
        for (int i = 0; i < 600000000; i++) {
            rip.h(i, null);
        }
    }

    @SuppressWarnings("unchecked")
    public Hashtable(int minSize, int[] a) {
        // TODO: Change and complete
        table = (List<Pair<K, V>>[]) new List[getNextPowerOfTwo(minSize)];
        for (int i = 0; i < table.length; i++) table[i] = new ArrayList<>();

        this.a = a;
    }

    public List<Pair<K, V>>[] getTable() {
        return table;
    }

    public static int getNextPowerOfTwo(int i) {
        int p = 1;
        while(p<i) {
            p *= 2;
            if(p < 0) return Integer.MAX_VALUE;
        }
        return p;
    }

    public static int fastModulo(int i, int divisor) {
        return i & (divisor-1);
    }

    private byte[] bytes(K k) {
        return k.toString().getBytes(StandardCharsets.UTF_8);
    }

    public int h(K k, ModuloHelper mH) {
        byte[] x = bytes(k);

        int hash = 0;
        int n = 0;

        for (byte b : x) {
            hash += b * a[n];
            if (++n == a.length) n = 0;
        }

        return fastModulo(hash, table.length);
    }

    public void insert(K k, V v, ModuloHelper mH) {
        table[h(k,mH)].add(new Pair<>(k,v));
    }

    public boolean remove(K k, ModuloHelper mH) {
        return table[h(k,mH)].removeIf(p -> p.one().equals(k));
    }

    public Optional<V> find(K k, ModuloHelper mH) {
        return table[h(k,mH)].stream().filter(p -> p.one().equals(k)).map(Pair::two).findFirst();
    }

    public List<V> findAll(K k, ModuloHelper mH) {
        return table[h(k,mH)].stream().filter(p -> p.one().equals(k)).map(Pair::two).toList();
    }

    public Stream<Pair<K, V>> stream() {
        return Stream.of(table).filter(Objects::nonNull).flatMap(List::stream);
    }

    public Stream<K> keys() {
        return stream().map(Pair::one).distinct();
    }

    public Stream<V> values() {
        return stream().map(Pair::two);
    }
}