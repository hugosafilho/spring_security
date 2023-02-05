package hugosafilho.business;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationBusiness {

	@Value("${vaultFile}")
	private String vaultFile;

	private Properties properties;

	public String getProperty(String propertyName) throws IOException {
		loadPropertiesFile();
		return properties.getProperty(propertyName);
	}

	private void loadPropertiesFile() throws IOException {
		InputStream input = new FileInputStream(vaultFile);
		properties = new Properties();

		properties.load(input);
	}
}
