package ua.nure.usermanagement.shumak.web;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import ua.nure.usermanagement.shumak.User;

public class EditServletTest extends MockServletTestCase {

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		createServlet(EditServlet.class);
	}
	@Test
	public void testEdit() {
        Date date = new Date();
        User user = new User(new Long(1000), "John", "Doe", date);
        getMockUserDao().expect("update", user);
        
        addRequestParameter("id", "1000");
        addRequestParameter("firstName", "John");
        addRequestParameter("lastName", "Doe");
        addRequestParameter("date", DateFormat.getDateInstance().format(date));
        addRequestParameter("okButton", "Ok");
        doPost();
    }
	@Test
	 public void testEditEmptyFirstName() {
	        Date date = new Date();
	        addRequestParameter("id", "1000");
	        addRequestParameter("lastName", "Doe");
	        addRequestParameter("date", DateFormat.getDateInstance().format(date));
	        addRequestParameter("okButton", "Ok");
	        doPost();
	        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
	        assertNotNull("Could not find error message in session scope", errorMessage);
	    }
	@Test
	    public void testEditEmptyLastName() {
	        Date date = new Date();
	        addRequestParameter("id", "1000");
	        addRequestParameter("firstName", "John");
	        addRequestParameter("date", DateFormat.getDateInstance().format(date));
	        addRequestParameter("okButton", "Ok");
	        doPost();
	        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
	        assertNotNull("Could not find error message in session scope", errorMessage);
	    }
	@Test
	    public void testEditEmptyDate() {
	        Date date = new Date();
	        addRequestParameter("id", "1000");
	        addRequestParameter("firstName", "John");
	        addRequestParameter("lastName", "Doe");
	        addRequestParameter("okButton", "Ok");
	        doPost();
	        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
	        assertNotNull("Could not find error message in session scope", errorMessage);
	    }
	@Test
	    public void testEditEmptyDateIncorrect() {
	        Date date = new Date();
	        addRequestParameter("id", "1000");
	        addRequestParameter("firstName", "John");
	        addRequestParameter("lastName", "Doe");
	        addRequestParameter("date", "abcdefg");
	        addRequestParameter("okButton", "Ok");
	        doPost();
	        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
	        assertNotNull("Could not find error message in session scope", errorMessage);
}

}
