package gad.avl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AVLTreeNode {
    private int key;
    private int balance = 0;
    private AVLTreeNode left = null;
    private AVLTreeNode right = null;

    public AVLTreeNode(int key) {
        this.key = key;
    }

    public AVLTreeNode getLeft() {
        return left;
    }

    public AVLTreeNode getRight() {
        return right;
    }

    public int getBalance() {
        return balance;
    }

    public int getKey() {
        return key;
    }

    public void setLeft(AVLTreeNode left) {
        this.left = left;
    }

    public void setRight(AVLTreeNode right) {
        this.right = right;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int height() {
        int leftHeight = left == null ? 0 : left.height();
        int rightHeight = right == null ? 0 : right.height();
        return 1 + Math.max(leftHeight,rightHeight);
    }

    public boolean validAVL() {
        //Kreise
        if(checkForCircle(new HashSet<>())) return false;

        //Balance richtig
        int leftHeight = left == null ? 0 : left.height();
        int rightHeight = right == null ? 0 : right.height();
        if (balance != rightHeight - leftHeight) return false;
        if (balance > 1 || balance < -1) return false;

        //Key Invarianz
        if (left != null && left.maxKey() > key) return false;
        if (right != null && right.minKey() < key) return false;

        //Verifikation Kinder
        if(left != null && !left.validAVL()) return false;
        if(right != null && !right.validAVL()) return false;

        return true;
    }

    private int maxKey() {
        int leftMax = left == null ? Integer.MIN_VALUE : left.maxKey();
        int rightMax = right == null ? Integer.MIN_VALUE : right.maxKey();
        int childMax = Math.max(leftMax,rightMax);
        return Math.max(key, childMax);
    }

    private int minKey() {
        int leftMin = left == null ? Integer.MAX_VALUE : left.minKey();
        int rightMin = right == null ? Integer.MAX_VALUE : right.minKey();
        int childMin = Math.min(leftMin,rightMin);
        return Math.min(key, childMin);
    }

    private boolean checkForCircle(Set<AVLTreeNode> visitedNodes) {
        if (visitedNodes.contains(this)) {
            return true;
        }

        visitedNodes.add(this);

        boolean leftHasCircle = false;
        boolean rightHasCircle = false;

        if (left != null) {
            leftHasCircle = left.checkForCircle(visitedNodes);
        }

        if (right != null) {
            rightHasCircle = right.checkForCircle(visitedNodes);
        }

        return leftHasCircle || rightHasCircle;
    }

    public boolean find(int key) {
        if(key == this.key) return true;
        if(key <= this.key && left != null && left.find(key)) return true;
        if(key >= this.key && right != null && right.find(key)) return true;
        return false;
    }

    public int insert(int key) {
        if(key >= this.key) {
            if(right == null) { //kein Knoten vorhanden, setze ein
                right = new AVLTreeNode(key);
                balance++;
                return 1;
            } else {
                int returnValue = right.insert(key);

                if(Math.abs(returnValue) < 2) { //balance ändern
                    //balance += Math.abs(returnValue);
                    updateOnce();
                    if(Math.abs(balance) == 2) { //rotate in parent
                        if(right.balance > 0) { //einfach, ANDERS BEI LINKS
                            return -2; //linksrotation
                        } else { //doppel
                            return 3; //rechtslinksrotation
                        }
                    }
                    else { //aktualisiere im parent
                        return returnValue;
                    }
                }
                else { //rotation, negativ = links zuerst | positiv = rechts zuerst, 2 = einfach | 3 = doppel
                    switch (returnValue) { //ANDERS BEI LINKS
                        case 2 -> einfachRotation(false, false);
                        case 3 -> doppelRotation(false, false);
                        case -2 -> einfachRotation(true, false);
                        case -3 -> doppelRotation(true, false);
                        default -> throw new RuntimeException("Komischer Rückgabewert");
                    }

                    return 0;
                }
            }
        } else {
            if(left == null) {
                left = new AVLTreeNode(key);
                balance--;
                return 1;
            } else {
                int returnValue = left.insert(key);

                if(Math.abs(returnValue) < 2) { //balance ändern
                    //balance -= Math.abs(returnValue);
                    updateOnce();
                    if(Math.abs(balance) == 2) { //rotate in parent
                        if(left.balance < 0) { //einfach
                            return 2; //rechtsrotation
                        } else { //doppel
                            return -3; //linksrechtsrotation
                        }
                    }
                    else { //aktualisiere im parent
                        return returnValue;
                    }
                }
                else { //rotation, negativ = links zuerst | positiv = rechts zuerst, 2 = einfach | 3 = doppel
                    switch (returnValue) {
                        case 2 -> einfachRotation(false, true);
                        case 3 -> doppelRotation(false, true);
                        case -2 -> einfachRotation(true, true);
                        case -3 -> doppelRotation(true, true);
                        default -> throw new RuntimeException("Komischer Rückgabewert");
                    }

                    return 0;
                }
            }
        }
    }

    void einfachRotation(boolean leftDir, boolean fromLeftNode) {
        //System.out.println("EinfachRotation an " + (fromLeftNode ? left.key : right.key));
        if(leftDir) {
            AVLTreeNode a = fromLeftNode ? left : right;
            AVLTreeNode b = a.right;
            AVLTreeNode c = b.left;

            a.right = c;
            b.left = a;
            if(fromLeftNode) left = b;
            else right = b;

            a.updateOnce();
            b.updateOnce();
            if(c != null) c.updateOnce();
        }
        else {
            AVLTreeNode a = fromLeftNode ? left : right;
            AVLTreeNode b = a.left;
            AVLTreeNode c = b.right;

            a.left = c;
            b.right = a;
            if(fromLeftNode) left = b;
            else right = b;

            a.updateOnce();
            b.updateOnce();
            if(c != null) c.updateOnce();
        }
    }

    void doppelRotation(boolean leftFirst, boolean fromLeftNode) {
        //System.out.println("Doppelrotation an " + (fromLeftNode ? left.key : right.key));
        if(leftFirst) {
            AVLTreeNode a = fromLeftNode ? left : right;
            a.einfachRotation(true, true);
            einfachRotation(false, fromLeftNode);
        }
        else {
            AVLTreeNode a = fromLeftNode ? left : right;
            a.einfachRotation(false, false);
            einfachRotation(true, fromLeftNode);
        }
    }

    void updateBalance() {
        updateOnce();

        if(left != null) left.updateBalance();
        if(right != null) right.updateBalance();
    }

    void updateOnce() {
        int leftHeight = left == null ? 0 : left.height();
        int rightHeight = right == null ? 0 : right.height();
        balance = rightHeight - leftHeight;
    }

    /**
     * Diese Methode wandelt den Baum in das Graphviz-Format um.
     *
     * @param sb der StringBuilder für die Ausgabe
     */
    public void dot(StringBuilder sb) {
        dotNode(sb, 0);
    }

    private int dotNode(StringBuilder sb, int idx) {
        sb.append(String.format("\t%d [label=\"%d, b=%d\"];%n", idx, key, balance));
        int next = idx + 1;
        if (left != null) {
            next = left.dotLink(sb, idx, next, "l");
        }
        if (right != null) {
            next = right.dotLink(sb, idx, next, "r");
        }
        return next;
    }

    private int dotLink(StringBuilder sb, int idx, int next, String label) {
        sb.append(String.format("\t%d -> %d [label=\"%s\"];%n", idx, next, label));
        return dotNode(sb, next);
    }
}