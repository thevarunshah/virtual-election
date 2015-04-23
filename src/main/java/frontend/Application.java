package frontend;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
        	public void run(){
        		VotingController.releaseResults();
    		}
        }, 300000);
    }

}
