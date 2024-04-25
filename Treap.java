/*package whatever //do not write package name here */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Scanner;
// A Treap Node
class TreapNode
{
    int key;
    double priority;
    TreapNode left,right;

}
/* T1, T2 and T3 are subtrees of the tree rooted with y
(on left side) or x (on right side)
				y							 x
			/ \	 Right Rotation		 / \
			x T3 – – – – – – – >	 T1 y
			/ \	 < - - - - - - -		 / \
			T1 T2	 Left Rotation		 T2 T3 */

// A utility function to right rotate subtree rooted with y
// See the diagram given above.
class Treap
{
    public static TreapNode rightRotate(TreapNode y) {
        TreapNode x = y.left;
        TreapNode T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Return new root
        return x;
    }

    // A utility function to left rotate subtree rooted with x
// See the diagram given above.
    public static TreapNode leftRotate(TreapNode x) {
        TreapNode y = x.right;
        TreapNode T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Return new root
        return y;
    }

    /* Utility function to add a new key */
    public static TreapNode newNode(int key, double priority) {
        TreapNode temp = new TreapNode();
        temp.key = key;
        temp.priority = priority;
        temp.left = temp.right = null;
        return temp;
    }
    /* Recursive implementation of insertion in Treap */
    public static TreapNode insert(TreapNode root, int key, double priority) {
        // If root is null, create a new node and return it
        if (root == null) {
            return newNode(key, priority);
        }

        // If key is smaller than root
        if (key <= root.key) {
            // Insert in left subtree
            root.left = insert(root.left, key, priority);

            // Fix Heap property if it is violated
            if (root.left.priority > root.priority) {
                root = rightRotate(root);
            }
        } else { // If key is greater
            // Insert in right subtree
            root.right = insert(root.right, key, priority);

            // Fix Heap property if it is violated
            if (root.right.priority > root.priority) {
                root = leftRotate(root);
            }
        }
        return root;
    }
    /* Recursive implementation of Delete() */
    public static TreapNode deleteNode(TreapNode root, int key) {
        if (root == null)
            return root;

        if (key < root.key)
            root.left = deleteNode(root.left, key);
        else if (key > root.key)
            root.right = deleteNode(root.right, key);

            // IF KEY IS AT ROOT

            // If left is NULL
        else if (root.left == null)
        {
            TreapNode temp = root.right;
            root = temp; // Make right child as root
        }
        // If Right is NULL
        else if (root.right == null)
        {
            TreapNode temp = root.left;
            root = temp; // Make left child as root
        }
        // If key is at root and both left and right are not NULL
        else if (root.left.priority < root.right.priority)
        {
            root = leftRotate(root);
            root.left = deleteNode(root.left, key);
        }
        else
        {
            root = rightRotate(root);
            root.right = deleteNode(root.right, key);
        }

        return root;
    }
    // Java function to search a given key in a given BST
    public static TreapNode search(TreapNode root, int key)
    {
        // Base Cases: root is null or key is present at root
        if (root == null || root.key == key)
            return root;

        // Key is greater than root's key
        if (root.key < key)
            return search(root.right, key);

        // Key is smaller than root's key
        return search(root.left, key);
    }
    static void inorder(TreapNode root)
    {
        if (root != null)
        {
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

    public static int countNodes(TreapNode node) {
        if (node == null)
            return 0;
        else
            return 1 + countNodes(node.left) + countNodes(node.right);
    }

    // Driver Program to test above functions
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Double> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Scanner scnr = new Scanner(new File("randomnumber2.txt"));
            int bufferSize = 50;
            Set<Integer> set = new HashSet<Integer>();
            Random random = new Random();
            TreapNode root = null;
            int t = 0;
            double p = 1;

            while (scnr.hasNextInt()) {
                int a = scnr.nextInt();
                set.add(a);
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
            System.out.println(p);
            list.add((countNodes(root) / p));
            System.out.println(list.get(list.size() - 1));
            scnr.close();
        }
        System.out.println(list.get(list.size() / 2));

    }

}
