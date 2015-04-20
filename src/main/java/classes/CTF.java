package classes;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class CTF {
	
	final private Map<Integer, BigInteger> validationKeyMap;
	final private Map<Integer, Integer> idVoteMap;
	
	public CTF(){
		this.validationKeyMap = new HashMap<Integer, BigInteger>();
		this.idVoteMap = new HashMap<Integer, Integer>();
	}
}
