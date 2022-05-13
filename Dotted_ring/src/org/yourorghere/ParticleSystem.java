/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.yourorghere;

import javax.media.opengl.GL; //для работы метода draw

/**
 *
 * @author kondo
 */
public class ParticleSystem { //класс, представляющий систему частиц
    int n;
    Particle particles[]; //массив частиц (указателей на объекты)
    
    public ParticleSystem(int n) //определяем сколько будет частиц
    {
        this.n=n; //поля класса указываем через this
        particles = new Particle[n];  //указываем сколько частиц, выделяем память для каждого указателя на частицу
        
        for(int i=0;i<n;i++)
        {
            particles[i]=new Particle(); //вызов конструктора , для каждой частицы выделяем память
        }
    }
    
    void Work(GL gl) //описываем работу частиц
    {
        for(int i=0;i<n;i++)
        {
            particles[i].Move();
            particles[i].Draw(gl);
        }
    }
    
}
