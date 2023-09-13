package gad.dynamicarray;

public class RingQueue implements Queue {
    private DynamicArray array;
    private Result result;
    private int next;
    private int last;
    private int isFull;

    public RingQueue(int growthFactor, int maxOverhead, Result result) {
        array = new DynamicArray(growthFactor, maxOverhead);
        this.result = result;

        next = 0;
        last = 0;
        isFull = 1;
    }

    @Override
    public int size() {
        if(isFull == 1) return array.getLength();

        if(next >= last) return next - last;

        return array.getLength()-last + next;
    }

    @Override
    public void pushBack(int value) {
        if(isFull == 1) {
            Interval i = array.reportUsage(array.getLength() == 0? Interval.EmptyInterval.getEmptyInterval():
                    new Interval.NonEmptyInterval(last,next-1<0? array.getLength()-1 : next-1),array.getLength()+1);
            if(i.isEmpty()) {
                next = 0;
                last = 0;
            }
            else {
                next = (i.getTo()+1) % array.getLength();
                last = i.getFrom();
            }
            isFull = 0;
        }

        array.set(next,value);
        next = (next+1) % array.getLength();

        if(next == last) isFull = 1;

        array.reportArray(result);
    }

    @Override
    public int popFront() {
        int value = array.get(last);
        last = (last+1) % array.getLength();

        Interval nI = new Interval.NonEmptyInterval(last,next-1<0? array.getLength()-1 : next-1);
        Interval i = array.reportUsage(next == last? Interval.EmptyInterval.getEmptyInterval():
                nI,next == last? 0 : nI.getSize(array.getLength()));

        if(i.isEmpty()) {
            next = 0;
            last = 0;
        }
        else {
            next = (i.getTo()+1) % array.getLength();
            last = i.getFrom();
        }

        isFull = array.getLength() == 0? 1:0;

        array.reportArray(result);

        return value;
    }

    @Override
    public String toString() {
        return array + ", size: " + size();
    }
}