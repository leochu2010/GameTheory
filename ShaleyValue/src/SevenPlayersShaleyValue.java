import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SevenPlayersShaleyValue {

	class PlayerPermutation{
		
		private List<Integer> players = new ArrayList<Integer>();
		
		public PlayerPermutation(int[] playerArray){
			for(int i=0;i<playerArray.length;i++){
				players.add(playerArray[i]);
			}
		}
		
		public PlayerPermutation addPlayer(int player){
			
			int[] playerArray = new int[players.size()+1];
			for(int i=0;i<players.size();i++){
				playerArray[i]=players.get(i);
			}
			playerArray[players.size()] = player;
			
			return new PlayerPermutation(playerArray);
		}
						
		public List<Integer> getPlayers(){
			return players;
		}
		
		public PlayerPermutation getPreceedingPlayerPermutation(int player){
			
			int preceedingSize = 0;
			for(int i=0; i<players.size(); i++){
				if(players.get(i) == player){
					preceedingSize = i;
					break;
				}
			}
			
			int[] preceedingPlayers = new int[preceedingSize];
			for(int i=0; i<preceedingSize; i++){
				preceedingPlayers[i] = players.get(i); 
			}
			
			return new PlayerPermutation(preceedingPlayers);
		}
		
		public void printPlayers(){
			if(players.size()==0){
				System.out.print("{0}");
				return;
			}
			System.out.print("{");
			for(int i=0; i<players.size(); i++){
				System.out.print(players.get(i));
			}
			System.out.print("}");
			//System.out.println();
		}
		
	}	
	
	public static void main(String[] args) {
		
		SevenPlayersShaleyValue s = new SevenPlayersShaleyValue();		
		s.run();		
		
	}
	
	@SuppressWarnings("serial")
	private List<Integer> greenPlayers = new ArrayList<Integer>(){{
	    add(3);
	    add(4);
	    add(5);
	    add(6);
	    add(7);	    
	}};
	
	@SuppressWarnings("serial")
	private List<Integer> redPlayers = new ArrayList<Integer>(){{
	    add(1);
	    add(2);	    	    
	}};
	
	private int getDeltaValue(PlayerPermutation playerPermutation, int player){
		if(playerPermutation.getPlayers().size() == 0){
			return 0;
		}
		
		int addPlayerValue = this.getValue(playerPermutation.addPlayer(player));
		int value = this.getValue(playerPermutation);
		System.out.print(value+"->"+addPlayerValue+"=");
				
		return addPlayerValue - value;
		
	}
	
	private int getValue(PlayerPermutation playerPermutation){	
			
		int green = 0;
		int red = 0;		
		List<Integer> players = playerPermutation.getPlayers();
		for(int i=0; i< players.size(); i++){
			int player = players.get(i);
			if (greenPlayers.contains(player)){
				green +=1;
			};
			
			if (redPlayers.contains(player)){
				red +=1;
			};
		}
		
		if(red > 0 && green > 0){
			if (green >= red){
				return red;
			}
		
			if(green <red){
				return green;
			}
		}
		
		return 0;
	}
	
	/*
	private int getValueTest(PlayerPermutation playerPermutation){
		List<Integer> players = playerPermutation.getPlayers();
		if(players.contains(1) && players.contains(2) && players.contains(3)){
			System.out.println("V[123]");
			return 1; 
		}
		
		if(players.contains(1) && players.contains(3)){
			System.out.println("V[13]");
			return 1; 
		}
		
		if(players.contains(1) && players.contains(2)){
			System.out.println("v[12]");
			return 1; 
		}
		
		return 0;
	}*/
	
	public void run(){
			
		//setup value function
		int numOfPlayers = 7;		
		
		int factorial = 1;
		for(int i=0;i<numOfPlayers;i++){
			factorial*= numOfPlayers-i;
		}
		//System.out.println(factorial);
		
		Map<Integer,Integer> playerValueMap = new HashMap<Integer,Integer>();
		for(int i=0;i<numOfPlayers;i++){
			int player = i+1;
			playerValueMap.put(player, 0);	
		}
		
		Permutation p = new Permutation(numOfPlayers,numOfPlayers);
	    while (p.hasNext()) {
	    	int[] players = p.next();
	    	for (int i=0; i<players.length; i++){
	    		players[i]+=1;
	    	}
			PlayerPermutation playerPermutation = new PlayerPermutation(players);
			System.out.println("");
			playerPermutation.printPlayers();
			System.out.println(":");
			
			for(int i=0;i<numOfPlayers;i++){
				int player = i+1;
				System.out.print("player "+player+":");
				PlayerPermutation preceedingPlayers = playerPermutation.getPreceedingPlayerPermutation(player);
				preceedingPlayers.printPlayers();
				int deltaValue = getDeltaValue(preceedingPlayers, player);
				System.out.println(":"+deltaValue);
				int playerValue = playerValueMap.get(player) + deltaValue;				
				
				playerValueMap.put(player, playerValue);
								
			}
	    }
		
	    System.out.println();
	    System.out.println("Shaley Value=");
	    
		for (int i=0; i<numOfPlayers; i++){
    		int player = i+1;
    		RationalNumber sv = new RationalNumber(+playerValueMap.get(player),factorial);    		
    		System.out.println("player "+player+":"+sv +" or " + ((float)playerValueMap.get(player))/factorial);
    	}

	}

}
