public class point2 {
    public double x;
    public double y;

    public point2(double x, double y ){
        this.x = x;
        this.y = y;
    }
    public point2 clone(){
        return new point2(x,y);
    }

    public String toString(){
        if(0.001 > Math.abs(x)) x = 0;
        if(0.001 > Math.abs(y)) y = 0;
        return "x: " + x + "  y: " + y;
    }
}
