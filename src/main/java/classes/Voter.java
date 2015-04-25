package classes;

class Voter {
	
	private String name;
	private int validationNum;
	private int idNum;
	
	Voter(String name){
		this.name = name;
	}

	String getName() {
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
