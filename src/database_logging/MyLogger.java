package database_logging;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyLogger {
	
	private static Logger logger = null;
	
    public MyLogger() {

        logger = Logger.getLogger(MyLogger.class.getName());
        
		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(Level.INFO);  
		logger.addHandler(consoleHandler);
		logger.setLevel(Level.FINE);
    }
	
	public static Logger getLogger(){
	    if(logger == null)
	    	new MyLogger();
	    return logger;
	}
	
}
