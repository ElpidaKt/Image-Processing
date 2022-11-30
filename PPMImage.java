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
public class PPMImage extends RGBImage {
    
    public PPMImage(){
        super(0, 0, 255);
    }
    public PPMImage(java.io.File file)throws UnsupportedFileFormatException, FileNotFoundException{
        super();
        int i, j;
        String start = "P3";
        
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
                    int colordepth = read.nextInt();
                    
                    setWidth(width);
                    setHeight(height);
                    setColordepth(colordepth);
                    initiate_image(width, height, colordepth);
                    
                    for(i=0; i<height; i++){
                        for(j=0; j<width; j++){
                            short red = read.nextShort();
                            short green = read.nextShort();
                            short blue = read.nextShort();
                            RGBPixel each_pixel = new RGBPixel(red, green, blue);
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
  
    public PPMImage(RGBImage img){
        super(0, 0, 255);
        int i, j;
        width = img.getWidth();
        height = img.getHeight();
        colordepth = img.getColorDepth();
        initiate_image(width, height, colordepth);
        for(i=0; i<height; i++){
            for(j=0; j<width; j++){
                image[i][j] = new RGBPixel(img.getPixel(i, j).getRed(), img.getPixel(i, j).getGreen(), img.getPixel(i, j).getBlue());
            }
        }
    }
    public PPMImage(YUVImage img){
        super(0, 0, 255);
        int i, j;
        short c, d, e, temp_red, temp_green, temp_blue;
        
        width = img.getWidth();
        height = img.getHeight();
        colordepth = MAX_COLORDEPTH;
        initiate_image(width, height, colordepth);
        for(i=0; i<height; i++){
            for(j=0; j<width; j++){
                c = (short)(img.getPixel(i, j).getY() - 16);
                d = (short)(img.getPixel(i, j).getU() - 128);
                e = (short)(img.getPixel(i, j).getV() - 128);
                
                temp_red = clip(( 298 * c + 409 * e + 128) >> 8);
                temp_green = clip(( 298 * c - 100 * d - 208 * e + 128) >> 8);
                temp_blue = clip(( 298 * c + 516 * d + 128) >> 8);


                image[i][j] = new RGBPixel(temp_red, temp_green, temp_blue);
            }
        }
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

    @Override
    public String toString(){
        int i,j;
        StringBuilder ppm_image = new StringBuilder();
        
        ppm_image.append("P3\n");
        ppm_image.append(getWidth() + " " );
        ppm_image.append(getHeight() + "\n");
        ppm_image.append(getColorDepth() + "\n");
        
        for(i=0; i<height; i++){
            for(j=0; j<width; j++){
                ppm_image.append(image[i][j].toString() + " ");
            }
            ppm_image.append("\n");
        }
        return ppm_image.toString();
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
}