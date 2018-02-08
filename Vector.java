public class Vector {

    public double i;
    public double j;
    public double k;

    public Vector(double i, double j ,double k){
        this.i = i;
        this.j = j;
        this.k = k;
    }

    public static Vector normal (point3 p1, point3 p2, point3 p3) {
        Vector V = new Vector(p1.x - p2.x, p1.y - p2.y, p1.z - p2.z);
        Vector W = new Vector(p3.x - p1.x, p3.y - p1.y, p3.z - p1.z);

        return cross(W,V);

    }

    public static Vector dot (Vector v1, Vector v2) {
        return new Vector(v1.i *v2.i, v1.j *v2.j, v1.k *v2.k);
    }

    public static Vector cross (Vector v1, Vector v2) {
        return new Vector((v1.j*v2.k - v1.k*v2.j), (v1.k*v2.i - v1.i*v2.k), (v1.i*v2.j - v1.j*v2.i));
    }


}
