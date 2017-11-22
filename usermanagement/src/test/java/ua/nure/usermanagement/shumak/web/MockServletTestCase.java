package ua.nure.usermanagement.shumak.web;

import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mockobjects.dynamic.Mock;
import com.mockrunner.servlet.BasicServletTestCaseAdapter;

import ua.nure.usermanagement.shumak.db.DAOFactory;
import ua.nure.usermanagement.shumak.db.MockDAOFactory;

public abstract class MockServletTestCase extends BasicServletTestCaseAdapter {
	private Mock mockUserDao;

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		Properties properties = new Properties();
		properties.setProperty("dao.factory", MockDAOFactory.class.getName());
		DAOFactory.init(properties);
		setMockUserDao(((MockDAOFactory) DAOFactory.getInstance()).getMockUserDAO());
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
		getMockUserDao().verify();
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	public Mock getMockUserDao() {
		return mockUserDao;
	}

	public void setMockUserDao(Mock mockUserDao) {
		this.mockUserDao = mockUserDao;
	}
}
