import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Arrays;

public class NameRanker {
	private int m_year;
	private String m_gender;
	private String m_babyName;
	private String m_fileName;

	// default constructor
	NameRanker() {
		m_year = 0;
		m_gender = "\0";
		m_babyName = "\0";
		m_fileName = "\0";
	};

	// setter methods
	void setYear(int year) {
		m_year = year;
	}

	void setGender(String gender) {
		// set gender to "Boy" or "Girl", based on 'M' or 'F' input
		if (Character.toUpperCase(gender.charAt(0)) == 'M') {
			m_gender = "Boy";
		} else {
			m_gender = "Girl";
		}
	}

	void setBabyName(String name) {
		m_babyName = name;
	}

	void setFileName() {
		String filename = "babynamesranking";
		String year = String.valueOf(getYear());
		m_fileName = filename + year + ".txt";
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
		setFileName();

	}

	// reads file and returns ranking as a string
	String getRanking() {
		String rank = "\0"; // this is the ranking number i.e "#160"
		String ranking = "\0"; // this is the whole line i.e "Boy named Javier is ranked....."

		try {
			BufferedReader reader = new BufferedReader(new FileReader(getFileName()));
			String line = reader.readLine();
			// iterate until name is found, then break
			again: while (line != null) {
				String[] babyNameData = line.split(" "); // separates each line by a space delimiter, into an array of
				//isolates boyname and girl name into their own strings											// data
				String boyName = (babyNameData[1].replaceAll("[^a-zA-Z0-9]", "")).replaceAll("[^a-zA-Z]", ""); 
				String girlName = babyNameData[2].trim(); // isolate girls name

				// if the user was searching for a boy name and the name input matches one in
				// the file then set the ranking (factors unisex names).
				//compares strings not objects in memory
				if (getGender() == "Boy" && boyName.equalsIgnoreCase(getBabyName())) { 
					rank = babyNameData[0];
					break again;
				}
				if (getGender() == "Girl" && girlName.equalsIgnoreCase(getBabyName())) {
					rank = babyNameData[0];
					break again;
				}

				// read the next line in the file
				line = reader.readLine();
			}

			reader.close();
		} catch (Exception e) {
			System.out.print(e + "\n");
		}

		if (rank != "\0") { // if the name was found in file then create output message
			ranking = getGender() + " name " + getBabyName() + " is ranked #" + rank + " in " + getYear() + " year";
		} else {
			ranking = getGender() + " name " + getBabyName() + " is not on the " + getYear() + " ranking list.";

		}
		return ranking;
	}

}
