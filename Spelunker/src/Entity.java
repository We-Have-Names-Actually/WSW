
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;


public class Entity {

	public static enum Type {
		ENEMY, OBJECT, OBSTACLE, KILL_BOX
	}
	
	public Image leftsprite, rightsprite;
	public Type type;
	public float x;
	public float y;
	private int health = 2;
	private int damage = 1;
	public boolean goingleft = true;;
	public Rectangle location;
	
	Entity(String path, String other)
	{
		try {
			this.leftsprite = new Image(path);
			this.rightsprite = new Image(other);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.x = 0;
		this.y = 0;
		init();
	}
	
	Entity(Image sprite, Image other)
	{
		this.leftsprite = sprite;
		this.x = 0;
		this.y = 0;
		init();
	}
	
	Entity(Image sprite, int x, int y)
	{
		this.leftsprite = sprite;
		this.x = x;
		this.y = y;
		init();
	}
	
	Entity(String path, String other, int x, int y)
	{
		try {
			this.leftsprite = new Image(path);
			this.rightsprite = new Image(other);
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
		this.location = new Rectangle(this.x, this.y, leftsprite.getWidth(), leftsprite.getHeight());
		this.leftsprite.draw(this.x, this.y);
	}
	
	public void draw()
	{
		if(goingleft)
			this.leftsprite.draw(this.x, this.y);
		else
			this.rightsprite.draw(this.x, this.y);
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
		int myHeight = this.leftsprite.getHeight();
		int myWidth = this.leftsprite.getWidth();
		Rectangle self = new Rectangle(this.x, this.y, myWidth, myHeight);
		if(sprite.intersects(self))
			return true;
		
		return false;
	}
	
	public void setType(Type type)
	{
		this.type = type;
	}
	
	public boolean applyDamage(int damage)
	{
		health -= damage;
		if(health <= 0)
		{
			try {
				leftsprite.setAlpha((float) 0.0);
				leftsprite.destroy();
				rightsprite.setAlpha((float) 0.0);
				rightsprite.destroy();
				return false;
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public int getDamage()
	{
		return this.damage;
	}
	
	public void setDamage(int damage)
	{
		this.damage = damage;
	}
	
	public void move(float x, float y)
	{
		if(type == Type.ENEMY)
		{
			this.x = x;
			this.y = y;
			this.location = new Rectangle(this.x, this.y, leftsprite.getWidth(), leftsprite.getHeight());
			this.draw();
		}
	}
	
}

