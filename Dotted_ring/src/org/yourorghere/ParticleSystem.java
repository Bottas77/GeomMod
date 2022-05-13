/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.yourorghere;

import javax.media.opengl.GL; //��� ������ ������ draw

/**
 *
 * @author kondo
 */
public class ParticleSystem { //�����, �������������� ������� ������
    int n;
    Particle particles[]; //������ ������ (���������� �� �������)
    
    public ParticleSystem(int n) //���������� ������� ����� ������
    {
        this.n=n; //���� ������ ��������� ����� this
        particles = new Particle[n];  //��������� ������� ������, �������� ������ ��� ������� ��������� �� �������
        
        for(int i=0;i<n;i++)
        {
            particles[i]=new Particle(); //����� ������������ , ��� ������ ������� �������� ������
        }
    }
    
    void Work(GL gl) //��������� ������ ������
    {
        for(int i=0;i<n;i++)
        {
            particles[i].Move();
            particles[i].Draw(gl);
        }
    }
    
}
