package chess.sound;

import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class WinSound extends SoundClass {

	public void playWinDangerSound (){
	try
	  {
		InputStream inputStream;
		
			inputStream = getClass().getResourceAsStream("win.au");
			
		
	    AudioStream audioStream = new AudioStream(inputStream);
	    AudioPlayer.player.start(audioStream);
	  }
	  catch (Exception e)
	  {
	   
	  }
	
	}
}
