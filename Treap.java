package main.java;

import java.util.Random;


class Treap {
    public TreapNode root;
    public Random random;

    public Treap() {
        this.root = null;
        this.random = new Random();
    }

    private int size(TreapNode node) {
        return node != null ? node.size : 0;
    }

    private void updateSize(TreapNode node) {
        if (node != null) {
            node.size = size(node.left) + size(node.right) + 1;
        }
    }

    private TreapNode rotateRight(TreapNode node) {
        TreapNode x = node.left;
        node.left = x.right;
        x.right = node;
        updateSize(node);
        updateSize(x);
        return x;
    }

    private TreapNode rotateLeft(TreapNode node) {
        TreapNode x = node.right;
        node.right = x.left;
        x.left = node;
        updateSize(node);
        updateSize(x);
        return x;
    }

    public void insert(int key, double priority) {
        root = insert(root, key, priority);
    }

    private TreapNode insert(TreapNode node, int key, double priority) {
        if (node == null) {
            return new TreapNode(key, priority);
        }

        if (key < node.key) {
            node.left = insert(node.left, key, priority);
            if (node.left.priority > node.priority) {
                node = rotateRight(node);
            }
        } else {
            node.right = insert(node.right, key, priority);
            if (node.right.priority > node.priority) {
                node = rotateLeft(node);
            }
        }

        updateSize(node);
        return node;
    }

    public void delete(int key) {
        root = delete(root, key);
    }

    private TreapNode delete(TreapNode node, int key) {
        if (node == null) {
            return null;
        }

        if (key < node.key) {
            node.left = delete(node.left, key);
        } else if (key > node.key) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                if (node.left.priority > node.right.priority) {
                    node = rotateRight(node);
                } else {
                    node = rotateLeft(node);
                }
                node = delete(node, key);
            }
        }

        updateSize(node);
        return node;
    }

    public int getSize() {
        return size(root);
    }
}