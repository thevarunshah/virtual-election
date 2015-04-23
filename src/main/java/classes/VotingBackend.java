package classes;

import java.util.ArrayList;
import java.util.List;

public class VotingBackend {
	
	private static Integer validationNumGen = 1000;
	private static List<Voter> voters = new ArrayList<Voter>();
	
	public static boolean approveVoter(String name, int ssn){
		
		CLA.buildBase(voters);
		
		if(CLA.isVoterOnList(name, ssn)){
			validationNumGen++;
			CLA.validate(name, validationNumGen);
			for(Voter v : voters){
				if(v.getName().equals(name)){
					v.setValidationNum(validationNumGen);
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static int getValidationNum(String name){
		
		for(Voter v : voters){
			if(v.getName().equals(name)){
				return v.getValidationNum();
			}
		}
		
		return -1;
	}
	
	public static void sendVNumToCTF(String name){
		
		for(Voter v : voters){
			if(v.getName().equals(name)){
				CLA.sendToCTF(v);
			}
		}
	}
	
	public static int getID(int validationNum, int ssn, String vote){
		
		return CTF.addVote(validationNum, ssn, vote);
	}
	
	public static void lockVote(int id){
		
		CTF.lockVote(id);
	}

}
