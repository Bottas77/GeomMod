/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package org.yourorghere; //описывает принадлежность класса к отпределенному пакету (группировка однотипных классов)

import javax.media.opengl.GL; //для работы метода draw

/**
 *
 * @author kondo
 */
public class Particle { //описание частицы
    double x,y,z;
    double dx,dy,dz;
    int size;
    
    Particle() //конструктор
    {
        Init();
    }
    
    void Init(){ //нужен для того, чтобы неоднократно вызывать конструкцию
        x=Math.random()*2-1;
        y=Math.random()*2-1;
        z=Math.random()*2-1;
        
        dx=0; //множитель - ширина диапозона, а выч итаем значение в 2 раза меньше, чтобы скорость и в плюс, и в минус уходила
        dy=0;
        dz=-0.04 ;
        
        size = (int)(Math.random()*10+1);
    }
    
    void Move()//движение// то, что происходит в каждый момент времени
    {
        x=x+dx;
        y=y+dy;
        z=z+dz;
        
        if (z<-1)
        {
            z=1;
            
        }
    }
    
    void Draw(GL gl)//отрисовка  
    {
        gl.glPointSize(size);
        gl.glBegin(GL.GL_LINES);
            gl.glVertex3d(x-2*dx,y-2*dy,z-2*dz);
            gl.glVertex3d(x+2*dx,y+2*dy,z+2*dz);
        gl.glEnd();
    }
}
    