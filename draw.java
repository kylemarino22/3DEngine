import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class draw extends JPanel implements ActionListener, model, KeyListener, FocusListener, MouseListener{

    private Graphics g;
    public static ArrayList<loadedTri> loaded = new ArrayList<>();
    public static ArrayList<Poly> p1 = new ArrayList<>();
    public static Camera cam = new Camera(new Euler(0,0,0), new point3(0,0,0));
    private boolean focused = false;
    private boolean KEY_W = false;
    private boolean KEY_A = false;
    private boolean KEY_S = false;
    private boolean KEY_D = false;
    private boolean KEY_SHIFT = false;
    private boolean KEY_SPACE = false;
    private boolean mouseStarted = false;


    public double x = 0;

    public draw(){
        addKeyListener(this);
        addFocusListener(this);
        addMouseListener(this);
    }

    public void keyPressed(KeyEvent evt) {
        System.out.println("pressed");

        int key = evt.getKeyCode();

        switch(key){

            case 68:

                KEY_D = true;
                break;

            case 65:
                KEY_A = true;
                break;

            case 87:
                KEY_W = true;
                break;

            case 83:
                KEY_S = true;
                break;

            case 16:
                KEY_SHIFT = true;
                break;

            case 32:
                KEY_SPACE = true;
                break;

            case 27:
                focused = false;
                break;

            case 71:
                while(true);

        }



    }

    public void mousePressed(MouseEvent evt) {
        // Request that the input focus be given to the
        // canvas when the user clicks on the applet.
        focused = true;
        requestFocus();
    }

    public void focusGained(FocusEvent evt) {
        // The applet now has the input focus.

//        while(true);
        focused = true;
        repaint();  // redraw with cyan border
    }


    public void focusLost(FocusEvent evt) {
        // The applet has now lost the input focus.
        focused = false;
        System.out.println("focus lost");
        repaint();  // redraw without cyan border
    }



    public void keyReleased(KeyEvent evt) {

        int key = evt.getKeyCode();

        switch(key){

            case 68:
                KEY_D = false;
                break;

            case 65:
                KEY_A = false;
                break;

            case 87:
                KEY_W = false;
                break;

            case 83:
                KEY_S = false;
                break;

            case 16:
                KEY_SHIFT = false;
                break;

            case 32:
                KEY_SPACE = false;
                break;
        }

    }
    public void keyTyped(KeyEvent evt){ }
    public void mouseEntered(MouseEvent evt) { }  // Required by the
    public void mouseExited(MouseEvent evt) { }   //    MouseListener
    public void mouseReleased(MouseEvent evt) { } //       interface.
    public void mouseClicked(MouseEvent evt) { }


    Timer tm = new Timer(25, this);


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.g = g;

        Graphics2D g2 = (Graphics2D)g;
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHints(rh);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        this.setBackground(new Color(224, 219, 208));

        g2.setColor(Color.BLACK);
        g2.fillRect(600,400,5,5);

        g2.setColor(new Color(0,0,0));


        for(int i = 0; i < loaded.size(); i++){
            g2.drawPolyline(loaded.get(i).x, loaded.get(i).y, 4);
        }

        g2.drawString("pitch: " + (cam.angle.p * 180/Math.PI), 0,11);
        g2.drawString("yaw: " + (cam.angle.y * 180/Math.PI), 0,22);

        g2.drawString("x: " + (cam.center.x), 0,44);
        g2.drawString("y: " + (cam.center.y), 0,55);
        g2.drawString("z: " + (cam.center.z), 0,66);
        incrementKeys();
        resetMouse();
        loaded.clear();
        tm.start();


    }


    public void actionPerformed(ActionEvent e){
        //main loop

        Render r = new Render(p1, cam);
//        Poly rds = r.RenderPoly(0,g); //wireframe is done in render poly
//        Poly b = r.RenderPoly(1,g);
//        r.RenderPoly(2,g);

        for(int i = 0; i < p1.size(); i++){
            r.RenderPoly(i,g);
        }

        x += 2;
//        p1.get(0).center.x = x;
//        p1.get(0).center.y = x;
//        p1.get(0).angle.setPitch(x);
//        p1.get(0).angle.setYaw(x);




        repaint();

    }

    public void setup(){

        t1.add(test1);
        t1.add(test2);
        t1.add(test3);
        t1.add(test4);
        t1.add(test5);
        t1.add(test6);

//        t2.add(test1);
//        t2.add(test2);
//        t2.add(test3);
//        t2.add(test4);
//        t2.add(test5);
//        t2.add(test6);
//
//        t3.add(test1);
//        t3.add(test2);
//        t3.add(test3);
//        t3.add(test4);
//        t3.add(test5);
//        t3.add(test6);
//
//        t4.add(test1);
//        t4.add(test2);
//        t4.add(test3);
//        t4.add(test4);
//        t4.add(test5);
//        t4.add(test6);






        point3 center = new point3(11.5,15,-5);
        Euler angle = new Euler(90,0,0);

        point3 center2 = new point3(18.5,15,-5);
        Euler angle2 = new Euler(90,0,0);

        point3 center3 = new point3(18,15,-10);
        Euler angle3 = new Euler(0,0,0);

        point3 center4 = new point3(12,15,-10);
        Euler angle4 = new Euler(0,0,0);



        Poly brown = new Poly(t1, center2, angle2);
        Poly kelek = new Poly(t1, center3, angle3);
        Poly antiphot = new Poly(t1,center4, angle4 );

        Poly testPoly = new Poly(t1, center, angle);
        p1.add(testPoly);
        p1.add(brown);
        p1.add(kelek);
        p1.add(antiphot);

    }

    private void incrementKeys(){


        if(KEY_D){
            System.out.println("BOOm BIg SHACK");
            cam.center.x += 0.5 * Math.cos(cam.angle.y);
            cam.center.z += 0.5 * Math.sin(cam.angle.y);
        }
        if(KEY_A){
            System.out.println("BOOm BIg SHACK");
            cam.center.x -= 0.5 * Math.cos(cam.angle.y);
            cam.center.z -= 0.5 * Math.sin(cam.angle.y);
        }
        if(KEY_W){
            System.out.println("BOOm BIg SHACK");

//            double y = Math.sin(cam.angle.p);
//            double x = Math.sin(cam.angle.y) * Math.cos(cam.angle.p);
//            double z = Math.cos(cam.angle.y) * Math.cos(cam.angle.p);
//            double N = Math.abs(x) + Math.abs(y) + Math.abs(z);
//
//            System.out.println("hereeas");
//            System.out.println(N);
//            System.out.println(y);
//            System.out.println(z);
//            cam.center.x += 0.6 * x / N;
//            cam.center.y += 0.6 * y / N;
//            cam.center.z -= 0.6 * z / N;

            cam.center.x += 0.5 * Math.sin(cam.angle.y);
            cam.center.z -= 0.5 * Math.cos(cam.angle.y);
        }
        if(KEY_S){
            System.out.println("BOOm BIg SHACK");

            cam.center.x -= 0.5 * Math.sin(cam.angle.y);
            cam.center.z += 0.5 * Math.cos(cam.angle.y);
        }
        if(KEY_SHIFT){
            System.out.println("BOOm BIg SHACK");
            cam.center.y -= 0.5;
        }
        if(KEY_SPACE){
            System.out.println("BOOm BIg SHACK");
            cam.center.y += 0.5;
        }
    }

    public void hideMouse(JFrame f){
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

// Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");

// Set the blank cursor to the JFrame.
        f.getContentPane().setCursor(blankCursor);
    }


    private void resetMouse(){



        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        int x = (int) b.getX();
        int y = (int) b.getY();

        if(x == 500 && y == 500) {
            mouseStarted = true;
        }
        if(focused == false){
            mouseStarted = false;
        }

        if(mouseStarted == true){
            int deltax = x - 500;
            int deltay = y - 500;

            cam.angle.incrementYaw(deltax * 0.11);

            System.out.println(cam.angle.p);
            if(cam.angle.p * 180/Math.PI > 90){
                cam.angle.setPitch(89);
            }
            else if(cam.angle.p * 180/Math.PI < -90){
                cam.angle.setPitch(-89);
            }
            else{
                cam.angle.incrementPitch(-deltay * 0.16);
            }

        }





        if(focused){
            try {
                // These coordinates are screen coordinates
                int xCoord = 500;
                int yCoord = 500;

                // Move the cursor
                Robot robot = new Robot();
                robot.mouseMove(xCoord, yCoord);
            } catch (AWTException e) {
            }
        }
    }







}