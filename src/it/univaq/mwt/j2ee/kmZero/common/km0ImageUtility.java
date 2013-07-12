package it.univaq.mwt.j2ee.kmZero.common;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class km0ImageUtility {
	
public static byte[] getScaledImage(int width,int height,byte[] imgData,String contentType) throws IOException{
		
		//convert the byte array to InputStream because ImageIO does not read byte array directly
		ByteArrayInputStream bais = new ByteArrayInputStream(imgData);
		
		//create a BufferedImage for thumbnailer
		BufferedImage bimg = ImageIO.read(bais);
		
		//generate the thumb
		BufferedImage thumbnail = Thumbnails.of(bimg)
				.crop(Positions.CENTER)
		        .size(width,height)
		        .asBufferedImage();
		
		byte[] bytes = null;
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			
			//contentType is a String that contains the image contetType e.g. image/jpg or image/png
			//ImageIO.write as 2 parameter takes the informal name of the format e.g. jpg or png
			//then is necessary to substring the contentType stripping the first part of the string e.g. image/ 

			ImageIO.write(thumbnail,contentType.substring(6), baos);
			baos.flush();
			bytes = baos.toByteArray();
			baos.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bytes;
	}

}
