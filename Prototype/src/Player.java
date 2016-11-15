import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
	public int id;
	public String name;
	public List<Card> playerCard;
	public List<String> autoNameSet;
	
	public Player() {
		generateAutoNameSet();
		Random ran = new Random();
		id = ran.nextInt(100);
		name = (String)autoNameSet.get(ran.nextInt(9));
		this.playerCard = new ArrayList<Card>();
	}
	
	public Player(int id, String name) {
		this.id = id;
		this.name = name;
		this.playerCard = new ArrayList<Card>();
	}
	
	public String getPlayerInfo(){
		return "["+this.id+"]" + " " + this.name;
	}
	
	public void generateAutoNameSet(){
		autoNameSet = new ArrayList<String>();
		autoNameSet.add("Jaina");
		autoNameSet.add("Rexxar");
		autoNameSet.add("Uther");
		autoNameSet.add("Garrosh");
		autoNameSet.add("Malfurion");
		autoNameSet.add("Gul'dan");
		autoNameSet.add("Thrall");
		autoNameSet.add("Anduin");
		autoNameSet.add("Valeera");
	}
}
