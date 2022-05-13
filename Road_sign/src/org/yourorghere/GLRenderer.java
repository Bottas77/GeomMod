package org.yourorghere;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;


/**
 * GLRenderer.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class GLRenderer implements GLEventListener {

    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT); //◊»—“»Ã ¡”‘≈–
        
         gl.glBegin(GL.GL_QUAD_STRIP); //ÒÚÓÎ·
          gl.glColor3f(0.5f,0.5f,0.6f);
          gl.glVertex2f(-0.02f,0.3f);        
          gl.glVertex2f(0.02f,0.3f);
          gl.glVertex2f(-0.02f,-1.0f);
          gl.glVertex2f(0.02f,-1.0f); 
         gl.glEnd(); 
        
        int n;
        float x,y,R,alpha,da;
        n=32;
        da=(float)(2*Math.PI/n);
        
        
        gl.glPointSize(10.0f);
        R = 0.4f;
        alpha = 0;
        gl.glBegin(GL.GL_POLYGON);
            while (alpha<2*Math.PI+da)
            {
                x=(float)(R*Math.cos(alpha));
                y=(float)(R*Math.sin(alpha));
                gl.glColor3f(1.0f,1.0f,1.0f);
                gl.glVertex2f(x,y+0.5f);
                alpha = alpha + da;
            }      
         gl.glEnd();
      
        gl.glPointSize(10.0f);
        alpha = 0;
        
        R = 0.38f;   
        gl.glBegin(GL.GL_POLYGON);
            while (alpha<2*Math.PI+da)
            {
                x=(float)(R*Math.cos(alpha));
                y=(float)(R*Math.sin(alpha));          
                gl.glColor3f(1.0f,0.0f,0.0f);
                gl.glVertex2f(x,y+0.5f);
                alpha = alpha + da;
            }      
         gl.glEnd(); 
            
  
         gl.glBegin(GL.GL_QUAD_STRIP);
            gl.glColor3f(1.0f,1.0f,1.0f);
            gl.glVertex2f(-0.25f,0.57f);        
            gl.glVertex2f(0.25f,0.57f);
            gl.glVertex2f(-0.25f,0.43f);
            gl.glVertex2f(0.25f,0.43f); 
         gl.glEnd(); 
    
    }
    
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}

