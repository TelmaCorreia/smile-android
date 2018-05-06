package com.thesis.smile.presentation.utils;

import java.util.ArrayList;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class SortUtils {

    public static ArrayList<String> sortMapByKey(Map<Integer, String> map){
        SortedSet<Integer> keys = new TreeSet<>(map.keySet());
        ArrayList<String> sortedList = new ArrayList<>();
        for (Integer key : keys) {
            sortedList.add(map.get(key));
        }
        return sortedList;
    }
}
