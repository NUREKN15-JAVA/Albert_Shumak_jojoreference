package ua.nure.usermanagement.shumak.db;

import com.mockobjects.dynamic.Mock;

public class MockDAOFactory extends DAOFactory {

	private Mock mockUserDao;
	
	public MockDAOFactory() {
		super();
		mockUserDao = new Mock(UserDAO.class);
	}

	public Mock getMockUserDAO() {
		return mockUserDao;
	}
	
	@Override
	public UserDAO getUserDAO() {
		return (UserDAO) mockUserDao.proxy();
	}

}
