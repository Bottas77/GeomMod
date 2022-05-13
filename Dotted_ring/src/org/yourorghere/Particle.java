/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package org.yourorghere; //��������� �������������� ������ � �������������� ������ (����������� ���������� �������)

import javax.media.opengl.GL; //��� ������ ������ draw

/**
 *
 * @author kondo
 */
public class Particle { //�������� �������
    double x,y,z;
    double dx,dy,dz;
    int size;
    
    Particle() //�����������
    {
        Init();
    }
    
    void Init(){ //����� ��� ����, ����� ������������ �������� �����������
        x=Math.random()*2-1;
        y=Math.random()*2-1;
        z=Math.random()*2-1;
        
        dx=0; //��������� - ������ ���������, � ��� ����� �������� � 2 ���� ������, ����� �������� � � ����, � � ����� �������
        dy=0;
        dz=-0.04 ;
        
        size = (int)(Math.random()*10+1);
    }
    
    void Move()//��������// ��, ��� ���������� � ������ ������ �������
    {
        x=x+dx;
        y=y+dy;
        z=z+dz;
        
        if (z<-1)
        {
            z=1;
            
        }
    }
    
    void Draw(GL gl)//���������  
    {
        gl.glPointSize(size);
        gl.glBegin(GL.GL_LINES);
            gl.glVertex3d(x-2*dx,y-2*dy,z-2*dz);
            gl.glVertex3d(x+2*dx,y+2*dy,z+2*dz);
        gl.glEnd();
    }
}
    