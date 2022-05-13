package Rekief;

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
 * Relief.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class Relief implements GLEventListener {

    public static void main(String[] args) {
        Frame frame = new Frame("Simple JOGL Application");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Relief());
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
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();
    }
    
        public void cube(GL gl, float x, float y, float z)
    {    
        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_LINE);
        gl.glBegin(GL.GL_QUADS);
        
        gl.glVertex3f(1 + x, 0 + y, 1 + z);
        gl.glVertex3f(1 + x, 0 + y, 0 + z);
        gl.glVertex3f(0 + x, 0 + y, 0 + z);
        gl.glVertex3f(0 + x, 0 + y, 1 + z);

        gl.glVertex3f(0 + x, 0 + y, 1 + z);
        gl.glVertex3f(0 + x, 0 + y, 0 + z);
        gl.glVertex3f(0 + x, 1 + y, 0 + z);
        gl.glVertex3f(0 + x, 1 + y, 1 + z);

        gl.glVertex3f(0 + x, 0 + y, 0 + z);
        gl.glVertex3f(1 + x, 0 + y, 0 + z);
        gl.glVertex3f(1 + x, 1 + y, 0 + z);
        gl.glVertex3f(0 + x, 1 + y, 0 + z);

        gl.glVertex3f(0 + x, 0 + y, 1 + z);
        gl.glVertex3f(1 + x, 0 + y, 1 + z);
        gl.glVertex3f(1 + x, 1 + y, 1 + z);
        gl.glVertex3f(0 + x, 1 + y, 1 + z);   

        gl.glVertex3f(1 + x, 0 + y, 0 + z);
        gl.glVertex3f(1 + x, 1 + y, 0 + z);
        gl.glVertex3f(1 + x, 1 + y, 1 + z);
        gl.glVertex3f(1 + x, 0 + y, 1 + z);

        gl.glVertex3f(1 + x, 1 + y, 1 + z);
        gl.glVertex3f(1 + x, 1 + y, 0 + z);
        gl.glVertex3f(0 + x, 1 + y, 0 + z);
        gl.glVertex3f(0 + x, 1 + y, 1 + z); 

        gl.glEnd();
    }
    
        

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glLoadIdentity();

        gl.glScaled(0.04d, 0.04d, 0.04d);
        gl.glRotatef(-135, 1, 0, 0);
        gl.glRotatef(45, 0, 0, 1);
        
        double c1,c2,c3,a; //цвета
        c1=0.5;c2=0.2;c3=0.05;
     //   c1=0;c2=0;c3=0;
        int scale,x,y,z;
        scale=15;
        int [][][] mas = new int[scale][scale][scale]; //объ€вление трехмерного массива   
        
        for (z=0;z<scale;z++)
        {
            //код про цвета
            gl.glColor3d(c1,c2,c3);
            c1=c1+0.1;
            c2=c2+0.1;
            c3=c3+0.1;
            for(y=0;y<scale;y++)
            {
                for(x=0;x<scale;x++)
                {
                    a = Math.random();
                    if(z>=1){
                        if ((mas[x][y][z-1]==1)&&(a>0.3)){
                            mas[x][y][z]=1;
                        } else {
                            mas[x][y][z]=0;
                        }
                    } else
                    {
                        mas[x][y][z]=1;   
                    }
                    if(mas[x][y][z]>0)
                    {
                        cube(gl, x, y, z);
                    }  
                }
            }
        }
        
        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}

