    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ce326.hw2;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.*;
import java.io.File; 
/**
 *
 * @author kouel
 */
public class PPMImageStacker {
    List<File> all_files_list;
    RGBImage final_image; 
            
    public PPMImageStacker(java.io.File dir) throws UnsupportedFileFormatException, FileNotFoundException{
        if (!dir.exists()){
            System.out.println("[ERROR] Directory"+ dir.getName() +"does not exist!");
            throw new java.io.FileNotFoundException();
        }
        
        if(dir.exists() && !dir.isDirectory()){
            System.out.println("[ERROR] Directory"+ dir.getName() +"is not a directory!");
            throw new java.io.FileNotFoundException();
        }
        
        all_files_list = new ArrayList<>();
        final_image = new RGBImage();
        
        File[] all_files = dir.listFiles();     // create the file list
         if (all_files != null && all_files.length > 0) {
            for (File file : all_files) {
                all_files_list.add(file);
            }
         }
         else{
             throw new java.io.FileNotFoundException();
         }
    }
    
    public void stack() throws UnsupportedFileFormatException{
        Iterator <File> it = all_files_list.iterator();
        int i, j, counter = 0;
        short[][] sum_red = null, sum_green =null, sum_blue = null;
        short red, green, blue;
        String start = "P3";
            
        while(it.hasNext()){
            File cur_file = it.next();
            counter = counter + 1;
            try {
                Scanner read = new Scanner(cur_file);
                if (cur_file.exists() && cur_file.canRead() == true) {
                    while (read.hasNext()) {
                        String magic_num = read.next();
                        if( magic_num.compareTo(start) != 0 ){
                            throw new UnsupportedFileFormatException();
                        }

                        int width = read.nextInt();
                        int height = read.nextInt();
                        int colordepth = read.nextInt();
                        
                        if(counter == 1){
                            sum_red = new short[height][width];
                            sum_green= new short[height][width];
                            sum_blue = new short[height][width];
                            for(i=0; i<height; i++){
                                for(j=0; j<width; j++){
                                    sum_red[i][j] = 0;
                                    sum_green[i][j] = 0;
                                    sum_blue[i][j] = 0;
                                }
                            }
                            
                            final_image.setWidth(width);
                            final_image.setHeight(height);
                            final_image.setColordepth(colordepth);
                            final_image.initiate_image(width, height, colordepth);
                        }
                        for(i=0; i<height; i++){
                            for(j=0; j<width; j++){
                                 red = read.nextShort();
                                 green = read.nextShort();
                                 blue = read.nextShort();
                                RGBPixel each_pixel = new RGBPixel(red, green, blue);
                                if(counter == 1){
                                    sum_red[i][j] = red;
                                    sum_green[i][j] = green;
                                    sum_blue[i][j] = blue;
                                }
                                else{
                                    sum_red[i][j] = (short)(sum_red[i][j] + red);
                                    sum_green[i][j] = (short)(sum_green[i][j] + green);
                                    sum_blue[i][j] = (short)(sum_blue[i][j] + blue);
                                    
                                }
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
        
        for(i=0; i<final_image.height; i++){
            for(j=0; j<final_image.width; j++){
                short new_red = (short)(sum_red[i][j]/(short)(counter));
                short new_green = (short)(sum_green[i][j]/(short)(counter));
                short new_blue = (short)(sum_blue[i][j]/(short)(counter));
                RGBPixel new_pixel = new RGBPixel(new_red, new_green, new_blue);
                final_image.setPixel(i,j, new_pixel);
            }
        }
    }
    
    public PPMImage getStackedImage(){
        PPMImage final_ppm = new PPMImage(final_image);
        
        return final_ppm;
    }
}
