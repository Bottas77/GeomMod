package Shest;

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
 * Shestirenki.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class Shestirenki implements GLEventListener {
    double sk1,sk2;

    public static void main(String[] args) {
        Frame frame = new Frame("Simple JOGL Application");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Shestirenki());
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
        gl.glClearColor(1,1,1,0);
        gl.glEnable(GL.GL_DEPTH_TEST);
        
        gl.glPolygonMode(GL.GL_FRONT, GL.GL_FILL);
  
        sk1 =sk1+2;
        
        double s1 = sk1; //speed
        
        gl.glLoadIdentity();
        gl.glTranslated(-0.65, 0, 0);
        gl.glRotated(30, 1, 0, 0);
        //gl.glRotated(90, 0, 0, 1);
        gl.glRotated(-45, 0, 0, 1);
        gl.glRotated(s1, 0, 0, 1);
        
        double thickness = 0.2, R = 0.25, iR = 0.1, tR = 0.3, alpha, da, x, y,skc1;

        int n=8; //кол-во зубчиков
        da = 2*Math.PI/(n*4);
        double aOffset = da*0.5;
       
        //skc1=0;
        
        //нижн€€ поверхность
        double xi, yi;
        alpha = 0;
        gl.glBegin(GL.GL_QUAD_STRIP);
             while(alpha<=Math.PI*2+da) {
                for(int i = 0; i < 4; i++) {
                    double radius = R;
                    if (i > 1)
                        radius = tR;
                    skc1 = s1/180*Math.PI;
                    x = radius*Math.cos(alpha+aOffset);
                    y = radius*Math.sin(alpha+aOffset);
                    gl.glColor3d((Math.abs(Math.cos(alpha+skc1)))*0.95, (Math.abs(Math.cos(alpha+skc1)))*0.95, (Math.abs(Math.cos(alpha+skc1)))*0.95); 
                    xi = iR*Math.cos(alpha+aOffset);
                    yi = iR*Math.sin(alpha+aOffset);
                    gl.glVertex3d(x, y, -thickness/2);
                    gl.glVertex3d(xi, yi, -thickness/2);
                    alpha += da;
                }
            }
        gl.glEnd();
        
        //верхн€€ поверхность 
        alpha = 0;
        gl.glBegin(GL.GL_QUAD_STRIP);
             while(alpha<=Math.PI*2+da) {
                for(int i = 0; i < 4; i++) {
                    double radius = R;
                    skc1 = s1/360*2*Math.PI;
                    if (i > 1)
                        radius = tR;
                    x = radius*Math.cos(alpha+aOffset);
                    y = radius*Math.sin(alpha+aOffset);
                    if((alpha>Math.PI/4)&&(alpha<3*Math.PI/4)|(alpha>5*Math.PI/4)&&(alpha<7*Math.PI/4)){
                        gl.glColor3d((Math.abs(Math.cos(alpha/2+skc1)))+0.3, (Math.abs(Math.cos(alpha/2+skc1)))+0.3, (Math.abs(Math.cos(alpha/2+skc1)))+0.3);}
                    else
                    {
                        gl.glColor3d((Math.abs(Math.cos(alpha/2+skc1)))*0.95, (Math.abs(Math.cos(alpha/2+skc1)))*0.95, (Math.abs(Math.cos(alpha/2+skc1)))*0.95);  }
                    xi = iR*Math.cos(alpha+aOffset);
                    yi = iR*Math.sin(alpha+aOffset);
                    gl.glVertex3d(x, y, thickness/2);
                    gl.glVertex3d(xi, yi, thickness/2);
                    alpha += da;
                }
            }
        gl.glEnd();
        
        alpha = 0;
       
        gl.glBegin(GL.GL_QUADS);
             while(alpha<=Math.PI*2+da) {
                for(int i = 0; i < 4; i++) {
                    double radius = R;
                    if (i > 1) {
                        radius = tR;
                    }
                    x = radius*Math.cos(alpha+aOffset);
                    y = radius*Math.sin(alpha+aOffset);
                    
                    if(alpha == 0) {
                        gl.glVertex3d(x, y, thickness/2);
                        gl.glVertex3d(x, y, -thickness/2);
                    } else {
                        gl.glVertex3d(x, y, -thickness/2);
                        gl.glVertex3d(x, y, thickness/2);
                        skc1 = s1/360*2*Math.PI;
                        switch (i) {
                            case  (0):
                                //меж зубцов
                                 gl.glColor3d(Math.cos(alpha/2+skc1), Math.cos(alpha/2+skc1), Math.cos(alpha/2+skc1));
                                break;
                            case (1):
                                //зубец справа
                                    gl.glColor3d(Math.cos(alpha/2-4*da+skc1), Math.cos(alpha/2-4*da+skc1), Math.cos(alpha/2-4*da+skc1));
                                break;
                            case (2):
                                //зубец спереди
                                    gl.glColor3d(Math.cos(alpha/2+skc1), Math.cos(alpha/2+skc1), Math.cos(alpha/2+skc1));
                                break;
                            case (3):
                                //зубец слева
                                    gl.glColor3d(Math.cos(alpha/2+4*da+skc1), Math.cos(alpha/2+4*da+skc1), Math.cos(alpha/2+4*da+skc1));
                                break;
                        }
                        gl.glVertex3d(x, y, thickness/2);
                        gl.glVertex3d(x, y, -thickness/2);
                    }

                    alpha += da;
                }
            }
        gl.glEnd();
        
        //цилиндр внутри
        alpha = 0;
        gl.glBegin(GL.GL_QUAD_STRIP);
             while(alpha<Math.PI*2+da) {
                double radius = iR;
                skc1 = s1/360*2*Math.PI;
                x = radius*Math.cos(alpha+aOffset);
                y = radius*Math.sin(alpha+aOffset);
                gl.glColor3d((Math.abs(Math.cos(alpha+3.14+skc1)))*0.95, (Math.abs(Math.cos(alpha+3.14+skc1)))*0.95, (Math.abs(Math.cos(alpha+3.14+skc1)))*0.95);  
                gl.glVertex3d(x, y, thickness/2);
                gl.glVertex3d(x, y, -thickness/2);
                alpha += da;
            }
        gl.glEnd();
        
        
        ///////////////втора€ шестеренка///////////////
        
     
        n=16;
        double s2 = sk2;
        sk2 =sk2+1;
        
        gl.glLoadIdentity();
        gl.glTranslated(0.2, 0, 0);
        gl.glRotated(30, 1, 0, 0);
        gl.glRotated(-45, 0, 0, 1);
        gl.glRotated(-s2, 0, 0, 1);
        double skc2;
        thickness = 0.2; R = 0.55; iR = 0.1; tR = 0.6;
        da = 2*Math.PI/(n*4);
        aOffset = da*0.5;
        
        //верхн€€ поверхность
        alpha = 0;
        gl.glBegin(GL.GL_QUAD_STRIP);
             while(alpha<=Math.PI*2+da) {
                for(int i = 0; i < 4; i++) {
                    double radius = R;
                    if (i > 1)
                        radius = tR;
                    skc2 = s2/180*Math.PI;
                    x = radius*Math.cos(alpha+aOffset);
                    y = radius*Math.sin(alpha+aOffset);
                    gl.glColor3d((Math.abs(Math.cos(alpha-skc2)))*0.95, (Math.abs(Math.cos(alpha-skc2)))*0.95, (Math.abs(Math.cos(alpha-skc2)))*0.95); 
                    xi = iR*Math.cos(alpha+aOffset);
                    yi = iR*Math.sin(alpha+aOffset);
                    gl.glVertex3d(x, y, -thickness/2);
                    gl.glVertex3d(xi, yi, -thickness/2);
                    alpha += da;
                }
            }
        gl.glEnd();
      
        //нижн€€ поверхность 
        alpha = 0;
        gl.glBegin(GL.GL_QUAD_STRIP);
             while(alpha<=Math.PI*2+da) {
                for(int i = 0; i < 4; i++) {
                    double radius = R;
                    skc2 = s2/360*2*Math.PI;
                    if (i > 1)
                        radius = tR;
                    x = radius*Math.cos(alpha+aOffset);
                    y = radius*Math.sin(alpha+aOffset);
                    if((alpha>Math.PI/4)&&(alpha<3*Math.PI/4)|(alpha>5*Math.PI/4)&&(alpha<7*Math.PI/4)){
                        gl.glColor3d((Math.abs(Math.cos(alpha+skc2)))+0.3, (Math.abs(Math.cos(alpha+skc2)))+0.3, (Math.abs(Math.cos(alpha+skc2)))+0.3);}
                    else
                    {
                        gl.glColor3d((Math.abs(Math.cos(alpha+skc2)))*0.95, (Math.abs(Math.cos(alpha+skc2)))*0.95, (Math.abs(Math.cos(alpha+skc2)))*0.95);  }
                    xi = iR*Math.cos(alpha+aOffset);
                    yi = iR*Math.sin(alpha+aOffset);
                    gl.glVertex3d(x, y, thickness/2);
                    gl.glVertex3d(xi, yi, thickness/2);
                    alpha += da;
                }
            }
        gl.glEnd();
        
        //зубцы
        alpha = 0; 
        gl.glBegin(GL.GL_QUADS);
             while(alpha<Math.PI*2+da) {
                for(int i = 0; i < 4; i++) {
                    double radius = R;
                    if (i > 1) {
                        radius = tR;
                    }
                    x = radius*Math.cos(alpha+aOffset);
                    y = radius*Math.sin(alpha+aOffset);
                    
                    if(alpha == 0) {
                        gl.glVertex3d(x, y, thickness/2);
                        gl.glVertex3d(x, y, -thickness/2);
                    } else {
                        gl.glVertex3d(x, y, -thickness/2);
                        gl.glVertex3d(x, y, thickness/2);
                        skc2 = s2/360*2*Math.PI;
                        switch (i) {
                            case  (0):
                                //меж зубцов
                                 gl.glColor3d(Math.cos(alpha-skc2), Math.cos(alpha-skc2), Math.cos(alpha-skc2));
                                break;
                            case (1):
                                //зубец справа
                                    gl.glColor3d(Math.cos(alpha-4*da-skc2), Math.cos(alpha-4*da-skc2), Math.cos(alpha-4*da-skc2));
                                break;
                            case (2):
                                //зубец спереди
                                    gl.glColor3d(Math.cos(alpha-skc2), Math.cos(alpha-skc2), Math.cos(alpha-skc2));
                                break;
                            case (3):
                                //зубец слева
                                    gl.glColor3d(Math.cos(alpha+4*da-skc2), Math.cos(alpha+4*da-skc2), Math.cos(alpha+4*da-skc2));
                                break;
                        }
                        gl.glVertex3d(x, y, thickness/2);
                        gl.glVertex3d(x, y, -thickness/2);
                    }

                    alpha += da;
                }
            }
        gl.glEnd();
        
        //цилиндр внутри
        alpha = 0;
        gl.glBegin(GL.GL_QUAD_STRIP);
             while(alpha<Math.PI*2+da) {
                double radius = iR;
                skc2 = s2/180*Math.PI;
                x = radius*Math.cos(alpha+aOffset);
                y = radius*Math.sin(alpha+aOffset);
                gl.glColor3d((Math.abs(Math.cos(alpha+3.14-skc2)))*0.95, (Math.abs(Math.cos(alpha+3.14-skc2)))*0.95, (Math.abs(Math.cos(alpha+3.14-skc2)))*0.95);  
                gl.glVertex3d(x, y, thickness/2);
                gl.glVertex3d(x, y, -thickness/2);
                alpha += da;
            }
        gl.glEnd();
   
        gl.glDisable(GL.GL_DEPTH_TEST);
        
        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}

