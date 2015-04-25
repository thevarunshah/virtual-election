package frontend;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args); //run server
        
        //timer which determines how long the voting server will be open for (5 minutes)
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
        	public void run(){
        		VotingController.releaseResults();
    		}
        }, 300000);
    }
    
    //adding SSL certificate to the server
    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
    	
        /*
         * Creating a self-signed SSL certificate:
         * 
         * -navigate to the 'bin' folder where your Java is installed
         * -run the following command:
         * 		keytool -genkey -alias tomcat -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 3650
         * 		-alias is the name of the certificate
         * 		-keystore is the name of the file
         * 		-validity is 10 years (in days)
         * -to check proper creation, run the following command:
         * 		keytool -list -v -keystore keystore.p12 -storetype pkcs12
         * 
         */
        
    	//absolute path of the keystore file
        final String keystoreFile = "C:\\Users\\Varun\\Documents\\~Rutgers\\CS 419\\virtual-election\\src\\main\\resources\\keystore.p12";
        //keystore file password
        final String keystorePass = "securityelection";
        //keystore file alias
        final String keystoreAlias = "tomcat";
        //other keystore file specifications
        final String keystoreType = "PKCS12";
        final String keystoreProvider = "SunJSSE";
        
        //setting up the SSL certificate
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
            public void customize(Connector con) {
	            con.setScheme("https");
	            con.setSecure(true);
	            Http11NioProtocol proto = (Http11NioProtocol) con.getProtocolHandler();
	            proto.setSSLEnabled(true);
	            proto.setKeystoreFile(keystoreFile);
	            proto.setKeystorePass(keystorePass);
	            proto.setKeystoreType(keystoreType);
	            proto.setProperty("keystoreProvider", keystoreProvider);
	            proto.setKeyAlias(keystoreAlias);
            }
        });
     
         
        return factory;
    }

}
