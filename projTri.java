import java.awt.*;
import java.util.ArrayList;

public class projTri {

    public point2[] pointArray = new point2[3];
    public Color triColor;

    public projTri(point2 a, point2 b, point2 c, Color d){
        pointArray[0] = a;
        pointArray[1] = b;
        pointArray[2] = c;
        triColor = d;
    }

    public projTri(point2 a, point2 b, point2 c){
        pointArray[0] = a;
        pointArray[1] = b;
        pointArray[2] = c;
    }

    public ArrayList<point2> cpyArr(){
        ArrayList<point2> tempCpy = new ArrayList<>();
        tempCpy.add(pointArray[0].clone());
        tempCpy.add(pointArray[1].clone());
        tempCpy.add(pointArray[2].clone());

        return tempCpy;
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

