package Palitra;

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.FloatBuffer;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;



/**
 * Trenazher.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class Palitra implements GLEventListener {
GLU glu = new GLU();

    public static void main(String[] args) {
        Frame frame = new Frame("Simple JOGL Application");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Palitra());
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
        animator.stop();
    }

    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClearColor(0, 0, 0, 0);//цвет фона
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
   
        //палитра
        gl.glColor3f(0.8f, 0.4f, 0.2f);
        
         float cp[] =
        {
           -0.6f, -0.3f, 0, -0.97f, -0.97f, 0,
            0.3f, -0.9f, 0,  0.65f, -0.35f, 0,

           -0.5f, -0.1f, 0, -0.3f, -0.3f, 0,
            0.3f, -0.3f, 0,  0.9f, -0f, 0,

           -0.5f,  0.1f, 0, -0.3f,  0.3f, 0,
            0.3f,  0.3f, 0,  0.9f,  0.3f, 0,

            -0.6f, 0.2f, 0, -1.5f,  0.85f, 0,
             0f, 0.97f, 0,  0.55f,  0.55f, 0
        };
         
        FloatBuffer fb;
        fb = FloatBuffer.wrap(cp);

        gl.glMap2f(GL.GL_MAP2_VERTEX_3, 0, 1, 3, 4, 0, 1, 12, 4, fb);
        gl.glEnable(GL.GL_MAP2_VERTEX_3);

        gl.glMapGrid2f(20, 0, 1, 20, 0, 1);
        gl.glEvalCoord2d(GL.GL_FILL, 0, 20, 0, 20); 
        
        
        //отверстие для кисточек
        gl.glColor3f(0.0f, 0.0f, 0.0f);
         float cp1[] =
        {
           -0.12f, -0.06f, 0, -0.194f, -0.194f, 0,
            0.06f, -0.18f, 0,  0.13f, -0.07f, 0,

           -0.1f, -0.02f, 0, -0.06f, -0.06f, 0,
            0.06f, -0.06f, 0,  0.18f, -0f, 0,

           -0.1f,  0.02f, 0, -0.06f,  0.06f, 0,
            0.05f,  0.06f, 0,  0.18f,  0.06f, 0,

            -0.12f, 0.04f, 0, -0.3f,  0.17f, 0,
             0f, 0.194f, 0,  0.11f,  0.11f, 0
        };
         
        
        gl.glPushMatrix();
        gl.glTranslatef(-0.2f,-0.2f,0);
        gl.glRotatef(-20,0,0,1);
        
        fb = FloatBuffer.wrap(cp1);
        gl.glMap2f(GL.GL_MAP2_VERTEX_3, 0, 1, 3, 4, 0, 1, 12, 4, fb);
        gl.glEnable(GL.GL_MAP2_VERTEX_3);
        gl.glMapGrid2f(20, 0, 1, 20, 0, 1);
        gl.glEvalMesh2(GL.GL_FILL, 0, 20, 0, 20); 

        gl.glPopMatrix();
        
        //краска синяя
        gl.glColor3f(0.1f, 1.0f, 0.1f);
        
        float cp2[] =
        {
           -0.09f, 0f, 0,  0f ,-0.2f, 0,  0.1f, -0.1f, 0,

            -0.09f,0,0,  0.5f,0.7f,0,  0.4f, 0, 0,

            -0.07f, 0.06f, 0,  0.0f, 0.2f, 0,  0.2f, 0.15f, 0
        };

        fb = FloatBuffer.wrap(cp2);
        
        gl.glPushMatrix();
        gl.glTranslatef(0.4f,0.1f,0);
        gl.glRotatef(-20,0,0,1);
        
        gl.glColor3d(0, 0, 1);
        gl.glMap2f(GL.GL_MAP2_VERTEX_3, 0, 1, 3, 3, 0, 1, 9, 3, fb);
        gl.glEnable(GL.GL_MAP2_VERTEX_3);

        gl.glMapGrid2f(20, 0, 1, 20, 0, 1);
        gl.glEvalMesh2(GL.GL_FILL, 0, 20, 0, 20); 
        
        gl.glPopMatrix();
        //2-ая краска
        gl.glPushMatrix();
        gl.glTranslatef(0.1f,0.4f,0);
        gl.glRotatef(20,0,0,1);
        
        gl.glColor3d(0, 1, 1);
        gl.glMap2f(GL.GL_MAP2_VERTEX_3, 0, 1, 3, 3, 0, 1, 9, 3, fb);
        gl.glEnable(GL.GL_MAP2_VERTEX_3);

        gl.glMapGrid2f(20, 0, 1, 20, 0, 1);
        gl.glEvalMesh2(GL.GL_FILL, 0, 20, 0, 20); 
        
        gl.glPopMatrix();

        //3-я краска
        gl.glPushMatrix();
        gl.glTranslatef(-0.4f,0.45f,0);
        gl.glRotatef(70,0,0,1);
        
        gl.glColor3d(0, 0.8, 0.8);
        gl.glMap2f(GL.GL_MAP2_VERTEX_3, 0, 1, 3, 3, 0, 1, 9, 3, fb);
        gl.glEnable(GL.GL_MAP2_VERTEX_3);

        gl.glMapGrid2f(20, 0, 1, 20, 0, 1);
        gl.glEvalMesh2(GL.GL_FILL, 0, 20, 0, 20); 
        
        gl.glPopMatrix();

        // Flush all drawing operations to the graphics card
        gl.glFlush();
        }
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}

