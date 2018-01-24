import java.util.ArrayList;

public class Poly{

    public ArrayList<Tri> TriArray;
    public point3 center;
    public Euler angle;

    public Poly(ArrayList<Tri> TriArray, point3 center, Euler angle){
        this.TriArray = (ArrayList<Tri>) TriArray.clone();
        this.center = center;
        this.angle = angle;
    }

    public Poly clone(){

        return new Poly(copyTriArray(),center.clone(), angle.clone());
    }

    public ArrayList<Tri> copyTriArray(){
        ArrayList<Tri> TriFinal = new ArrayList<>();
        for(Tri t: TriArray){
            TriFinal.add(t.clone());
        }
        return TriFinal;
    }

    public String toString(){

        String s = "";

        for(int i = 0; i < TriArray.size(); i++){
            s += "Tri " + i + ":\n";
            s += TriArray.get(i).toString() + "\n";
        }

        s+= "\ncenter: " + center.toString() + "\n";
        s+= "angle: " + angle.toString();

        return s;
    }

//    private ArrayList<projTri> orderTris(ArrayList<projTri>){
//
//    }
//
//    private boolean zDepth(projTri Tri1, projTri Tri2){
//
//    }
}
