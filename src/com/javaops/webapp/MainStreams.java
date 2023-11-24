package com.javaops.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStreams {
    public static void main(String[] args) {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        integerList.add(4);
        integerList.add(1);
        integerList.add(6);
        integerList.add(3);
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9, 8}));
        System.out.println(oddOrEven(integerList));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce((left, right) -> left * 10 + right)
                .orElse(0);
    }

    private static List<Integer> oddOrEven(List<Integer> integerList) {
        return integerList.stream()
                .collect(Collectors.partitioningBy(integer -> integer % 2 == 0))
                .entrySet()
                .stream()
                .filter(booleanListEntry -> (integerList.stream().reduce(Integer::sum).orElse(0) % 2 == 0) == booleanListEntry.getKey())
                .flatMap(el -> el.getValue().stream())
                .toList();
    }
}
