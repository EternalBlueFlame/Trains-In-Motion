package ebf.tim.models.tmt;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import javax.imageio.ImageIO;
import javax.vecmath.Vector2f;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.google.common.io.Resources.getResource;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

public class TextureLoader {
    private static final int BYTES_PER_PIXEL = 4;//3 for RGB, 4 for RGBA
    private static final Random rand = new Random();
    public static int loadTexture(BufferedImage image, int scale){


        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL); //4 for RGBA, 3 for RGB
        if (scale !=0) {
            Map<Vector2f, Integer> filterPixels = new HashMap<Vector2f, Integer>();
            for (int filterY = 0; filterY < image.getHeight(); filterY += scale) {
                for (int filterX = 0; filterX < image.getWidth(); filterX += scale) {
                    int randonInt = rand.nextInt(0xFF - 0xF6) + 0xF6;
                    if (scale > 1) {
                        for (int scaleY = 0; scaleY < scale; scaleY++) {
                            for (int scaleX = 0; scaleX < scale; scaleX++) {
                                filterPixels.put(new Vector2f(scaleY + filterY, scaleX + filterX), randonInt);
                            }
                        }
                    } else {
                        filterPixels.put(new Vector2f(filterY, filterX), randonInt);
                    }
                }
            }


            int[] pixels = new int[image.getWidth() * image.getHeight()];
            image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int pixel = pixels[y * image.getWidth() + x];
                    buffer.put((byte) ((pixel >> 16) & filterPixels.get(new Vector2f(y, x))));     // Red component
                    buffer.put((byte) ((pixel >> 8) & filterPixels.get(new Vector2f(y, x))));      // Green component
                    buffer.put((byte) (pixel & filterPixels.get(new Vector2f(y, x))));               // Blue component
                    buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
                }
            }
        } else {
            int[] pixels = new int[image.getWidth() * image.getHeight()];
            image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int pixel = pixels[y * image.getWidth() + x];
                    buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                    buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                    buffer.put((byte) (pixel & 0xFF));               // Blue component
                    buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
                }
            }
        }

        //because textures are generated backwards, for some reason..
        buffer.flip();

        int textureID = GL11.glGenTextures(); //Generate texture ID
        GL11.glBindTexture(GL_TEXTURE_2D, textureID); //Bind texture ID

        //Setup wrap mode
        GL11.glTexParameteri(GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        //Setup texture scaling filtering
        GL11.glTexParameteri(GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        //Send texel data to OpenGL
        GL11.glTexImage2D(GL_TEXTURE_2D, 0, GL11.GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

        //Return the texture ID so we can bind it later again
        return textureID;
    }

    public static BufferedImage loadImage(String loc) {
        try {
            URL f = getResource(loc);
            if (f != null) {
                return ImageIO.read(f);
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }
}
