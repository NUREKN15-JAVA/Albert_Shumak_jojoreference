package ua.nure.usermanagement.shumak;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserTest {
	private User user;
	private Date dateOfBirthd;

	@Before
	public void setUp() throws Exception {
		user = new User();
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(1997, Calendar.DECEMBER, 19);
		dateOfBirthd = calendar.getTime();
		
		user.setFirstName("Albert");
		user.setLastName("Jojo");
		user.setDateOfBirthd(dateOfBirthd);
	}

	@Test
	public void testGetFullName() {	
		assertEquals("Jojo, Albert", user.getFullName());
	}

	@Test
	public void testGetAge() {
		assertEquals(2017 - 1997, user.getAge());
	}
	
	@After
	public void endUp() {
	 user = null;
	}
}
