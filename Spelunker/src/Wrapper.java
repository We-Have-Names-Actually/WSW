
 
import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
 
public class Wrapper extends BasicGame{
	
	
	public static int gamewidth = 1000;
	public static int gameheight = 700;
	Input myinput = new Input(600);
	Room room;
	private Image background;
	private Image hero;
	private float x = 400, y = 300;
	private int jumplength = 0;
	private boolean jumping, onground, why;
 
    public Wrapper()
    {
        super("World of Spelunking Warrior");
    }
 
    @Override
    public void init(GameContainer gc) 
			throws SlickException {
    	gc.setMaximumLogicUpdateInterval(1);
		gc.setMinimumLogicUpdateInterval(1);
		hero = new Image("res/hero.png");
		background = new Image("res/cave.jpg");
		//SpriteSheet sheet = new SpriteSheet("res/tiles_nes.png", 16, 16);
        room = new Room();
    }
 
    @Override
    public void update(GameContainer gc, int delta) 
			throws SlickException     
    {
    	 if(myinput.isKeyDown(myinput.KEY_A) || myinput.isKeyDown(myinput.KEY_LEFT) )
         {
    		 if(issolid(x-1, y, 4))
    			 x-=.5;
         }
    	 if(myinput.isKeyDown(myinput.KEY_D) || myinput.isKeyDown(myinput.KEY_RIGHT))
         {
    		 if(issolid(x+50, y, 1))
    			 x+= .5;
         }
    	 if(myinput.isKeyDown(myinput.KEY_W) || myinput.isKeyDown(myinput.KEY_UP))
         {
    		 if(issolid(x, y-1, 0) && onground == true){
    			 jumping = true;
    			 onground = false;
    		 }
    		 if(jumping && jumplength <150){
    			 y-=1;
    		 }
         }
    	 if(myinput.isKeyDown(myinput.KEY_S) || myinput.isKeyDown(myinput.KEY_DOWN))
         {
    		 if(issolid(x, y+50, 3))
    			 y += .5;
         }
    	 if(issolid(x, y+50, 3)){
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
 
    public boolean issolid(float x, float y, int direction){
    	
    	if(direction == 4 || direction == 3){
    		if(room.terrain[(int) (x/50+1)][(int) (y/50)] != 0 || room.terrain[(int) (x/50)][(int) (y/50)] != 0 )
    			return false;
    	}
    	if(room.terrain[(int) (x/50)][(int) (y/50)] != 0)
    			return false;
    	
    	
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
    	background.draw(0,0);    	
    	hero.draw(x,y);
    	Image brick = new Image("res/brick.jpg");
    	for(int i = 0; i < room.width;i++){
    		for(int j = 0; j < room.height;j++){
    			if(room.terrain[i][j] == 1){
    				brick.draw(i*50,j*50);
    			}
    		}
    	}
    	
    	//Image spike = new Image("res/spike.png");
    	//spike.draw(900, 650);

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