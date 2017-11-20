/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sce;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

/**
 *
 * @author Parker
 */
public class PathWindow extends JFrame {
    
    Point source = new Point(1915,1699);
    Point destination = new Point(1920, 1701);
    BufferedImage data, map, mapNew;
    CostPath lcp = new CostPath();
    ArrayList<Point> path = new ArrayList<Point>();
    ImagePath pathNew = new ImagePath();
    //DisplayWindow newImage = new DisplayWindow();
    int height, width;
    ImageIcon icon = new ImageIcon("Icon.jpg");
    double distance;
    
    public void setDataImage(BufferedImage image){
        data = image;
    }
    
    public void setDisplayImage(BufferedImage image){
        map = image;
    }

    public PathWindow() {
        
    }

    public PathWindow(BufferedImage im) {
        
        BufferedImage image = im;
        ScrollPane = new javax.swing.JScrollPane();
        PictureLabel = new javax.swing.JLabel();
        ButtonPanel = new javax.swing.JPanel();
        CalculateRoute = new javax.swing.JButton();
        SetDestination = new javax.swing.JToggleButton();
        SetSource = new javax.swing.JToggleButton();
        super.setIconImage(icon.getImage());
        textArea = new JTextArea();
        

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LandNav Mobile");
        
        
        ImageIcon ii = new ImageIcon(image);
        PictureLabel.setIcon(ii);
        
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(false);
        textArea.setEditable(false);
        textArea.setText("Select a source point.");

        ScrollPane.setViewportView(PictureLabel);
        ScrollPane.setSize(600,600);
        ScrollPane.setVisible(true);

        ScrollPane.addMouseMotionListener(new MouseMotionListener(){
            public void mouseMoved(MouseEvent e) {
                if (ScrollPane.contains(e.getPoint())){ 
                ScrollPane.setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
                }
            }        
            private Point origin;
            //@Override
            public void mousePressed(MouseEvent e) {
                origin = new Point(e.getPoint());
                repaint();
            }

            //@Override
            public void mouseReleased(MouseEvent e) {
            }
            
            @Override            
            public void mouseDragged(MouseEvent e) {
               if (origin != null) {
                            JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, PictureLabel);
                            if (viewPort != null) {
                                int deltaX = origin.getX() - e.getX();
                                int deltaY = origin.getY() - e.getY();

                                Rectangle view = viewPort.getViewRect();
                                view.x += deltaX;
                                view.y += deltaY;

                                PictureLabel.scrollRectToVisible(view);
                            }
                        }
            }
        });
        MouseListener destListener = new MouseListener(){
                        @Override
                        public void mouseClicked(MouseEvent e){
                            destination.setX(e.getX());
                            destination.setY(e.getY());
                            textArea.setText("Destination: " + destination.getX()
                            + ", " + destination.getY() + ". Please Confirm.");
                            //activeDest = false;
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {
                            
                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {
                            
                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {
                            
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            
                        }
        };
         MouseListener srcListener = new MouseListener(){
                        @Override
                        public void mouseClicked(MouseEvent e){
                            source.setX(e.getX());
                            source.setY(e.getY());
                            textArea.setText("Source: " + source.getX() 
                            + ", " + source.getY() + ". Please Confirm.");
                            //activeDest = false;    
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {
                            
                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {
                            
                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {
                            
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            
                        }
        };
                            
                        
                    
        JTextArea info = new JTextArea();
        info.setEditable(false);
        JButton back = new JButton("BacK");
        
        CalculateRoute.setText("Calculate Route");
        CalculateRoute.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                textArea.setText("Calculating...");
                mapNew = map;
                path = lcp.leastCostPath(source, destination, data);
                mapNew = pathNew.addPathToImage(path, mapNew);
                DisplayWindow newImage = new DisplayWindow(mapNew);
                newImage.setImage(mapNew);
                newImage.setSize(600,600);
                newImage.add(back, BorderLayout.SOUTH);
                back.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent evt){
                        newImage.setVisible(false);
                        PathWindow.super.setVisible(true);
                    }
                });
                distance = 1.5 * path.size();
                info.setText("Total distance of path: " + distance + " meters");
                newImage.add(info, BorderLayout.NORTH);
                PathWindow.super.setVisible(false);
                newImage.setVisible(true);
            }
        });
        
        //Boolean active = false;
        SetDestination.setText("Set Destination");
        SetDestination.addActionListener(new java.awt.event.ActionListener() {
            
            int destClicks;
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                 destClicks = destClicks + 1;
                 if(destClicks % 2 != 0){
                    PictureLabel.addMouseListener(destListener);
                    SetDestination.setText("Confirm?");
                 }
                 if(destClicks % 2 == 0){
                    PictureLabel.removeMouseListener(destListener);
                    textArea.setText("Destination confirmed.");
                    SetDestination.setText("Set Destination");
                }   
                
            }
                
            
        });

        SetSource.setText("Set Source");
        SetSource.addActionListener(new java.awt.event.ActionListener() {
            int srcClicks;
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                srcClicks = srcClicks + 1;
                if(srcClicks % 2 != 0){
                    PictureLabel.addMouseListener(srcListener);
                    SetSource.setText("Confirm?");
                }
                if(srcClicks % 2 == 0){
                    PictureLabel.removeMouseListener(srcListener);
                    textArea.setText("Source confirmed.");
                    SetSource.setText("Set Source");
                }
            }
        });

        javax.swing.GroupLayout ButtonPanelLayout = new javax.swing.GroupLayout(ButtonPanel);
        ButtonPanel.setLayout(ButtonPanelLayout);
        ButtonPanelLayout.setHorizontalGroup(
            ButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ButtonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SetSource, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(SetDestination)
                .addGap(10, 91, 91)
                .addComponent(CalculateRoute)
                .addGap(10, 91, 91)
                .addComponent(textArea)
                .addContainerGap())
        );
        ButtonPanelLayout.setVerticalGroup(
            ButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ButtonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SetSource)
                    .addComponent(SetDestination)
                    .addComponent(CalculateRoute)
                    .addComponent(textArea))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ScrollPane)
            .addComponent(ButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(ScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }
    

    private void SetSourceActionPerformed(java.awt.event.ActionEvent evt) {                                          
    }                                         

    private void SetDestinationActionPerformed(java.awt.event.ActionEvent evt) {                                               
        
    }                                              

    public static void main(String args[]) {
 
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PathWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PathWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PathWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PathWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PathWindow().setVisible(true);
            }
        });
    }
    
    public Point getSource(){
     return source;   
    }
    
    public Point getDestination(){
        return destination;
    }

    // Variables declaration - do not modify                     
    private javax.swing.JPanel ButtonPanel;
    private javax.swing.JButton CalculateRoute;
    private javax.swing.JLabel PictureLabel;
    private javax.swing.JScrollPane ScrollPane;
    private javax.swing.JToggleButton SetDestination;
    private javax.swing.JToggleButton SetSource;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextArea textArea;
    // End of variables declaration                   

}
