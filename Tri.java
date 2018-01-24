import java.awt.*;

public class Tri {

    public point3[] pointArray = new point3[3];
    public Color triColor;

    public Tri(point3 a, point3 b, point3 c, Color d){
        pointArray[0] = a;
        pointArray[1] = b;
        pointArray[2] = c;
        triColor = d;
    }

    public Tri clone(){
        return new Tri(pointArray[0].clone(),
                       pointArray[1].clone(),
                       pointArray[2].clone(),
                       triColor);
    }
    public String toString(){
        String s = "";

        for(int i = 0; i < 3; i++){
            s += pointArray[i].toString() + "\n";
        }
        s += triColor.toString();

        return s;
    }

}
