package gad.binomilia;

import java.util.LinkedList;
import java.util.List;

public class BinomialTreeNode {
	private int element;
	private int rank;
	private List<BinomialTreeNode> childNodes;

	public BinomialTreeNode(int element) {
		this.element = element;
		rank = 0;
		childNodes = new LinkedList<>();
	}

	public int min() {
		return element;
	}

	public int rank() {
		return rank;
	}

	public BinomialTreeNode getChildWithRank(int rank) {
		return childNodes.get(rank);
	}

	public static BinomialTreeNode merge(BinomialTreeNode a, BinomialTreeNode b) {
		if(a.min() < b.min()) { //a um b erweitern
			a.rank++;
			a.childNodes.add(b);
			return a;
		} else { //b um a erweitern
			b.rank++;
			b.childNodes.add(a);
			return b;
		}
	}

	public List<BinomialTreeNode> getChildNodes() {
		return childNodes;
	}

	public int dotNode(StringBuilder sb, int idx) {
		sb.append(String.format("\t\t%d [label=\"%d\"];%n", idx, element));
		int rank = rank();
		int next = idx + 1;
		for (int i = 0; i < rank; i++) {
			next = getChildWithRank(i).dotLink(sb, idx, next);
		}
		return next;
	}

	private int dotLink(StringBuilder sb, int idx, int next) {
		sb.append(String.format("\t\t%d -> %d;%n", idx, next));
		return dotNode(sb, next);
	}
}