package com.xiaohuashifu.recruit.organization.service.mq;

import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.Arrays;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2021/3/3 00:31
 */
public class test {

    public static void main(String[] args) {
        byte a = -1;
        System.out.println(a & 0xff);
    }

    int n;
    char[] nums = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    char[] num;
    StringBuilder sb = new StringBuilder();
    int nine = 0;
    int start;
    public int[] printNumbers(int n) {
        int[] arr = new int[((int)Math.pow(10, n)) - 1];

        this.n = n;
        this.num = new char[n];
        this.start = n - 1;
        dfs(0);

        String[] numArr = sb.substring(0, sb.length() - 1).split(",");
         for (int i = 0; i < arr.length; i++) {
             arr[i] = Integer.parseInt(numArr[i]);
         }

        return arr;
    }

    public void dfs(int x) {
        if (x == n) {
            String numString = String.valueOf(num).substring(start);
            if (!numString.equals("0")) {
                sb.append(numString).append(",");
            }
            if (n - start == nine) {
                start--;
            }
            return;
        }
        for (char c : nums) {
            if (c == '9') {
                nine++;
            }
            num[x] = c;
            dfs(x + 1);
        }
        nine--;
    }
}
