/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sce;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
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
    public Boolean isCorrect(Point p, int[][] arr){
        if(p.getX() < 1 || p.getX() > arr.length - 1
            || p.getY() < 1 || p.getY() > arr[0].length - 1)
            return false;
        else
            return true;
    }

    
    public ArrayList leastCostPath(Point source, Point destination, BufferedImage img){
      //////////////////////Prepare Image For Processing////////////////////////////////////////////////////////////  
        //Classify all Pixels         
        int weights[][] = new int[img.getWidth()][img.getHeight()]; //create 2d array to run least cost path over
        ArrayList<Integer> unPix = new ArrayList<>(); //store unique pixel values 
        int p, total; //initialize pixel values
        //Find all unique pixels
        Raster imgRaster = img.getData();
        for(int x = 0; x <img.getWidth(); x++){
            for(int y = 0; y <img.getHeight(); y++){
                p = imgRaster.getSample(x,y,0);
                total = p;
                weights[x][y] = total; //add totaled rgb into search array
                
                if(unPix.contains(total) == false) //add unique values to list for classification
                    unPix.add(total);
            }
        }
        Collections.sort(unPix); //sort values for classification
        //Collections.reverse(unPix);//reverse values unPix to give greatest to least as larger numbers are whiter
        
        HashMap<Integer, Integer> classified = new HashMap<>();//create hashmap to store classified values
        for(int i = 0; i < unPix.size(); i++)  //classify values from smallest to largest as 1 --> unPix.size
            classified.put(unPix.get(i), i+1);
        
        int classValue; 
        for(int x = 0; x < img.getWidth(); x++){ //change values in weights from total of all 3 pixels
            for(int y = 0; y < img.getHeight(); y++){ //to their classified values stored in HashMap Classified
                classValue = classified.get(weights[x][y]);
                weights[x][y] = classValue;
            }
        }
    //////////////////////Begin Processing////////////////////////////////////////////////////////////////////////////
        
        ArrayList<Point> predecessor = new ArrayList<>();
        ArrayList<Point> explored = new ArrayList<>();
        ArrayList<Integer> bestCost = new ArrayList<>();
        Point temp[];
        Point m, bestNeigh = null;
        int minDist = 0;
        
        
        Queue<Point> active = new LinkedList<>();
        active.add(source);
        
        while(explored.contains(destination) == false){
            m = active.remove(); //get pixel to test
            temp = m.getNeighbors(); //return all 8 neighbors of the active pixel
            minDist = 10000; //set arbitrarily high
            for(int j = 0; j < temp.length; j++){
                //if(temp[j] == destination){ //if we've reached the destination point, break
                //    break;
                //}
                if(isCorrect(temp[j], weights) == true){ //test pixel is in image
                        if(weights[temp[j].getX()][temp[j].getY()] < minDist){ //if pixel being test has smaller weight than current best
                        minDist = weights[temp[j].getX()][temp[j].getY()]; //best weight is current pixel
                        bestNeigh = temp[j]; //record best neighbor of pixel m
                    }
                    explored.add(bestNeigh); //add all explored pixels to explored
                }
            }
            predecessor.add(m); //store m outside for loop
            bestCost.add(minDist); //store best cost from all neighboring pixels of m
            active.add(bestNeigh); //add best neighbor to queue for exploration
        }
           return predecessor;
    }   
}
