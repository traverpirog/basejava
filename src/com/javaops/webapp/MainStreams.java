package com.javaops.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainStreams {
    public static void main(String[] args) {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        integerList.add(4);
        integerList.add(1);
        integerList.add(6);
        integerList.add(3);

        int[] array = new int[]{1, 2, 3, 3, 2, 3};
        System.out.println("Have array: " + Arrays.toString(array));
        System.out.println("Result minValue: " + minValue(array));

        array = new int[]{9, 8};
        System.out.println("Have array: " + Arrays.toString(array));
        System.out.println("Result minValue: " + minValue(array));

        System.out.println("Have list: " + integerList);
        System.out.println("Result oddOrEven: " + oddOrEven(integerList));
    }

    private static int minValue(int[] values) {
        int identity = 0;
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(identity, ((left, right) -> left * 10 + right));
    }

    private static List<Integer> oddOrEven(List<Integer> integerList) {
        Map<Boolean, List<Integer>> listMap = integerList.stream().collect(Collectors.partitioningBy(integer -> integer % 2 == 0));
        boolean oddOrEvenSum = integerList.stream().reduce(Integer::sum).orElse(0) % 2 == 0;
        return listMap.get(oddOrEvenSum);
    }
}
