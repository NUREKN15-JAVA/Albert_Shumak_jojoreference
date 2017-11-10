package ua.nure.usermanagement.shumak.db;

public class DAOFactoryImpl extends DAOFactory {

	@Override
	public UserDAO getUserDAO() {
		UserDAO result = null;
		try {
			Class clazz = Class.forName(properties.getProperty(USER_DAO));
			result = (UserDAO) clazz.newInstance();
			result.setConnectionFactory(getConnectionFactory());
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}

		return result;
	}

}
