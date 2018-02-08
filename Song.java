package TheLastAssign;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Song {
	String myName;
	int myBeatsPerMinute;
	String mySoundbank;
	ArrayList<MidiTrack> myTracks;

	// The constructor of this class
	public Song() {
		myTracks = new ArrayList<MidiTrack>();
		myBeatsPerMinute = 200;
		mySoundbank = "";
		myName = "Default_Name";
	}

	// GETTER METHODS

	public String getName() {
		return myName;
	}

	public String getSoundbank() {
		return mySoundbank;
	}

	public int getBPM() {
		return myBeatsPerMinute;
	}

	public ArrayList<MidiTrack> getTracks() {
		return myTracks;
	}

	//this method receive filepath as a string
	public Song loadSongFromFile(String file_path) throws IOException {
		Song song_info = new Song();
		String[] temp;
		String line;
		// BufferedReader will read texts line by line
		BufferedReader read = new BufferedReader(new InputStreamReader(
				new FileInputStream(file_path)));

		// Creating an object midiTrack of type MidiTrack
		MidiTrack midiTrack;

		temp = read.readLine().split(" ");
		// While avoiding NullPointerException
		while (temp != null) {
			//Setting all the information about the songs
			//using numerous if-else conditional statements. 
			if (temp[0].equals("bpm")) {
				song_info.myBeatsPerMinute = Integer.valueOf(temp[2]);
			}
			if (temp[0].equals("name")) {
				song_info.myName = temp[2];
			}
			if (temp[0].equals("soundbank")) {
				song_info.mySoundbank = temp[2];
			}
			if (temp[0].equals("instrument")) {
				midiTrack = new MidiTrack(Integer.valueOf(temp[2]));
				temp = read.readLine().split(" ");
				midiTrack.loadNoteString(temp[2]);
				song_info.myTracks.add(midiTrack);
			} else if (temp[0].equals("track")) {
				// Default instrument value is 0 (piano)
				midiTrack = new MidiTrack(0);
				midiTrack.loadNoteString(temp[2]);
				song_info.myTracks.add(midiTrack);
			}
			
			line = read.readLine();
			
			if (line == null) {
				break;
				// otherwise, execute this
			} else {
				temp = line.split(" ");
			}
		}
		
		read.close();
		
		return song_info;
	}

	public void revert() {
		for (int i = 0; i < myTracks.size(); i++) {
			myTracks.get(i).revert();
		}
	}
}