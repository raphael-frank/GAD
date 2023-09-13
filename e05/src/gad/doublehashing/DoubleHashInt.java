package gad.doublehashing;

import java.math.BigInteger;
import java.util.Random;

public class DoubleHashInt implements DoubleHashable<Integer> {

	private int m;
	private int w;
	private int k;
	private int[] a;

	public DoubleHashInt(int primeSize) {
		m = primeSize;
		/*w = logTwo(m);
		k = divideUp(w);
		a = new int[k];

		Random random = new Random(17);
		for (int i = 0; i < a.length; i++) {
			a[i] = random.nextInt(m);
		}
		 */
	}

	private static int divideUp(int n) {
		int i = 1;
		int base = n;

		while(base < 32) {
			i++;
			base += n;
		}

		return i;
	}

	private static int logTwo(int n) {
		int i = 0;
		int base = 2;

		while(base <= n) {
			i++;
			base *= 2;
		}

		return i;
	}

	@Override
	public int hash(Integer key) {
		if(key < 0) key *= -1;
		return key % m;
	}

	@Override
	public int hashTick(Integer key) {
		/*String s = Integer.toBinaryString(key);
		StringBuilder bitBuilder = new StringBuilder(s);
		while(bitBuilder.length() < k * w) bitBuilder.append('0');
		String bitString = bitBuilder.toString();

		int sum = 0;

		for (int i = 0; i < k; i++) {
			int x = Integer.parseInt(bitString.substring(i * w, (i+1) * w),2);
			sum += a[i] * x;
			if((i+1)*w > s.length()) break;
		}

		int ret = sum % m;
		if (ret <= 1) ret += 2;

		return ret % m;

		//return (1 + key) % (m-1);
		 */

		if(key < 0) key *= -1;
		return 2 + (key % (m-2));
	}
}