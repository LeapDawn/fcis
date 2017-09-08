package g11.wangyi;

import java.util.Scanner;

/**
 * 如果一个01串任意两个相邻位置的字符都是不一样的,我们就叫这个01串为交错01串。
 * 例如: "1","10101","0101010"都是交错01串。
 * 小易现在有一个01串s,小易想找出一个最长的连续子串,并且这个子串是一个交错01串。
 * 小易需要你帮帮忙求出最长的这样的子串的长度是多少。
 */
public class Main3 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        scanner.close();
        char[] chars = s.toCharArray();
        if (chars.length <= 2) {
            System.out.println(chars.length);
        } else {
            int maxLength = 1;
            int thisMaxLength = 1;
            for (int i = 0; i < chars.length - 1; i++) {
                if (chars[i] != chars[i + 1]) {
                    thisMaxLength ++;
                } else {
                    maxLength = maxLength > thisMaxLength ? maxLength : thisMaxLength;
                    thisMaxLength = 1;
                }
            }
            // 处理原串以最长交错串结尾的情况
            maxLength = maxLength > thisMaxLength ? maxLength : thisMaxLength;
            System.out.println(maxLength);
        }
    }
}


