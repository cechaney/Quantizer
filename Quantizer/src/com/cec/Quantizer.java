/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cec;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author CXC3533
 */
public class Quantizer {
    
    private static int SWATCH_COUNT = 24;

    public static void main(String[] args) {

        try {
            
            //URL lennaUrl = new URL("https://upload.wikimedia.org/wikipedia/en/2/24/Lenna.png");
            URL lennaUrl = new URL("https://upload.wikimedia.org/wikipedia/commons/0/0d/Edouard_Manet%2C_A_Bar_at_the_Folies-Berg%C3%A8re.jpg");
            //URL lennaUrl = new URL("http://cdn.home-designing.com/wp-content/uploads/2012/08/Contemporary-bedroom-scheme.jpeg");
            //URL lennaUrl = new URL("http://weblog.wpengine.netdna-cdn.com/wp-content/uploads/2014/02/2-25-juice-oranges.jpg");
            

            int[][] lenna = getPixels(lennaUrl);

            int[] lennaQuantized = Quantize.quantizeImage(lenna, SWATCH_COUNT);

            for (int i = 0; i < lennaQuantized.length; i++) {
                System.out.println(lennaQuantized[i]);
            }            

            JFrame frame = new JFrame("Color Palette");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            frame.setLayout(new BorderLayout());
            
            ImageIcon pic = new ImageIcon(lennaUrl);
            pic.setImage(pic.getImage().getScaledInstance(300, 600, 0));
            
            JLabel picLabel = new JLabel(pic, SwingConstants.CENTER);
            
            
            frame.add(picLabel, BorderLayout.NORTH);
            
            JPanel colorPanel = new JPanel();
            GridLayout colorGrid = new GridLayout(SWATCH_COUNT / 2 ,SWATCH_COUNT / 2);
            colorPanel.setLayout(colorGrid);
            
            for (int i = 0; i < lennaQuantized.length; i++) {
                
                Color swatch = new Color(lennaQuantized[i]);
                
                StringBuilder label = new StringBuilder();
                
                int red = swatch.getRed();
                int green = swatch.getGreen();
                int blue = swatch.getBlue();
                        

                label.append("<html>");
                label.append("<div>");
                label.append(swatch.getRGB());
                label.append("</div>");
                label.append("<div>");
                label.append(red);
                label.append(", ");
                label.append(green);
                label.append(", ");
                label.append(blue);
                label.append("</div>");
                label.append("<html>");

                
                JLabel colorLabel = new JLabel(label.toString(), SwingConstants.CENTER);
                colorLabel.setOpaque(true);
                colorLabel.setBackground(swatch);
                colorLabel.setSize(SWATCH_COUNT * 100 , SWATCH_COUNT * 100);

                colorPanel.add(colorLabel);
            }
            
            frame.add(colorPanel, BorderLayout.SOUTH);

            frame.pack();
            frame.setVisible(true);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static int[][] getPixels(URL imageUrl) throws IOException {

        int[][] results;

        BufferedImage img = ImageIO.read(imageUrl);

        int width = img.getWidth();
        int height = img.getHeight();

        results = new int[width][height];

        for (int x = 0; x < width; x++) {

            for (int y = 0; y < height; y++) {

                results[x][y] = img.getRGB(x, y);

            }
        }

        return results;

    }

}
