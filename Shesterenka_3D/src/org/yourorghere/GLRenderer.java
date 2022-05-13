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
 * GLRenderer.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class GLRenderer implements GLEventListener {
   
    GLU glu = new GLU();
    static int w = 800, h = 800;

    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());
    }
     
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        w = width;
        h = height;
    }

    public void display(GLAutoDrawable drawable) {
         GL gl = drawable.getGL();
        
        double thickness = 0.5, R = 0.7, iR = 0.4, tR = 0.85, alpha, da, x, y;
        double maxX = 0, minX = 0, maxY = 0, minY = 0;
        int n=15; //кол-во зубчиков
        da = 2*Math.PI/(n*4);
        double aOffset = da*0.5;
        alpha = 0;
        
        //вид сверху
        gl.glViewport(0, 0, w/2, h/2);
        gl.glScissor(0, 0, w/2, h/2);
        gl.glEnable(GL.GL_SCISSOR_TEST);
        gl.glDisable(GL.GL_LINE_STIPPLE);
        gl.glClearColor(1,1,1,0); 
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        
        gl.glLoadIdentity();
        
        gl.glColor3d(0, 0, 0);
        gl.glLineWidth(3);

        //3-ий вид
        gl.glBegin(GL.GL_LINE_STRIP);
             while(alpha<Math.PI*2+da) {
                for(int i = 0; i < 4; i++) {
                    double radius = R;
                    if (i > 1)
                        radius = tR;
                    x = radius*Math.cos(alpha+aOffset);
                    y = radius*Math.sin(alpha+aOffset);
                    maxX = Math.max(maxX, x);
                    minX = Math.min(minX, x);
                    maxY = Math.max(maxY, x);
                    minY = Math.min(minY, x);
                    gl.glVertex2d(x, y);
                    alpha += da;
                }
            }
        gl.glEnd();
        
        
        //круг
        alpha = 0;
        gl.glBegin(GL.GL_LINE_STRIP);
             while(alpha<Math.PI*2+da) {
                x = iR*Math.cos(alpha);
                y = iR*Math.sin(alpha);
                gl.glVertex2d(x, y);
                alpha += da;
            }
        gl.glEnd();
        
        //линии
        gl.glEnable(GL.GL_LINE_STIPPLE);
        gl.glColor3d(1, 0.5, 0);
        gl.glLineWidth(1);
        gl.glLineStipple(3, (short)0xFF2F);
        gl.glBegin(GL.GL_LINES);
             gl.glVertex2d(0.95, 0);
             gl.glVertex2d(-0.95, 0);
             gl.glVertex2d(0, 0.95);
             gl.glVertex2d(0, -0.95);
        gl.glEnd();

        //фронтальный вид
        gl.glViewport(0, h/2, w/2, h/2);
        gl.glScissor(0, h/2, w/2, h/2);
        gl.glEnable(GL.GL_SCISSOR_TEST);
        gl.glClearColor(1,1,1,0);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        
        gl.glLoadIdentity();
        
        gl.glColor3d(0, 0, 0);
        gl.glLineWidth(3);
        
        gl.glLineStipple(2, (short)0xF00F);
        
        alpha = 0;
        gl.glBegin(GL.GL_LINES);
            while(alpha<Math.PI*2+da) {
               for(int i = 0; i < 4; i++) {
                   double radius = R;
                   if (i > 1)
                       radius = tR;
                   x = radius*Math.cos(alpha+aOffset);
                   y = radius*Math.sin(alpha+aOffset);
                   if(y >= 0) {
                       gl.glVertex2d(x, thickness/2);
                       gl.glVertex2d(x, -thickness/2);
                   }
                   alpha += da;
               }
           }
        gl.glEnd();
        
        gl.glBegin(GL.GL_LINES);
           gl.glVertex2d(-iR, thickness/2);
           gl.glVertex2d(-iR, -thickness/2);
           gl.glVertex2d(iR, thickness/2);
           gl.glVertex2d(iR, -thickness/2);
        gl.glEnd();
        
        gl.glDisable(GL.GL_LINE_STIPPLE);
        
        alpha = 0;
        gl.glBegin(GL.GL_LINES);
            while(alpha<Math.PI*2+da) {
               for(int i = 0; i < 4; i++) {
                   double radius = R;
                   if (i > 1)
                       radius = tR;
                   x = radius*Math.cos(alpha+aOffset);
                   y = radius*Math.sin(alpha+aOffset);
                   if(y < 0) {
                       gl.glVertex2d(x, thickness/2);
                       gl.glVertex2d(x, -thickness/2);
                   }
                   alpha += da;
               }
           }
        gl.glEnd();
        
        gl.glBegin(GL.GL_LINES);
           gl.glVertex2d(minX, thickness/2);
           gl.glVertex2d(maxX, thickness/2);
           gl.glVertex2d(minX, -thickness/2);
           gl.glVertex2d(maxX, -thickness/2);
        gl.glEnd();
        
        gl.glEnable(GL.GL_LINE_STIPPLE);
        gl.glColor3d(1, 0.5, 0);
        gl.glLineWidth(1);
        gl.glLineStipple(3, (short)0xFF0F);
        gl.glBegin(GL.GL_LINES);
             gl.glVertex2d(0, 0.5);
             gl.glVertex2d(0, -0.5);
        gl.glEnd();
        
        //боковой вид
        gl.glViewport(w/2, h/2, w/2, h/2);
        gl.glScissor(w/2, h/2, w/2, h/2);
        gl.glEnable(GL.GL_SCISSOR_TEST);
        gl.glClearColor(1,1,1,0);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        
        gl.glColor3d(0, 0, 0);
        gl.glLineWidth(3);
        
        gl.glLineStipple(1, (short)0xFFF0);
        
        alpha = 0;
        gl.glBegin(GL.GL_LINES);
            while(alpha<Math.PI*2+da) {
               for(int i = 0; i < 4; i++) {
                   double radius = R;
                   if (i > 1)
                       radius = tR;
                   x = radius*Math.cos(alpha+aOffset);
                   y = radius*Math.sin(alpha+aOffset);
                   if(x >= 0) {
                       gl.glVertex2d(-y, thickness/2);
                       gl.glVertex2d(-y, -thickness/2);
                   }
                   alpha += da;
               }
           }
        gl.glEnd();
        
        gl.glBegin(GL.GL_LINES);
           gl.glVertex2d(-iR, thickness/2);
           gl.glVertex2d(-iR, -thickness/2);
           gl.glVertex2d(iR, thickness/2);
           gl.glVertex2d(iR, -thickness/2);
        gl.glEnd();
        
        gl.glDisable(GL.GL_LINE_STIPPLE);
        
        alpha = 0;
        gl.glBegin(GL.GL_LINES);
            while(alpha<Math.PI*2+da) {
               for(int i = 0; i < 4; i++) {
                   double radius = R;
                   if (i > 1)
                       radius = tR;
                   x = radius*Math.cos(alpha+aOffset);
                   y = radius*Math.sin(alpha+aOffset);
                   if(x < 0) {
                       gl.glVertex2d(-y, thickness/2);
                       gl.glVertex2d(-y, -thickness/2);
                   }
                   alpha += da;
               }
           }
        gl.glEnd();
        
        gl.glBegin(GL.GL_LINES);
           gl.glVertex2d(minY, thickness/2);
           gl.glVertex2d(maxY, thickness/2);
           gl.glVertex2d(minY, -thickness/2);
           gl.glVertex2d(maxY, -thickness/2);
        gl.glEnd();
        
        gl.glEnable(GL.GL_LINE_STIPPLE);
        gl.glColor3d(1, 0.5, 0);
        gl.glLineWidth(1);
        gl.glLineStipple(3, (short)0xFF0F);
        gl.glBegin(GL.GL_LINES);
             gl.glVertex2d(0, 0.5);
             gl.glVertex2d(0, -0.5);
        gl.glEnd(); 
        
        //3D-вид
        gl.glViewport(w/2, 0, w/2, h/2);
        gl.glScissor(w/2, 0, w/2, h/2);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glEnable(GL.GL_SCISSOR_TEST);
        gl.glClearColor(1,1,1,0);
        gl.glEnable(GL.GL_DEPTH_TEST);
        
        gl.glLoadIdentity();
        
        gl.glPolygonMode(GL.GL_FRONT, n);

        gl.glRotated(-135, 1, 0, 0);
        gl.glRotated(35, 0, 0, 1);
        
        double xi, yi;
        alpha = 0;
        gl.glBegin(GL.GL_QUAD_STRIP);
             while(alpha<Math.PI*2) {
                for(int i = 0; i < 4; i++) {
                    double radius = R;
                    if (i > 1)
                        radius = tR;
                    x = radius*Math.cos(alpha+aOffset);
                    y = radius*Math.sin(alpha+aOffset);
                    gl.glColor3d((Math.abs(Math.cos(alpha)))*0.95, (Math.abs(Math.cos(alpha)))*0.95, (Math.abs(Math.cos(alpha)))*0.95); 
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
             while(alpha<Math.PI*2) {
                for(int i = 0; i < 4; i++) {
                    double radius = R;
                    if (i > 1)
                        radius = tR;
                    x = radius*Math.cos(alpha+aOffset);
                    y = radius*Math.sin(alpha+aOffset);
                    if((alpha>Math.PI/4)&&(alpha<3*Math.PI/4)|(alpha>5*Math.PI/4)&&(alpha<7*Math.PI/4)){
                        gl.glColor3d((Math.abs(Math.cos(alpha)))+0.3, (Math.abs(Math.cos(alpha)))+0.3, (Math.abs(Math.cos(alpha)))+0.3);}
                    else
                    {
                        gl.glColor3d((Math.abs(Math.cos(alpha)))*0.95, (Math.abs(Math.cos(alpha)))*0.95, (Math.abs(Math.cos(alpha)))*0.95);  }
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
             while(alpha<Math.PI*2) {
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
                        switch (i) {
                            case  (0):
                                //меж зубцов
                                    //gl.glColor3d(Math.cos(alpha)+0.05, Math.cos(alpha)+0.05, Math.cos(alpha)+0.05);
                                 gl.glColor3d(Math.cos(alpha), Math.cos(alpha), Math.cos(alpha));
                                break;
                            case (1):
                                //зубец справа
                                    gl.glColor3d(Math.cos(alpha-4*da), Math.cos(alpha-4*da), Math.cos(alpha-4*da));
                                break;
                            case (2):
                                //зубец спереди
                                    gl.glColor3d(Math.cos(alpha), Math.cos(alpha), Math.cos(alpha));
                                break;
                            case (3):
                                //зубец слева
                                    gl.glColor3d(Math.cos(alpha+4*da), Math.cos(alpha+4*da), Math.cos(alpha+4*da));
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
             while(alpha<Math.PI*2) {
                double radius = iR;
                x = radius*Math.cos(alpha+aOffset);
                y = radius*Math.sin(alpha+aOffset);
                gl.glColor3d((Math.abs(Math.cos(alpha+3.14)))*0.95, (Math.abs(Math.cos(alpha+3.14)))*0.95, (Math.abs(Math.cos(alpha+3.14)))*0.95);  
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