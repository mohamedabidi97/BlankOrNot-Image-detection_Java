/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import sun.misc.BASE64Decoder;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.tess4j.*; 
import net.sourceforge.tess4j.TesseractException; 


public class BlankImageDetector {

   /**
     * If length > 30 : Filled Image
     */
    public static final int LIMIT = 30;
    
    /**
     * @param imageBase64 the command line arguments
     * @return 
     * @throws java.lang.InterruptedException
     */
    	public static boolean isBlankImage(String imageBase64) throws InterruptedException{

            double length = 0;

                // Decrypt The Imagebase64
                
                String imageString = imageBase64;
                BufferedImage image;
                image = null;
                byte[] imageByte;

                try {
                    BASE64Decoder decoder = new BASE64Decoder();
                    imageByte = decoder.decodeBuffer(imageString);
                    try (ByteArrayInputStream bis = new ByteArrayInputStream(imageByte)) {
                        image = ImageIO.read(bis);
                    }
                } catch (IOException e) 
                {
                    System.out.println(e); 
                }
                
                /*
                ***** String64 ==> image
                ***** Tesseract OCR To recognize If Image contain text or not
                */
                
                Tesseract tesseract = new Tesseract(); 
                tesseract.setDatapath("src\\tessdata");
                try {
                    String text; 
                    text = tesseract.doOCR(image);
                    length = text.length();
                } 
                catch (TesseractException e)
                { 
                }
           
                  /**
                    * Check if an Image is blank or not
                    * @return true : it's blank
                    */
             
return( length < LIMIT );
         
                
        
}
                 
    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
            // TODO code application logic here
            /*
            ***** String64 is too long so We need to copy the string in this file Text : src/input/txt.txt
            ***** Read the Text file as String TO solve the "too long string" Error
            */
            
            BufferedReader br = new BufferedReader(new FileReader("src/input/txt.txt"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                line = br.readLine();
            } 
            catch (IOException ex) {
                Logger.getLogger(BlankImageDetector.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            while (line != null) {
              sb.append(line).append("\n");
                  try {
                      line = br.readLine();
                  } catch (IOException ex) {
                      Logger.getLogger(BlankImageDetector.class.getName()).log(Level.SEVERE, null, ex);
                  }
            }

            String fileAsString = sb.toString();
            
            
            /*
            ****** fileAsString = Imagebase64 String
            ****** if the image is blank the program return BLANK Image if not return NOT BLANK
            */


         
            System.out.println(isBlankImage(fileAsString)?"BLANK Image":"NOT BLANK");
    }
}
