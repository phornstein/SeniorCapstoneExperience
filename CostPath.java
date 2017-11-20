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

    
    public ArrayList leastCostPath(Point src, Point dest, BufferedImage img){
      //////////////////////Prepare Image For Processing////////////////////////////////////////////////////////////  
        //Classify all Pixels         
        int graph[][] = new int[img.getWidth()][img.getHeight()]; //create 2d array to run least cost path over
        ArrayList<Integer> unPix = new ArrayList<>(); //store unique pixel values 
        int p, total; //initialize pixel values
        //Find all unique pixels
        Raster imgRaster = img.getData();
        for(int x = 0; x <img.getWidth(); x++){
            for(int y = 0; y <img.getHeight(); y++){
                p = imgRaster.getSample(x,y,0);
                total = p;
                graph[x][y] = total; //add totaled rgb into search array
                
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
                classValue = classified.get(graph[x][y]);
                graph[x][y] = classValue;
            }
        }
        
        System.out.print("\n.......Pre-Processing Completed............. \n");
      
        if(dest.getX() > src.getX() && src.getX() > 25 && img.getWidth() - dest.getX() > 25){ //lower Y value is higher on the image
            for(int x = 0; x < src.getX() - 25; x++){
                for(int y = 0; y < graph[0].length; y++){
                    graph[x][y] = graph[x][y] +100;
                }
            }
            for(int x = dest.getX() + 25 ; x < img.getWidth(); x++){
                for(int y = 0; y < graph[0].length; y++){
                    graph[x][y] = graph[x][y] +100;
                }
            }
        }
        if(dest.getX() < src.getX() && dest.getX() > 25 && img.getWidth() - src.getX() > 25){ //lower Y value is higher on the image
            for(int x = 0; x < dest.getX() - 25; x++){
                for(int y = 0; y < graph[0].length; y++){
                    graph[x][y] = graph[x][y] +100;
                }
            }
            for(int x = src.getX() + 25 ; x < img.getWidth(); x++){
                for(int y = 0; y < graph[0].length; y++){
                    graph[x][y] = graph[x][y] +100;
                }
            }
        }
        
        if(dest.getY() > src.getY() && src.getY() > 25 && img.getHeight() - dest.getY() > 25){ //lower Y value is higher on the image
            for(int y = 0; y < src.getX() - 25; y++){
                for(int x = 0; x < graph.length; x++){
                    graph[x][y] = graph[x][y] +100;
                }
            }
            for(int y = dest.getY() + 25 ; y < img.getHeight(); y++){
                for(int x = 0; x < graph.length; x++){
                    graph[x][y] = graph[x][y] +100;
                }
            }
        }
        if(dest.getY() < src.getY() && dest.getY() > 25 && img.getHeight() - src.getY() > 25){ //lower Y value is higher on the image
            for(int y = 0; y < dest.getY() - 25; y++){
                for(int x = 0; x < graph.length; x++){
                    graph[x][y] = graph[x][y] +100;
                }
            }
            for(int y = src.getY() + 25 ; y < img.getHeight(); y++){
                for(int x = 0; x < graph.length; x++){
                    graph[x][y] = graph[x][y] +100;
                }
            }
        }

//////////////////////Begin Processing////////////////////////////////////////////////////////////////////////////    
    
    
       ArrayList<Point> explored = new ArrayList();
       Point[][] predecessor = new Point[graph[0].length][graph.length];
       int minDist[][] = new int[graph[0].length][graph.length];
       Point point = null;
       Point bestNeigh = new Point(0,0);
       Point neigh[];
       Point previous = null;
       int minCost = 0;
       int nextCost = 0;
       explored.add(src);
       minDist[src.getX()][src.getY()] = graph[src.getX()][src.getY()];
       
       
       while(dest.isWithin(explored)!= true){
           minCost = Integer.MAX_VALUE;
           for(int i = 0; i <explored.size(); i++){
               point = explored.get(i);
               neigh = point.getNeighbors();
               for(int j = 0; j < neigh.length; j++){
                   if(isCorrect(neigh[j], graph) == true && neigh[j].isWithin(explored) == false){
                       nextCost = minDist[point.getX()][point.getY()] + graph[neigh[j].getX()][neigh[j].getY()];
                       if(nextCost < minCost){
                           minCost = nextCost;
                           bestNeigh = neigh[j];
                           previous = point;
                       }
                    }
                }
           }
       explored.add(bestNeigh);
       predecessor[bestNeigh.getX()][bestNeigh.getY()] = previous;
       minDist[bestNeigh.getX()][bestNeigh.getY()] = minCost;
       }
    
       ArrayList<Point> answer = new ArrayList<>();
       Point current = dest;
       while(!current.equals(src)){
           answer.add(predecessor[current.getX()][current.getY()]);
           current = predecessor[current.getX()][current.getY()];
        }
       answer.add(src);
       Collections.reverse(answer);
       return answer;
    
    }   
}
