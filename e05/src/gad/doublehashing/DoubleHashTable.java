package gad.doublehashing;

import java.util.Optional;

public class DoubleHashTable<K, V> {

	private final DoubleHashable<K> doubleHashable;
	private final Pair<K, V>[] pairs;
	private final int m;
	private int maxRehash;
	private int collisions;

	@SuppressWarnings("unchecked")
	public DoubleHashTable(int primeSize, HashableFactory<K> hashableFactory) {
		doubleHashable = hashableFactory.create(primeSize);
		pairs = new Pair[primeSize];
		m = primeSize;
	}

	public int hash(K key, int i) {
		return (doubleHashable.hash(key) + i * doubleHashable.hashTick(key)) % m;
	}



	public boolean insert(K k, V v) {
		int i = 0;
		int h = hash(k,i);
		int firstH = h;

		if(pairs[h] == null || pairs[h].one().equals(k)) {
			pairs[h] = new Pair<>(k,v);

			return true;
		}
		else {
			i++;
			h = hash(k,i);

			if(h == firstH) {
				if(i > maxRehash) maxRehash = i;
				return false;
			}
			while(pairs[h] != null && !pairs[h].one().equals(k)) {
				i++;
				h = hash(k,i);

				if(h == firstH) {
					if(i > maxRehash) maxRehash = i;
					return false;
				}
			}
			if(i > maxRehash) maxRehash = i;
			if(pairs[h] == null && i != 0) collisions++;

			pairs[h] = new Pair<>(k,v);

			return true;
		}
	}

	public Optional<V> find(K k) {
		int i = 0;
		int h = hash(k,i);
		int firstH = h;

		while(pairs[h] == null || !pairs[h].one().equals(k)) {
			i++;
			h = hash(k,i);

			if(h == firstH) return Optional.empty();
		}

		return Optional.of(pairs[h].two());
	}

	public int collisions() {
		return collisions;
	}

	public int maxRehashes() {
		return maxRehash;
	}
}