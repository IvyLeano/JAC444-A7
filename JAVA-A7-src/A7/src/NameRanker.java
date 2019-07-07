
public class NameRanker {
	private int m_year;
	private String m_gender;
	private String m_babyName;
	private String m_fileName;
	private int m_position;

	// default constructor
	NameRanker() {
		m_year = 0;
		m_gender = "\0";
		m_babyName = "\0";
		m_fileName = "\0";
		m_position = 0;
	};

	// setter methods
	void setYear(int year) {
		m_year = year;
	}

	void setGender(String gender) {
		// set gender to "Boy" or "Girl", based on 'M' or 'F' input
		if (gender.charAt(0) == 'M') {
			m_gender = "Boy";
		} else {
			m_gender = "Girl";
		}
	}

	void setBabyName(String name) {
		m_babyName = name;
	}

	void setFileName() {
		String filename = "babynameranking";
		String year =  String.valueOf(getYear());
		m_fileName = filename + year + ".txt";
		System.out.print(m_fileName);
	}

	void setPosition() {
		if(getGender() == "Boy") {
		//m_position = ;
		}
		else {
			//m_position = ;
		}
	}

	// getter methods
	int getYear() {
		return m_year;
	}

	String getGender() {
		return m_gender;
	}

	String getBabyName() {
		return m_babyName;
	}

	String getFileName() {
		return m_fileName;
	}

	int getPosition() {
		return m_position;
	}

	// methods
	// validates user input for year, gender and name
	boolean isValid(int year, String gender, String name) {
		boolean validYear = year >= 2001 && year <= 2010;
		boolean validGender = (gender.length() == 1)
				&& (Character.toUpperCase(gender.charAt(0)) == 'F' || Character.toUpperCase(gender.charAt(0)) == 'M');
		boolean validName = name.chars().allMatch(Character::isLetter) && name.length() != 0;
		return validYear && validGender && validName;
	}

	// sets the state of the current object based on values passed through
	void setState(int year, String gender, String name) {
		setYear(year);
		setBabyName(name);
		setGender(gender);
		setPosition();
		setFileName();
	}
	//reads file and returns ranking as a string
	String getRanking(){
		int rank = 190; //change this to file line, after reading file
		String ranking = getGender() + " name " + getBabyName() + " is ranked #" + rank + " in " + getYear() + " year";
		
		return ranking;
	}

}
