package se.su.ovning3;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Exercise3 {

	private final List<Recording> recordings = new ArrayList<>();

	public void exportRecordings(String fileName) {
		try {	
			FileWriter fileWriter = new FileWriter(fileName);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			
			for (Recording recording : recordings){
				printWriter.println("<recording>");
				printWriter.println("\t<artist>" + recording.getArtist() + "</artist>");
				printWriter.println("\t<title>" + recording.getTitle() + "</title>");
				printWriter.println("\t<year>" + recording.getYear() + "</year>");
				printWriter.println("\t<genres>");
				for(String genre : recording.getGenre()){
					printWriter.println("\t\t<genre>" + genre + "</genre>");
				}
				printWriter.println("\t</genres>");
				printWriter.println("</recording>");
			}
		printWriter.close();

		} catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		

	}

	public void importRecordings(String fileName) {
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader reader = new BufferedReader(fileReader);
			String line;
			line = reader.readLine();
			// int noOfRecordings = Integer.parseInt(line);
			while ((line = reader.readLine()) != null) {
				recordings.add(parseLine(line, reader));
			}
			fileReader.close();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Map<Integer, Double> importSales(String fileName) {

		try{
			FileInputStream fis = new FileInputStream(fileName);
			DataInputStream dis = new DataInputStream(fis);

			Map<Integer, Double> returnMap = new HashMap<>();

			int NoI = dis.readInt();
			for(int i = 0; i< NoI; i++){
				int year = dis.readInt();
				int month = dis.readInt();
				int day = dis.readInt();
				double cash = dis.readDouble();

				if(!returnMap.containsKey(year * 100 + month)){
					returnMap.put((year * 100 + month), cash);
				} else {
					double value = returnMap.get(year * 100 + month);
					returnMap.put((year * 100 + month), cash + value);
					
				}	
			}
			dis.close();
			return returnMap;
			


		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public List<Recording> getRecordings() {
		return Collections.unmodifiableList(recordings);
	}

	public void setRecordings(List<Recording> recordings) {
		this.recordings.clear();
		this.recordings.addAll(recordings);
	}

	public Recording parseLine(String line, BufferedReader reader) {
		String[] split = line.split(";");
		String artist = split[0];
		String title = split[1];
		int year = Integer.parseInt(split[2]);
		try{
		String line2 = reader.readLine();
		int noOfGenres = Integer.parseInt(line2);
		Recording returnRecording = new Recording(title, artist, year, parseGenre(noOfGenres, reader));
		return returnRecording;
		}catch (IOException e){
			e.printStackTrace();
		}
		return null;
	}

	public Set<String> parseGenre(int n, BufferedReader reader){
		Set<String> genres = new HashSet<>();
		for (int i = 0; i < n; i++){
			try{
				String line = reader.readLine();
				genres.add(line);

				
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return genres;
	}
}
