import static org.junit.Assert.*;

import org.junit.Test;

public class PointTest {

    @Test
    public void testgetDistanceTo() {
        Point p1 = new Point(1, 2);
        Point p2 = new Point(4, 6);
        
        assertEquals(p1.getDistanceTo(p2), 5, 1e-7);
    }

}
