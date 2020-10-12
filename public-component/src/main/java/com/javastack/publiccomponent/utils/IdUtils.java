package com.javastack.publiccomponent.utils;

import java.util.Random;
import java.util.UUID;

public class IdUtils {

    /**
     * 生成18位随机数字串
     *
     * @return
     */
    public static String getOrderSnByTime18() {
        String s = (System.currentTimeMillis() + "").substring(1) + (System.nanoTime() + "").substring(7, 10);
        Random rm = new Random();
        double pross = (1 + rm.nextDouble()) * Math.pow(10, 12);
        String fixLenthString = String.valueOf(pross);
        return s + fixLenthString.substring(2, 5);
    }

    public static String id() {
        String code = String.valueOf(Math.abs(IdUtils.uuid().hashCode()));
        String random = String.valueOf(new Random().nextInt(900) + 100);
        return code + random;
    }

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static int random(int size) {
        if (size == 0) {
            throw new RuntimeException("Array is Empty.");
        }

        if (size == 1) {
            return 0;
        }

        return new Random().nextInt(size);
    }


    public static void main(String[] args) {
        System.out.println(uuid());
        System.out.println(id());
//        System.out.println(getOrderSnByTime18());
//        System.out.println(getOrderSnByTime18());
//        System.out.println(getOrderSnByTime18());
//        System.out.println(getOrderSnByTime18());
//        System.out.println(getOrderSnByTime18());
//        System.out.println(getOrderSnByTime18());

    }
}
