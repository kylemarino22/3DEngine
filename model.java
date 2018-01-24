import java.awt.*;
import java.util.ArrayList;

public interface model {
    ArrayList<Tri> t1 = new ArrayList<>();
    //center at (0,0,0)
    Tri test1 = new Tri(new point3(-0.5,-3.5,0.5), new point3(0.5,-3.5,0.5), new point3(0.5,3.5,0.5), Color.RED);
    Tri test2 = new Tri(new point3(-0.5,-3.5,0.5), new point3(-0.5,3.5,0.5), new point3(0.5,3.5,0.5), Color.RED);
    Tri test3 = new Tri(new point3(-0.5,-3.5,0.5), new point3(-0.5,3.5,0.5), new point3(-0.5,3.5,-0.5), Color.RED);
    Tri test4 = new Tri(new point3(-0.5,-3.5,0.5), new point3(-0.5,-3.5,-0.5), new point3(-0.5,3.5,-0.5), Color.RED);
    Tri test5 = new Tri(new point3(-0.5,3.5,-0.5), new point3(-0.5,-3.5,-0.5), new point3(0.5,-3.5,-0.5), Color.RED);
    Tri test6 = new Tri(new point3(-0.5,3.5,-0.5), new point3(0.5,3.5,-0.5), new point3(0.5,-3.5,-0.5), Color.RED);
}
