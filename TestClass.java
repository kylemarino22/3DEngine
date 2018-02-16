import java.util.ArrayList;

public class TestClass implements model{

    public static ArrayList<loadedTri> loaded = new ArrayList<>();
    public static ArrayList<Poly> p1 = new ArrayList<>();
    public static Camera cam = new Camera(new Euler(0,0,0), new point3(0,0,0));


    Render r = new Render(p1, cam);

//    public void orderLoaded(){
//
//    }

}
