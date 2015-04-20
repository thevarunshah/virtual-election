package classes;

import java.util.ArrayList;
import java.util.List;

public class VotingBackend {
	
	static Integer validationNumGen = 1000;
	static List<Voter> voters = new ArrayList<Voter>();
	
	public static void approveVoter(String name, String ssn){
		
		CLA.buildBase(voters);
		
		if(CLA.isVoterOnList(name, ssn)){
			validationNumGen++;
			CLA.validate(name, validationNumGen);
			for(Voter v : voters){
				if(v.getName().equals(name)){
					v.setValidationNum(validationNumGen);
					return;
				}
			}
		}
		else{
			System.out.println("voter is not on the list or ssn doesn't match");
			return;
		}
	}
	
	public static int getValidationNum(String name){
		
		for(Voter v : voters){
			if(v.getName().equals(name)){
				return v.getValidationNum();
			}
		}
		
		return -1;
	}

}
