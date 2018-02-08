# Song-Player
This was an academic project in Fall 2014. 

A notestring is a sequence of characters which encodes the order and timing of musical notes in a melody. This is an example of a notestring: “<<3E3P2E2GP2EPDP8C<8B>”. The letters ‘A’, ‘B’, ‘C’, ‘D’, ‘E’, ‘F’, ‘G’ correspond to the notes on a musical scale, each one having a corresponding pitch. The letter ‘P’ represents a pause, or the absence of a note. The numbers represent the duration, measured in beats, of the note or pause they precede. The symbols ‘>’ and ‘<’ will change how high or low a particular note will sound like.

The MusicInterpreter class takes care of all the sound generation. It uses a synthesizer to generate sounds, and a sequencer to determine the order and timing of sounds. MidiTrack class stores all the information from a notestring: it has an instrumentId, to determine which instrument sound should be used, and a list of notes, implemented as an ArrayList of MidiNote objects. A MidiNote object stores two properties of a single note: its pitch, its duration.
