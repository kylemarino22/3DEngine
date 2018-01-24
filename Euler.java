public class Euler {

    private final double DEG_RAD = Math.PI/180;
    public double r;
    public double p;
    public double y;

    public Euler(double r, double p ,double y){
        this.r = r * DEG_RAD;
        this.p = p * DEG_RAD;
        this.y = y * DEG_RAD;
    }
    public void setRoll(double r){
        this.r = r * DEG_RAD;
    }
    public void setPitch(double p){
        this.p = p * DEG_RAD;
    }
    public void setYaw(double y){
        this.y = y * DEG_RAD;
    }

    public void incrementRoll(double r){
        this.r += r * DEG_RAD;
    }
    public void incrementPitch(double p){
        this.p += p * DEG_RAD;
    }
    public void incrementYaw(double y){
        this.y += y * DEG_RAD;
    }

    public Euler clone(){
        return new Euler(r,p,y);
    }

    public String toString(){
        return "r: " + r + "  p: " + p + "  y: " + y;
    }

}
