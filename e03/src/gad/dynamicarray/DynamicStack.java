package gad.dynamicarray;

public class DynamicStack implements Stack {
    private DynamicArray array;
    private Result result;
    private int topIndex;

    public DynamicStack(int growthFactor, int maxOverhead, Result result) {
        array = new DynamicArray(growthFactor,maxOverhead);
        this.result = result;
        topIndex = -1;
    }

    @Override
    public int size() {
        return topIndex+1;
    }

    @Override
    public void pushBack(int value) {
        topIndex++;
        array.reportUsage(topIndex < 1? Interval.EmptyInterval.getEmptyInterval(): new Interval.NonEmptyInterval(0,topIndex-1),size());
        array.set(topIndex,value);
        array.reportArray(result);
    }

    @Override
    public int popBack() {
        int value = array.get(topIndex);
        topIndex--;
        array.reportUsage(topIndex < 0? Interval.EmptyInterval.getEmptyInterval(): new Interval.NonEmptyInterval(0,topIndex),size());
        array.reportArray(result);
        return value;
    }

    @Override
    public String toString() {
        return array + ", length: " + size();
    }
}