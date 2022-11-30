/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce326.hw2;
import java.io.BufferedWriter;
import java.io.File;  
import java.io.FileNotFoundException;  
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner; 
/**
 *
 * @author kouel
 */
public class YUVImage {
    int width, height;
    YUVPixel image[][];
    
    public YUVImage(){
        
    }
    
    public YUVImage(int width, int height){
        this.height = height;
        this.width = width;
        image = new YUVPixel[height][width];
        int i, j;
        short k=16, l=128;
        
        for(i=0; i<height; i++){
            for(j=0; j<width; j++){
                image[i][j] = new YUVPixel(k, l, l);
            }
        }
    }
    
    public YUVImage(YUVImage copyImg){
        int i, j;
        this.height = copyImg.getHeight();
        this.width = copyImg.getWidth();
        image = new YUVPixel[height][width];
        
        for(i=0; i<height; i++){
            for(j=0; j<width; j++){
                image[i][j] = new YUVPixel(copyImg.getPixel(i, j));
            }
        }
    }
    
    public YUVImage(RGBImage RGBImg){
        int i, j;
        height = RGBImg.getHeight();
        width = RGBImg.getWidth();
        image = new YUVPixel[height][width];
        short temp_y, temp_u, temp_v;
        
        for(i=0; i<height ; i++){
            for(j=0; j<width; j++){
                temp_y = (short)(( ( 66 * RGBImg.getPixel(i, j).getRed() + 129 * RGBImg.getPixel(i, j).getGreen() +  25 * RGBImg.getPixel(i, j).getBlue() + 128) >> 8) +  16);
                temp_u = (short)(( ( -38 * RGBImg.getPixel(i, j).getRed() -  74 * RGBImg.getPixel(i, j).getGreen() + 112 * RGBImg.getPixel(i, j).getBlue() + 128) >> 8) + 128);
                temp_v = (short)(( ( 112 * RGBImg.getPixel(i, j).getRed() -  94 * RGBImg.getPixel(i, j).getGreen() -  18 * RGBImg.getPixel(i, j).getBlue() + 128) >> 8) + 128);
                        
                image[i][j] = new YUVPixel(temp_y, temp_u, temp_v);
            }
        }
    }
    
    public YUVImage(java.io.File file)throws UnsupportedFileFormatException, FileNotFoundException{
        String start = "YUV3";
        int i, j;
        
        
        try {
            Scanner read = new Scanner(file);
            if (file.exists() && file.canRead() == true) {
                while (read.hasNext()) {
                    String magic_num = read.next();
                    if( magic_num.compareTo(start) != 0 ){
                        throw new UnsupportedFileFormatException();
                    }
                 
                    int width = read.nextInt();
                    int height = read.nextInt();
                    
                    setWidth(width);
                    setHeight(height);
                    initiate_yuv_image(width, height);
                    
                    for(i=0; i<height; i++){
                        for(j=0; j<width; j++){
                            short y = read.nextShort();
                            short u = read.nextShort();
                            short v = read.nextShort();
                            YUVPixel each_pixel = new YUVPixel(y, u, v);
                            setPixel(i,j, each_pixel);
                        }
                    }
                }
                read.close();
            }
            else{
                throw new FileNotFoundException();
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    public void initiate_yuv_image(int width, int height){
        this.width = width;
        this.height = height;
        image = new YUVPixel[height][width];
    
    }
    
    YUVPixel getPixel(int row, int col){
        return image[row][col];
    }
    
    void setPixel(int row, int col,  YUVPixel pixel){
        image[row][col] = new YUVPixel(pixel);
    }
    
    
    void setHeight(int my_height){
        height = my_height;
    }
    
    void setWidth(int my_width){
        width = my_width;
    }
    
    int getWidth(){
        return width;
    }
    
    int getHeight(){
        return height;
    }
    @Override
    public String toString(){
        int i,j;
        StringBuilder yuv_image = new StringBuilder();
        
        yuv_image.append("YUV3\n");
        yuv_image.append(getWidth() + " " );
        yuv_image.append(getHeight() + "\n");
        
        for(i=0; i<height; i++){
            for(j=0; j<width; j++){
                yuv_image.append(image[i][j].toString() + " ");
            }
            yuv_image.append("\n");
        }
        return yuv_image.toString();
    }
    
    public void toFile(java.io.File file){
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
        YUVImage param_image = new YUVImage(width, height);
        
        for(i=0; i<height; i++){
            for(j=0; j<width; j++){
                param_image.setPixel(i, j, this.getPixel(i, j));
            }
        }
        Histogram hist = new Histogram(param_image);
        
        hist.equalize();
        
        for(i=0; i<height; i++){
            for(j=0; j<width; j++){
                image[i][j].setY(hist.getEqualizedLuminocity(image[i][j].getY()));
            }
        }
    }
}
