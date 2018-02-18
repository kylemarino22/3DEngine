import java.awt.*;
import java.util.ArrayList;


public class Render extends draw{

    private ArrayList<Poly> polyArray;
    private Camera cam;

    private point3 eye = new point3(15,15,100);

    public Render(ArrayList<Poly> polyArray, Camera cam){
        super();
        this.polyArray = (ArrayList<Poly>) polyArray.clone();
        this.cam = cam;
    }

    //renderPoly takes in a poly number
    //outputs finished poly rotation and translation

    public void renderAll(ArrayList<Poly> polyArray){
        ArrayList<Tri> TransformedTris = new ArrayList<>();

        for(int i = 0; i < p1.size(); i++){
            TransformedTris.addAll(RenderPoly(i));
        }

        TransformedTris = frustumCulling(TransformedTris);

        ArrayList<projTri> renderedTris = renderTri(TransformedTris);

        OrderTri.orderTri(renderedTris, TransformedTris);
        super.loaded.addAll(loadWireFrame(renderedTris));

    }

    private ArrayList<Tri> RenderPoly(int PolyNumber){

//        if(PolyNumber == 1){
//            System.out.println("bepis");
//            while(true);
//        }
        //poly being modified
        Poly p = polyArray.get(PolyNumber);

        //number of tris in the poly Array
        ArrayList<Tri> TransformedTri = p.copyTriArray();
        double tempX;
        double tempY;
        double tempZ;

        //internal euler rotation p, y, r
        if(p.angle.p != 0) {


            double pitchCos = Math.cos(p.angle.p);
            double pitchSin = Math.sin(p.angle.p);

            for(int i = 0; i < TransformedTri.size(); i++){

                for(int j = 0; j < 3; j++){
                    tempZ = pitchCos * (TransformedTri.get(i).pointArray[j].z) -
                            pitchSin * (TransformedTri.get(i).pointArray[j].y);
                    tempY = pitchCos * (TransformedTri.get(i).pointArray[j].y) +
                            pitchSin * (TransformedTri.get(i).pointArray[j].z);

                    TransformedTri.get(i).pointArray[j].z = tempZ;
                    TransformedTri.get(i).pointArray[j].y = tempY;
                }
            }
        }

        if(p.angle.y  != 0) {

            double yawCos = Math.cos(p.angle.y);
            double yawSin = Math.sin(p.angle.y);

            for(int i = 0; i < TransformedTri.size(); i++){

                for(int j = 0; j < 3; j++){
                    tempZ = yawCos * (TransformedTri.get(i).pointArray[j].z) -
                            yawSin * (TransformedTri.get(i).pointArray[j].x);
                    tempX = yawCos * (TransformedTri.get(i).pointArray[j].x) +
                            yawSin * (TransformedTri.get(i).pointArray[j].z);

                    TransformedTri.get(i).pointArray[j].z = tempZ;
                    TransformedTri.get(i).pointArray[j].x = tempX;
                }
            }
        }

        if(p.angle.r != 0) {

            double rollCos = Math.cos(p.angle.r);
            double rollSin = Math.sin(p.angle.r);

            for(int i = 0; i < TransformedTri.size(); i++){

                for(int j = 0; j < 3; j++){
                    tempX = rollCos * (TransformedTri.get(i).pointArray[j].x) -
                            rollSin * (TransformedTri.get(i).pointArray[j].y);
                    tempY = rollCos * (TransformedTri.get(i).pointArray[j].y) +
                            rollSin * (TransformedTri.get(i).pointArray[j].x);

                    TransformedTri.get(i).pointArray[j].x = tempX;
                    TransformedTri.get(i).pointArray[j].y = tempY;
                }
            }
        }



        //Translation to poly center and by camera
        for(int i = 0; i < TransformedTri.size(); i++){

            for(int j = 0; j < 3; j++){
                TransformedTri.get(i).pointArray[j].x += p.center.x - cam.center.x;
                TransformedTri.get(i).pointArray[j].y += p.center.y - cam.center.y;
                TransformedTri.get(i).pointArray[j].z += p.center.z - cam.center.z;
            }
        }



        //global rotations around eye by camera angles

        if(cam.angle.y != 0){
            double yawCos = Math.cos(cam.angle.y);
            double yawSin = Math.sin(cam.angle.y);

            for(int i = 0; i < TransformedTri.size(); i++){

                for(int j = 0; j < 3; j++){
                    tempZ = yawCos * (TransformedTri.get(i).pointArray[j].z - eye.z) -
                            yawSin * (TransformedTri.get(i).pointArray[j].x - eye.x) + eye.z;
                    tempX = yawCos * (TransformedTri.get(i).pointArray[j].x - eye.x) +
                            yawSin * (TransformedTri.get(i).pointArray[j].z - eye.z) + eye.x;

                    TransformedTri.get(i).pointArray[j].z = tempZ;
                    TransformedTri.get(i).pointArray[j].x = tempX;
                }
            }
        }

        if(cam.angle.p != 0){
            double pitchCos = Math.cos(cam.angle.p);
            double pitchSin = Math.sin(cam.angle.p);

            for(int i = 0; i < TransformedTri.size(); i++){

                for(int j = 0; j < 3; j++){
                    tempZ = pitchCos * (TransformedTri.get(i).pointArray[j].z - eye.z) -
                            pitchSin * (TransformedTri.get(i).pointArray[j].y - eye.y) + eye.z;
                    tempY = pitchCos * (TransformedTri.get(i).pointArray[j].y - eye.y) +
                            pitchSin * (TransformedTri.get(i).pointArray[j].z - eye.z) + eye.y;

                    TransformedTri.get(i).pointArray[j].z = tempZ;
                    TransformedTri.get(i).pointArray[j].y = tempY;
                }
            }
        }

        if(cam.angle.r != 0){
            double rollCos = Math.cos(cam.angle.r);
            double rollSin = Math.sin(cam.angle.r);

            for(int i = 0; i < TransformedTri.size(); i++){

                for(int j = 0; j < 3; j++){
                    tempX = rollCos * (TransformedTri.get(i).pointArray[j].x - eye.x) -
                            rollSin * (TransformedTri.get(i).pointArray[j].y - eye.y) + eye.x;
                    tempY = rollCos * (TransformedTri.get(i).pointArray[j].y - eye.y) +
                            rollSin * (TransformedTri.get(i).pointArray[j].x - eye.x) + eye.y;

                    TransformedTri.get(i).pointArray[j].x = tempX;
                    TransformedTri.get(i).pointArray[j].y = tempY;
                }
            }
        }

        return TransformedTri;


    }

