package oy.interact.tira.student;

import java.util.Comparator;


import oy.interact.tira.util.Pair;

public class PairComparator implements Comparator<Pair<String, Integer>>{
    @Override
    public int compare(Pair<String, Integer> p1, Pair<String, Integer> p2){
        return p2.getValue().compareTo(p1.getValue());
    }
}
