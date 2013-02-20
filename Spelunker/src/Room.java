import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import org.newdawn.slick.geom.Rectangle;


public class Room {
	public char[][] terrain;
	public int width, height;
	public boolean northexit, eastexit, southexit, westexit;
	public float westx, westy, eastx, easty, northx, northy, southx, southy;
	public int xlocation, ylocation;
	private static long oldseed = 0;
	public LinkedList<Rectangle> blocks = new LinkedList<Rectangle>();
	public ArrayList<Entity> enemies = new ArrayList<Entity>();
	
	
	Room(){
		terrain = new char[20][14];
		width = 20;
		height = 14;
		xlocation = 0;
		ylocation = 0;
		westx = 400;
		westy = 300;
		northexit = true;
		westexit = true;
		eastexit = true;
		for(int i = 0; i < width; i++){
			terrain[i][height-1] = '1';
			Rectangle brick = new Rectangle(i*50, (height-1)*50, 50, 50);
			blocks.add(brick);
		}
		for(int i = 0; i < 13;i++){
			int place = getRand(0,width-1);
			int y = height-1;
			boolean found = true;
			while(found){
				if(terrain[place][y] != '1'){
					terrain[place][y] = '1';
					found = false;
					Rectangle brick = new Rectangle(place*50, y*50, 50, 50);
					blocks.add(brick);
				}
				y--;
			}
		}
	}
	Room(int xlocation, int ylocation,  World world, String source){
		terrain = new char[20][14];
		width = 20;
		height = 14;
		this.xlocation = xlocation;
		this.ylocation = ylocation;
		if(world.isroom(xlocation, ylocation+1)){
			if(world.roomat(xlocation, ylocation + 1).northexit){
				southexit = true;
			}
		}else if(getRand(0,3) == 1){
			southexit = true;
		}
		if(world.isroom(xlocation, ylocation-1)){
			if(world.roomat(xlocation, ylocation - 1).southexit){
				northexit = true;
			}
		}else if(getRand(0,3) == 1){
			northexit = true;
		}
		if(world.isroom(xlocation-1, ylocation)){
			if(world.roomat(xlocation-1, ylocation).eastexit){
				westexit = true;
			}
		}else if(getRand(0,2) == 1){
			westexit = true;
		}
		if(world.isroom(xlocation+1, ylocation)){
			if(world.roomat(xlocation+1, ylocation).westexit){
				eastexit = true;
			}
		}else if(getRand(0,2) == 1){
			eastexit = true;
		}
		
		Scanner input = null;
    	try {
			input = new Scanner(new BufferedReader(new FileReader("res/rooms.txt")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	int c = 0;
    	westx = 500;
		westy = 400;
		eastx = 500;
		easty = 400;
		northx = 500;
		northy = 400;
		southx = 500;
		southy = 400;
		
		int numrooms = input.nextInt();
		
		int iterator = getRand(0, numrooms-1);
		
		for(int i = 0; i <= (iterator * 15); i++){
			input.nextLine();
		}
		
    	
    	world.possible--;
    	if(northexit && world.isroom(xlocation, ylocation-1) == false)
			world.possible++;
		if(eastexit && world.isroom(xlocation+1, ylocation) == false)
			world.possible++;
		if(westexit && world.isroom(xlocation-1, ylocation) == false)
			world.possible++;
		if(southexit && world.isroom(xlocation, ylocation+1) == false)
			world.possible++;
		System.out.println(world.possible);
		if(world.possible == 0){
			System.out.println("trying to keep your game from being fucked.");
			boolean working = true;
			int count = 0;
			while(working){
				int choice = getRand(0,3);
				if(choice == 0 && world.isroom(xlocation, ylocation-1) == false && northexit == false){
					working = false;
					northexit = true;
					world.possible++;
				}
				if(choice == 1 && world.isroom(xlocation+1, ylocation) == false && eastexit == false){
					working = false;
					eastexit = true;
					world.possible++;
				}
				if(choice == 2 && world.isroom(xlocation, ylocation+1) == false && southexit == false){
					working = false;
					southexit = true;
					world.possible++;
				}
				if(choice == 3 && world.isroom(xlocation-1, ylocation) == false && southexit == false){
					working = false;
					southexit = true;
					world.possible++;
				}
				count++;
				if(count > 15){
					working = false;
					System.out.println("sorry your game is fucked.");
				}
			}
		}
		
		
		for(int i = 0; i < 280; i++){
			terrain[c%20][(c)/20] = input.next().charAt(0);
			if((terrain[c%20][c/20] == 'n' || terrain[c%20][c/20] == 'N')&& northexit == false){
				terrain[c%20][c/20] = '1';
			}
			if((terrain[c%20][c/20] == 'w' || terrain[c%20][c/20] == 'W')&& westexit == false){
				terrain[c%20][c/20] = '1';
			}
			if((terrain[c%20][c/20] == 'e' || terrain[c%20][c/20] == 'E') && eastexit == false){
				terrain[c%20][c/20] = '1';
			}
			if((terrain[c%20][c/20] == 's') && southexit == false){
				terrain[c%20][c/20] = '1';
			}
			if(terrain[c%20][c/20] == 'S'){
				northx = (c%20)*50;
				northy = (c/20)*50;
			}
			if(terrain[c%20][c/20] == 'N'){
				southx = (c%20)*50;
				southy = (c/20)*50;
			}
			if(terrain[c%20][c/20] == 'W'){
				westx = (c%20)*50;
				westy = (c/20)*50;
			}
			if(terrain[c%20][c/20] == 'E'){
				eastx = (c%20)*50;
				easty = (c/20)*50;
			}
				
			if(terrain[c%20][(c)/20] == '1'){
				Rectangle brick = new Rectangle((c%20)*50, ((c)/20)*50, 49, 49);
				blocks.add(brick);
			}
			c++;
	}
		
	}
	
	 public void read() throws IOException{
	    	Scanner input = null;
	    	input = new Scanner(new BufferedReader(new FileReader("res/rooms.txt")));
	    	int c = 0;
	    	while(input.hasNext()){
	    			System.out.println(input.next());
	    			c++;
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
		terrain = new char[20][14];
		width = 20;
		height = 14;
		this.xlocation = xlocation;
		this.ylocation = ylocation;
		if(world.isroom(xlocation, ylocation+1)){
			if(world.roomat(xlocation, ylocation + 1).northexit){
				southexit = true;
				northx = 400;
				northy = 150;
			}
		}else if(getRand(0,2) == 0){
			southexit = true;
			northx = 400;
			northy = 150;
		}
		if(world.isroom(xlocation, ylocation-1)){
			if(world.roomat(xlocation, ylocation - 1).southexit){
				northexit = true;
				southx = 400;
				southy = 150;
			}
		} else if(getRand(0,1) == 0){
			northexit = true;
			southx = 400;
			southy = 150;
		}
		if(world.isroom(xlocation-1, ylocation)){
			if(world.roomat(xlocation-1, ylocation).eastexit){
				westexit = true;
				westx = 400;
				westy = 150;
			}
		}else if(getRand(0,1) == 0){
			westexit = true;
			westx = 400;
			westy = 150;
		}
		if(world.isroom(xlocation+1, ylocation)){
			if(world.roomat(xlocation+1, ylocation).westexit){
				eastexit = true;
				eastx = 400;
				easty = 150;
			}
		}else if(getRand(0,1) == 0){
			eastexit = true;
			eastx = 400;
			easty = 150;
		}
		for(int i = 0; i < width-1; i++){

			if(southexit){
				if(i <= 4 || i >= 10 ){
					terrain[i][height-1] = '1';
					Rectangle brick = new Rectangle(i*50, (height-1)*50, 49, 49);
					blocks.add(brick);
				}
			}else{
				terrain[i][height-1] = '1';
				Rectangle brick = new Rectangle(i*50, (height-1)*50, 49, 49);
				blocks.add(brick);
			}

			if(northexit){
				if(i <= 0 || i >= 19 ){
					terrain[i][0] = '1';
					Rectangle brick = new Rectangle(i*50, 50, 49, 49);
					blocks.add(brick);
				}
			}else{
				terrain[i][0] = '1';
				Rectangle brick = new Rectangle(i*50, 50, 49, 49);
				blocks.add(brick);
			}
		}
		int exitheight = getRand(0, 8);
		for(int i = 0; i < height; i++){
			if(westexit){
				if(i <= 1 + exitheight || i >= 5 + exitheight ){
					terrain[0][i] = '1';
					Rectangle brick = new Rectangle(0, (i)*50, 49, 49);
					blocks.add(brick);
				}
			}else{
				terrain[0][i] = '1';
				Rectangle brick = new Rectangle(0, (i)*50, 49, 49);
				blocks.add(brick);
			}
			
			if(eastexit){
				if(i <= 1 + exitheight || i >= 5 + exitheight ){
					terrain[width-1][i] = '1';
					Rectangle brick = new Rectangle((width-1)*50, i*50, 49, 49);
					blocks.add(brick);
				}
			}else{
				terrain[width-1][i] = '1';
				Rectangle brick = new Rectangle((width-1)*50, i*50, 49, 49);
				blocks.add(brick);
			}
		}
		for(int i = 0; i < 13;i++){
			int place = getRand(1,width-2);
			int y = height-1;
			boolean found = true;
			while(found){
				
				if(terrain[place][y] != '1'){
					terrain[place][y] = '1';
					found = false;
					Rectangle brick = new Rectangle(place*50, y*50, 49, 49);
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
