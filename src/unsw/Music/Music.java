package unsw.Music;  
import java.io.File;  
  
import javafx.application.Application;  
import javafx.scene.Group;  
import javafx.scene.media.Media;  
import javafx.scene.media.MediaPlayer;  
import javafx.scene.media.MediaView;  
import javafx.stage.Stage;  
public class Music
{  
  
    
    public Music() throws Exception {  
        // TODO Auto-generated method stub  
        //Initialising path of the media file, replace this with your file path   

        String path = "src/unsw/Music/gameBGM.mp3";  
          
        //Instantiating Media class  
        Media media = new Media(new File(path).toURI().toString());  
          
        //Instantiating MediaPlayer class   
        MediaPlayer mediaPlayer = new MediaPlayer(media);  
          
        //by setting this property to true, the audio will be played   
        mediaPlayer.setAutoPlay(true);  
    } 
      
}  