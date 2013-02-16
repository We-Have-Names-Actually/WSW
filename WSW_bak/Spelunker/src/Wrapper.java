
 
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
 
public class Wrapper extends BasicGame{
	
	Input myinput = new Input(600);
	private Texture background;
	private Texture hero;
	private float x = 400, y = 300;
 
    public Wrapper()
    {
        super("Slick2DPath2Glory - SimpleGame");
    }
 
    @Override
    public void init(GameContainer gc) 
			throws SlickException {
    	try {
    		hero = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("/Users/whit/Downloads/hero.PNG"));
			background = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("/Users/whit/Downloads/cave.JPG"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
 
    @Override
    public void update(GameContainer gc, int delta) 
			throws SlickException     
    {
    	 if(myinput.isKeyDown(myinput.KEY_A))
         {
    		 x--;
         }
    	 if(myinput.isKeyDown(myinput.KEY_D))
         {
    		 x++;
         }
    	 if(myinput.isKeyDown(myinput.KEY_W))
         {
    		 y--;
         }
    	 if(myinput.isKeyDown(myinput.KEY_S))
         {
    		 y++;
         }
        
    }
 
    public void render(GameContainer gc, Graphics g) 
			throws SlickException 
    {
    	
    	background.bind();
    	
    	
    	GL11.glBegin(GL11.GL_QUADS);
    	GL11.glTexCoord2f(0,0);
    	GL11.glVertex2f(0,0);
    	GL11.glTexCoord2f(1,0);
    	GL11.glVertex2f(background.getTextureWidth(),0);
    	GL11.glTexCoord2f(1,1);
    	GL11.glVertex2f(background.getTextureWidth(),background.getTextureHeight());
    	GL11.glTexCoord2f(0,1);
    	GL11.glVertex2f(0,background.getTextureHeight());
    	GL11.glEnd();
    	
    	hero.bind();
    	
    	GL11.glBegin(GL11.GL_QUADS);
    	GL11.glTexCoord2f(0,0);
    	GL11.glVertex2f(x,y);
    	GL11.glTexCoord2f(1,0);
    	GL11.glVertex2f(x+hero.getTextureWidth(),y);
    	GL11.glTexCoord2f(1,1);
    	GL11.glVertex2f(x+hero.getTextureWidth(),y+hero.getTextureHeight());
    	GL11.glTexCoord2f(0,1);
    	GL11.glVertex2f(x,y+hero.getTextureHeight());
    	GL11.glEnd();
    	
    	
    }
 
    public static void main(String[] args) 
			throws SlickException
    {
         AppGameContainer app = 
			new AppGameContainer(new Wrapper());
 
         app.setDisplayMode(800, 600, false);
         app.start();
    }
}