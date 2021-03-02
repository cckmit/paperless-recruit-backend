package com.xiaohuashifu.recruit.organization.service.mq.mq2;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2021/2/24 23:18
 */
public class Test {
    public static void main(String[] args) {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put(null, 1);
        objectObjectHashMap.put(null, 2);
        System.out.println(objectObjectHashMap.get(null));
    }
}
