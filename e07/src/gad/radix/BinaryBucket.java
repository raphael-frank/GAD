package gad.radix;

import java.util.Arrays;

public class BinaryBucket {

	private int[] bucket;
	private int nextLeft;
	private int nextRight;
	private int readLeft;
	private int readRight;

	public BinaryBucket(int size) {
		bucket = new int[size];
		nextLeft = 0;
		nextRight = size - 1;
		resetPointer();
	}

	public static BinaryBucket arrayAsUnsortedBucket(int[] numbers) {
		BinaryBucket binaryBucket = new BinaryBucket(numbers.length);
		for (int number : numbers) {
			binaryBucket.insertLeft(number);
		}
		return binaryBucket;
	}

	public void insertLeft(int number) {
		bucket[nextLeft++] = number;
	}

	public void insertRight(int number) {
		bucket[nextRight--] = number;
	}

	public int getMid() {
		return nextLeft;
	}

	public int readLeft() {
		return bucket[readLeft++];
	}

	public int readRight() {
		return bucket[readRight--];
	}

	public int getSize() {
		return bucket.length;
	}

	public void resetPointer() {
		readLeft = 0;
		readRight = getSize() - 1;
	}

	public void logArray(Result result) {
		result.logArray(bucket);
	}

	public void resetInsert() {
		nextLeft = 0;
		nextRight = bucket.length - 1;
	}

	public static void copyBucket(BinaryBucket base, BinaryBucket toCopy) {
		base.bucket = Arrays.copyOf(toCopy.bucket, toCopy.bucket.length);
		base.nextLeft = toCopy.nextLeft;
		base.nextRight = toCopy.nextRight;
		base.readLeft = toCopy.readLeft;
		base.readRight = toCopy.readRight;
	}

	public void clear() {
		Arrays.fill(bucket,0);
		nextLeft = 0;
		nextRight = bucket.length - 1;
		resetPointer();
	}
}
