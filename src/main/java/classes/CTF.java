package classes;

import java.util.HashMap;
import java.util.Map;

class CTF {
	
	private static Integer idGen = 100;
	final private static Map<Integer, Integer> validationSSNMap = new HashMap<Integer, Integer>();
	final private static Map<Integer, Integer> validationIDMap = new HashMap<Integer, Integer>();
	final private static Map<Integer, String> idVoteMap = new HashMap<Integer, String>();
	final private static Map<Integer, Boolean> idLockMap = new HashMap<Integer, Boolean>();
	final private static int[] voteTally = new int[4];

	static void updateValidationMap(int validationNum, int ssn){
		
		//security check to prevent double checking
		if(validationSSNMap.containsKey(validationNum)){
			return;
		}
		
		validationSSNMap.put(validationNum, ssn);
	}
	
	static int addVote(int validationNum, int ssn, String vote){
		
		if(validationSSNMap.containsKey(validationNum)){ //validation number is present
			if(validationSSNMap.get(validationNum) == ssn){ //validation number-ssn pair is correct
				if(!validationIDMap.containsKey(validationNum)){ //they weren't assigned an id before
					idGen++;
					validationIDMap.put(validationNum, idGen);
					idVoteMap.put(idGen, vote);
					idLockMap.put(idGen, false);
					return idGen;
				}
				else{
					if(!idLockMap.get(validationIDMap.get(validationNum))){ //they didn't confirm their id, regenerate
						idGen++;
						int previousID = validationIDMap.get(validationNum);
						validationIDMap.remove(validationNum);
						validationIDMap.put(validationNum, idGen);
						idVoteMap.remove(previousID);
						idVoteMap.put(idGen, vote);
						idVoteMap.remove(previousID);
						idLockMap.put(idGen, false);
						return idGen;
					}
				}
			}
		}
		
		return -1;
	}
	
	private static void updateTally(String vote){
		
		if(vote.contains("1")){
			voteTally[0] = voteTally[0]+1;
		}
		else if(vote.contains("2")){
			voteTally[1] = voteTally[1]+1;
		}
		else if(vote.contains("3")){
			voteTally[2] = voteTally[2]+1;
		}
		else if(vote.contains("4")){
			voteTally[3] = voteTally[3]+1;
		}
	}
	
	static void lockVote(int id){
		
		updateTally(idVoteMap.get(id));
		idLockMap.put(id, true);
		
		//tell CLA this person voted
		for(Integer validationNum : validationIDMap.keySet()){
			if(validationIDMap.get(validationNum) == id){
				CLA.updateVotedMap(validationNum);
				break;
			}
		}
	}
	
	static Map<Integer, String> getIDVoteMap(){
		
		return idVoteMap;
	}
	
	static int[] getVoteTally(){
		
		return voteTally;
	}
}
