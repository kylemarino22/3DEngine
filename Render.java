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

    private ArrayList<projTri> orderTri (ArrayList<projTri> arr, ArrayList<Tri> original){

        //array of tris
        //calculate tri projections
        //projArray is an array of projectedTris
        ArrayList<projTri> projArray = new ArrayList<>();
        ArrayList<projTri> finalArray = new ArrayList<>();

//        for(int i = 0; i < arr.size(); i++){
//            projArray.add(new projTri(new point2(0,0), new point2(0,0), new point2(0,0)));
//
//            for(int j = 0; j <3; j++){
//                projArray[i].pointArray[j].x = simpleCalcXZ(arr[i].pointArray[j].x + center.x,
//                        arr[i].pointArray[j].z + center.z);
//            }
//
//            for(int j = 0; j <3; j++){
//                projArray[i].pointArray[j].y = simpleCalcYZ(arr[i].pointArray[j].y + center.y,
//                        arr[i].pointArray[j].z + center.z);
//            }
//
//        }



        for(int i = 0; i < arr.size(); i++){
            for(int j = i; j< arr.size(); j++){

                // console.log(arr[i].color + " and " + arr[j].color);
                if(j == i){
                    continue;
                }


                ArrayList<point2> inside = isInside(projArray.get(i), projArray.get(j));

                if(inside.size() == 0){
                    inside = isInside(projArray.get(j), projArray.get(i));
                }

                if(inside.size() == 0){

                    ArrayList<point2> points = clippedPoints(projArray.get(i), projArray.get(j));
                    ArrayList<point2> samePoints = samePoints(points, projArray.get(i).pointArray);



                    if(points.size() == 0|| samePoints.size() == points.size()){
                        // console.log("no intersect");
                        double averageZ1 = averageZ(original.get(i).pointArray[0].z,
                                                    original.get(i).pointArray[1].z,
                                                    original.get(i).pointArray[2].z);
                        double averageZ2 = averageZ(original.get(j).pointArray[0].z,
                                                    original.get(j).pointArray[1].z,
                                                    original.get(j).pointArray[2].z);

                        if(averageZ1 > averageZ2){
                            continue;
                        }
                        else{

                            Tri temp = original.get(i);
                            original.set(i, original.get(j));
                            original.set(j, temp);

                            projTri projTemp = projArray.get(i);
                            projArray.set(i, projArray.get(j));
                            projArray.set(j,  projTemp);
                            continue;
                        }
                    }

                    samePoints(projArray[i].pointArray, projArray[j].pointArray,points);
                    var result = new point2D(averageArray(points, 'x'),
                            averageArray(points, 'y'));
                    printLog(result);
                    inside[0] = result;
                }
                else{
                    printLog(inside);
                }



                var result = compareTri(arr[i],arr[j],inside[0],center);

                if(result == 0){
                    continue;
                }
                else{
                    var temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;

                    temp = projArray[i];
                    projArray[i] = projArray[j];
                    projArray[j] = temp;
                    continue;
                }

            }
        }

        return arr;


    }

    private ArrayList<point2> isInside(projTri proj1, projTri proj2) {

        ArrayList<point2> temp = proj2.cpyArr();
        point2 centroid = new point2(proj1.pointArray[0].x + proj1.pointArray[1].x + proj1.pointArray[2].x,
                proj1.pointArray[0].y + proj1.pointArray[1].y + proj1.pointArray[2].y);

        for (int i = 0; i < 3; i++) {

            double slope = (proj1.pointArray[i].y - proj1.pointArray[(i + 1) % 3].y) /
                    (proj1.pointArray[i].x - proj1.pointArray[(i + 1) % 3].x);

            if (centroid.y > slope * (centroid.x - proj1.pointArray[i].x) + proj1.pointArray[i].y) {

                for (int j = 0; j < temp.size(); j++) {
                    //operator is switched to check if point is outside triangle region
                    if (-0.01 < slope * (temp.get(j).x - proj1.pointArray[i].x) + proj1.pointArray[i].y - temp.get(j).y) {
                        //the point is outside the line
                        temp.remove(j);
                        j--;
                    }
                }
            } else {

                for (int j = 0; j < temp.size(); j++) {
                    //operator is switched to check if point is outside triangle region
                    if (0.01 > slope * (temp.get(j).x - proj1.pointArray[i].x) + proj1.pointArray[i].y - temp.get(j).y) {
                        //the point is outside the line
                        temp.remove(j);
                        j--;
                    }
                }
            }
        }
        return temp;
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
                double x = ((slope * proj1.pointArray[i].x) - ((tempSlope * proj2.pointArray[pointsOutside.get(0).intValue()].x) + (proj1.pointArray[i].y - proj2.pointArray[pointsOutside.get(0).intValue()].y))) / (slope - tempSlope);

                double y = slope * (x - proj1.pointArray[i].x) + proj1.pointArray[i].y;

                //check if the point of intersection is within x coords of proj1

                double linex1;
                double linex2;
                if(proj1.pointArray[i].x < proj1.pointArray[(i+1)%3].x){
                    linex1 = proj1.pointArray[i].x;
                    linex2 = proj1.pointArray[(i+1)%3].x;
                }
                else{
                    linex2 = proj1.pointArray[i].x;
                    linex1 = proj1.pointArray[(i+1)%3].x;
                }

                if(linex1 < x && x < linex2){
                    //it is within
                    //push coord to newPoints
                    newPoints.add(new point2(x,y));
                }

                // projArray

                tempSlope = (proj2.pointArray[(pointsOutside.get(0).intValue() + shift) % 3].y - proj2.pointArray[(pointsOutside.get(0).intValue() + 1 + shift) % 3].y) /
                            (proj2.pointArray[(pointsOutside.get(0).intValue() + shift) % 3].x - proj2.pointArray[(pointsOutside.get(0).intValue() + 1 + shift) % 3].x);

                if(tempSlope > 1000){
                    tempSlope = 1000;
                }
                else if(tempSlope < -1000){
                    tempSlope = -1000;
                }

                double x2 = ((slope * proj1.pointArray[i].x) - ((tempSlope * proj2.pointArray[(pointsOutside.get(0).intValue() + shift) % 3].x) + (proj1.pointArray[i].y - proj2.pointArray[(pointsOutside.get(0).intValue() + shift) % 3].y))) / (slope - tempSlope);

                double y2 = slope * (x2 - proj1.pointArray[i].x) + proj1.pointArray[i].y;

                if(linex1 < x2 && x2 < linex2){
                    //it is within
                    //push coord to newPoints
                    newPoints.add(new point2(x,y));

                }


                if(pointsOutside.size() == 1){
                    originalPoints.remove(pointsOutside.get(0).intValue());
                }
                else if(pointsOutside.size() == 2){
                    originalPoints.remove(pointsOutside.get(0).intValue());
                    originalPoints.remove(pointsOutside.get(1).intValue());
                }
                //empties array in js - not needed in java
//                for(int j = 0; j < pointsOutside.size(); j++){
//                    pointsOutside.splice(0,1);
//                }

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

    private class Component{
        double t;
        double c;

        private Component(double c, double t){
            this.t = t;
            this.c = c;
        }
    }

    private boolean compareTri(Tri tri1, Tri tri2, point3 inside){
        //find intersection
        //https://www.google.com/search?q=find+intersection+of+plane+and+line+(vector+form)&oq=find+intersection+of+plane+and+line+(vector+form)&aqs=chrome..69i57.24125j0j1&sourceid=chrome&ie=UTF-8#kpvalbx=1
        //(15,15,-100)
        //(result, 0)

        //parametric  for line

        // var p1 = new point3D(15,15,-100);
        // var p2 = new point3D(inside.x,inside.y,0);

        Tri[] selector = new Tri[]{tri1,tri2};


        point3 p1 = new point3(15,15,-100);
        point3 p2 = new point3(inside.x,inside.y,0);

        Component xComp = new Component(p1.x, (p2.x - p1.x));
        Component yComp = new Component(p1.y, (p2.y - p1.y));
        Component zComp = new Component(p1.z, (p2.z - p1.z));

        double zDepth1 = 0;
        double zDepth2 = 0;

        for(int i = 0; i < 2; i++){

            point3 p3 = new point3(selector[i].pointArray[0].x,
                                   selector[i].pointArray[0].y,
                                   selector[i].pointArray[0].z);
            point3 p4 = new point3(selector[i].pointArray[1].x,
                                   selector[i].pointArray[1].y,
                                   selector[i].pointArray[1].z);
            point3 p5 = new point3(selector[i].pointArray[2].x,
                                   selector[i].pointArray[2].y,
                                   selector[i].pointArray[2].z);


            //plane
            Vector vector1 = new Vector(p4.x - p3.x, p4.y - p3.y, p4.z - p3.z);
            Vector vector2 = new Vector(p5.x - p5.x, p4.y - p3.y, p5.z - p3.z);

            //normal
            Vector normalVec = Vector.normal(selector[i].pointArray[0], selector[i].pointArray[1], selector[i].pointArray[2]);


            //plane constant
            double d = normalVec.i * (xComp.c - p3.x) +
                       normalVec.j * (yComp.c - p3.y) +
                       normalVec.k * (zComp.c - p3.z);

            //calculate T
            double t = -d / (normalVec.i * xComp.t +
                             normalVec.j * yComp.t +
                             normalVec.k * zComp.t);

            if(i == 0){
                zDepth1 = zComp.c + zComp.t * t;
            }
            else{
                zDepth2 = zComp.c + zComp.t * t;
            }

        }

        if(zDepth1 > zDepth2){
            return false;
        }
        else{
            return true;
        }
    }



    private ArrayList<point2> samePoints(ArrayList<point2> pArray1, point2[] pArray2){

        ArrayList<point2> output = new ArrayList<>();

        for(int j = 0; j < pArray1.size(); j++){
            for(int i = 0; i < pArray2.length; i++){

                if(0.01 > Math.abs(pArray1.get(j).x - pArray2[i].x)){
                    //x check
                    if(0.01 > Math.abs(pArray1.get(j).y - pArray2[i].y)){
                        //y check
                        output.add(new point2(pArray1.get(j).x , pArray1.get(j).y));
                    }
                }
            }
        }
        return output;
    }




}
