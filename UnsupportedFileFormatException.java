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
public class UnsupportedFileFormatException extends java.lang.Exception{
    public UnsupportedFileFormatException(){
    }
    public UnsupportedFileFormatException(String msg){
        System.out.println(msg);
    }
    static final long serialVersionUID = -4567891456L;
}
