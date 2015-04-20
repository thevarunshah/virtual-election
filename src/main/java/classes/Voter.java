package classes;

public class Voter {
	
	private String name;
	private int validationNum;
	private int idNum;
	//boolean for if they can vote or not
	
	public Voter(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	int getValidationNum() {
		return validationNum;
	}

	void setValidationNum(int validationNum) {
		this.validationNum = validationNum;
	}

	int getIdNum() {
		return idNum;
	}

	void setIdNum(int idNum) {
		this.idNum = idNum;
	}

}
