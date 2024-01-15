package oy.interact.tira.student;
import java.util.Comparator;

import oy.interact.tira.model.Coder;

public class CoderFullNameComparator implements Comparator<Coder> {
    @Override
    public int compare(Coder c1, Coder c2){
        return c1.getFullName().compareTo(c2.getFullName());
    }
}
