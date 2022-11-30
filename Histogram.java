/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce326.hw2;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;  

/**
 *
 * @author kouel
 */
public class Histogram {
    long[] array;
    double[] pdf;
    double[] cdf;
    int[] equalized_array;
    
    public Histogram(YUVImage img){
        int i, j;
        array = new long[236];
        int height = img.getHeight();
        int width = img.getWidth();
        
        for(i=0; i<236; i++){
            array[i] = 0;
        }
        
        for(i=0; i<img.getHeight(); i++){
            for(j=0; j<img.getWidth(); j++){
               array[img.getPixel(i, j).getY()] += 1;
            }
        }
        
        pdf = new double [236];
        
        for(i=0; i<236; i++){
            pdf[i] = 0;
        }
        
        for(i=0; i<236; i++){
            pdf[i] = (double)(array[i])/((double)(height*width));
        }
        
        cdf();
    }
    
    public void cdf() {
        int i, j;
        cdf = new double [236];
        
        for(i=0; i<236; i++){
            cdf[i] = 0;
        }
        
        cdf[0] = pdf[0];
        for(i=1; i<236; i++){
            cdf[i] = cdf[i-1] + pdf[i];
        }
    }
    
    int counter(long num){
        int coun = 0;
        
        while (num != 0)
        {
            num = num / 10;
            coun = coun + 1;
        }
        return coun;
    }
 
    
    @Override
    public String toString(){
        int i, j, thousands, hundreds, decades, monades, lum_counter, hist_counter;
        long new_num;
        StringBuilder str = new StringBuilder();
        
        for(i=0; i<236; i++){
          str.append("\n");
          lum_counter = counter(i);
          
          if(i == 0){
              str.append("  ");
              str.append(i);
              str.append(".");
          }
          if(lum_counter == 1){
              str.append("  ");
              str.append(i);
              str.append(".");
          }
          
          if(lum_counter == 2){
              str.append(" ");
              str.append(i);
              str.append(".");
          }
          
          if(lum_counter == 3){
              str.append(i);
              str.append(".");
          }
          
          hist_counter = counter(array[i]);
          
          if(array[i] == 0){
            str.append("(");
            str.append("   ");
            str.append(array[i]);
            str.append(")");
            str.append("\t");
          }
          
          if(hist_counter == 1){
            str.append("(");
            str.append("   ");
            str.append(array[i]);
            str.append(")");
            str.append("\t");
          }
          
          if(hist_counter == 2){
            str.append("(");
            str.append("  ");
            str.append(array[i]);
            str.append(")");
            str.append("\t");
          }
          
          if(hist_counter == 3){
            str.append("(");
            str.append(" ");
            str.append(array[i]);
            str.append(")");
            str.append("\t");
          }
          
          if(hist_counter == 4){
            str.append("(");
            str.append(array[i]);
            str.append(")");
            str.append("\t");
          }
          thousands = (int)(array[i] / 1000);
          for(j=0; j<thousands; j++){
            str.append("#");
          }
          
          new_num = array[i] % 1000;
          hundreds = (int)(new_num/100);
          for(j=0; j<hundreds; j++){
            str.append("$");
          } 
          
          new_num = array[i] % 100;
          decades = (int)(new_num / 10);
          for(j=0; j<decades; j++){
            str.append("@");
          } 
          
          new_num = array[i] % 10;
          monades = (int)(new_num);
          for(j=0; j<monades; j++){
            str.append("*");
          } 
        }
        str.append("\n");
        return str.toString();
    }
    
    public void toFile(File file){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(toString());
            writer.close();
        }
        catch(IOException e){
            System.out.println("Error in writing to the file");
            e.printStackTrace();
        }
    }
    
    public void equalize(){
        int i, j;
        equalized_array = new int[236];
        
        for(i=0; i<236; i++){
            equalized_array[i] = (int)(cdf[i] * 235);
        }
    }
    
    public short getEqualizedLuminocity(int luminocity){
        return (short)(equalized_array[luminocity]);
    }
}
