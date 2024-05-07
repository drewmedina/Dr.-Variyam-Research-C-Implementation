import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class TreapStringNode {
    String key;
    double priority;
    TreapStringNode left, right;
}

class TreapString {
    public static TreapStringNode rightRotate(TreapStringNode y) {
        TreapStringNode x = y.left;
        TreapStringNode T2 = x.right;
        x.right = y;
        y.left = T2;
        return x;
    }

    public static TreapStringNode leftRotate(TreapStringNode x) {
        TreapStringNode y = x.right;
        TreapStringNode T2 = y.left;
        y.left = x;
        x.right = T2;
        return y;
    }

    public static TreapStringNode newNode(String key, double priority) {
        TreapStringNode temp = new TreapStringNode();
        temp.key = key;
        temp.priority = priority;
        temp.left = temp.right = null;
        return temp;
    }

    public static TreapStringNode insert(TreapStringNode root, String key, double priority) {
        if (root == null) {
            return newNode(key, priority);
        }

        if (key.compareTo(root.key) <= 0) {
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

    public static TreapStringNode deleteNode(TreapStringNode root, String key) {
        if (root == null)
            return root;

        int cmp = key.compareTo(root.key);
        if (cmp < 0)
            root.left = deleteNode(root.left, key);
        else if (cmp > 0)
            root.right = deleteNode(root.right, key);
        else if (root.left == null) {
            TreapStringNode temp = root.right;
            root = temp;
        } else if (root.right == null) {
            TreapStringNode temp = root.left;
            root = temp;
        } else if (root.left.priority < root.right.priority) {
            root = leftRotate(root);
            root.left = deleteNode(root.left, key);
        } else {
            root = rightRotate(root);
            root.right = deleteNode(root.right, key);
        }
        return root;
    }

    public static TreapStringNode search(TreapStringNode root, String key) {
        if (root == null || root.key.equals(key))
            return root;
        if (root.key.compareTo(key) < 0)
            return search(root.right, key);
        return search(root.left, key);
    }

    static void inorder(TreapStringNode root) {
        if (root != null) {
            inorder(root.left);
            System.out.print("key: " + root.key + " | priority: " + root.priority);
            if (root.left != null)
                System.out.print(" | left child: " + root.left.key);
            if (root.right != null)
                System.out.print(" | right child: " + root.right.key);
            System.out.println();
            inorder(root.right);
        }
    }

    public static int countNodes(TreapStringNode node) {
        if (node == null)
            return 0;
        else
            return 1 + countNodes(node.left) + countNodes(node.right);
    }

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Double> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Scanner scnr = new Scanner(new File("Hamlet.txt"));
            int bufferSize = 100;
            Random random = new Random();
            TreapStringNode root = null;
            int t = 0;
            double p = 1;

            while (scnr.hasNextLine()) {
                String a = scnr.nextLine().trim();
                t++;
                double u = random.nextDouble();
                root = deleteNode(root, a);
                if (u >= p) {
                    continue;
                } else if (countNodes(root) < bufferSize) {
                    root = insert(root, a, u);
                } else {
                    if ((root).priority < u) {
                        p = u;
                    } else {
                        p = (root).priority;
                        root = deleteNode(root, root.key);
                        root = insert(root, a, u);
                    }
                }
            }
            System.out.println(i + " " + p + " " + countNodes(root) + " " + (countNodes(root) / p));
            list.add((countNodes(root) / p));
            scnr.close();
        }
        System.out.println(list.get(list.size() / 2));
    }
}
