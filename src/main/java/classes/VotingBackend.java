package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VotingBackend {
	
	private static Integer validationNumGen = 1000;
	private static List<Voter> voters = new ArrayList<Voter>();
	
	public static boolean approveVoter(String name, int ssn){
		
		CLA.buildBase(voters);
		
		if(CLA.isVoterOnList(name, ssn)){
			for(Voter v : voters){
				if(v.getName().equals(name)){
					if(v.getValidationNum() == -1){
						validationNumGen++;
						CLA.validate(name, validationNumGen);
						v.setValidationNum(validationNumGen);
						return true;
					}
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
	
	public static Map<Integer, String> getIDVoteMap(){
		
		return CTF.getIDVoteMap();
	}
	
	public static int[] getVoteTally(){
		
		return CTF.getVoteTally();
	}
	
	public static Map<String, String> getWhoVotedMap(){
		
		return CLA.whoVoted();
	}
}
