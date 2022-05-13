package org.yourorghere;

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
 * Koordinati.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class Kist implements GLEventListener {

    public static void main(String[] args) {
        Frame frame = new Frame("Simple JOGL Application");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Kist());
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

       // gl.glClearColor(0.0f,0.0f,0.0f,0.0f);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glEnable(GL.GL_DEPTH_TEST);
        
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();
        
        gl.glRotated(-135,1,0,0);
        gl.glRotated(80, 0, 0, 1);
        
        int n=36,m;
        double R=0.1,r=0.08,alpha,beta,da,db,x,y,z;
        
        gl.glBegin(GL.GL_POLYGON);
            for(int i=0;i<n;i++)
            {
                alpha=i*2*Math.PI/n;
                x=R*Math.cos(alpha);
                y=R*Math.sin(alpha);
                gl.glVertex3d(x,y,0.6);
            }
        gl.glEnd();
       
       //цилиндр под кисточкой
       n=32;
       gl.glBegin(GL.GL_QUAD_STRIP);

       for(int i=0;i<=n;i++)
       {
           alpha=i*2*Math.PI/n;
           gl.glColor3d(Math.cos(alpha)+0.2,
                Math.cos(alpha)+0.2,
                Math.cos(alpha));

          x=R*Math.cos(alpha);
          y=R*Math.sin(alpha);
          gl.glVertex3d(x,y,0.4);

          x=R*Math.cos(alpha);
          y=R*Math.sin(alpha);
          gl.glVertex3d(x,y,0.6);
       }
        gl.glEnd(); 
        
       //усеченный конус 
       n=32;
       gl.glBegin(GL.GL_QUAD_STRIP);
       
           for(int i=0;i<=n;i++)
           {
               alpha=i*2*Math.PI/n;
               gl.glColor3d(Math.cos(alpha)+0.2,
                    Math.cos(alpha)+0.2,
                    Math.cos(alpha));

              x=r*Math.cos(alpha);
              y=r*Math.sin(alpha);
              gl.glVertex3d(x,y,0.02);

              x=R*Math.cos(alpha);
              y=R*Math.sin(alpha);
              gl.glVertex3d(x,y,0.4);
           }
        gl.glEnd();
        
       //усеченный конус между
       n=32;
       R=0.09;
       
       gl.glBegin(GL.GL_QUAD_STRIP);
       
           for(int i=0;i<=n;i++)
           {
               alpha=i*2*Math.PI/n;
               gl.glColor3d(Math.cos(alpha)+0.5,
                    Math.cos(alpha)+0.5,
                    Math.cos(alpha)+0.5);

              x=r*Math.cos(alpha);
              y=r*Math.sin(alpha);
              gl.glVertex3d(x,y,0.02);

              x=R*Math.cos(alpha);
              y=R*Math.sin(alpha);
              gl.glVertex3d(x,y,0);
           }
        gl.glEnd(); 
        
        
      //усеченный конус нижний
       n=32;
       r=0.07;
       R=0.09;
       gl.glBegin(GL.GL_QUAD_STRIP);
       
           for(int i=0;i<=n;i++)
           {
               alpha=i*2*Math.PI/n;
               gl.glColor3d(Math.cos(alpha)+0.5,
                    Math.cos(alpha)+0.5,
                    Math.cos(alpha)+0.5);

              x=r*Math.cos(alpha);
              y=r*Math.sin(alpha);
              gl.glVertex3d(x,y,-0.7);

              x=R*Math.cos(alpha);
              y=R*Math.sin(alpha);
              gl.glVertex3d(x,y,0);
           }
        gl.glEnd();
       
        //нижняя поверхность
         gl.glBegin(GL.GL_POLYGON);
            for(int i=0;i<n;i++)
            {
                alpha=i*2*Math.PI/n;
                gl.glColor3d(Math.cos(alpha)+0.5,
                    Math.cos(alpha)+0.5,
                    Math.cos(alpha)+0.5);
                x=r*Math.cos(alpha);
                y=r*Math.sin(alpha);
                gl.glVertex3d(x,y,-0.7);
            }
        gl.glEnd();
        
        //волоски
        alpha=0; 
        r=0.005;
        R=0.1;
        n=100;
        da=2*Math.PI/n;
        gl.glColor3d(0.6,0.3,0);   
        while(r<=R)
            {   
             while(alpha<=2*Math.PI+da){
                 gl.glBegin(GL.GL_LINE_STRIP);
                     x = r*Math.cos(alpha);
                     y = r*Math.sin(alpha);    
                     gl.glColor3d(0.7,0.4,0);
                     gl.glVertex3d(x*1.1,y*1.1,0.6);
                     gl.glVertex3d(x*1.2,y*1.2,0.63);
                     gl.glColor3d(0.6,0.3,0);
                     gl.glVertex3d(x*1.3,y*1.3,0.66);
                     gl.glVertex3d(x*1.4,y*1.4,0.7);
                     gl.glColor3d(0.4,0.25,0);
                     gl.glVertex3d(x*1.5,y*1.5,0.75);
              gl.glEnd();
             alpha=alpha+da;
             }
         alpha=0;
         da=da/1.1;
         r=r+0.002;
        }
        
                
//        //нижний шар
//        m=18;
//        beta=-Math.PI/2;
//        db=Math.PI/m;
//        da=2*Math.PI/n;
//        
//        //gl.glColor3f(0.5,0.5,0.5);
//        while(beta<=Math.PI/2)
//        {
//           alpha=0;
//           gl.glBegin(GL.GL_QUAD_STRIP);
//               while(alpha<=2*Math.PI+da)
//                   {
//                      x=r*Math.cos(beta)*Math.cos(alpha);
//                      y=r*Math.cos(beta)*Math.sin(alpha);
//                      z=r*Math.sin(beta);
//                      gl.glVertex3d(x,y,z-0.7);
//                      
//                      x=r*Math.cos(beta+db)*Math.cos(alpha);
//                      y=r*Math.cos(beta+db)*Math.sin(alpha);
//                      z=r*Math.sin(beta+db);
//                      gl.glColor3d(Math.abs(Math.cos(alpha)*2),
//                            Math.abs(Math.cos(alpha)*2),
//                            Math.abs(Math.cos(alpha))*2);
//                      gl.glVertex3d(x,y,z-0.7);
//                      alpha=alpha+da;
//                   }
//            gl.glEnd();
//            beta=beta+db;
//        }
        
        
        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}

