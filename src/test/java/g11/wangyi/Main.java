//package g11.wangyi;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Scanner;
//
///**
// * 小易将n个棋子摆放在一张无限大的棋盘上。
// * 第i个棋子放在第x[i]行y[i]列。同一个格子允许放置多个棋子。
// * 每一次操作小易可以把一个棋子拿起并将其移动到原格子的上、下、左、右的任意一个格子中。
// * 小易想知道要让棋盘上出现有一个格子中至少有i(1 ≤ i ≤ n)个棋子所需要的最少操作次数.
// * <p>
// * 输入包括三行,第一行一个整数n(1 ≤ n ≤ 50),表示棋子的个数
// * 第二行为n个棋子的横坐标x[i](1 ≤ x[i] ≤ 10^9)
// * 第三行为n个棋子的纵坐标y[i](1 ≤ y[i] ≤ 10^9)
// */
//public class Main {
//
//    private static int avgX = 0;
//    private static int avgY = 0;
//    private List<Node> dataList = null;
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        Main main = new Main();
//        main.method(scanner);
//        scanner.close();
//    }
//
//    private void method(Scanner scanner) {
//        int n = scanner.nextInt();
//        int[] xArray = new int[n + 1];
//        int[] yArray = new int[n + 1];
//        dataList = new ArrayList<>(n);
//        Node node = null;
//        long xAvg = 0L;
//        long yAvg = 0L;
//        for (int i = 0; i < n; i++) {
//            node = new Node();
//            node.x = scanner.nextInt();
//            dataList.add(node);
//            xAvg += xArray[i];
//        }
//        for (int i = 0; i < n; i++) {
//            dataList.get(i).y = scanner.nextInt();
//            yAvg += yArray[i];
//        }
//        avgX =(int) xAvg/n;
//        avgY =(int) yAvg/n;
//        Collections.sort(dataList);
//        System.out.print(0);
//        for (int i = 2; i <= n; i++) {
//            System.out.print(" ");
//            System.out.print(result(i));
//        }
//    }
//
//    private long result(int n) {
//        List<Node> list = new ArrayList<>(n);
//        long xAvg = 0L;
//        long yAvg = 0L;
//        Node node = null;
//        for (int i = 0; i < n; i++) {
//            node = dataList.get(i);
//            list.add(node);
//            xAvg += node.x;
//            yAvg += node.y;
//        }
//        avgX =(int) xAvg/n;
//        avgY =(int) yAvg/n;
//        long min = Long.MAX_VALUE;
//        for (int i = -1; i <= 1; i++) {
//            for (int j = -1; j<=1;j++) {
//                long sum = sum(list, avgX, avgY);
//                min = min < sum ? min : sum;
//            }
//        }
//        return min;
//    }
//
//    private long sum(List<Node> list, int x, int y) {
//        if (x <=0 || y <=0) {
//            return Long.MAX_VALUE;
//        } else {
//            long result = 0L;
//            for (Node node : list) {
//                result += Math.abs(node.x - x) + Math.abs(node.y - y);
//            }
//            return result;
//        }
//    }
//
//
//
//    class Node implements Comparable<Node> {
//        int x;
//        int y;
//
//        @Override
//        public int compareTo(Node o) {
////            return (Math.abs((x - Main.avgX)) + Math.abs((y - Main.avgY))) - (Math.abs((o.x - Main.avgX)) + Math.abs((o.y - Main.avgY)));
//            double a = Math.pow((double) Math.abs((x - Main.avgX)), 2.0) + (Math.pow((double) Math.abs((y - Main.avgY)), 2.0));
//            double b = Math.pow((double) Math.abs((o.x - Main.avgX)), 2.0) + (Math.pow((double) Math.abs((o.y - Main.avgY)), 2.0));
//            return (int)(a-b);
//        }
//    }
//}
