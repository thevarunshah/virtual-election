package classes;

import java.util.HashMap;
import java.util.Map;

public class CTF {
	
	private static Integer idGen = 100;
	final private static Map<Integer, Integer> validationSSNMap = new HashMap<Integer, Integer>();
	final private static Map<Integer, String> idVoteMap = new HashMap<Integer, String>();

	static void updateValidationMap(int validationNum, int ssn){
		
		validationSSNMap.put(validationNum, ssn);
	}
	
	static int addVote(int validationNum, int ssn, String vote){
		
		if(validationSSNMap.containsKey(validationNum)){
			if(validationSSNMap.get(validationNum) == ssn){
				idGen++;
				idVoteMap.put(idGen, vote);
				return idGen;
			}
		}
		
		return -1;
	}
}
