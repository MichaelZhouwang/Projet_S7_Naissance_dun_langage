
import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXParseException;

import lecture.*;

public class LectureTester {

	private static String configExample = "config/config.xml";
	private static LecteurConfigurationSysteme lecteurConfigurationSysteme = new LecteurConfigurationSysteme();
	private static ConfigurationSysteme configurationSysteme;

	private static String cheminValide = "config/config.xml";
	private static String cheminNonValide = "";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		try {
			configurationSysteme = lecteurConfigurationSysteme.lireConfigSystemeDepuisFichier(configExample);
			Assert.assertNotNull(configurationSysteme);
		} catch (Exception e) {
			Assert.fail("No exception to be thrown");
		}

	}

	@Test
	public void testLireConfigSystemeDepuisFichier() {
		try {
			Assert.assertNotNull(lecteurConfigurationSysteme.lireConfigSystemeDepuisFichier(cheminValide));
		} catch (Exception e) {
			Assert.fail("No exception to be thrown");
		}

		try {
			Assert.assertNotNull(lecteurConfigurationSysteme.lireConfigSystemeDepuisFichier(cheminNonValide));
			Assert.fail("Expected exception to be thrown");
		} catch (Exception e) {
			// Content is not allowed in prolog.
			Assert.assertTrue(e.getMessage().endsWith("Content is not allowed in prolog."));
		}
	}

}
