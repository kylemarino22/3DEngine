import javax.swing.*;
import java.awt.event.*;

public class Camera {

    public Euler angle;
    public point3 center;
    private int counter;

    private boolean dPressed = false;
    private boolean sPressed = false;

    public Camera(Euler angle, point3 center){
        this.angle = angle;
        this.center = center;

    }

    public String toString(){
        return "Euler: " + angle.toString() + "\nCenter: " + center.toString();
    }

    public Camera clone(){
        return new Camera(angle.clone(), center.clone());
    }

//    public


//    public void memesrcool(draw d, KeyEvent evt){
//
//        d.keyPressed(evt){
//
//        };
//        d.getInputMap().put(KeyStroke.getKeyStroke(68, 0, false), "D_PRESS");
//        d.getInputMap().put(KeyStroke.getKeyStroke(68, 0, true), "D_RELEASE");
//
//        d.getInputMap().put(KeyStroke.getKeyStroke(65, 0, false), "S_PRESS");
//        d.getInputMap().put(KeyStroke.getKeyStroke(65, 0, true), "S_RELEASE");
//
//        d.getActionMap().put("D_PRESS", D_UP);
//        d.getActionMap().put("D_RELEASE", D_DOWN);
//
//        d.getActionMap().put("S_PRESS", S_UP);
//        d.getActionMap().put("S_RELEASE", S_DOWN);
//
//
//
//        if(dPressed == true){
//            d.cam.center.x += 0.3;
//        }
//        if (sPressed == true) {
//            d.cam.center.x -= 0.3;
//        }
//    }
//
//    Action D_UP = new AbstractAction() {
//        public void actionPerformed(ActionEvent e) {
//            dPressed = true;
//            System.out.println("dad");
//        }
//    };
//
//    Action D_DOWN = new AbstractAction() {
//        public void actionPerformed(ActionEvent e) {
//            dPressed = false;
//            System.out.println("son");
//        }
//    };
//
//    Action S_UP = new AbstractAction() {
//        public void actionPerformed(ActionEvent e) {
//            sPressed = true;
//            System.out.println("aasdf");
//        }
//    };
//
//    Action S_DOWN = new AbstractAction() {
//        public void actionPerformed(ActionEvent e) {
//            sPressed = false;
//            System.out.println("fdsa");
//        }
//    };

//    public void keyPressed(KeyEvent evt) {
//
//        int key = evt.getKeyCode();
//
//            System.out.println("DA TING GO SKKKKRRRRRRAAAAAAAAAAAAAAAA");
//            System.out.println(key);
//
//
//    }
//
//
//    public void keyReleased(KeyEvent evt) {
//        int key = evt.getKeyCode();
//        if (key == 119){
//            System.out.println("KA KA KA KA KA");
//        }
//
//    }
//    public void keyTyped(KeyEvent evt){
//        //empty cuz why tf would i need
//    }



}
