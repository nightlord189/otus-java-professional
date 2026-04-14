package org.aburavov.otus.java.professional.hw02;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("HW02");

        String[] array1 = {"a", "b", "c"};
        System.out.println("Swaping array: "+Arrays.toString(array1));
        swap(array1, 0, 2);
        System.out.println("Swaped array: "+Arrays.toString(array1));

        List<String> list1 = arrayToList(array1);
        System.out.println("List from array: "+list1);

        String[] array2 = {
                "dog",
                "cat",
                "goose",
                "parrot",
                "pig",
                "dog",
                "chicken",
                "turkey",
                "horse",
                "bear",
                "cat",
                "monkey",
                "cow",
                "sheep",
                "ostrich",
                "dog",
                "shark",
                "giraffe"
        };

        processUnique(array2);
    }

    static <T> void processUnique (T[] array) {
        System.out.println("Array of non-unique elements: "+Arrays.toString(array));

        Map<T, Integer> mapCount = new HashMap<>();
        for (T item: array) {
            mapCount.put(item, mapCount.getOrDefault(item, 0) + 1);
        }

        System.out.println("Unique elements: "+mapCount.keySet());
        System.out.println("Map count of unique elements: "+mapCount);
    }

    static <T> void swap (T[] array, int i, int j) {
        T firstVal = array[i];
        array[i] = array[j];
        array[j] = firstVal;
    }

    static <T> List<T> arrayToList (T[] array) {
        List<T> list = new ArrayList<T>();
        for (T item: array) {
            list.add(item);
        }
        return list;
    }
}
