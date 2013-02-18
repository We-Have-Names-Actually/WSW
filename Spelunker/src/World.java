import java.util.LinkedList;


public class World {
	public LinkedList<Room> map = new LinkedList<Room>();
	
	World(){
		Room start = new Room();
		map.add(start);
	}
	
	public boolean isroom(int x, int y){
		for(int i = 0; i < map.size(); i++){
			if(map.get(i).xlocation == x && map.get(i).ylocation == y){
				return true;
			}
		}
		return false;
	}
	
	public Room roomat(int x, int y){
		for(int i = 0; i < map.size(); i++){
			if(map.get(i).xlocation == x && map.get(i).ylocation == y){
				return map.get(i);
			}
		}
		return map.getFirst();
	}
	
	
	public Room changeroom(Room current, int direction, Wrapper where){
		if(direction == 0){//north
			System.out.println("going north");
			for(int i = 0; i < map.size(); i++){
				if(map.get(i).xlocation == current.xlocation && map.get(i).ylocation == current.ylocation + 1){
					where.x = map.get(i).southx;
					where.y = map.get(i).southy;
					where.room = map.get(i);
					return map.get(i);
				}
			}
			Room room = new Room(current.xlocation, current.ylocation+1,this);
			where.x = room.southx;
			where.y = room.southy;
			map.add(room);
			where.room = room;
			return room;
		}
		if(direction == 1){//east
			System.out.println("going east");
			for(int i = 0; i < map.size(); i++){
				if(map.get(i).xlocation == current.xlocation + 1 && map.get(i).ylocation == current.ylocation){
					where.x = map.get(i).westx;
					where.y = map.get(i).westy;
					where.room = map.get(i);
					return map.get(i);
				}
			}
			Room room = new Room(current.xlocation+1, current.ylocation,this);
			where.x = room.westx;
			where.y = room.westy;
			map.add(room);
			where.room = room;
			return room;
		}
		if(direction == 2){//south
			System.out.println("going south");
			for(int i = 0; i < map.size(); i++){
				if(map.get(i).xlocation == current.xlocation && map.get(i).ylocation == current.ylocation - 1){
					where.x = map.get(i).northx;
					where.y = map.get(i).northy;
					where.room = map.get(i);
					return map.get(i);
				}
			}
			Room room = new Room(current.xlocation, current.ylocation-1, this);
			where.x = room.northx;
			where.y = room.northy;
			map.add(room);
			where.room = room;
			return room;
		}
		if(direction == 3){//west
			System.out.println("going west");
			for(int i = 0; i < map.size(); i++){
				if(map.get(i).xlocation == current.xlocation - 1 && map.get(i).ylocation == current.ylocation){
					where.x = map.get(i).eastx;
					where.y = map.get(i).easty;
					where.room = map.get(i);
					return map.get(i);
				}
			}
			Room room = new Room(current.xlocation-1, current.ylocation, this);
			where.x = room.eastx;
			where.y = room.easty;
			map.add(room);
			where.room = room;
			return room;
		}
		return map.getFirst();
	}
}
