import sun.audio.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Sound {
	private String soundName;
	private boolean loop;


	public Sound(String soundName, boolean loop){
		this.soundName = soundName;
		this.loop = loop;
		initSound();
	}

	public void initSound(){
	    AudioPlayer MGP = AudioPlayer.player;
    	AudioStream BGM;
    	AudioData MD;

	    ContinuousAudioDataStream loop = null;

    	try {
        	InputStream test = new FileInputStream("./res/" + this.soundName + ".wav");
        	BGM = new AudioStream(test);
        	AudioPlayer.player.start(BGM);
    	}
    	
    	catch(FileNotFoundException e) {
        	System.out.print(e.toString());
    	}
    	
    	catch(IOException error) {
    	    System.out.print(error.toString());
    	}
    
    	if(this.loop) {
    		MGP.start(loop);
    	}


	}

}