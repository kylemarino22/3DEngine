import java.awt.*;

public class projTri {

    public point2[] pointArray = new point2[3];
    public Color triColor;

    public projTri(point2 a, point2 b, point2 c, Color d){
        pointArray[0] = a;
        pointArray[1] = b;
        pointArray[2] = c;
        triColor = d;
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

