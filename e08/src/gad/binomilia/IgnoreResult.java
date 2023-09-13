package gad.binomilia;

import java.util.Collection;

public class IgnoreResult implements Result {
    @Override
    public void startInsert(int value, Collection<BinomialTreeNode> heap) {

    }

    @Override
    public void startInsert(int value, BinomialTreeNode[] heap) {
    }

    @Override
    public void startDeleteMin(Collection<BinomialTreeNode> heap) {
    }

    @Override
    public void startDeleteMin(BinomialTreeNode[] heap) {
    }

    @Override
    public void logIntermediateStep(Collection<BinomialTreeNode> heap) {
    }

    @Override
    public void logIntermediateStep(BinomialTreeNode[] heap) {
    }

    @Override
    public void logIntermediateStep(BinomialTreeNode tree) {
    }

    @Override
    public void addToIntermediateStep(Collection<BinomialTreeNode> additionalHeap) {
    }

    @Override
    public void addToIntermediateStep(BinomialTreeNode[] additionalHeap) {
    }

    @Override
    public void addToIntermediateStep(BinomialTreeNode tree) {
    }

    @Override
    public void printCurrentIntermediateStep() {
    }
}