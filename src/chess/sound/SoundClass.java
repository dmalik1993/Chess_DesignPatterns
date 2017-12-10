package chess.sound;

import java.io.InputStream;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public abstract class SoundClass {
	public void playSound(Piece piece) {

		try {
			InputStream inputStream = null;
			if (piece instanceof Pawn) {
				inputStream = getClass().getResourceAsStream("pawn.au");
			} else if (piece instanceof Bishop) {

				inputStream = getClass().getResourceAsStream("bishop.au");
			} else if (piece instanceof Rook) {

				inputStream = getClass().getResourceAsStream("rook.au");
			} else if (piece instanceof Knight) {

				inputStream = getClass().getResourceAsStream("knight.au");
			} else if (piece instanceof King) {

				inputStream = getClass().getResourceAsStream("king.au");
			} else if (piece instanceof Queen) {

				inputStream = getClass().getResourceAsStream("queen.au");
			} else {

				inputStream = getClass().getResourceAsStream("pawn.au");
			}

		    AudioStream audioStream = new AudioStream(inputStream);
		    AudioPlayer.player.start(audioStream);
		} catch (Exception e) {

		}
	}
	
	public abstract void playWinDangerSound ();
	
		
		
	
	

	
}
