package com.maolin.Algorithm;

public class FindUnduplicatedNum {
    public static void main(String[] args) {
        int[] nums = new int[]{0,1,3,3,5,1,0,5,2};
        int result = getUnduplicatedNum(nums);
        System.out.println(result);

    }

    private static int getUnduplicatedNum(int[] nums) {
        int result = 0;

        for(int item : nums) {
            result ^= item;
        }

        return result;
    }
}
