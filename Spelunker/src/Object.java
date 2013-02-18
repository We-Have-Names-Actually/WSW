import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Object {

	private Image sprite;
	private int x;
	private int y;
	
	Object(String path)
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
	
	Object(Image sprite)
	{
		this.sprite = sprite;
		this.x = 0;
		this.y = 0;
		init();
	}
	
	Object(Image sprite, int x, int y)
	{
		this.sprite = sprite;
		this.x = x;
		this.y = y;
		init();
	}
	
	Object(String path, int x, int y)
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
		this.sprite.draw(this.x, this.y);
	}
}
