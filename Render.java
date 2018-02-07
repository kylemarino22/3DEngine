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
    public Poly RenderPoly(int PolyNumber, Graphics d){

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


        TransformedTri = frustumCulling(TransformedTri);

        ArrayList<projTri> a = renderTri(TransformedTri);
        super.loaded.addAll(loadWireFrame(a));

        return p;

    }

    public ArrayList<loadedTri> loadWireFrame(ArrayList<projTri> projTris){



        ArrayList<loadedTri> loaded = new ArrayList<>();
        for(int i = 0; i < projTris.size(); i++){

            int[] a = {0,0,0,0};
            int[] b = {0,0,0,0};
            loaded.add(new loadedTri(a,b));

            for(int j = 0; j < 3; j++){
                loaded.get(i).x[j] = (int) (300 + projTris.get(i).pointArray[j].x * 26.6);
                loaded.get(i).y[j] = (int) (800 - (projTris.get(i).pointArray[j].y * 26.6));
            }

            loaded.get(i).x[3] = (int) (300 + projTris.get(i).pointArray[0].x * 26.6);
            loaded.get(i).y[3] = (int) (800 - (projTris.get(i).pointArray[0].y * 26.6));



        }
//        loaded.get(1).x[0] = 40;




        return loaded;
    }

    public ArrayList<projTri> renderTri(ArrayList<Tri> TriArray) {
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

    public ArrayList<Tri> frustumCulling(ArrayList<Tri> triArray){

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

    private ArrayList<projTri> orderTri (ArrayList<projTri>, ArrayList<Tri>){



    }

    private ArrayList<point2> isInside(projTri proj1, projTri proj2) {

        ArrayList<point2> temp = proj2.cpyArr();
        point2 centroid = new point2(proj1.pointArray[0].x + proj1.pointArray[1].x + proj1.pointArray[2].x,
                                     proj1.pointArray[0].y + proj1.pointArray[1].y + proj1.pointArray[2].y);

    for(int i = 0; i < 3; i++){

        double slope = (proj1.pointArray[i].y - proj1.pointArray[(i+1)%3].y)/
                       (proj1.pointArray[i].x - proj1.pointArray[(i+1)%3].x);

        if(centroid.y > slope * (centroid.x - proj1.pointArray[i].x) + proj1.pointArray[i].y){

            for(int j = 0; j < temp.size(); j++){
                //operator is switched to check if point is outside triangle region
                if(-0.01 < slope * (temp.get(j).x - proj1.pointArray[i].x) + proj1.pointArray[i].y - temp.get(j).y){
                    //the point is outside the line
                    temp.remove(j);
                    j--;
                }
            }
        }
        else{

            for(int j = 0; j < temp.size(); j++){
                //operator is switched to check if point is outside triangle region
                if(0.01 > slope * (temp.get(j).x - proj1.pointArray[i].x) + proj1.pointArray[i].y - temp.get(j).y){
                    //the point is outside the line
                    temp.remove(j);
                    j--;
                }
            }

        }
        return temp;
    }


    }

    private ArrayList<point2> clippedPoints(projTri proj1, projTri proj2){
        ArrayList<point2> originalPoints = proj2.cpyArr();
        ArrayList<point2> newPoints = new ArrayList<>();

        point2 cent1 = new point2((proj1.pointArray[0].x + proj1.pointArray[1].x + proj1.pointArray[2].x)/3,
                                  (proj1.pointArray[0].y + proj1.pointArray[1].y + proj1.pointArray[2].y)/3);

        for(int i = 0; i < 3; i++){

            //stores array location of outside points
            ArrayList<Integer> pointsOutside = new ArrayList<>();
            //slope of two points from proj1
            double slope = (proj1.pointArray[i].y - proj1.pointArray[(i+1)%3].y)/(proj1.pointArray[i].x - proj1.pointArray[(i+1)%3].x);


            if(cent1.y > slope * (cent1.x - proj1.pointArray[i].x) + proj1.pointArray[i].y){

                //centroid is included from region

                for(int j = 0; j < proj2.pointArray.length; j++){
                    //operator is switched to check if point is outside triangle region
                    if(-0.1 < slope * (proj2.pointArray[j].x - proj1.pointArray[i].x) + proj1.pointArray[i].y - proj2.pointArray[j].y){
                        //the point is outside the line
                        pointsOutside.add(j);
                    }
                }
            }
            else{
                //centroid is excluded from region
                for(int j = 0; j < proj2.pointArray.length; j++){
                    //operator is switched to check if point is outside triangle region
                    if(0.1 > slope * (proj2.pointArray[j].x - proj1.pointArray[i].x) + proj1.pointArray[i].y - proj2.pointArray[j].y){
                        //the point is outside the line
                        pointsOutside.add(new Integer(j));
                    }
                }
            }

            if(pointsOutside.size() > 0){
                //one point is outside or two are outside.


                int shift = pointsOutside.size() - 1;


                // original plus +/- 1
                //original -1,  original+1 plus 2

                double tempSlope = (proj2.pointArray[pointsOutside.get(0).intValue()].y - proj2.pointArray[(pointsOutside.get(0).intValue() +2) % proj2.pointArray.length].y) /
                                   (proj2.pointArray[pointsOutside.get(0).intValue()].x - proj2.pointArray[(pointsOutside.get(0).intValue() +2) % proj2.pointArray.length].x);

                if(tempSlope > 1000){
                    tempSlope = 1000;
                }
                else if(tempSlope < -1000){
                    tempSlope = -1000;
                }
                var x = ((slope1 * proj1[i].x) - ((tempSlope1 * proj2[pointsOutside[0]].x) + (proj1[i].y - proj2[pointsOutside[0]].y))) / (slope1 - tempSlope1);

                var y = slope1 * (x - proj1[i].x) + proj1[i].y;

                //check if the point of intersection is within x coords of proj1

                var linex1;
                var linex2;
                if(proj1[i].x < proj1[(i+1)%3].x){
                    linex1 = proj1[i].x;
                    linex2 = proj1[(i+1)%3].x;
                }
                else{
                    linex2 = proj1[i].x;
                    linex1 = proj1[(i+1)%3].x;
                }

                if(linex1 < x && x < linex2){
                    //it is within
                    //push coord to newPoints
                    newPoints.push(new point2D(round(x,3), round(y,3)));
                }

                // projArray

                tempSlope1 = (proj2[(pointsOutside[0] + shift) % 3].y - proj2[(pointsOutside[0] + 1 + shift) % 3].y) /
                        (proj2[(pointsOutside[0] + shift) % 3].x - proj2[(pointsOutside[0] + 1 + shift) % 3].x);

                if(tempSlope1 == Number.POSITIVE_INFINITY){
                    tempSlope1 = 1000;
                }
                else if(tempSlope1 == Number.NEGATIVE_INFINITY){
                    tempSlope1 = -1000;
                }

                var x2 = ((slope1 * proj1[i].x) - ((tempSlope1 * proj2[(pointsOutside[0] + shift) % 3].x) + (proj1[i].y - proj2[(pointsOutside[0] + shift) % 3].y))) / (slope1 - tempSlope1);

                var y2 = slope1 * (x2 - proj1[i].x) + proj1[i].y;

                if(linex1 < x2 && x2 < linex2){
                    //it is within
                    //push coord to newPoints
                    newPoints.push(new point2D(round(x2,3), round(y2,3)));

                }


                if(pointsOutside.length == 1){
                    pointsO.splice(pointsOutside[0], 1, 0);
                }
                else if(pointsOutside.length == 2){
                    pointsO.splice(pointsOutside[0], 1, 0);
                    pointsO.splice(pointsOutside[1], 1, 0);
                }
                for(var j = 0; j < pointsOutside.length; j++){
                    pointsOutside.splice(0,1);
                }

                // printLog(newPoints);
                // printLog(pointsO);
            }

        }

    // for(var i = 2; i > -1; i--){
    // 	if(pointsO[i] == 0){
    // 		pointsO.splice(i, 1);
    // 	}
    // }

	return newPoints;

    }

    private boolean compareTri(Tri tri1, Tri tri2){

    }

}
