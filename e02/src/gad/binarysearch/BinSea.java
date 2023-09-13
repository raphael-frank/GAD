package gad.binarysearch;

import gad.binarysearch.Interval.NonEmptyInterval;

import java.util.Arrays;

public final class BinSea {

    private BinSea() {
    }

    public static int search(int[] sortedData, int value, Result result) {
        return searchWithInterval(sortedData,value,new NonEmptyInterval(0, sortedData.length-1),result);
    }

    private static int searchWithInterval(int[] sortedData, int value, NonEmptyInterval rangeInterval, Result result) {
        int length = rangeInterval.getTo()-rangeInterval.getFrom()+1;
        int midIndex = rangeInterval.getFrom()+(length/2)+(length%2)-1;
        int x = sortedData[midIndex];

        result.addStep(midIndex);

        if(x == value) return midIndex;

        NonEmptyInterval newInterval;

        if(x < value) { //value größer
            if(midIndex+1 >= sortedData.length || midIndex+1 > rangeInterval.getTo()) return midIndex;
            newInterval = new NonEmptyInterval(midIndex + 1, rangeInterval.getTo());
        }
        else { //value kleiner
            if(midIndex-1 < 0 || midIndex-1 < rangeInterval.getFrom()) return midIndex;
            newInterval = new NonEmptyInterval(rangeInterval.getFrom(), midIndex - 1);
        }

        return searchWithInterval(sortedData,value,newInterval,result);
    }

    public static int search(int[] sortedData, int value, boolean lowerBound, Result result) {
        int currentIndex = search(sortedData,value,result);
        int currentValue = sortedData[currentIndex];

        if(currentValue == value) {
            while(true) {
                int newIndex = currentIndex + (lowerBound? -1:1);
                if(newIndex<0 || newIndex >= sortedData.length) return currentIndex;

                if(sortedData[newIndex] == currentValue) currentIndex = newIndex;
                else return currentIndex;
            }
        }
        else {
            if(lowerBound) {
                if(currentValue < value) {
                    while(true) {
                        int newIndex = currentIndex + 1;
                        if(newIndex >= sortedData.length) return -1;

                        if(sortedData[newIndex] >= currentValue) return newIndex;
                        currentIndex = newIndex;
                    }
                }
                else {
                    while(true) {
                        int newIndex = currentIndex - 1;
                        if(newIndex < 0) return currentIndex;

                        if(sortedData[newIndex] < currentValue) return currentIndex;
                        currentIndex = newIndex;
                    }
                }
            }
            else {
                if(currentValue < value) {
                    while(true) {
                        int newIndex = currentIndex + 1;
                        if(newIndex >= sortedData.length) return currentIndex;

                        if(sortedData[newIndex] > currentValue) return currentIndex;
                        currentIndex = newIndex;
                    }
                }
                else {
                    while(true) {
                        int newIndex = currentIndex - 1;
                        if(newIndex < 0) return -1;

                        if(sortedData[newIndex] <= currentValue) return newIndex;
                        currentIndex = newIndex;
                    }
                }
            }
        }
    }

    public static Interval search(int[] sortedData, NonEmptyInterval valueRange, Result resultLower, Result resultHigher) {
        int lower = search(sortedData,valueRange.getFrom(),true,resultLower);
        if(lower == -1) return Interval.EmptyInterval.getEmptyInterval();

        int higher = search(sortedData,valueRange.getTo(),false,resultHigher);

        if(lower > higher) return Interval.EmptyInterval.getEmptyInterval();

        return new NonEmptyInterval(lower,higher);
    }

    public static void main(String[] args) {
        int[] array = new int[] { 2, 7, 7, 42, 69, 1337, 2000, 9001 };

        //System.out.println(search(array, 7, new StudentResult()));
        //System.out.println(search(array, 100, new StudentResult()));

        //System.out.println(search(array, 7, false, new StudentResult()));
        //System.out.println(search(array, 100, true, new StudentResult()));
        //System.out.println(search(array, 1, false, new StudentResult()));

        System.out.println(search(array, new NonEmptyInterval(7, 1500), new StudentResult(), new StudentResult()));
        System.out.println(search(array, new NonEmptyInterval(9002, 10000), new StudentResult(), new StudentResult()));
    }
}