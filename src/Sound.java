import sun.audio.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class Sound {

	protected AudioPlayer ap = AudioPlayer.player;
  private AudioStream as;
  private AudioData ad;
	private ContinuousAudioDataStream loop;
	private InputStream test;

	private String soundName;
	private boolean loopAudio;

	public Sound(String soundName, boolean loopAudio, boolean start){
		this.soundName = soundName;
		this.loopAudio = loopAudio;

		initSound(start);
	}

	protected void initSound(boolean start){
   	try {
   		test = new FileInputStream("./res/" + this.soundName + ".wav");
      as = new AudioStream(test);
      if (start)
      AudioPlayer.player.start(as);
    }
    	
    catch(FileNotFoundException e) {
      System.out.print(e.toString());
    }
    	
    catch(IOException error) {
      System.out.print(error.toString());
    }

	}

	protected void startSound(){
    if (as != null) {
     	AudioPlayer.player.start(as);
    } 
    else {
    	System.out.print("unable to start sound");
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

  protected void setInputStream(){
   	try {
   		InputStream test = new FileInputStream("./res/" + this.soundName + ".wav");
    }
      catch(FileNotFoundException e){
        System.out.println("File not found");
      }
  }

  protected void setAudioStream(){
   	try {
   		as = new AudioStream(test);
   	}
   	catch(IOException e){
      System.out.println("audio stream error");
   	}
  }
		
}