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
public class RGBImage implements Image{
    RGBPixel image[][];
    int width, height, colordepth;
    final static int MAX_COLORDEPTH = 255;
    
    public RGBImage(){
    }
    public RGBImage(int width, int height, int colordepth){
        this.height = height;
        this.width = width;
        this.colordepth = colordepth;
        image = new RGBPixel[height][width];
        
    }
    public RGBImage(RGBImage copyImg){
        int i, j;
        this.height = height;
        this.width = width;
        this.colordepth = colordepth;
        image = new RGBPixel[height][width];
        
        for(i=0; i<height; i++){
            for(j=0; j<width; j++){
                image[i][j] = new RGBPixel(copyImg.getPixel(i, j));
            }
        }
    }
    public RGBImage(YUVImage YUVImg){
        int i, j;
        this.height = YUVImg.getHeight();
        this.width = YUVImg.getWidth();
        this.colordepth = MAX_COLORDEPTH;
        image = new RGBPixel[height][width];
        
        for(i=0; i<height; i++){
            for(j=0; j<width; j++){
                image[i][j] = new RGBPixel(YUVImg.getPixel(i, j));
            }
        }
    }
    
    void initiate_image(int width, int height, int colordepth){
        this.height = height;
        this.width = width;
        this.colordepth = colordepth;
        image = new RGBPixel[height][width];
    }
    void setHeight(int my_height){
        height = my_height;
    }
    
    void setWidth(int my_width){
        width = my_width;
    }
    
    void setColordepth(int my_colordepth){
        colordepth = my_colordepth;
    }
    
    int getWidth(){
        return width;
    }
    
    int getHeight(){
        return height;
    }
    
    int getColorDepth(){
        return MAX_COLORDEPTH;
    }
    
    RGBPixel getPixel(int row, int col){
        return image[row][col];
    }
    
    void setPixel(int row, int col,  RGBPixel pixel){
        image[row][col] = new RGBPixel(pixel);
    }
    
    public void grayscale(){
        int i, j;
        short gray;
        
        for(i=0; i<height; i++){
            for(j=0; j<width; j++){
                gray = (short)(image[i][j].getRed() * 0.3 + image[i][j].getGreen() * 0.59 + image[i][j].getBlue() * 0.11);
                image[i][j].setRGB(gray, gray, gray);
            }
        }
    }
    public void doublesize(){
        int i, j;
        RGBPixel new_double_image[][] = new RGBPixel [2*height][2*width];
        
        for(i=0; i<height; i++){
            for(j=0; j<width; j++){
                new_double_image[2*i][2*j] = new RGBPixel(image[i][j].getRed(), image[i][j].getGreen(), image[i][j].getBlue());
                new_double_image[2*i+1][2*j] = new RGBPixel(image[i][j].getRed(), image[i][j].getGreen(), image[i][j].getBlue());
                new_double_image[2*i][2*j+1] = new RGBPixel(image[i][j].getRed(), image[i][j].getGreen(), image[i][j].getBlue());
                new_double_image[2*i+1][2*j+1] = new RGBPixel(image[i][j].getRed(), image[i][j].getGreen(), image[i][j].getBlue());
            }
        }
        initiate_image(width*2, height*2, colordepth);
        image = new_double_image;
    }
    public void halfsize(){
        int i, j;
        short red1, red2, red3, red4, green1, green2, green3, green4, blue1, blue2, blue3, blue4, mean_red, mean_green, mean_blue;
        RGBPixel new_halfsize_image[][] = new RGBPixel [height/2][width/2];
        
        for(i=0; i<height/2; i++){
            for(j=0; j<width/2; j++){
                red1 = image[2*i][2*j].getRed();    //Calculating the mean of reds
                red2 = image[2*i+1][2*j].getRed();
                red3 = image[2*i][2*j+1].getRed();
                red4 = image[2*i+1][2*j+1].getRed();
                mean_red = (short)((red1 + red2 + red3 + red4)/4);
                
                green1 = image[2*i][2*j].getGreen();  //Calculating the mean of greens
                green2 = image[2*i+1][2*j].getGreen();
                green3 = image[2*i][2*j+1].getGreen();
                green4 = image[2*i+1][2*j+1].getGreen();
                mean_green = (short)((green1 + green2 + green3 + green4)/4);
                
                blue1 = image[2*i][2*j].getBlue();  //Calculating the mean of blues
                blue2 = image[2*i+1][2*j].getBlue();
                blue3 = image[2*i][2*j+1].getBlue();
                blue4 = image[2*i+1][2*j+1].getBlue();
                mean_blue = (short)((blue1 + blue2 + blue3 + blue4)/4);
                
                new_halfsize_image[i][j] = new RGBPixel(mean_red, mean_green, mean_blue); //Assining the new mean values
            }
        }
        initiate_image(width/2, height/2, colordepth);
        image = new_halfsize_image;
    }
    public void rotateClockwise(){
        RGBPixel[][] rotaded_image = new RGBPixel[width][height];
        int i, j;
        
        for(i=0; i<height; i++){
            for(j=0; j<width; j++){
                rotaded_image[j][height - 1 - i] = new RGBPixel(image[i][j].getRed(),image[i][j].getGreen(), image[i][j].getBlue());
            }
        }
        initiate_image(height, width, colordepth);
        image = rotaded_image;
    }
}

