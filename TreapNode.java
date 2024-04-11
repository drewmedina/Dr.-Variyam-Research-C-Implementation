package main.java;

class TreapNode {
    int key;
    double priority;
    int size;
    public TreapNode left, right;

    TreapNode(int key, double priority) {
        this.key = key;
        this.priority = priority;
        this.size = 1;
        this.left = null;
        this.right = null;
    }
}
