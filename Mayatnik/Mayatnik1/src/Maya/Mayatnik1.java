package Maya;

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;



/**
 * Mayatnik1.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class Mayatnik1 implements GLEventListener {
    double time = 0;

    public static void main(String[] args) {
        Frame frame = new Frame("Simple JOGL Application");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Mayatnik1());
        frame.add(canvas);
        frame.setSize(1000, 1000);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }

    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        //3D-вид
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glEnable(GL.GL_SCISSOR_TEST);
        //gl.glClearColor(1,1,1,0);
        
        gl.glPolygonMode(GL.GL_FRONT, GL.GL_FILL);

        double alpha = 0, R = 0.1, da = 2*Math.PI/30, x, y;

        time = time + 0.05;
        
        double wwe = Math.cos(time)*30;
        
        gl.glLoadIdentity();
        gl.glTranslated(0, 0.7, 0);
        gl.glBegin(GL.GL_QUADS);
            gl.glColor3d(0.7, 0.7, 0.7);
            gl.glVertex2d(-0.3, 0.1);
            gl.glVertex2d(0.3, 0.1);
            gl.glVertex2d(0.3, 0);
            gl.glVertex2d(-0.3, 0);
        gl.glEnd();
        
        gl.glLoadIdentity(); //сбрасываем координаты
        gl.glTranslated(0, 0.7, 0); 
        gl.glRotated(wwe, 0, 0, 1); //теперь ось z будет поворачиваться по координатам выше
        gl.glTranslated(0, -1.3, 0); //опускаем координаты для дальнейший построений в этой системы
        
        gl.glBegin(GL.GL_LINES);
            gl.glColor3d(1, 1, 1);
            gl.glVertex2d(0, 1.3);
            gl.glVertex2d(0, 0);
        gl.glEnd();
        
        gl.glBegin(GL.GL_POLYGON);
            while(alpha<=Math.PI*2+da) {
               x = R*Math.cos(alpha);
               y = R*Math.sin(alpha);
               gl.glColor3d(0.6, 0.6, 0.6);  
               gl.glVertex2d(x, y);
               alpha += da;
           }
        gl.glEnd();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}

