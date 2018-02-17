import java.util.ArrayList;

public class OrderTri {

    public static ArrayList<projTri> orderTri (ArrayList<projTri> projArray, ArrayList<Tri> original){

        //array of tris
        //calculate tri projections
        //projArray is an array of projectedTris
//        ArrayList<projTri> projArray = new ArrayList<>();
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



        for(int i = 0; i < projArray.size(); i++){
            for(int j = i; j< projArray.size(); j++){

                // console.log(arr[i].color + " and " + arr[j].color);
                if(j == i){
                    continue;
                }


                ArrayList<point2> inside = isInside(projArray.get(i),
                        projArray.get(j));

                if(inside.size() == 0){
                    inside = isInside(projArray.get(j), projArray.get(i));
                }

                if(inside.size() == 0){

                    ArrayList<point2> points = clippedPoints(projArray.get(i), projArray.get(j));

                    point2 pointsArray[] = points.toArray(new point2[points.size()]);
                    ArrayList<point2> samePoints = samePoints(pointsArray, projArray.get(i).pointArray);



                    if(points.size() == 0|| samePoints.size() == points.size()){
                        // console.log("no intersect");
                        double averageZ1 = average3(original.get(i).pointArray[0].z,
                                original.get(i).pointArray[1].z,
                                original.get(i).pointArray[2].z);
                        double averageZ2 = average3(original.get(j).pointArray[0].z,
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

                    samePoints(projArray.get(i).pointArray, projArray.get(j).pointArray);
                    point2 result = averagePoints(points);
//                    System.out.println(result.toString());
//                    inside.get(0);
                    inside.add(result);
                }
//                else{
//                    printLog(inside);
//                }



                boolean result = compareTri(original.get(i),original.get(j),inside.get(0));

                if(result == false){
//                    continue;
                }
                else{

                    Tri temp = original.get(i);
                    original.set(i, original.get(j));
                    original.set(j, temp);

                    projTri projTemp = projArray.get(i);
                    projArray.set(i, projArray.get(j));
                    projArray.set(j,  projTemp);
//                    continue;
                }

            }
        }

        return projArray;


    }

    private static ArrayList<point2> isInside(projTri proj1, projTri proj2) {

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


    public static ArrayList<point2> clippedPoints(projTri proj1, projTri proj2){
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
                        System.out.println("here1 " + j);
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
                        System.out.println("here2 " + j);
                        pointsOutside.add(j);
                    }
                }
            }

            if(pointsOutside.size() > 0){
                //one point is outside or two are outside.


                int shift = pointsOutside.size() - 1;
                System.out.println("shift " + shift);


                // original plus +/- 1
                //original -1,  original+1 plus 2

                double tempSlope = (proj2.pointArray[pointsOutside.get(0)].y - proj2.pointArray[(pointsOutside.get(0) +2) % proj2.pointArray.length].y) /
                                   (proj2.pointArray[pointsOutside.get(0)].x - proj2.pointArray[(pointsOutside.get(0) +2) % proj2.pointArray.length].x);

                System.out.println("tempSlope " + tempSlope);
                if(tempSlope > 1000){
                    tempSlope = 1000;
                }
                else if(tempSlope < -1000){
                    tempSlope = -1000;
                }
                double x = ((slope * proj1.pointArray[i].x) - ((tempSlope * proj2.pointArray[pointsOutside.get(0)].x) + (proj1.pointArray[i].y - proj2.pointArray[pointsOutside.get(0)].y))) / (slope - tempSlope);

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
                    System.out.println("New point " + x + " " + y);
                    newPoints.add(new point2(x,y));
                }

                // projArray

                tempSlope = (proj2.pointArray[(pointsOutside.get(0) + shift) % 3].y - proj2.pointArray[(pointsOutside.get(0) + 1 + shift) % 3].y) /
                            (proj2.pointArray[(pointsOutside.get(0) + shift) % 3].x - proj2.pointArray[(pointsOutside.get(0) + 1 + shift) % 3].x);

                if(tempSlope > 1000){
                    tempSlope = 1000;
                }
                else if(tempSlope < -1000){
                    tempSlope = -1000;
                }

                double x2 = ((slope * proj1.pointArray[i].x) - ((tempSlope * proj2.pointArray[(pointsOutside.get(0) + shift) % 3].x) + (proj1.pointArray[i].y - proj2.pointArray[(pointsOutside.get(0) + shift) % 3].y))) / (slope - tempSlope);

                double y2 = slope * (x2 - proj1.pointArray[i].x) + proj1.pointArray[i].y;

                System.out.println("x2 y2 thingy " + x2 + " " + y2);

                if(linex1 < x2 && x2 < linex2){
                    //it is within
                    //push coord to newPoints
                    newPoints.add(new point2(x2,y2));

                }

                System.out.println("here" + i);
//                if(pointsOutside.size() == 1){
//                    originalPoints.remove(pointsOutside.get(0).intValue());
//                }
//                else if(pointsOutside.size() == 2){
//                    //horribly inefficient
//                    System.out.println(pointsOutside.get(0));
//                    System.out.println(pointsOutside.get(1));
//                    System.out.println(originalPoints.size());
//
//                    originalPoints.remove(pointsOutside.get(0).intValue());
//                    originalPoints.remove(pointsOutside.get(1).intValue());
//
//
////                    if(pointsOutside.get(0) > pointsOutside.get(1)){
////                        originalPoints.remove(pointsOutside.get(1).intValue());
////                        originalPoints.remove(pointsOutside.get(0).intValue());
////                    }
////                    else{
////
////                    }
//
//                }
//                pointsOutside.clear();
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

    private static class Component{
        double t;
        double c;

        private Component(double c, double t){
            this.t = t;
            this.c = c;
        }
    }

    private static boolean compareTri(Tri tri1, Tri tri2, point2 inside){
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
//            point3 p4 = new point3(selector[i].pointArray[1].x,
//                                   selector[i].pointArray[1].y,
//                                   selector[i].pointArray[1].z);
//            point3 p5 = new point3(selector[i].pointArray[2].x,
//                                   selector[i].pointArray[2].y,
//                                   selector[i].pointArray[2].z);


            //plane
//            Vector vector1 = new Vector(p4.x - p3.x, p4.y - p3.y, p4.z - p3.z);
//            Vector vector2 = new Vector(p5.x - p5.x, p4.y - p3.y, p5.z - p3.z);

            //normal
            Vector normalVec = Vector.normal(selector[i].pointArray[0], selector[i].pointArray[1], selector[i].pointArray[2]);


            //plane constant
            double d = (normalVec.i * (xComp.c - p3.x)) +
                    (normalVec.j * (yComp.c - p3.y)) +
                    (normalVec.k * (zComp.c - p3.z));

            //calculate T
            double t = -d / ((normalVec.i * xComp.t) +
                    (normalVec.j * yComp.t) +
                    (normalVec.k * zComp.t));

            if(i == 0){
                zDepth1 = zComp.c + zComp.t * t;
            }
            else{
                zDepth2 = zComp.c + zComp.t * t;
            }

        }

        return !(zDepth1 > zDepth2);
    }



    private static ArrayList<point2> samePoints(point2[] pArray1, point2[] pArray2){

        ArrayList<point2> output = new ArrayList<>();

        for(int j = 0; j < pArray1.length; j++){
            for(int i = 0; i < pArray2.length; i++){

                if(0.01 > Math.abs(pArray1[j].x - pArray2[i].x)){
                    //x check
                    if(0.01 > Math.abs(pArray1[j].y - pArray2[i].y)){
                        //y check
                        output.add(new point2(pArray1[j].x , pArray1[j].y));
                    }
                }
            }
        }
        return output;
    }

    private static double average3(double n1, double n2, double n3){
        return (n1 + n2 + n3)/3;
    }

    private static point2 averagePoints(ArrayList<point2> points){
        double x = 0;
        double y = 0;
        //x average
        for (point2 point : points) {
            x += point.x;
        }
        x /= points.size();

        //y average
        for (point2 point : points) {
            y += point.y;
        }
        y /= points.size();

        return new point2(x,y);
    }
}
