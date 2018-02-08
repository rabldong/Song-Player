package TheLastAssign;

import java.io.IOException;

public class PlaySong {
	public static void main(String[] args) {
		MusicInterpreter myMusicPlayer = new MusicInterpreter();
		// uncomment this line to print the available instruments
		// System.out.println(myMusicPlayer.availableInstruments());

		// TODO: Q3. b
		// Create a Song object
		Song mySong = new Song();

		// load text file using the given song_filename,
		// remember to catch the appropriate Exceptions

		// try and catch block for "catching" errors when reading files for more
		// user-friendly purpose
		try {
			String song_file_path = "./data/07.txt";
			mySong = mySong.loadSongFromFile(song_file_path);
			// load it and play it
			myMusicPlayer.loadSong(mySong);
			myMusicPlayer.play();
			myMusicPlayer.close();
		} catch (IOException e) {
			// if caught an exception, print this:
			System.out.println("The file FILENAME_HERE was not found.");
			return;
		}

	}
}
