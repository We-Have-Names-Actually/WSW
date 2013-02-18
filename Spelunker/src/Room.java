import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

import org.newdawn.slick.geom.Rectangle;


public class Room {
	public int[][] terrain;
	public int width, height;
	public boolean northexit, eastexit, southexit, westexit;
	public float westx, westy, eastx, easty, northx, northy, southx, southy;
	public int xlocation, ylocation;
	private static long oldseed = 0;
	public LinkedList<Rectangle> blocks = new LinkedList<Rectangle>();
	
	
	Room(){
		terrain = new int[20][14];
		width = 20;
		height = 14;
		xlocation = 0;
		ylocation = 0;
		westx = 400;
		westy = 300;
		for(int i = 0; i < width; i++){
			terrain[i][height-1] = 1;
			Rectangle brick = new Rectangle(i*50, (height-1)*50, 50, 50);
			blocks.add(brick);
		}
		for(int i = 0; i < 13;i++){
			int place = getRand(0,width-1);
			int y = height-1;
			boolean found = true;
			while(found){
				if(terrain[place][y] != 1){
					terrain[place][y] = 1;
					found = false;
					Rectangle brick = new Rectangle(place*50, y*50, 50, 50);
					blocks.add(brick);
				}
				y--;
			}
		}
	}
	
	Room(int xlocation, int ylocation,  World world){
		westx = 400;
		westy = 400;
		eastx = 400;
		easty = 400;
		northx = 400;
		northy = 400;
		southx = 400;
		southy = 400;
		terrain = new int[20][14];
		width = 20;
		height = 14;
		this.xlocation = xlocation;
		this.ylocation = ylocation;
		if(world.isroom(xlocation, ylocation+1)){
			//if(world.roomat(xlocation, ylocation + 1).southexit){
				southexit = true;
				northx = 400;
				northy = 150;
			//}
		}else if(getRand(0,2) == 0){
			southexit = true;
			northx = 400;
			northy = 150;
		}
		if(world.isroom(xlocation, ylocation-1)){
			//if(world.roomat(xlocation, ylocation - 1).northexit){
				northexit = true;
				southx = 400;
				southy = 150;
			//}
		} else if(getRand(0,1) == 0){
			northexit = true;
			southx = 400;
			southy = 150;
		}
		if(world.isroom(xlocation-1, ylocation)){
			//if(world.roomat(xlocation-1, ylocation).eastexit){
				westexit = true;
				westx = 400;
				westy = 150;
			//}
		}else if(getRand(0,1) == 0){
			westexit = true;
			westx = 400;
			westy = 150;
		}
		if(world.isroom(xlocation+1, ylocation)){
			//if(world.roomat(xlocation+1, ylocation).westexit){
				eastexit = true;
				eastx = 400;
				easty = 150;
			//}
		}else if(getRand(0,1) == 0){
			eastexit = true;
			eastx = 400;
			easty = 150;
		}
		for(int i = 0; i < width-1; i++){

			if(southexit){
				if(i <= 4 || i >= 10 ){
					terrain[i][height-1] = 1;
					Rectangle brick = new Rectangle(i*50, (height-1)*50, 50, 50);
					blocks.add(brick);
				}
			}else{
				terrain[i][height-1] = 1;
				Rectangle brick = new Rectangle(i*50, (height-1)*50, 50, 50);
				blocks.add(brick);
			}

			if(northexit){
				if(i <= 0 || i >= 19 ){
					terrain[i][0] = 1;
					Rectangle brick = new Rectangle(i*50, 0, 50, 50);
					blocks.add(brick);
				}
			}else{
				terrain[i][0] = 1;
				Rectangle brick = new Rectangle(i*50, 0, 50, 50);
				blocks.add(brick);
			}
		}
		
		for(int i = 0; i < height; i++){
			if(westexit){
				if(i <= 1 || i >= 5 ){
					terrain[0][i] = 1;
					Rectangle brick = new Rectangle(0, (i)*50, 50, 50);
					blocks.add(brick);
				}
			}else{
				terrain[0][i] = 1;
				Rectangle brick = new Rectangle(0, (i)*50, 50, 50);
				blocks.add(brick);
			}
			
			if(eastexit){
				if(i <= 1 || i >= 5 ){
					terrain[width-1][i] = 1;
					Rectangle brick = new Rectangle((width-1)*50, i*50, 50, 50);
					blocks.add(brick);
				}
			}else{
				terrain[width-1][i] = 1;
				Rectangle brick = new Rectangle((width-1)*50, i*50, 50, 50);
				blocks.add(brick);
			}
		}
		for(int i = 0; i < 13;i++){
			int place = getRand(1,width-2);
			int y = height-1;
			boolean found = true;
			while(found){
				
				if(terrain[place][y] != 1){
					terrain[place][y] = 1;
					found = false;
					Rectangle brick = new Rectangle(place*50, y*50, 50, 50);
					blocks.add(brick);
				}
				y--;
				if(y < 0)
					found = false;
			}
		}
	}
	
	public static int getRand(int min, int max){
		//instantiate a new date function
		Date now = new Date();
		//make a new seed based on the previous seed and the time since the epoch
		long seed = now.getTime()	+ oldseed;
		//set this to be the new oldseed
		oldseed = seed;
 
		//make a new randomizer seeded with the new seed
		Random randomizer = new Random(seed);
		int n = 1+max - min;
		int i = randomizer.nextInt(n);
		//if for some reason it ends up negative turn it positive
		if (i < 0)
			i = -i;
		//return the random value plus the minimum value
		return min + i;
	}
}
