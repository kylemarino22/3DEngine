public class point3 {

    public double x;
    public double y;
    public double z;

    public point3(double x, double y ,double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public point3 clone(){
        return new point3(x,y,z);
    }
    public String toString(){
        if(0.001 > Math.abs(x)) x = 0;
        if(0.001 > Math.abs(y)) y = 0;
        if(0.001 > Math.abs(z)) z = 0;
        return "x: " + x + "  y: " + y + "  z: " + z;
    }
}
