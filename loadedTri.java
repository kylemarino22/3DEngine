import java.awt.Color;

public class loadedTri {

    public int[] x = new int[4];
    public int[] y = new int[4];
    public Color c;


    public loadedTri(int[] x, int[] y, Color c){
        this.x = x;
        this.y = y;
        this.c = c;
    }
    public String toString(){
        String s = "";
        s += "x: ";
        for(int i = 0; i < 4; i++){
           s += x[i] + "  ";
        }
        s += "\ny: ";
        for(int i = 0; i < 4; i++){
            s += y[i] + "  ";
        }

        return s + "\n";
    }

}
