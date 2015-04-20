package classes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CLA {

	final private static Map<String, Integer> nameKeyMap = new HashMap<String, Integer>();
	final private static Map<String, Integer> nameValidationMap = new HashMap<String, Integer>();
		
	static void buildBase(List<Voter> voters){
		
		if(nameKeyMap.size() >= 1){
			return;
		}
		
		for(int i = 0; i < 25; i++){
			Voter v = new Voter("voter"+i);
			int ssn = 100000000;
			ssn += i;
			nameKeyMap.put(v.getName(), ssn);
			voters.add(v);
		}
	}
	
	static boolean isVoterOnList(String name, String ssn){
		
		if(nameKeyMap.containsKey(name)){
			if(nameKeyMap.get(name) == Integer.parseInt(ssn)){
				return true;
			}
		}
		
		return false;
	}
	
	static void validate(String name, Integer validationNum){
		
		nameValidationMap.put(name, validationNum);
		
	}
}
