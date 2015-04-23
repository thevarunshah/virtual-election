package classes;

import java.util.HashMap;
import java.util.Map;

public class CTF {
	
	private static Integer idGen = 100;
	final private static Map<Integer, Integer> validationSSNMap = new HashMap<Integer, Integer>();
	final private static Map<Integer, Integer> validationIDMap = new HashMap<Integer, Integer>();
	final private static Map<Integer, String> idVoteMap = new HashMap<Integer, String>();
	final private static Map<Integer, Boolean> idLockMap = new HashMap<Integer, Boolean>();

	static void updateValidationMap(int validationNum, int ssn){
		
		if(validationSSNMap.containsKey(validationNum)){
			return;
		}
		
		validationSSNMap.put(validationNum, ssn);
	}
	
	static int addVote(int validationNum, int ssn, String vote){
		
		if(validationSSNMap.containsKey(validationNum)){
			if(validationSSNMap.get(validationNum) == ssn){
				if(!validationIDMap.containsKey(validationNum)){
					idGen++;
					validationIDMap.put(validationNum, idGen);
					idVoteMap.put(idGen, vote);
					idLockMap.put(idGen, false);
					return idGen;
				}
				else{
					if(!idLockMap.get(validationIDMap.get(validationNum))){
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
	
	static void lockVote(int id){
		
		idLockMap.put(id, true);
	}
	
	static Map<Integer, String> getIDVoteMap(){
		
		return idVoteMap;
	}
}
