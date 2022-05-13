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
        gl.glClear(GL.GL_COLOR_BUFFER_BIT); 
        
        int n,time_m,time_ch,m,k;
        float x,y,R,alpha,da,x1,x2,y1,y2;
        n=32;
        da=(float)(2*Math.PI/n);
       
        //внутренний круг
        gl.glPointSize(1.0f);
        R = 0.45f;
        alpha = 0;
        gl.glBegin(GL.GL_POLYGON);
            while (alpha<2*Math.PI+da)
            {
                x=(float)(R*Math.cos(alpha));
                y=(float)(R*Math.sin(alpha));
                gl.glColor3f(1.0f,0.77f,0.41f);
                gl.glVertex2f(x,y);
                alpha = alpha + da;
            }      
        gl.glEnd();
        
        //точки
        gl.glPointSize(7.0f);
        alpha = 0;
        n=720;
        da=(float)(2*Math.PI/n);
        n=0;
        R = 0.40f;   
        gl.glColor3f(0.47f,0.41f,1.0f);
        gl.glBegin(GL.GL_POINTS);
            while (alpha<2*Math.PI+da)
            {   
                if (n%60 == 0){
                    x=(float)(R*Math.cos(alpha));
                    y=(float)(R*Math.sin(alpha));  
                    gl.glVertex2f(x,y); 
                }
                alpha = alpha + da;
                n=n+1;
            }      
         gl.glEnd();          
         
        alpha = (float)(2*Math.PI+da+2*Math.PI/4);
        n=720;
//        da=(float)(2*Math.PI/n);
        //задаем время
        time_ch=20;
        time_m=40;
        //циферблат 12-и часовой, поэтому можно убрать 12 лишних часов (720 n)
        m=time_ch*60+time_m;
        if (m>n) {
           m=m-n;}
        k=time_m*12; //умножаем минуты на 12
        n=0;
        gl.glLineWidth(3.0f);
        gl.glBegin(GL.GL_LINES);
            while (alpha>2*Math.PI/4)
            {   
                if (n==m){ //часы
                    R = 0.25f; 
                    x1=(float)(R*Math.cos(alpha));
                    y1=(float)(R*Math.sin(alpha));          
                    gl.glColor3f(0.47f,0.41f,1.0f);
                    gl.glVertex2f(0,0);
                    gl.glVertex2f(x1,y1); }
                if (n==k) //минуты
                {   
                    R=0.35f;
                    x2=(float)(R*Math.cos(alpha));
                    y2=(float)(R*Math.sin(alpha));          
                    gl.glColor3f(0.47f,0.41f,1.0f);
                    gl.glVertex2f(0,0);
                    gl.glVertex2f(x2,y2);
                }
                alpha = alpha - da;
                n=n+1;
            }      
         gl.glEnd();
         
         gl.glBegin(GL.GL_LINES);
           //12
          gl.glVertex2f(-0.07f, 0.5f);
          gl.glVertex2f(0.01f, 0.65f);
          gl.glVertex2f(0.01f, 0.5f);
          gl.glVertex2f(-0.07f, 0.65f);
          gl.glVertex2f(0.03f, 0.65f);
          gl.glVertex2f(0.03f, 0.5f);
          gl.glVertex2f(0.05f, 0.65f);
          gl.glVertex2f(0.05f, 0.5f);
          
          //6
          gl.glVertex2f(-0.07f, -0.5f);
          gl.glVertex2f(-0.04f, -0.65f);
          gl.glVertex2f(-0.04f, -0.65f);
          gl.glVertex2f(-0.01f, -0.5f);
          gl.glVertex2f(0.03f, -0.65f);
          gl.glVertex2f(0.03f, -0.5f);
          
          //3
          gl.glVertex2f(0.65f, -0.07f);
          gl.glVertex2f(0.65f, 0.08f);
          gl.glVertex2f(0.63f, -0.07f);
          gl.glVertex2f(0.63f, 0.08f);
          gl.glVertex2f(0.61f, -0.07f);
          gl.glVertex2f(0.61f, 0.08f);
          
          //9
          gl.glVertex2f(-0.65f, -0.07f);
          gl.glVertex2f(-0.65f, 0.08f);
          gl.glVertex2f(-0.63f, -0.07f);
          gl.glVertex2f(-0.55f, 0.08f);
          gl.glVertex2f(-0.63f, 0.08f);
          gl.glVertex2f(-0.55f, -0.07f);
        
        gl.glEnd();
        
        //внешний круг
        R =0.75f;
        alpha = 0;
        gl.glBegin(GL.GL_LINE_STRIP);
            while (alpha<2*Math.PI+da)
            {
                x=(float)(R*Math.cos(alpha));
                y=(float)(R*Math.sin(alpha));
                gl.glColor3f(1.0f,0.77f,0.41f);
                gl.glVertex2f(x,y);
                alpha = alpha + da;
            }      
        gl.glEnd();
         
    }
    
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}

