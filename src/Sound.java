import sun.audio.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class Sound {

	protected AudioPlayer MGP = AudioPlayer.player;
   	private AudioStream as = null;
   	private AudioData MD = null;
	private ContinuousAudioDataStream loop = null;

	private String soundName;
	private boolean loopAudio;

	public static Sound music = new Sound("sovietSong", true);



	public Sound(String soundName, boolean loopAudio){
		this.soundName = soundName;
		this.loopAudio = loopAudio;
		initSound();
	}

	protected void initSound(){
    	try {
        	InputStream test = new FileInputStream("./res/" + this.soundName + ".wav");
        	as = new AudioStream(test);
        	AudioPlayer.player.start(as);
    	}
    	
    	catch(FileNotFoundException e) {
        	System.out.print(e.toString());
    	}
    	
    	catch(IOException error) {
    	    System.out.print(error.toString());
    	}
    
    	if(this.loopAudio) {
    		MGP.start(loop);
    	}

	}

	protected void killSound(){
    	if (as != null) {
       		AudioPlayer.player.stop(as);
    	} 
    	else {
 		    System.out.print("unable to stop sound");
    	}

    }
		
}