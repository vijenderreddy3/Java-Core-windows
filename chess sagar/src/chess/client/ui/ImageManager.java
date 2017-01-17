package chess.client.ui;

import java.awt.Image;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * 
 * This class is responsible for caching and managing the images.
 *
 */
public class ImageManager
{
    private static final Map<String, Image> PATH_TO_IMAGE_MAP = new HashMap<>();
    
    private ImageManager()
    {
        
    }
    
    public static Image getImage(String path)
    {
        String fullPath = "images/" + path;
        Image image = PATH_TO_IMAGE_MAP.get(fullPath);
        if(image == null)
        {
            try
            {
                image = ImageIO.read(new File(fullPath));
            }catch(Exception e)
            {
                throw new RuntimeException(e);
            }
            PATH_TO_IMAGE_MAP.put(fullPath, image);
        }
        return image;
    }
}
