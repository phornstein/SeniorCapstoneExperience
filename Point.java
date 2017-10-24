/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sce;



/**
 *
 * @author Parker
 */
public class Point{
    
    int x1;
    int y1;
    
    public Point(int x, int y){
        x1 = x;
        y1 = y;
    }
    
    public int getX(){
        return x1;
    }
    public int getY(){
        return y1;
    }
    public void setX(int x){
        x1 = x;
    }
    public void setY(int y){
        y1 = y;
    }
    public Point[] getNeighbors(){
        Point tL,tC,tR,cL,cR,bL,bC,bR;
        int x = this.getX();
        int y = this.getY();
        
        tL = new Point(x-1,y+1);
        tC = new Point(x, y+1);
        tR = new Point(x+1, y+1);
        cL = new Point(x-1,y);
        cR = new Point(x+1,y);
        bL = new Point(x-1,y-1);
        bC = new Point(x, y-1);
        bR = new Point(x+1,y-1);
        
        Point neigh[] = {tL,tC,tR,cL,cR,bL,bC,bR};
        return neigh;
    }
}
