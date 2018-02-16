import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public class Main {

    public static void main(String[] args) {




        projTri c = new projTri(new point2(-1.72,1), new point2(0.82,4.34), new point2(1.98,1.92));
        projTri b = new projTri(new point2(4.37,1.53), new point2(-1.56,3.56), new point2(0.88,1.39));

        ArrayList<point2> m = OrderTri.clippedPoints(c,b);

        for(int i = 0; i < m.size(); i++){
            System.out.println(m.get(i).toString());
        }

        draw a = new draw();

        a.setup();
        JFrame f = new JFrame("Title");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment()
//                .getDefaultScreenDevice();
////        device.setFullScreenWindow(f);
//
//        device.setDisplayMode(new DisplayMode(2560, 1600, 32, 60));


        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();




        f.setSize((int) screenSize.getWidth(),(int) screenSize.getHeight());
        f.setVisible(true);
        f.add(a);
        f.setFocusable(true);

        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

// Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");

// Set the blank cursor to the JFrame.
        f.getContentPane().setCursor(blankCursor);





//        point3 center = new point3(1,0,0);
//        Euler angle = new Euler(90,0,0);
//        Poly testPoly = new Poly(t1, center, angle);
//
//        ArrayList<Poly> p1 = new ArrayList<>();
//        p1.add(testPoly);
//
//







//        double x = 0;
//
//        while(true){
//            x+=0.1;
//            a.setX((int)x);
//            f.add(a);
//            f.revalidate();
//            f.repaint();
//            if(x > 1600){
//                x = 0;
//            }
//
//        }
//
//        System.out.println("aasdfasdfasdf");
//
//        p1.get(0).center.x = 1;
//
//        rds = r.RenderPoly(0,a);
//
//        System.out.println("A");
//
//        System.out.println(rds.toString());
//
//        f.revalidate();
//        f.repaint();

    }

}
