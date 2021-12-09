package convexHull;

import java.util.ArrayList;

public class MyStack extends ArrayList<Integer>{

    public void push(Integer e) {
        add(e);
    }

    public Integer pop() {
        return remove(size()-1);
    }

    public Integer peek() {
        return get(size() - 1);
    }
}
