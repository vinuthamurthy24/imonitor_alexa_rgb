/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */

package in.xpeditions.generic.util;
import org.apache.log4j.Logger;

/**
 * @author Coladi
 *
 */
public class LogUtil {
	public static void error(Object message){
		Logger logger = Logger.getLogger(new Throwable().getStackTrace()[1].getClassName());
		logger.error(message);
	}
	public static void info(Object message){
		Logger logger = Logger.getLogger(new Throwable().getStackTrace()[1].getClassName());
		logger.info(message);
	}
}
