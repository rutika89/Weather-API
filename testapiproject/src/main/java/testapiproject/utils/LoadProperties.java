package testapiproject.utils;

import java.io.IOException;
import java.util.Properties;

public class LoadProperties {
	
	public static Properties getProperties() {
		String fileName = "application.properties";
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties properties = new Properties();
		try {
			properties.load(loader.getResourceAsStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	public static String getReportConfigPath(){
		Properties properties = new Properties();
		String reportConfigPath = properties.getProperty("reportConfigPath");
		if(reportConfigPath!= null) return reportConfigPath;
		else throw new RuntimeException("Report Config Path not specified in the Configuration.properties file for the Key:reportConfigPath");		
	}
}
