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
public class Koordinati implements GLEventListener {

    public static void main(String[] args) {
        Frame frame = new Frame("Simple JOGL Application");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Koordinati());
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
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();
        
        gl.glRotated(120,1,0,0);
        gl.glRotated(35, 0, 0, 1);
        
        double dx,dy,dz;
        dx=-0.9;
        dy=-0.9;
        
        
        gl.glOrtho(-1.5,1.5,-1.5,1.5,-1.5,1.5);
        //сетка
        gl.glBegin(GL.GL_LINES);
            gl.glColor3d(0.5, 0.5, 0.5);
                for(int i = 0; i < 19; i++){
                     gl.glVertex2d(dx,dy);
                     gl.glVertex2d(dx,-dy);
                     dx+=0.1;
                }
         gl.glEnd(); 
         
        dx=-0.9;
        dy=-0.9;
        gl.glBegin(GL.GL_LINES);
            gl.glColor3d(0.5, 0.5, 0.5);
                for(int i = 0; i < 19; i++){
                     gl.glVertex2d(dx,dy);
                     gl.glVertex2d(-dx,dy);
                     dy+=0.1;
                }
         gl.glEnd(); 
               
         //треугольники
         gl.glBegin(GL.GL_TRIANGLES);
         //gl.glLineWidth(0.5f);
            //x
            gl.glColor3f(0.0f, 1.0f, 0.0f);  
            gl.glVertex3d(1,0,0);
            gl.glVertex3d(0.90,0,0.015);
            gl.glVertex3d(0.90,0,-0.015);
         
            //y
            gl.glColor3f(1.0f, 0.0f, 0.0f);  
            gl.glVertex3d(0,1,0);
            gl.glVertex3d(0,0.90,0.015);
            gl.glVertex3d(0,0.90,-0.015);
            
            //z
            gl.glColor3f(0.0f, 0.0f, 1.0f);  
            gl.glVertex3d(0,0,1);
            gl.glVertex3d(0.015,0,0.91);
            gl.glVertex3d(-0.015,0,0.90);
        gl.glEnd(); 
        
        //оси
        gl.glBegin(GL.GL_LINES);
            //х
            gl.glColor3f(0.0f, 1.0f, 0.0f);   
            gl.glVertex3f(-1.0f, 0.0f, 0.0f);
            gl.glVertex3f(1.0f, 0.0f, 0.0f);  
            //y
            gl.glColor3f(1.0f, 0.0f, 0.0f);    
            gl.glVertex3f(0.0f, -1.0f, 0.0f);
            gl.glVertex3f(0.0f, 1.0f, 0.0f);  
            //z
            gl.glColor3f(0.0f, 0.0f, 1.0f);    
            gl.glVertex3f(0.0f, 0.0f, -1.0f);
            gl.glVertex3f(0.0f, 0.0f, 1.0f);  
        gl.glEnd();
        
        gl.glBegin(GL.GL_LINES);
            //х
            dx=-0.9;
            dy=0.02;
            gl.glColor3f(0.0f, 1.0f, 0.0f); 
                for(int i = 0; i <19; i++){
                    if(i!=9){
                        gl.glVertex2d(dx,dy);
                        gl.glVertex2d(dx,-dy);
                    }
                    dx+=0.1;
                }
              
            //y
            dx=0.02;
            dy=-0.9;
            gl.glColor3f(1.0f, 0.0f, 0.0f); 
                for(int i = 0; i <19; i++){
                    if(i!=9){
                         gl.glVertex2d(dx,dy);
                         gl.glVertex2d(-dx,dy);
                    }
                    dy+=0.1;
                }
               
            //z
            gl.glColor3f(0.0f, 0.0f, 1.0f);
            dy=0.02;
            dz=-0.9;
                for(int i = 0; i <19; i++){
                    if(i!=9){
                        gl.glVertex3d(0,dy,dz);
                        gl.glVertex3d(0,-dy,dz);}
                    dz+=0.1;
                }
 
        gl.glEnd();
        
        //буквы
        gl.glBegin(GL.GL_LINES);
         //gl.glLineWidth(0.5f);
            //x
            gl.glColor3f(0.0f, 1.0f, 0.0f);  
           
            gl.glVertex3d(1,0,-0.05);
            gl.glVertex3d(0.95,0,-0.1);
            
            gl.glVertex3d(0.95,0,-0.05);
            gl.glVertex3d(1,0,-0.1);
            
         
            //y
            gl.glColor3f(1.0f, 0.0f, 0.0f);  
            
            gl.glVertex3d(0,1,-0.05);
            gl.glVertex3d(0,0.975,-0.075);
            
            gl.glVertex3d(0,0.95,-0.05);
            gl.glVertex3d(0,1,-0.1);
            
            //z
            gl.glColor3f(0.0f, 0.0f, 1.0f);  
            
            gl.glVertex3d(0.1,0,1);
            gl.glVertex3d(0.2,0,1.032);
            
            gl.glVertex3d(0.2,0,1.032);
            gl.glVertex3d(0.1,0,0.95);
            
            gl.glVertex3d(0.1,0,0.95);
            gl.glVertex3d(0.2,0,0.982);
            
            //1
            gl.glColor3f(1.0f, 1.0f, 1.0f);  
            gl.glVertex3d(0.085,0,-0.03);
            gl.glVertex3d(0.1,0,-0.01);
            
            gl.glVertex3d(0.1,0,-0.01);
            gl.glVertex3d(0.1,0,-0.06);
            
        gl.glEnd(); 
        
        
        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}

