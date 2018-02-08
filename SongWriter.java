package TheLastAssign;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Hashtable;

public class SongWriter {
	private Hashtable<Integer, String> pitchToNote;

	
	public SongWriter() {
		this.initPitchToNoteDictionary();
	}

	public void initPitchToNoteDictionary() {
		pitchToNote = new Hashtable<Integer, String>();
		pitchToNote.put(60, "C");
		pitchToNote.put(61, "C#");
		pitchToNote.put(62, "D");
		pitchToNote.put(63, "D#");
		pitchToNote.put(64, "E");
		pitchToNote.put(65, "F");
		pitchToNote.put(66, "F#");
		pitchToNote.put(67, "G");
		pitchToNote.put(68, "G#");
		pitchToNote.put(69, "A");
		pitchToNote.put(70, "A#");
		pitchToNote.put(71, "B");
	}

	// This method converts a single MidiNote to its notestring representation
	public String noteToString(MidiNote note) {
		String result = "";
	
		int duration = note.getDuration();
		int octave = note.getOctave();
		int pitch = note.getPitch();
		
		if (note.isSilent()) {
			
			result = duration + "P";
		} else {
			// otherwise, if the duration is not equal to one:
			if (duration != 1) {
				// strings have the following :
				result += duration;
				// add || subtracting for finding the corresponding note symbol in the pitchToNote hashtable
				result += pitchToNote.get(pitch + (-octave * 12));
			}
		}
		return result;
	}

	// This method converts a MidiTrack to its notestring representation.
	// You should use the noteToString method here
	public String trackToString(MidiTrack track) {
		ArrayList<MidiNote> notes = track.getNotes();
		String result = "";
		int previous_octave = 0;
		MidiNote current_note;
		// TODO: Q4.b.

		
		result += "instrument = " + track.getInstrumentId() + "\n";
		result += "track = ";
		for (MidiNote note : notes) {
			current_note = note;
			// compare current octave with previous octave
			if (current_note.getOctave() > previous_octave) { // increase octave
				for (int i = 0; i < (current_note.getOctave() - previous_octave); i++) {
					result += ">";
				}
			} else if (current_note.getOctave() < previous_octave) { // decrease
																		// octave
				for (int i = 0; i < (previous_octave - current_note.getOctave()); i++) {
					result += "<";
				}
			}
			result += noteToString(current_note);
			previous_octave = note.getOctave();
		}
		return result;
	}

	// TODO Q4.c.
	// Implement the void writeToFile( Song s1 , String file_path) method
	// This method writes the properties of the Song s1 object
	// and writes them into a file in the location specified by
	// file_path. This file should have the same format as the sample
	// files in the 'data/' folder.
	public void writeToFile(Song s1, String file_path) {

		// create new file path
		// name of the Song followed by "_reverse"
		String reverse_file_path = "";
		String[] fileToken = file_path.split("/");
		for (int i = 0; i < fileToken.length - 1; i++)
			reverse_file_path += fileToken[i] + "/";
		reverse_file_path += s1.getName() + "_reverse.txt";

		try {
			// Writer to file
			// create BufferedWriter object
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(reverse_file_path)));

			// bpm property
			writer.write("bpm = " + s1.getBPM());
			writer.newLine();

			// name property
			writer.write("name = " + s1.getName());
			writer.newLine();

			// if exist,
			if (!s1.getSoundbank().isEmpty()) {
				// soundbank property
				writer.write("soundbank = " + s1.getSoundbank());
				writer.newLine();
			}

			// MidiTrack : instrument and track
			for (MidiTrack midi : s1.getTracks()) {
				writer.write(trackToString(midi));
				writer.newLine();
			}

			writer.close();
			// Catch the following exceptions:
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// Create a Song object
		Song mySong = new Song();
		/*
		 * Load text file using the given song_filename, remember to catch the
		 * appropriate Exceptions, print meaningful messages! e.g. if the file
		 * was not found, print "The file FILENAME_HERE was not found" Load a
		 * Song file into a Song object.
		 */
		String song_file_path = "./data/07.txt";
		// try and catch block here:
		try {
			mySong = mySong.loadSongFromFile(song_file_path);
		} catch (IOException e) {
			System.out.println("The file FILENAME_HERE was not found.");
			return;
		}

		// call the revert method of the song object.
		mySong.revert();

		// Create a SongWriter object here, and call its writeToFile( Song s,
		// String file_location) method.

		SongWriter songWriter = new SongWriter();
		songWriter.writeToFile(mySong, song_file_path);
	}
}
