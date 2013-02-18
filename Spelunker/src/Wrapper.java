
 
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
 
public class Wrapper extends BasicGame{
	
	
	public static int gamewidth = 1000;
	public static int gameheight = 700;
	Input myinput = new Input(600);
	Room room;
	private Image background;
	private Image hero;
	private Rectangle herolocation;
	private World world;
	private ArrayList<Entity> tempImg;
	public float x = 400, y = 300;
	private int jumplength = 0;
	private boolean jumping, onground, facingLeft;
	private int health = 5;
	private int damage = 5;
 
    public Wrapper()
    {
        super("World of Spelunking Warrior");
    }
 
    @Override
    public void init(GameContainer gc) 
			throws SlickException {
    	tempImg = new ArrayList<Entity>();
    	gc.setMaximumLogicUpdateInterval(1);
		gc.setMinimumLogicUpdateInterval(1);
		world = new World();
		hero = new Image("res/hero.png");
		background = new Image("res/cave.jpg");
		herolocation = new Rectangle(x,y,49,49);
		//SpriteSheet sheet = new SpriteSheet("res/tiles_nes.png", 16, 16);
        room = world.map.getFirst();
    }
 
    @Override
    public void update(GameContainer gc, int delta) 
			throws SlickException     
    {

    	 if(myinput.isKeyDown(Input.KEY_SPACE))
    	 {
    		 int toRemove = -1;
    		 boolean hit = false;
    		 int modifier = 49;
    		 if(facingLeft == true)
    		 {
    			 modifier = -20;
    		 }
    		 tempImg.add(new Entity("res/hitbox.jpg", (int)x+modifier, (int)y));
    		 Rectangle attack = new Rectangle(x+modifier, y-49, 25, 50);
    		 for(Entity enemy : room.enemies)
    		 {
    			 if(enemy.type == Entity.Type.ENEMY && enemy.collision(attack))
    			 {
    				 hit = true;
    				 if(!enemy.damage(1))
    				 {
    					 toRemove = room.enemies.indexOf(enemy);
    				 }
    			 }
    		 }
    		 if (room.enemies.size() > toRemove && toRemove > -1 && hit == true){
    			 room.enemies.remove(toRemove);
    		 }
    		 
    	 }
    	 
    	 if(myinput.isKeyDown(myinput.KEY_A) || myinput.isKeyDown(myinput.KEY_LEFT) )
         {
    		 facingLeft = true;
    		if(issolid(x-1, y-1, herolocation)){
    			 x-=.4;
    			 herolocation.setLocation(x, y);
    		 }else
    			 onground = true;
         }
    	 if(myinput.isKeyDown(myinput.KEY_D) || myinput.isKeyDown(myinput.KEY_RIGHT))
         {
    		 facingLeft = false;
    		 if(issolid(x+1, y-1, herolocation)){
    			 x+= .4;
    			 herolocation.setLocation(x, y);
    		 }else
    			 onground = true;
         }
    	 if(myinput.isKeyDown(myinput.KEY_W) || myinput.isKeyDown(myinput.KEY_UP))
         {
    		 if(issolid(x, y-1, herolocation) && onground == true){
    			 jumping = true;
    			 onground = false;
    		 }
         }
    	 if(myinput.isKeyDown(myinput.KEY_S) || myinput.isKeyDown(myinput.KEY_DOWN))
         {
    		 if(issolid(x, y, herolocation)){
    			 y += .3;
    			 herolocation.setLocation(x, y);
    		 }
         }
    	 if(myinput.isKeyDown(myinput.KEY_P))
         {
    		for(int i = 0; i < world.map.size();i++)
    			System.out.println(world.map.get(i).xlocation + " " + world.map.get(i).ylocation);
         }
    	 if(issolidfalling(x, y, herolocation)){
    		 y+=.5;
    		 herolocation.setLocation(x, y);
    	 }else
    		 onground = true;
    	 if(jumping && jumplength < 150){
    		 y -=1.0;
    		 herolocation.setLocation(x, y);
    		 jumplength++;
    		 if(jumplength >= 150){
    			 jumplength = 0;
    			 jumping = false;
    		 }
    			 
    	 }
    	 
    	 for(Entity entity : room.enemies)
    	 {
    		 if(entity.y < 700 && issolid(entity.x, entity.y+1, entity.location))
    			 entity.move(entity.x, entity.y+1);
    		 
    		 if(x > entity.x)
    			 entity.move((int)x+1, (int)y);
    		 else
    			 entity.move((int)x-1, (int)y);
    		 
    		 if(entity.collision(herolocation))
    		 {
    			 //This is about where we need to decide if the player dies or whatever
    			 switch(entity.type)
    			 {
    			 case KILL_BOX:
    				 //This is the part where you die
    				 health = 0;
    				 break;
    				 
    			 case ENEMY:
    				 //This is the part where we damage the enemy or something
    				 health -= 1;
    				 //entity.damage(1);
    				 System.out.println("Damage calc, player health: " + health);
    				 break;
    				 
    			 case OBJECT:
    				 //This is the part where maybe there's dialog or something
    				 break;
    				 
    			 case OBSTACLE:
    				 //Probably don't actually need this but it's here
    				 break;
    				 
    			 }
    		 }
    	 }
        
    }
    public boolean issolid(float x, float y, Rectangle herolocation){
		float tmpy = herolocation.getY(), tmpx = herolocation.getX();
		
    	if(y  > gameheight){
    		world.changeroom(room, 0, this);
    	}
    	if(x < 0){
    		world.changeroom(room, 3, this);
    	}
    	if(x > gamewidth){
    		world.changeroom(room, 1, this);
    	}	
    	if(y < 0){
    		world.changeroom(room, 2, this);
    	}
    		
    	
    	herolocation.setLocation(x, y);
    	
    	for(int i = 0; i < room.blocks.size();i++){
    		if(herolocation.intersects(room.blocks.get(i))){
    			herolocation.setLocation(tmpx, tmpy);
    			return false;
    		}
    	}
    	
    	return true;
    }
 
    public boolean issolidfalling(float x, float y, Rectangle herolocation){
		float tmpy = herolocation.getY(), tmpx = herolocation.getX();
		
    	/**if(y  > gameheight){
    		world.changeroom(room, 0, this);
    	}
    	if(x < 0){
    		world.changeroom(room, 3, this);
    	}
    	if(x > gamewidth){
    		world.changeroom(room, 1, this);
    	}	
    	if(y < 0){
    		world.changeroom(room, 2, this);
    	}**/
    		
    	
    	herolocation.setLocation(x, y);
    	
    	for(int i = 0; i < room.blocks.size();i++){
    		if(herolocation.intersects(room.blocks.get(i))){
    			herolocation.setLocation(tmpx, tmpy);
    			return false;
    		}
    	}
    	
    	return true;
    }
    
    
    
    public void render(GameContainer gc, Graphics g) 
			throws SlickException 
    {
    	background.draw(0,0);    	
    	hero.draw(x,y);
    	
    	for(Entity entity : room.enemies)
    	{
    		entity.draw();
    	}
    	
    	for(Entity img : tempImg)
    	{
    		img.draw();
    	}
    	tempImg.clear();
    	
    	Image brick = new Image("res/dirt.png");
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