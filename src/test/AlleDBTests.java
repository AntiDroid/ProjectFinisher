package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AuswahlbereichTest.class, FoliensatzTest.class,
		FolieTest.class, KursTest.class, LehrerTest.class, StudentTest.class,
		UservotingTest.class })

public class AlleDBTests {

}
