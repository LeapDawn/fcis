package g11.wangyi;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * 小易有一个长度为n的整数序列,a_1,...,a_n。然后考虑在一个空序列b上进行n次以下操作:
 * 1、将a_i放入b序列的末尾
 * 2、逆置b序列
 * 小易需要你计算输出操作n次之后的b序列
 */
public class Main4 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] intArray = new int[n];
        int left = 0;
        int right = n - 1;
        for (int i = 0; i < n; i++) {
            intArray[i] = scanner.nextInt();
        }
        /**
         * 分析结果串可以得知，结果串的规律为：(假设原串下标为0~n-1)
         * 结果串第一位为原串[n-1]，最后一位为原串[n-2],
         * 第二串为原串[n-3]，倒数第二位为原串[n-4]
         * ...
         */
        int[] resultArray = new int[n];
        for (int i = 0, j = n - 1; i < n; i++) {
            if (i % 2 == 0) {
                resultArray[left] = intArray[j-i];
                left++;
            } else {
                resultArray[right] = intArray[j-i];
                right--;
            }
        }
        System.out.print(resultArray[0]);
        for (int i = 1; i < resultArray.length; i++) {
            System.out.print(" ");
            System.out.print(resultArray[i]);
        }
        scanner.close();
    }
}
