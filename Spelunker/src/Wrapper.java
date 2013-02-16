
 
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
	
	
	public static int gamewidth = 1000;
	public static int gameheight = 700;
	Input myinput = new Input(600);
	private Texture background;
	private Texture hero;
	private float x = 400, y = 300;
	private int jumplength = 0;
	private boolean jumping, onground;
 
    public Wrapper()
    {
        super("World of Spelunking Warrior");
    }
 
    @Override
    public void init(GameContainer gc) 
			throws SlickException {
    	try {
    		gc.setMinimumLogicUpdateInterval(2);
    		gc.setMaximumLogicUpdateInterval(2);
    		hero = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/hero.png"));
			background = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("res/cave.JPG"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
 
    @Override
    public void update(GameContainer gc, int delta) 
			throws SlickException     
    {
    	 if(myinput.isKeyDown(myinput.KEY_A) || myinput.isKeyDown(myinput.KEY_LEFT) )
         {
    		 if(issolid(x-1, y))
    			 x-=.5;
         }
    	 if(myinput.isKeyDown(myinput.KEY_D) || myinput.isKeyDown(myinput.KEY_RIGHT))
         {
    		 if(issolid(x+160, y))
    			 x+= .5;
         }
    	 if(myinput.isKeyDown(myinput.KEY_W) || myinput.isKeyDown(myinput.KEY_UP))
         {
    		 if(issolid(x, y-1) && onground == true){
    			 jumping = true;
    			 onground = false;
    		}else if(jumping && issolid(x, y-1)){
    			y-=1;
    			
    		}
    		 
         }
    	 if(myinput.isKeyDown(myinput.KEY_S) || myinput.isKeyDown(myinput.KEY_DOWN))
         {
    		 if(issolid(x, y+140))
    			 y += .5;
         }
    	 if(issolid(x, y+140)){
    		 y+=.5;
    	 }else
    		 onground = true;
    	 if(jumping && jumplength < 150){
    		 y -=1.0;
    		 jumplength++;
    		 if(jumplength >= 150){
    			 jumplength = 0;
    			 jumping = false;
    		 }
    			 
    	 }
        
    }
 
    public boolean issolid(float x, float y){
    	if(y  > gameheight)
    		return false;
    	if(x < 0)
    		return false;
    	if(x > gamewidth)
    		return false;
    	if(y < 0)
    		return false;
    	return true;
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
         app.setDisplayMode(gamewidth, gameheight, false);
         app.start();
    }
}