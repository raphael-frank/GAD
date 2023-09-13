package gad.dynamicarray;

import java.util.Arrays;

public class DynamicArray {
    private int[] elements;
    private int growthFactor;
    private int maxOverhead;

    public DynamicArray(int growthFactor, int maxOverhead) {
        if(growthFactor < 1 || maxOverhead < 1 || growthFactor >= maxOverhead) throw new IllegalArgumentException();

        elements = new int[0];
        this.growthFactor = growthFactor;
        this.maxOverhead = maxOverhead;
    }

    public int getGrowthFactor() {return growthFactor;}

    public int getMaxOverhead() {return maxOverhead;}

    public int getLength() {
        return elements.length;
    }

    public Interval reportUsage(Interval usage, int minSize) {

        if(minSize > getLength() || minSize * maxOverhead < getLength()) {

            int[] usageValues;
            Interval resultInterval;

            if(usage.isEmpty()) {
                resultInterval = Interval.EmptyInterval.getEmptyInterval();

                elements = Arrays.copyOf(elements,minSize*growthFactor);
            }
            else {
                if(usage.getFrom() > usage.getTo()) {
                    usageValues = new int[getLength() - usage.getFrom() + usage.getTo() + 1];
                    for (int i = 0; i < getLength() - usage.getFrom(); i++) {
                        usageValues[i] = elements[usage.getFrom()+i];
                    }
                    for (int i = 0; i <= usage.getTo(); i++) {
                        usageValues[getLength() - usage.getFrom() + i] = elements[i];
                    }
                }
                else {
                    usageValues = new int[usage.getTo() - usage.getFrom() + 1];
                    for (int i = 0; i <= usage.getTo() - usage.getFrom(); i++) {
                        usageValues[i] = elements[usage.getFrom() + i];
                    }
                }

                if(usageValues.length > 0) resultInterval = new Interval.NonEmptyInterval(0, usageValues.length-1);
                else resultInterval = Interval.EmptyInterval.getEmptyInterval();

                elements = new int[minSize*growthFactor];
                for (int i = 0; i < usageValues.length; i++) {
                    elements[i] = usageValues[i];
                }
            }

            return resultInterval;
        }
        else return usage;
    }

    public int get(int index) {
        return elements[index];
    }

    public void set(int index, int value) {
        elements[index] = value;
    }

    public void reportArray(Result result) {
        result.logArray(elements);
    }

    @Override
    public String toString() {
        return Arrays.toString(elements);
    }
}