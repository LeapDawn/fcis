package g11.wangyi;

import java.util.*;

/**
 * 小易老师是非常严厉的,它会要求所有学生在进入教室前都排成一列,
 * 并且他要求学生按照身高不递减的顺序排列。有一次,n个学生在列队的时候,
 * 小易老师正好去卫生间了。学生们终于有机会反击了,于是学生们决定来一次疯狂的队列,
 * 他们定义一个队列的疯狂值为每对相邻排列学生身高差的绝对值总和。
 * 由于按照身高顺序排列的队列的疯狂值是最小的,他们当然决定按照疯狂值最大的顺序来进行列队。
 * 现在给出n个学生的身高,请计算出这些学生列队的最大可能的疯狂值。小易老师回来一定会气得半死。
 *
 * 输入包括两行,第一行一个整数n(1 ≤ n ≤ 50),表示学生的人数
 * 第二行为n个整数h[i](1 ≤ h[i] ≤ 1000),表示每个学生的身高
 *
 * 输出一个整数,表示n个学生列队可以获得的最大的疯狂值。
 */
public class Main7 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> list = new ArrayList<>();
        for (int i=0; i < n; i++) {
            list.add(scanner.nextInt());
        }
        if (n == 1) {
            System.out.println(0);
        } else if (n == 2) {
            System.out.println(Math.abs(list.get(0) - list.get(1)));
        } else {
            Collections.sort(list);
            int result = 0;
            Integer num = list.get(n / 2);
            list.remove(n / 2);
            boolean first = false;
            Integer firstValue = list.get(0);
            Integer endValue = list.get(list.size() - 1);
            if (endValue - num > num - firstValue) {
                first = true;
                result += (endValue - num);
                num = endValue;
                list.remove(list.size() - 1);
            } else {
                first = false;
                result += (num - firstValue);
                num = firstValue;
                list.remove(0);
            }
            while (list.size() > 0) {
                if (first) {
                    firstValue = list.get(0);
                    result += (num - firstValue);
                    num = firstValue;
                    list.remove(0);
                    first = false;
                } else {
                    endValue = list.get(list.size() - 1);
                    result += (endValue - num);
                    num = endValue;
                    list.remove(list.size() - 1);
                    first = true;
                }
            }
            System.out.println(result);
        }
    }
}
