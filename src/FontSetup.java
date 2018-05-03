/**
 * This class contains method readFontFile which returns a font
 *
 * CPSC 224-01, Spring 2018
 * Final Project
 * @author Vincent Lombardi, Luke Hartman, Mario Malodonado
 * @version V1.0 5/3/2018
 */

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileNotFoundException;

import java.io.*;

public class FontSetup {	
	
    protected Font readFontFile(){
    	// Read from font file
        Font font = null;
        try{
            // InputStream object reads from .ttf file
            InputStream is = new BufferedInputStream(new FileInputStream("res/RobotoMono-Medium.ttf"));
            font = Font.createFont(Font.TRUETYPE_FONT, is);
        }catch(FileNotFoundException e){
            System.out.println("File not found");
        }catch(IOException e){
            System.out.println("Input/Output error");
        }catch(FontFormatException e){
            System.out.println("Font format exception");
        }
        return font;
    }
}