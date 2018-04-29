import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileNotFoundException;

import java.io.*;

public class FontSetup {	
	
    protected Font readFontFile(){
    	//Read from font file
        Font font = null;
        try{
            //File path may need changing
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
