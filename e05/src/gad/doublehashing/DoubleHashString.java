package gad.doublehashing;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class DoubleHashString implements DoubleHashable<String> {

	private int m;
	private int[] a;
	private DoubleHashInt hashInt;

	public DoubleHashString(int primeSize) {
		m = primeSize;
		a = new int[m];

		Random random = new Random(42);
		for (int i = 0; i < a.length; i++) {
			a[i] = random.nextInt(m);
		}

		hashInt = new DoubleHashInt(m);
	}

	private static int sumOfBytes(String key) {
		byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
		int sum = 0;
		for (byte aByte : bytes) {
			sum += aByte;
		}
		return sum;
	}

	@Override
	public int hash(String key) {
		/*byte[] bytes = key.getBytes(StandardCharsets.UTF_8);

		int sum = new BigInteger(bytes).mod(BigInteger.valueOf(m)).intValue();
		if(sum < 0) sum *= -1;

		return sum % m;
		 */
		return hashInt.hash(sumOfBytes(key));
	}

	@Override
	public int hashTick(String key) {
		byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
		int sum = 0;
		for (int i = 0; i < bytes.length; i++) {
			sum += bytes[i] * a[i%a.length];
		}

		if(sum < 0) sum *= -1;
		//if (sum <= 1) sum += 2;

		return 2 + (sum % (m-2));
	}
}