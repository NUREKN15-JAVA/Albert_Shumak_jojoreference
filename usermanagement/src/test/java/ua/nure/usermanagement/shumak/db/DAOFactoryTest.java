package ua.nure.usermanagement.shumak.db;

import junit.framework.TestCase;

public class DAOFactoryTest extends TestCase {

	private DAOFactory dao;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		dao = DAOFactory.getInstance();
	}

	public void testGetUserDao() {
		assertNotNull("DAOFactory instance is null", dao);
		try {
			UserDAO userDao = dao.getUserDao();
		} catch (RuntimeException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

}