    private ArrayList<loadedTri> loadWireFrame(ArrayList<projTri> projTris){



        ArrayList<loadedTri> loaded = new ArrayList<>();
        for(int i = 0; i < projTris.size(); i++){

            int[] a = {0,0,0,0};
            int[] b = {0,0,0,0};
            loaded.add(new loadedTri(a,b,Color.blue));

            for(int j = 0; j < 3; j++){
                loaded.get(i).x[j] = (int) (300 + projTris.get(i).pointArray[j].x * 26.6);
                loaded.get(i).y[j] = (int) (800 - (projTris.get(i).pointArray[j].y * 26.6));
            }

            loaded.get(i).x[3] = (int) (300 + projTris.get(i).pointArray[0].x * 26.6);
            loaded.get(i).y[3] = (int) (800 - (projTris.get(i).pointArray[0].y * 26.6));
            loaded.get(i).c = projTris.get(i).triColor;



        }
//        loaded.get(1).x[0] = 40;




        return loaded;
    }

    private ArrayList<projTri> renderTri(ArrayList<Tri> TriArray) {
        //calculates and orders the tris
        double slope;
        double finalY;
        double finalX;
        ArrayList<projTri> projTriList = new ArrayList<>();

        for (int j = 0; j < TriArray.size(); j++) {

            projTriList.add(new projTri(null, null, null, TriArray.get(j).triColor));
            for (int i = 0; i < 3; i++) {

                //y calculation
                slope = (eye.y - TriArray.get(j).pointArray[i].y) / (eye.z - TriArray.get(j).pointArray[i].z);
                finalY = (slope * -eye.z) + eye.y;

                //x calculation
                slope = (eye.x - TriArray.get(j).pointArray[i].x) / (eye.z - TriArray.get(j).pointArray[i].z);
                finalX = (slope * -eye.z) + eye.x;

                projTriList.get(j).pointArray[i] = new point2(finalX, finalY);
            }
        }
        return projTriList;

    }

    private ArrayList<Tri> frustumCulling(ArrayList<Tri> triArray){

    //xz equations
    // point slope of eye and screen corner (15,-100) and (30,0)

        for(int i = triArray.size() -1; i > -1; i--){

            for(int j = 0; j < 3; j++){

                if(triArray.get(i).pointArray[j].z > 100){
                    triArray.remove(i);
                    break;
                }

//                if((triArray.get(i).pointArray[j].x < (200 + triArray.get(i).pointArray[j].z) /6.66) &&
//                   (triArray.get(i).pointArray[j].x > triArray.get(i).pointArray[j].z/6.66)){
//                    triArray.remove(i);
//                    break;
//                }
//
//                if((triArray.get(i).pointArray[j].x < (200 + triArray.get(i).pointArray[j].z) /6.66) &&
//                        (triArray.get(i).pointArray[j].x > triArray.get(i).pointArray[j].z/6.66)){
//                    triArray.remove(i);
//                    break;
//                }


            }


        }


        return triArray;

    }



}
