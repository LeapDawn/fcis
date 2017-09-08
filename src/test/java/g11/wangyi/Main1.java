//package g11.wangyi;
//
//import java.util.HashSet;
//import java.util.Scanner;
//import java.util.Set;
//
///**
// * 彩色方块，最多一对相邻不同颜色的排列组合数
// */
//public class Main1 {
//
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        Set<Character> set = new HashSet<>();
//        String s = scanner.nextLine();
//        char[] chars = s.toCharArray();
//        for (int i = 0; i < chars.length; i++) {
//            set.add(chars[i]);
//        }
//        System.out.println(set.size() > 2 ? 0 : set.size());
//        scanner.close();
//    }
//}
