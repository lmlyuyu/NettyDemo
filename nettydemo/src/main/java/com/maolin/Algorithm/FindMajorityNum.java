package com.maolin.Algorithm;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FindMajorityNum {

    public static void main(String[] args) {
        int[] nums = new int[]{3,3,5,1,0,5,2,5,5,5,5};

        System.out.println(findMajorityNum(nums));
    }

    public static int findMajorityNum(int[] nums) {
        int halfSize = nums.length /2;

        Map<Integer, Long> countMap =
                Arrays.stream(nums).boxed().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return countMap.entrySet().stream().filter(e -> e.getValue() > halfSize).map(e -> e.getKey()).collect(Collectors.toList()).get(0).intValue();    }
}
