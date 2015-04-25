package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VotingBackend {
	
	private static Integer validationNumGen = 1000; //validation number generator
	private static List<Voter> voters = new ArrayList<Voter>(); //list of voters
	
	public static boolean approveVoter(String name, int ssn){
		
		CLA.buildBase(voters); //build the voter database
		
		//match name-ssn pair
		if(CLA.isVoterOnList(name, ssn)){
			for(Voter v : voters){
				if(v.getName().equals(name)){
					if(v.getValidationNum() == -1){ //make sure a validation number was not already assigned
						validationNumGen++;
						CLA.validate(name, validationNumGen); //add name-validation number pair
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
				//tell cla to send validation number of this voter to the ctf
				CLA.sendToCTF(v.getName()); 
			}
		}
	}
	
	public static int getID(int validationNum, int ssn, String vote){
		
		return CTF.addVote(validationNum, ssn, vote); //add the vote
	}
	
	public static void lockVote(int id){
		
		CTF.lockVote(id); //lock the vote
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
