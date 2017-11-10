package ua.nure.usermanagement.shumak.db;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DAOFactoryTest {

	private DAOFactory dao;	
	
	@Before
	public void setUp() throws Exception {
		dao = DAOFactory.getInstance();
	}

	@Test
	public void testGetUserDAO(){	
		assertNotNull("DaoFactory instance is null", dao);
		try {
			UserDAO userdao = dao.getUserDAO();
		} catch (Exception e) {
			e.printStackTrace();
            fail(e.toString());
		}
		
	}
}
