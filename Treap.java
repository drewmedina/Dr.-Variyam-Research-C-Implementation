import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Treap {
    static class TreapNode {
        public String key;
        public double priority;
        TreapNode left, right;

        TreapNode(String key, double priority) {
            this.key = key;
            this.priority = priority;
        }
    }

    public static TreapNode rightRotate(TreapNode y) {
        TreapNode x = y.left;
        y.left = x.right;
        x.right = y;
        return x;
    }

    public static TreapNode leftRotate(TreapNode x) {
        TreapNode y = x.right;
        x.right = y.left;
        y.left = x;
        return y;
    }

    public static TreapNode newNode(String key, double priority) {
        return new TreapNode(key, priority);
    }

    public static TreapNode insert(TreapNode root, String key, double priority) {
        if (root == null) {
            return newNode(key, priority);
        }

        if (priority > root.priority || (priority == root.priority && key.compareTo(root.key) <= 0)) {
            root.left = insert(root.left, key, priority);
            if (root.left.priority > root.priority) {
                root = rightRotate(root);
            }
        } else {
            root.right = insert(root.right, key, priority);
            if (root.right.priority > root.priority) {
                root = leftRotate(root);
            }
        }

        return root;
    }

    public static TreapNode deleteNode(TreapNode root, String key) {
        if (root == null) {
            return root;
        }

        if (key.compareTo(root.key) < 0) {
            root.left = deleteNode(root.left, key);
        } else if (key.compareTo(root.key) > 0) {
            root.right = deleteNode(root.right, key);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            if (root.left.priority < root.right.priority) {
                root = leftRotate(root);
                root.left = deleteNode(root.left, key);
            } else {
                root = rightRotate(root);
                root.right = deleteNode(root.right, key);
            }
        }

        return root;
    }

    public static TreapNode search(TreapNode root, String key) {
        if (root == null || root.key.equals(key)) {
            return root;
        }

        if (key.compareTo(root.key) < 0) {
            return search(root.left, key);
        }

        return search(root.right, key);
    }

    static void inorder(TreapNode root) {
        if (root != null) {
            inorder(root.left);
            System.out.println("key: " + root.key + " | priority: " + root.priority);
            inorder(root.right);
        }
    }

    public static int size(TreapNode root) {
        if (root == null) {
            return 0;
        }

        return size(root.left) + size(root.right) + 1;
    }

    public static TreapNode findMaxPriorityNode(TreapNode root) {
        if (root == null) {
            return null;
        }

        TreapNode maxPriorityNode = root;

        while (maxPriorityNode.right != null) {
            maxPriorityNode = maxPriorityNode.right;
        }

        return maxPriorityNode;
    }

    public static void replaceMaxPriorityNode(TreapNode root, TreapNode newNode) {
        TreapNode maxPriorityNode = findMaxPriorityNode(root);

        if (maxPriorityNode != null) {
            deleteNode(root, maxPriorityNode.key);
            insert(root, newNode.key, newNode.priority);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        // Example usage
        Scanner scnr = new Scanner(new File("Hamlet.txt"));
        int bufferSize = 10000; // Example buffer size
        Set<String> set = new HashSet<String>();
        Random random = new Random();
        TreapNode root = null;
        int t = 0;
        double p = 1;

        while (scnr.hasNextLine()) {
            String a = scnr.nextLine();
            set.add(a);
            t++;
            double u = random.nextDouble();
            root = deleteNode(root, a);
            if (u >= p) {
                continue;
            } else if (size(root) < bufferSize) {
                root = insert(root, a, u);
            } else {
                if (findMaxPriorityNode(root).priority < u) {
                    p = u;
                } else {
                    p = findMaxPriorityNode(root).priority;
                    replaceMaxPriorityNode(root, new TreapNode(a, u));
                }
            }
        }
        System.out.println(p);
        System.out.println((size(root) / p));
        System.out.println(set.size());
        scnr.close();
    }

}
