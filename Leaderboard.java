import java.io.*;
/**
 * Leaderboard.java
 * Creates, reads, and edits a file with leaderboard information
 *
 * @author Sahith Konakalla
 * @author Aniruddh Khanwale
 *
 * @recitation section number and recitation instructor's name
 *
 * @date date of completion
 *
 */
public class Leaderboard {



	String[] lboardnames = new String[9]; // array of leaderboard members
	int[] lboardtimes = new int[9]; //array of leaderboard times

	int index = 0;

	String name = "";

	String time = "";
	String src = new File("").getAbsolutePath() + "/src/"; // path to image
	/*
	 * Reads the actual file; this allows it to get the data in a usable format
	 */
	public void readFile() throws IOException {


		FileReader fr = new FileReader(src + "Leaderboard");

		int i = 0;
		while (index < 9) {
			index = Character.getNumericValue((char)(fr.read()));
			i=fr.read();
			while((char)(i) != '&') {
				name = name + (char)(i);
				i=fr.read();
			}
			i=fr.read();
			while((char)(i) != '/') {
				time = time + (char)(i);
				i=fr.read();
			}



			lboardnames[index-1] = name;
			lboardtimes[index-1] = Integer.parseInt(time);

			name = "";
			time = "";
		}
		fr.close();

	}

	public void resetFile() throws IOException {
		File file = new File(src + "Leaderboard");
		file.delete();
		file.createNewFile();
	}
	/*
	 * File Writer
	 * Allows for editing of the leaderboard data
	 */
	public void writeFile() throws IOException {

		FileWriter writer = new FileWriter(src + "Leaderboard");

		for(int i = 0; i < lboardnames.length; i++) {
			writer.write(Integer.toString(i+1));
			writer.write(lboardnames[i]);
			writer.write("&");
			writer.write(Integer.toString(lboardtimes[i]));
			writer.write("/");
		}

		writer.close();

	}
	/*
	 * Does everything:
	 * Reads the file
	 * Edits if the new time is better
	 *
	 */
	public void addToBoard(String Name, int Time) throws IOException {
		this.readFile();

		for(int i = 0; i < lboardtimes.length; i++) {
			if(lboardnames[i].equals(Name) && lboardtimes[i] == Time) {
				return;
			}
		}
		for(int i = 0; i < lboardtimes.length; i++) {
			if (lboardtimes[i]>Time) {
				for(int j = lboardtimes.length-1; j > i; j--) {
					lboardtimes[j] = lboardtimes[j-1];
					lboardnames[j] = lboardnames[j-1];
				}
				lboardtimes[i] = Time;
				lboardnames[i] = Name;
				break;
			}
		}

		this.resetFile();

		this.writeFile();
	}
}
