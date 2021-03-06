package engine


import org.lwjgl.opengl.GL11

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.BufferUtils

import org.lwjgl.opengl.GL30

import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.nio.ByteBuffer

public class Texture {
    private int id;
    private int width;
    private int height;
    private int xPos;
    private int yPos;
    public static int sWidth;
    public static int sHeight;


    public Texture( byte [] img, int width, int height, int xPos, int yPos){

        BufferedImage bi;
        try{

            this.xPos = xPos;
            this.yPos = yPos;
            this.width = width;
            this.height = height;

            bi = ImageIO.read(new ByteArrayInputStream(img));

           // bi = ImageIO.read(new File(filename));
            //width = bi.getWidth();
            //height = bi.getHeight();

            int[] pixels_raw = new int[width * height * 4];
            pixels_raw = bi.getRGB(this.xPos,this.yPos, width, height, null, 0, width);

            ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);

            for (int i = 0; i  < height; i++){
                for (int j = 0; j < width; j++){
                    int pixel = pixels_raw[i*width + j];
                    pixels.put((byte)((pixel >> 16) & 0xFF)); //RED
                    pixels.put((byte)((pixel >> 8) & 0xFF));  //GREEN
                    pixels.put((byte)(pixel & 0xFF));         //BLUE
                    pixels.put((byte)((pixel >> 24) & 0xFF)); //ALPHA
                }
            }
            pixels.flip();

            id = glGenTextures();

            glBindTexture(GL_TEXTURE_2D, id);

            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            GL11.glTexImage2D(GL_TEXTURE_2D, 0 , GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels)


        }catch(IOException e){
            e.printStackTrace();

        }
    }

    public Texture(String filename){

        BufferedImage bi;
        try{

            bi = ImageIO.read(new File(filename));
            width = bi.getWidth();
            height = bi.getHeight();

            int[] pixels_raw = new int[width * height * 4];
            pixels_raw = bi.getRGB(0,0, width, height, null, 0, width);

            ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);

            for (int i = 0; i  < height; i++){
                for (int j = 0; j < width; j++){
                    int pixel = pixels_raw[i*width + j];
                    pixels.put((byte)((pixel >> 16) & 0xFF)); //RED
                    pixels.put((byte)((pixel >> 8) & 0xFF));  //GREEN
                    pixels.put((byte)(pixel & 0xFF));         //BLUE
                    pixels.put((byte)((pixel >> 24) & 0xFF)); //ALPHA
                }
            }
            pixels.flip();

            id = glGenTextures();

            glBindTexture(GL_TEXTURE_2D, id);

            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            GL11.glTexImage2D(GL_TEXTURE_2D, 0 , GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels)

            sWidth = width;
            sHeight = height;


        }catch(IOException e){
            e.printStackTrace();

        }
    }

    public void  bind(){
        glBindTexture(GL_TEXTURE_2D, id);
    }
}
