package gad.binomilia;

import java.util.*;

public class BinomialHeap {
	private final List<BinomialTreeNode> treeNodeList;
	private BinomialTreeNode minKey;

	public BinomialHeap() {
		treeNodeList = new ArrayList<>();
	}

	public int min() {
		if(treeNodeList.isEmpty() || minKey == null) throw new NoSuchElementException();
		return minKey.min();
	}

	public void insert(int key, Result result) {
		result.startInsert(key,treeNodeList);

		BinomialTreeNode newNode = new BinomialTreeNode(key);
		mergeWithNode(newNode);
		doMerge(result);

		if(minKey == null || newNode.min() < minKey.min()) {
			minKey = newNode;
		}
	}

	public int deleteMin(Result result) {
		result.startDeleteMin(treeNodeList);
		if(treeNodeList.isEmpty()) throw new NoSuchElementException();

		int value = minKey.min();

		treeNodeList.remove(minKey);
		List<BinomialTreeNode> children = minKey.getChildNodes();
		mergeWithHeap(children);

		doMerge(result);

		minKey = null;
		for (BinomialTreeNode node : treeNodeList) {
			if(minKey == null || node.min() < minKey.min()) minKey = node;
		}

		return value;
	}

	private void mergeWithNode(BinomialTreeNode node) {
		for (int i = 0; i < treeNodeList.size(); i++) {
			if(treeNodeList.get(i).rank() > node.rank()) {
				treeNodeList.add(i, node);
				return;
			}
		}
		treeNodeList.add(node);
	}

	private void mergeWithHeap(List<BinomialTreeNode> nodes) {
		int curIndex = 0;
		A: for (BinomialTreeNode node : nodes) {
			for (int i = curIndex; i < treeNodeList.size(); i++) {
				if(treeNodeList.get(i).rank() > node.rank()) {
					treeNodeList.add(i,node);
					curIndex = i;
					continue A;
				}
			}
			treeNodeList.add(node);
		}
	}

	private void doMerge(Result result) {
		result.logIntermediateStep(treeNodeList);

		for (int i = 0; i < treeNodeList.size() - 1; i++) {
			BinomialTreeNode currentNode = treeNodeList.get(i);
			BinomialTreeNode nextNode = treeNodeList.get(i+1);

			if(currentNode.rank() == nextNode.rank()) {
				if(i < treeNodeList.size() - 2 && currentNode.rank() == treeNodeList.get(i+2).rank()) {
					continue;
				} else {
					treeNodeList.remove(i+1);
					treeNodeList.remove(i);

					BinomialTreeNode mergedNode = BinomialTreeNode.merge(currentNode,nextNode);
					treeNodeList.add(i,mergedNode);

					result.logIntermediateStep(treeNodeList);
					i--;
				}
			}
		}
	}

	public static String dot(BinomialTreeNode[] trees) {
		return dot(Arrays.stream(trees).toList());
	}

	public static String dot(Collection<BinomialTreeNode> trees) {
		StringBuilder sb = new StringBuilder();
		sb.append("digraph {").append(System.lineSeparator());
		int id = 0;
		List<Integer> roots = new ArrayList<>();
		for (BinomialTreeNode tree : trees) {
			sb.append(String.format("\tsubgraph cluster_%d {%n", id));
			roots.add(id);
			id = tree.dotNode(sb, id);
			sb.append(String.format("\t}%n"));
		}
		for (int i = 0; i < roots.size() - 1; i++) {
			sb.append(String.format("\t%d -> %d [constraint=false,style=dashed];%n", roots.get(i), roots.get(i + 1)));
		}
		sb.append("}");
		return sb.toString();
	}

	public static void main(String[] args) {

		BinomialHeap heap = new BinomialHeap();
		Result result = new IgnoreResult();

		/*heap.insert(1, result);
		System.out.println(heap.min());
		heap.insert(2, result);
		System.out.println(heap.min());
		heap.insert(0, result);
		System.out.println(heap.min());
		heap.insert(1, result);
		System.out.println(heap.min());
		heap.insert(5, result);
		System.out.println(heap.min());
		heap.deleteMin(result);
		System.out.println(heap.min());
		System.out.println();
		System.out.println();
		heap.deleteMin(result);
		System.out.println(heap.min());

		System.out.println(dot(heap.treeNodeList));
		 */


		Random random = new Random();
		double sec = System.currentTimeMillis()/1000.0;

		for (int i = 0; i < 40000; i++) {
			heap.insert(random.nextInt(), result);
		}
		for (int i = 0; i < 40000; i++) {
			heap.min();
			heap.deleteMin(result);
		}

		sec = System.currentTimeMillis()/1000. - sec;

		System.out.println(sec);

	}
}