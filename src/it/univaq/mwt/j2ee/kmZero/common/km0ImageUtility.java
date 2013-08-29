package it.univaq.mwt.j2ee.kmZero.common;

import it.univaq.mwt.j2ee.kmZero.business.model.Image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class km0ImageUtility {
	
	public static Collection<Image> generateImages(Collection<MultipartFile> files,int width,int height) throws IOException{
		
		Collection<Image> images = new ArrayList<Image>();
		for (Iterator<MultipartFile> i = files.iterator(); i.hasNext();){
			MultipartFile mpf = (MultipartFile)i.next();
			//File cannot be null 
			if(!mpf.isEmpty()){
				byte [] scaledimg = getScaledImage(width, height, mpf.getBytes(), mpf.getContentType());
				Image img = new Image(mpf.getOriginalFilename(), scaledimg);
				images.add(img);
			}
        }
		return images;
	}
	
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
