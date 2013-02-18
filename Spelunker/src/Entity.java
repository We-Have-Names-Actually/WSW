import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;


public class Entity {

	public static enum Type {
		ENEMY, OBJECT, OBSTACLE, KILL_BOX
	}
	
	public Image sprite;
	public Type type;
	public int x;
	public int y;
	private int health = 2;
	public Rectangle location;
	
	Entity(String path)
	{
		try {
			this.sprite = new Image(path);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.x = 0;
		this.y = 0;
		init();
	}
	
	Entity(Image sprite)
	{
		this.sprite = sprite;
		this.x = 0;
		this.y = 0;
		init();
	}
	
	Entity(Image sprite, int x, int y)
	{
		this.sprite = sprite;
		this.x = x;
		this.y = y;
		init();
	}
	
	Entity(String path, int x, int y)
	{
		try {
			this.sprite = new Image(path);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.x = x;
		this.y = y;
		init();
	}
	
	private void init()
	{
		this.location = new Rectangle(this.x, this.y, sprite.getWidth(), sprite.getHeight());
		this.sprite.draw(this.x, this.y);
	}
	
	public void draw()
	{
		this.sprite.draw(this.x, this.y);
	}
	
	/*
	 * @param x top-left corner of the sprite image
	 * @param y top-left corner of the sprite image
	 * @param width Width of the sprite image
	 * @param height Height of the sprite image
	 * @return True if the sprite is colliding with this entity
	 * 
	 */
	public boolean collision(Rectangle sprite)
	{
		int myHeight = this.sprite.getHeight();
		int myWidth = this.sprite.getWidth();
		Rectangle self = new Rectangle(this.x, this.y, myWidth, myHeight);
		if(sprite.intersects(self))
			return true;
		
		return false;
	}
	
	public void setType(Type type)
	{
		this.type = type;
	}
	
	public boolean damage(int damage)
	{
		health -= damage;
		if(health <= 0)
		{
			try {
				sprite.setAlpha((float) 0.0);
				sprite.destroy();
				return false;
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public void move(int x, int y)
	{
		if(type == Type.ENEMY)
		{
			this.x = x;
			this.y = y;
			this.draw();
		}
	}
	
}
