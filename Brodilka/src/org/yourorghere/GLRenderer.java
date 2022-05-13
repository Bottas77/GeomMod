package org.yourorghere;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.math.*;
import java.nio.ByteBuffer;
import java.io.File;
import java.awt.image.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.util.*;


/**
 * GLRenderer.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class GLRenderer implements GLEventListener {
    double ex,ey,ez,px,py,pz,rad,rot,speed,lastx, lasty, lastz; //нужны для вращения глаза наблюдателя
    
    GLU glu = new GLU();
    
    public void Texture (GL gl, int n)
    {
        File f = new File(n + ".jpg");
        try {
            BufferedImage img  = ImageIO.read(f);
            Raster r = img.getData();//чтение растра из изображения
            
            byte tex[];
            int w = r.getWidth(), h = r.getHeight();
            tex = new byte[w * h * 3];
            
            double pixel[] = new double[3];
            
            int count = 0;
            for (int i = 0; i <= w - 1; i++)
            {
                for (int j = 0; j <= h - 1; j++)
                {
                    r.getPixel(i, j, pixel);
                    for (int k = 0; k <=2; k++)
                    {
                        tex[count] = (byte)pixel[k];
                        count++;
                    }
                }
            }
            
            ByteBuffer btex  = ByteBuffer.wrap(tex);
            gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGB, w, h, 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, btex);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);    
            gl.glEnable(GL.GL_TEXTURE_2D);          
        } catch (IOException ex) {
            Logger.getLogger(GLRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void aTexture (GL gl, int n)
    {
        File f = new File(n + ".jpg");
        try {
            BufferedImage img  = ImageIO.read(f);
            Raster r = img.getData();
            byte tex[];
            int w = 256;
            int h = 256;
            tex = new byte[w * h * 4];
            
            double pixel[] = new double[3];
            
            int c = 0;
            for (int i = 0; i <= w - 1; i++)
            {
                for (int j = 0; j <= h - 1; j++)
                {
                    r.getPixel(i, j, pixel);
                    
                    tex[c] = (byte)(pixel[0]);//r
                    c++;
                    tex[c] = (byte)(pixel[1]);//g
                    c++;
                    tex[c] = (byte)(pixel[2]);//b
                    c++;
                    if ((pixel[0] > 180) && (pixel[1] > 180) && (pixel[2] > 180))//близкий к белому, поэтому делаем прозрачным
                    {
                        tex[c] = 0;
                    }
                    else
                    {
                        tex[c] = (byte)255; //непрозрачный
                    }
                    c++;
                }
            }
            
            ByteBuffer btex  = ByteBuffer.wrap(tex);
            gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, w, h, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, btex);
                     
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        
            gl.glEnable(GL.GL_TEXTURE_2D);
            gl.glAlphaFunc(GL.GL_GREATER, 0.5f);
            gl.glEnable(GL.GL_ALPHA_TEST);//обработка альфа-прозрачности         
        } catch (IOException ex) {
            Logger.getLogger(GLRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void init(GLAutoDrawable drawable) { 
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        ex=1.5; //координаты глаза наблюдателя
        ey=-1.5;
        ez=0.5;
        rad=6.5; //расстояние от глаза наблюдателя до точки зрения
        speed=0.1;
        rot=90; //курсовой угол
        px = ex + rad*Math.cos(rot); //координаты того, куда мы смотрим
        py = ey + rad*Math.sin(rot);
        pz = ez - 1;
        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());
        
        //построение кустов
        gl.glNewList(1, GL.GL_COMPILE);   
        aTexture(gl,5);
        double kk=-1.4; 
        for(int i=0;i<3;i++){
            gl.glPushMatrix();
            gl.glTranslated(kk+0.3,-0.3,0);
            gl.glRotated(45, 0, 0, 1);
            gl.glBegin(GL.GL_QUADS);
                gl.glTexCoord2d(0, 0); gl.glVertex3f(0, -0.5f, 0.5f);
                gl.glTexCoord2d(0, 1); gl.glVertex3f(0,  0.5f, 0.5f);
                gl.glTexCoord2d(1, 1); gl.glVertex3f(0,  0.5f, 0);
                gl.glTexCoord2d(1, 0); gl.glVertex3f(0, -0.5f, 0);

                gl.glTexCoord2d(0, 0); gl.glVertex3f(-0.5f, 0, 0.5f);
                gl.glTexCoord2d(0, 1); gl.glVertex3f( 0.5f, 0, 0.5f);
                gl.glTexCoord2d(1, 1); gl.glVertex3f( 0.5f, 0, 0);
                gl.glTexCoord2d(1, 0); gl.glVertex3f(-0.5f, 0, 0);
            gl.glEnd();
            gl.glPopMatrix();
           kk=kk+0.4;
       }
        
        gl.glNewList(3, GL.GL_COMPILE);   
        aTexture(gl,6);
        kk=1.05; 
        for(int i=0;i<3;i++){
            gl.glPushMatrix();         
            gl.glTranslated(kk+0.3,-0.3,0);
            gl.glRotated(45, 0, 0, 1);
            gl.glBegin(GL.GL_QUADS);
                gl.glTexCoord2d(0, 0); gl.glVertex3f(0, -0.5f, 0.5f);
                gl.glTexCoord2d(0, 1); gl.glVertex3f(0,  0.5f, 0.5f);
                gl.glTexCoord2d(1, 1); gl.glVertex3f(0,  0.5f, 0);
                gl.glTexCoord2d(1, 0); gl.glVertex3f(0, -0.5f, 0);

                gl.glTexCoord2d(0, 0); gl.glVertex3f(-0.5f, 0, 0.5f);
                gl.glTexCoord2d(0, 1); gl.glVertex3f( 0.5f, 0, 0.5f);
                gl.glTexCoord2d(1, 1); gl.glVertex3f( 0.5f, 0, 0);
                gl.glTexCoord2d(1, 0); gl.glVertex3f(-0.5f, 0, 0);
            gl.glEnd();
            gl.glPopMatrix();
            kk=kk+0.4;
       }
       
        gl.glDisable(GL.GL_ALPHA_TEST);//rgba для прозрачности
        gl.glDisable(GL.GL_TEXTURE_2D);
        gl.glEndList();

        //импорт
        gl.glNewList(2, GL.GL_COMPILE);
           gl.glTranslated(-1,-1,1);
            try {
                File f = new File ("333.obj");
                Scanner SC  = new Scanner(f);
                String s;
                ArrayList<Double> x1, y1, z1;//динамический массив
                x1 = new ArrayList ();
                y1 = new ArrayList ();
                z1 = new ArrayList ();
               
                String coord[];
                String faces[];
                
                double x2, y2, z2;
                
                while (SC.hasNext())
                {
                    s = SC.nextLine(); //забирает строку из потока из файла
                    //обработка вершин
                    if( (s.charAt(0) == 'v') && (s.charAt(1) == ' '))
                    {
                        coord = s.split(" ");
                        x1.add(Double.parseDouble(coord[1]));
                        y1.add(Double.parseDouble(coord[2]));
                        z1.add(Double.parseDouble(coord[3]));
                    }
                    
                    //обработка граней
                    if (s.charAt(0) == 'f')
                    {
                        faces = s.split(" ");
                        gl.glColor3f(0.7f, 0.3f, 0);
                        gl.glBegin(GL.GL_POLYGON);
                        for (int j = 1; j < faces.length; j++)
                        {
                            x2 = x1.get(Integer.parseInt(faces[j]) - 1); //вычитаем единичку, потому что нумерация в массиве(динамич) идет с нуля
                            y2 = y1.get(Integer.parseInt(faces[j]) - 1);
                            z2 = z1.get(Integer.parseInt(faces[j]) - 1);
                            gl.glVertex3d(x2, z2, y2);
                        }        
                        gl.glEnd();
                        
                        gl.glColor3f(0, 0, 0);
                        gl.glBegin(GL.GL_LINE_LOOP);
                        for (int j = 1; j < faces.length; j++)
                        {
                            x2 = x1.get(Integer.parseInt(faces[j]) - 1);
                            y2 = y1.get(Integer.parseInt(faces[j]) - 1);
                            z2 = z1.get(Integer.parseInt(faces[j]) - 1);
                            gl.glVertex3d(x2, z2, y2);
                        }        
                        gl.glEnd();
                    }
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(GLRenderer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        gl.glEndList();
        
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!
        
            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
    }
    

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        // гравитация
        if (ez > 0.5)
        {
            ez = ez - 0.02f*(10-ez);
            pz = pz - 0.02f*(10-ez);
        }
        gl.glClear( GL.GL_DEPTH_BUFFER_BIT);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glMatrixMode(GL.GL_PROJECTION); //матрица изменения проекции перспективы
        
        gl.glLoadIdentity();
        
        //добавление перспектив, взгляда наблюдателя
        glu.gluPerspective(60, 1, 0.1, 500);
        glu.gluLookAt(ex, ey, ez, px, py, pz, 0, 0, 1);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        
         Texture(gl,4);
        //построение плосткости
        gl.glColor3f(1, 1f, 1);
            gl.glBegin(GL.GL_QUADS); //плоскость от -100 до 100
                gl.glTexCoord2d(0, 0);
                gl.glVertex3f(-100, -100, 0);
                gl.glTexCoord2d(300, 0);
                gl.glVertex3f(-100,  100, 0);
                gl.glTexCoord2d(300, 300);
                gl.glVertex3f( 100,  100, 0);
                gl.glTexCoord2d(0, 300);
                gl.glVertex3f( 100, -100, 0);
            gl.glEnd();
            
         gl.glDisable(GL.GL_TEXTURE_2D);   
            
         double alpha, da, db, beta, x, y, z, R; //строим skybox
            int n = 32;
            alpha = 0;
            R = 100;
            da = (2 * Math.PI/n);
            db = (2 * Math.PI/n);
            beta = 0;

            while (beta <= Math.PI/2)
            {
                alpha = 0;
                gl.glBegin(GL.GL_QUAD_STRIP);
                    while (alpha <= Math.PI * 2 + da)
                    {
                        alpha = alpha + da;
                        gl.glColor3d(0.4, 0.4, Math.cos(beta));
                        x = ex + (R * Math.cos(beta) * Math.sin(alpha));
                        y = ey + (R * Math.cos(beta) * Math.cos(alpha));
                        z = (R * Math.sin(beta));
                        gl.glVertex3d(x, y, z);

                       gl.glColor3d(0.4, 0.4, Math.cos(beta + db));
                        x = ex + (R * Math.cos(beta + db) * Math.sin(alpha));
                        y = ey + (R * Math.cos(beta + db) * Math.cos(alpha));
                        z = (R * Math.sin(beta + db));
                        gl.glVertex3d(x, y, z);
                    }
                gl.glEnd();
                beta = beta + db;
            }
            
            Texture(gl,1); //вызов метода текстуры
             //строим гараж
            gl.glColor3f(1, 1, 1);
            gl.glBegin(GL.GL_QUADS);
               gl.glTexCoord2d(0, 0); gl.glVertex3d( 0, 0, 0);
               gl.glTexCoord2d(2, 0); gl.glVertex3d( 0, 0, 1);
               gl.glTexCoord2d(2, 2);gl.glVertex3d( 1, 0, 1);
               gl.glTexCoord2d(0, 2);gl.glVertex3d(1, 0, 0);
                
               gl.glTexCoord2d(0, 0);gl.glVertex3d( 1, 1, 0);
               gl.glTexCoord2d(2, 0);gl.glVertex3d( 1, 1, 1);
               gl.glTexCoord2d(2, 2); gl.glVertex3d( 0, 1, 1);
               gl.glTexCoord2d(0, 2);gl.glVertex3d( 0, 1, 0);
               
               gl.glTexCoord2d(0, 0);gl.glVertex3d( 1, 0, 0);
               gl.glTexCoord2d(2, 0); gl.glVertex3d( 1, 0, 1);
               gl.glTexCoord2d(2, 2); gl.glVertex3d( 1, 1, 1);
               gl.glTexCoord2d(0, 2);gl.glVertex3d( 1, 1, 0);
               
               gl.glTexCoord2d(0, 0);gl.glVertex3d( 0, 1, 0);
               gl.glTexCoord2d(2, 0);gl.glVertex3d( 0, 1, 1);
               gl.glTexCoord2d(2, 2);gl.glVertex3d( 0, 0, 1);
               gl.glTexCoord2d(0, 2);gl.glVertex3d( 0, 0, 0);
         
            gl.glEnd();
            
            gl.glDisable(GL.GL_TEXTURE_2D);
            
            Texture(gl,2); //вызов метода текстуры
            
             //крыша гаража
            gl.glColor3f(1, 1, 1);
            gl.glBegin(GL.GL_QUADS);
               gl.glTexCoord2d(0, 0);gl.glVertex3d( -0.1, -0.1, 1);
               gl.glTexCoord2d(2, 0);gl.glVertex3d( -0.1, 1.1, 1);
               gl.glTexCoord2d(2, 2);gl.glVertex3d( 1.1, 1.1, 1);
               gl.glTexCoord2d(0, 2); gl.glVertex3d( 1.1, -0.1, 1);
            gl.glEnd();
            
            Texture(gl,3);
            //дверь гаража
            gl.glColor3f(1, 1, 1);
            gl.glBegin(GL.GL_QUADS);
               gl.glTexCoord2d(0, 0);gl.glVertex3d( 0.1, -0.001, 0);
               gl.glTexCoord2d(1, 0); gl.glVertex3d( 0.1, -0.001, 0.8);
               gl.glTexCoord2d(1, 1); gl.glVertex3d( 0.9, -0.001, 0.8);
               gl.glTexCoord2d(0, 1);gl.glVertex3d( 0.9, -0.001, 0);
            gl.glEnd();
            
            gl.glDisable(GL.GL_TEXTURE_2D);
            
            
            gl.glCallList(1);
            gl.glCallList(3);
            gl.glPushMatrix();
            gl.glScaled(0.15,0.15,0.3);
            gl.glTranslated(-2, -5.5, -0.5);
            gl.glCallList(2);
            gl.glPopMatrix();
            
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
    
    public void keyPress(int keycode) //метод, который обрабатывает нажатие клавиш
    {
        lastx = ex;
        lasty = ey;
        lastz = ez;
        switch(keycode)
        {
            case 65: ez = ez + speed; //подъем a
                     pz = pz + speed;
                     break; 
            case 90: ez = ez - speed; //спуск z
                     pz = pz - speed;
                     break; 
            case 38: ex = ex + (speed * Math.cos(rot)); //стрелка вверх
                     ey = ey + (speed * Math.sin(rot)); //шаги вперед
                     break;
            case 40: ex = ex - (speed * Math.cos(rot)); //стрелка вниз
                     ey = ey - (speed * Math.sin(rot)); //шаги назад
                     break;
            case 37: rot = rot + speed; //поворот влево
                     break;
            case 39: rot = rot - speed; //поворот вправо
                     break;
            case 33: pz = pz + speed; //pg up взгляд вверх
                     break;
            case 34: pz = pz - speed; //pg up взгляд вверх
                     break;
        }
        
        if  ((ex>-0.2 && ex<1.2)&&(ey>-0.2 && ey<1.2)) //коллизии для гаража
        {
            ex=lastx;
            ey=lasty;
        }
        
        if  ((ex>-1 && ex<0.7)&&(ey>-1.7 && ey<-0.8)) //коллизии для авто
        {
            ex=lastx;
            ey=lasty;
        }
        
        px = ex + rad * Math.cos((double)rot); //пересчитывает расположение точки зрения (точки, которая находится в центре вывода изображения)
        py = ey + rad * Math.sin((double)rot);
  
    }
}

