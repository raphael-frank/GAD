package gad.avl;

import java.util.Random;

public class AVLTree {
    private AVLTreeNode root = null;

    public AVLTree() {
    }

    public AVLTreeNode getRoot() {
        return root;
    }

    public void setRoot(AVLTreeNode root) {
        this.root = root;
    }

    public int height() {
        return root == null ? 0 : root.height();
    }

    public boolean validAVL() {
        return root == null || root.validAVL();
    }

    public void insert(int key) {
        if(root == null) {
            root = new AVLTreeNode(key);
        } else {
            int returnValue = root.insert(key);
            switch (returnValue) {
                case 2 -> einfachRotation(false);
                case 3 -> doppelRotation(false);
                case -2 -> einfachRotation(true);
                case -3 -> doppelRotation(true);
            }
        }
    }

    private void einfachRotation(boolean leftDir) {
        //System.out.println("Einfachrotation an " + root.getKey());
        if(leftDir) {
            AVLTreeNode a = root;
            AVLTreeNode b = a.getRight();
            AVLTreeNode c = b.getLeft();

            a.setRight(c);
            b.setLeft(a);
            root = b;

            a.updateOnce();
            b.updateOnce();
            if(c != null) c.updateOnce();
        }
        else {
            AVLTreeNode a = root;
            AVLTreeNode b = a.getLeft();
            AVLTreeNode c = b.getRight();

            a.setLeft(c);
            b.setRight(a);
            root = b;

            a.updateOnce();
            b.updateOnce();
            if(c != null) c.updateOnce();
        }
    }

    private void doppelRotation(boolean leftFirst) {
        //System.out.println("Doppelrotation an " + root.getKey());
        if(leftFirst) {
            AVLTreeNode a = root;
            a.einfachRotation(true, true);
            einfachRotation(false);
        }
        else {
            AVLTreeNode a = root;
            a.einfachRotation(false, false);
            einfachRotation(true);
        }
    }

    public boolean find(int key) {
        return root != null && root.find(key);
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        tree.root = new AVLTreeNode(10);

        AVLTreeNode node5 = new AVLTreeNode(5);
        AVLTreeNode node2 = new AVLTreeNode(2);
        AVLTreeNode node3 = new AVLTreeNode(3);
        AVLTreeNode node7 = new AVLTreeNode(7);
        AVLTreeNode node6 = new AVLTreeNode(6);
        AVLTreeNode node8 = new AVLTreeNode(8);
        AVLTreeNode node15 = new AVLTreeNode(15);
        AVLTreeNode node11 = new AVLTreeNode(11);
        AVLTreeNode node16 = new AVLTreeNode(16);

        /*node2.setRight(node3);
        node5.setLeft(node2);
        node7.setLeft(node6);
        node7.setRight(node8);
        node5.setRight(node7);
        node15.setLeft(node11);
        node15.setRight(node16);
        tree.root.setLeft(node5);
        tree.root.setRight(node15);
         */

        tree.root.updateBalance();

        Random random = new Random();

        for (int i = 0; i < 10000; i++) {
            tree.insert(random.nextInt());
        }

        System.out.println(tree.dot());
    }

    /**
     * Diese Methode wandelt den Baum in das Graphviz-Format um.
     *
     * @return der Baum im Graphiz-Format
     */
    private String dot() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {" + System.lineSeparator());
        if (root != null) {
            root.dot(sb);
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public String toString() {
        return dot();
    }
}