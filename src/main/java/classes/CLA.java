package classes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

class CLA {

	final private static Map<String, Integer> nameSSNMap = new HashMap<String, Integer>(); //name-ssn map
	final private static Map<String, Integer> nameValidationMap = new HashMap<String, Integer>(); //name-validation number map
		
	static void buildBase(List<Voter> voters){
		
		//don't initialize twice
		if(nameSSNMap.size() >= 1){
			return;
		}
		
		//generate database and add to name-ssn map and the list of voters
		for(int i = 0; i < 25; i++){
			Voter v = new Voter("voter"+i);
			int ssn = 100000000;
			ssn += i;
			nameSSNMap.put(v.getName(), ssn);
			v.setValidationNum(-1);
			v.setIdNum(-1);
			voters.add(v);
		}
	}
	
	static boolean isVoterOnList(String name, int ssn){
		
		if(nameSSNMap.containsKey(name)){
			if(nameSSNMap.get(name) == ssn){
				return true;
			}
		}
		
		return false;
	}
	
	static void validate(String name, Integer validationNum){
		
		nameValidationMap.put(name, validationNum);
	}
	
	static void sendToCTF(String name){
		
		//send over the validation number and ssn to the ctf
		CTF.updateValidationMap(nameValidationMap.get(name), nameSSNMap.get(name));
	}
	
	static Map<String, String> whoVoted(){
		
		Map<String, String> whoVoted = new HashMap<String, String>();
		//generate a map of who voted and who didn't
		for(Entry<String, Integer> e : nameSSNMap.entrySet()){
			if(nameValidationMap.containsKey(e.getKey())){
				whoVoted.put(e.getKey(), "Yes");
			}
			else{
				whoVoted.put(e.getKey(), "No");
			}
		}
		
		return whoVoted;
	}
}
