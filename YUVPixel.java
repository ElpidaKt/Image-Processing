/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce326.hw2;

/**
 *
 * @author kouel
 */
public class YUVPixel {
     private short my_Y, my_U, my_V;
    
    
    public YUVPixel(short Y, short U, short V){
        my_Y = Y;
        my_U = U;
        my_V = V;        
    }
    
    public YUVPixel(YUVPixel pixel){
        my_Y = pixel.getY();
        my_U = pixel.getU();
        my_V = pixel.getV();
        
    }
    
    public YUVPixel(RGBPixel pixel){
        
        my_Y = (short)(( (  66 * pixel.getRed() + 129 * pixel.getGreen() +  25 * pixel.getBlue() + 128) >> 8) +  16);
        my_U = (short)(( ( -38 * pixel.getRed() -  74 * pixel.getGreen() + 112 * pixel.getBlue() + 128) >> 8) + 128);
        my_V = (short)(( ( 112 * pixel.getRed() -  94 * pixel.getGreen() -  18 * pixel.getBlue() + 128) >> 8) + 128);

    }
    
    public short getY(){
        return my_Y;
    }
    public short getU(){
        return my_U;
    }
    public short getV(){
        return my_V;
    }
    public void setY(short Y){
       my_Y = Y;
    }
    public void setU(short U){
        my_U = U;
    }
    public void setV(short V){
        my_V = V;
    }
    
    @Override
    public String toString(){
        return getY() + " " + getU() + " " + getV();
    }

}
