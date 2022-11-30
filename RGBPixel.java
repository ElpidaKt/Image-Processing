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
public class RGBPixel {
    private int pixel;
    
    public RGBPixel(short red, short green, short blue){
        pixel = 0;
        setRGB(red, green, blue);  
        //System.out.println(pixel); //remember to extract this
    }
    
    public RGBPixel(RGBPixel pixel){ //makes a copy of the original pixel
 
        setRGB( pixel.getRed(), pixel.getGreen(), pixel.getBlue());
    }
    
    public RGBPixel(YUVPixel pixel){
        short c, d, e, temp_red, temp_green, temp_blue;
        
        c = (short)(pixel.getY() - 16);
        d = (short)(pixel.getU() - 128);
        e = (short)(pixel.getV() - 128);

        temp_red = clip(( 298 * c + 409 * e + 128) >> 8);
        temp_green = clip(( 298 * c - 100 * d - 208 * e + 128) >> 8);
        temp_blue = clip(( 298 * c + 516 * d + 128) >> 8);

        setRGB(temp_red, temp_green, temp_blue);
    }
    
    private short clip(int num){
        if(num > 255){
            num = 255;
        }
        
        if(num < 0){
            num = 0;
        }
        
        return (short)num;
    }
    
    short getRed(){
        short fin_red;
        int cur_pixel;
        int cur_red;
        
        cur_pixel = pixel >> 16;
        cur_red = cur_pixel & 0x000000ff;
        
        fin_red = (short)cur_red;
        return(fin_red);
    }
    short getGreen(){
        short fin_green;
        int cur_pixel;
        int cur_green;
        
        cur_pixel = pixel >> 8;
        cur_green = cur_pixel & 0x000000ff;
        
        fin_green = (short)cur_green;
        return(fin_green);
    }
    short getBlue(){
        short fin_blue;
        int cur_blue;
        
        cur_blue = pixel & 0x000000ff;
        
        fin_blue = (short)cur_blue;
        return(fin_blue);
    }
    void setRed(short red){
        pixel = red << 8;
    }
    void setGreen(short green){
        pixel = (pixel | green) << 8;
    }
    void setBlue(short blue){
        pixel = pixel | blue;
    }
    int getRGB(){
        return pixel;
    }
    void setRGB(int value){
        pixel = value;
    }
    final void setRGB(short red, short green, short blue){
        setRed(red);
        setGreen(green);
        setBlue(blue);  
    }
    @Override
    public String toString(){
        return getRed() + " " + getGreen() + " " + getBlue();
    }
}
