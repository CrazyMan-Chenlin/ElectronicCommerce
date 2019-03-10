package com.taotao.common;

public class test {
    public static void main(String[] args) {
        boolean status = false;
        for (int i = 2; i < 1000; i++) {
            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    status = true;
                    break;
                }
            }
            if (!status){
                System.out.println(i);
            }
            status = false;
            }
    }
}
