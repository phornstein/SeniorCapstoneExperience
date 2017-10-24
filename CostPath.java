/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sce;

import java.awt.image.BufferedImage;
import java.util.*;
import sce.Point;
import sce.ImagePath;
/**
 *
 * @author Parker
 */
public class CostPath {
    
    public Boolean isCorrect(Point p, BufferedImage img){
        if(p.getX() < 0 || p.getX() > img.getWidth()
            || p.getY() < 0 || p.getY() > img.getHeight())
            return false;
        else
            return true;
    }
    
    public Point relax(int dist[][], int weight[][], Point p, Point v){
        //Point pNew = new Point(0,0)
        if(dist[p.getX()][p.getY()] > 
                dist[v.getX()][v.getY()] + weight[v.getX()][v.getY()]) //if the 'distance' of the point is greater
            dist[v.getX()][v.getY()] = 
                dist[p.getX()][p.getY()] + weight[v.getX()][v.getY()];
        return v;                   
        }

    
    public ArrayList leastCostPath(Point source, Point destination, BufferedImage img){
      //////////////////////Prepare Image For Processing////////////////////////////////////////////////////////////  
        //Classify all Pixels         
        int weights[][] = new int[img.getWidth()][img.getHeight()]; //create 2d array to run least cost path over
        ArrayList<Integer> unPix = new ArrayList<>(); //store unique pixel values 
        int p, r, g, b, total; //initialize pixel values
        //Find all unique pixels
        for(int x = 0; x <img.getWidth(); x++){
            for(int y = 0; y <img.getHeight(); y++){
                p = img.getRGB(x,y);
                r = (p >> 16) & 0xff;
                g = (p >> 8) & 0xff;
                b = p & 0xff;
                total = r + g + b; //total rgb values into one
                
                weights[x][y] = total; //add totaled rgb into search array
                
                if(unPix.contains(total) == false) //add unique values to list for classification
                    unPix.add(total);
            }
        }
        unPix.sort(null); //sort values for classification
        
        HashMap<Integer, Integer> classified = new HashMap<>();//create hashmap to store classified values
        for(int i = 0; i < unPix.size(); i++)  //classify values from smallest to largest as 1 --> unPix.size
            classified.put(unPix.get(i), i+1);
    //////////////////////Begin Processing////////////////////////////////////////////////////////////////////////////
        
        ArrayList<Point> path = new ArrayList<>();
        int infinity = 1000000;
        int dist[][] = new int[img.getWidth()][img.getHeight()]; //initialize array containing 'nodes'
        for(int i = 0; i < img.getWidth(); i++){ //set distances to infinity
            for(int j = 0; j < img.getHeight(); j++){
                dist[i][j] = infinity;
            }
        }
        dist[source.getX()][source.getY()] = 0; //set source to 0
        Point n[] = source.getNeighbors();
        Point current = source;
        
        for(int x = 0; x < img.getWidth(); x++){
            for(int y = 0; y < img.getHeight(); y++){
                for (Point n1 : n) {
                    if (isCorrect(n1, img)) {//test to make sure neighbors are valid points in the image
                        current = relax(dist, weights, current, n1);
                    }
                }
                path.add(current);
            }
        }
           
           return path;
    }   
}
