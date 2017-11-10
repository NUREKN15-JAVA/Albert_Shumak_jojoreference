package ua.nure.usermanagement.shumak.db;

import java.util.Collection;
import java.util.Date;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;

import ua.nure.usermanagement.shumak.User;

public class HsqldbUserDAOTest extends DatabaseTestCase {
	private HsqldbUserDAO dao;
	private ConnectionFactory connectionFactory;
	private User sampleUser;

	protected void setUp() throws Exception {
		super.setUp();
		dao = new HsqldbUserDAO(connectionFactory);
		sampleUser = new User();
		sampleUser.setFirstName("Albert");
		sampleUser.setLastName("Shumak");
		sampleUser.setDateOfBirthd(new Date());
		sampleUser = dao.create(sampleUser);
	}

	public void testCreate() {
		try {
			User user = new User();
			user.setFirstName("Albert");
			user.setLastName("Shumak");
			user.setDateOfBirthd(new Date());
			assertNull(user.getId());
			User createdUser = dao.create(user);
			assertNotNull(createdUser);
			assertNotNull(createdUser.getId());
			assertEquals(user.getFullName(), createdUser.getFullName());
			assertEquals(user.getDateOfBirthd(), createdUser.getDateOfBirthd());
		} catch (DatabaseException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	public void testUpdate() {
		try {
			Long id = sampleUser.getId();
			sampleUser.setLastName("Brown");
			dao.update(sampleUser);
			User foundUser = dao.find(id);
			assertEquals("Brown", foundUser.getLastName());
		} catch (DatabaseException e) {
			fail(e.toString());
		}
	}

	public void testDelete() {
		try {
			dao.delete(sampleUser);
			dao.find(sampleUser.getId());
			fail("DatabaseException");
		} catch (DatabaseException e) {
			assertEquals("No user found", e.getMessage());
		}
	}

	public void testFind() {
		try {
			User foundUser = dao.find(sampleUser.getId());
			assertEquals(sampleUser.getId(), foundUser.getId());
		} catch (DatabaseException e) {
			fail(e.toString());
		}
	}

	public void testFindAll() {
		try {
			Collection<User> collection = dao.findAll();
			assertNotNull("Collection is null", collection);
			assertEquals("Collection size.", 3, collection.size());
		} catch (DatabaseException e) {
			fail(e.toString());
		}
	}

	@Override
	protected IDatabaseConnection getConnection() throws Exception {
		connectionFactory = new ConnectionFactoryImpl("org.hsqldb.jdbcDriver", "jdbc:hsqldb:file:db/usermanagement",
				"sa", "");
		return new DatabaseConnection(connectionFactory.createConnection());
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataset = new XmlDataSet(getClass().getClassLoader().getResourceAsStream("userDataSet.xml"));
		return dataset;
	}

}
