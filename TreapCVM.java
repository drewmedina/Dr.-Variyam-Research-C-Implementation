//import TreapNode;
//
//import java.util.Random;
//
//public class TreapCVM {
//    public static void main(String[] args) {
//        // Example usage
//        int[] stream = { 1, 2, 3, 3, 4, 5 }; // Example data stream
//        int bufferSize = 2; // Example buffer size
//
//        double estimate = unbiasedEstimate(stream, bufferSize);
//        System.out.println("Estimated number of distinct elements: " + estimate);
//    }
//
//    public static double unbiasedEstimate(int[] stream, int bufferSize) {
//        Treap treap = new Treap();
//        Random random = new Random();
//        TreapNode root = null;
//        int t = 0;
//        double p = 1;
//
//        for (int a : stream) {
//            t++;
//            double u = random.nextDouble();
//            treap.deleteNode(root, a);
//            if (u >= p) {
//                continue;
//            } else if (treap.size(root) < bufferSize) {
//                treap.insert(root, a, u);
//            } else {
//                if (treap.findMaxPriorityNode(root).priority < u) {
//                    p = u;
//                } else {
//                    treap.replaceMaxPriorityNode(root, new TreapNode(a, u));
//                }
//            }
//        }
//
//        return (treap.getSize() / p);
//    }
//
//}