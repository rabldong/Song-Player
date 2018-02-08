package TheLastAssign;

import java.util.ArrayList;
import java.util.Hashtable;

public class MidiTrack {
	private Hashtable<Character, Integer> noteToPitch;

	// ArrayList of type MidiNote
	private ArrayList<MidiNote> notes;
	// Type of instrument that will be played
	private int instrumentId;

	// The constructor for this class MidiTrack
	public MidiTrack(int instrumentId) {
		notes = new ArrayList<MidiNote>();
		this.instrumentId = instrumentId;
		this.initPitchDictionary();
	}

	// This initialises the noteToPitch dictionary,
	// which will be used by you to convert note letters
	// to pitch numbers
	public void initPitchDictionary() {
		noteToPitch = new Hashtable<Character, Integer>();
		noteToPitch.put('C', 60);
		noteToPitch.put('D', 62);
		noteToPitch.put('E', 64);
		noteToPitch.put('F', 65);
		noteToPitch.put('G', 67);
		noteToPitch.put('A', 69);
		noteToPitch.put('B', 71);
	}

	// GETTER METHODS
	public ArrayList<MidiNote> getNotes() {
		return notes;
	}

	public int getInstrumentId() {
		return instrumentId;
	}

	// This method converts notestrings like
	// <<3E3P2E2GP2EPDP8C<8B>
	// to an ArrayList of MidiNote objects
	// ( the notes attribute of this class )
	public void loadNoteString(String notestring) {
		// convert the letters in the notestring to upper case
		notestring = notestring.toUpperCase();
		// initialize duration, pitch, and octave
		int duration = 0;
		int pitch = 0;
		int octave = 0;

		String number = "";
		MidiNote midiNote;
		int prev_pitch = 60; // default : Octave is 0.

		for (int i = 0; i < notestring.length(); i++) {
			char note = notestring.charAt(i);

			switch (note) {
			// MidiNote Character
			// Format : A or B or C or ... or G
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
				midiNote = new MidiNote(noteToPitch.get(note) + pitch + octave,
						duration);
				// since it's play notes, setSilent to be false
				midiNote.setSilent(false);
				
				if (i < notestring.length() - 1) {
					// This increases pitch by 1 for sharp modifier
					if (notestring.charAt(i + 1) == '#')
						midiNote.setPitch(midiNote.getPitch() + 1);
					// This decreases pitch by 1 for flat modifier
					else if (notestring.charAt(i + 1) == '!')
						midiNote.setPitch(midiNote.getPitch() - 1);
				}
				notes.add(midiNote);
				prev_pitch = noteToPitch.get(note) + pitch + octave;
				number = "";
				duration = 1; // default value = 1
				break;

			// when it encounters the character 'P', it should be silence
			// instead of reading note. This is for pause
			case 'P':
				midiNote = new MidiNote(prev_pitch, duration);
				midiNote.setSilent(true);
				notes.add(midiNote);
				number = "";
				duration = 1; // default value 1
				break;

			// Octave decreases every time it encounters '<'
			// Format : <
			case '<':
				octave -= 12;
				break;

			// Octave increases every time it encounters '>'
			// Format : >
			case '>':
				octave += 12;
				break;

			default:
				// if encountered a number (between 0 and 9) in the notestring
				// don't have to worry about ascii values here
				if (note >= '0' && note <= '9') {
					number += note;
					duration = Integer.parseInt(number);
				}
				break;
			}
		}
	}

	public void revert() {
		ArrayList<MidiNote> reversedTrack = new ArrayList<MidiNote>();
		for (int i = notes.size() - 1; i >= 0; i--) {
			MidiNote oldNote = notes.get(i);

			// create a newNote
			MidiNote newNote = new MidiNote(oldNote.getPitch(),
					oldNote.getDuration());

			// check if the note was a pause
			if (oldNote.isSilent()) {
				newNote.setSilent(true);
			}

			// add the note to the new arraylist
			reversedTrack.add(newNote);
		}
		notes = reversedTrack;
	}

	// This will only be called if you try to run this file directly
	// You may use this to test your code.
	public static void main(String[] args) {

		// Test set
		String notestring = "<<3E3P2E2GP2EPDP8C<8B>3E3P2E2GP2EPDP8C<8B>"; // BPM
																			// 500
		notestring = "3C>3C<<3A>3A<3A#>3A#18P3C>3C<<3A>3A<3A#>3A#18P"; // BPM
																		// 1200

		int instrumentId = 0;
		// Build the MidiTrack object here
		MidiTrack newTrack = new MidiTrack(instrumentId);
		// pass the notestring here
		newTrack.loadNoteString(notestring);

		// Build a MusicInterpreter and set a playing speed
		MusicInterpreter mi = new MusicInterpreter();
		mi.setBPM(1200);

		// Load the track and play it
		mi.loadSingleTrack(newTrack);
		mi.play();

		// Close the player so that your program terminates
		mi.close();
	}
}
