package ua.nure.usermanagement.shumak.gui;

import java.awt.Component;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mockobjects.dynamic.Mock;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.eventdata.JTableMouseEventData;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.DialogFinder;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.usermanagement.shumak.User;
import ua.nure.usermanagement.shumak.db.DAOFactory;
import ua.nure.usermanagement.shumak.db.DAOFactoryImpl;
import ua.nure.usermanagement.shumak.db.MockDAOFactory;
import ua.nure.usermanagement.shumak.db.MockUserDAO;

public class MainFrameTest extends JFCTestCase {

	private MainFrame mainFrame;
	private Mock mockUserDao;
	private List<User> users;

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		try {
			Properties properties = new Properties();
			properties.setProperty("dao.factory", MockDAOFactory.class.getName());
			DAOFactory.init(properties);
			mockUserDao = ((MockDAOFactory) DAOFactory.getInstance()).getMockUserDAO();
			User expectedUser = new User(new Long(1000), "George", "Bush", new Date());
			users = Collections.singletonList(expectedUser);
			mockUserDao.expectAndReturn("findAll", new ArrayList());
			setHelper(new JFCTestHelper());
			mainFrame = new MainFrame();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mainFrame.setVisible(true);
	}

	@After
	protected void tearDown() throws Exception {
		try {
			mockUserDao.verify();
			mainFrame.setVisible(false);
			getHelper().cleanUp(this);
			super.tearDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Component find(Class componentClass, String name) {
		NamedComponentFinder finder;
		finder = new NamedComponentFinder(componentClass, name);
		finder.setWait(0);
		Component component = finder.find(mainFrame, 0);
		assertNotNull("Could not find component '" + name + "'", component);
		return component;
	}

	@Test
	public void testBrowseControls() {
		find(JPanel.class, "browsePanel");
		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(3, table.getColumnCount());
		assertEquals("ID", table.getColumnName(0));
		assertEquals("First name", table.getColumnName(1));
		assertEquals("Last name", table.getColumnName(2));

		find(JButton.class, "addButton");
		find(JButton.class, "editButton");
		find(JButton.class, "deleteButton");
		find(JButton.class, "detailsButton");
	}

	@Test
	public void testAddUser() {
		try {
			String firstName = "John";
			String lastName = "Doe";
			Date now = new Date();
			User user = new User(firstName, lastName, now);
			User expectedUser = new User(new Long(1), firstName, lastName, now);
			mockUserDao.expectAndReturn("create", user, expectedUser);
			users.add(expectedUser);
			mockUserDao.expectAndReturn("findAll", users);
			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			JButton addButton = (JButton) find(JButton.class, "addButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
			find(JPanel.class, "addPanel");
			fillField(firstName, lastName, now);
			JButton okButton = (JButton) find(JButton.class, "okButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
			find(JPanel.class, "browsePanel");
			table = (JTable) find(JTable.class, "userTable");
			assertEquals(2, table.getRowCount());
			mockUserDao.verify();
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	 public void testCancelAddUser() {
	        try {
	            String firstName = "John";
	            String lastName = "Doe";
	            Date now = new Date();
	            mockUserDao.expectAndReturn("findAll", users);	            
	            JTable table = (JTable) find(JTable.class, "userTable");
	            assertEquals(1, table.getRowCount());
	            JButton addButton = (JButton) find(JButton.class, "addButton");
	            getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
	            find(JPanel.class, "addPanel");
	            fillField(firstName, lastName, now);
	            JButton cancelButton = (JButton) find(JButton.class, "cancelButton");
	            getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));
	            find(JPanel.class, "browsePanel");
	            table = (JTable) find(JTable.class, "userTable");
	            assertEquals(1, table.getRowCount());	          
	            mockUserDao.verify();
	        } catch (Exception e) {
	            fail(e.toString());
	        }
	    }
	
	@Test
	 public void testCancelEditUser() {
	        try {
	            String firstName = "John";
	            String lastName = "Doe";
	            Date now = new Date();
	            User expectedUser = new User(new Long(1), firstName, lastName, now);
	            users.add(expectedUser);	           
	            mockUserDao.expectAndReturn("findAll", users);	            
	            JTable table = (JTable) find(JTable.class, "userTable");
	            assertEquals(1, table.getRowCount());
	            JButton editButton = (JButton) find(JButton.class, "editButton");
	            getHelper().enterClickAndLeave(new MouseEventData(this, editButton));
	            String title = "Edit user";
	            findDialog(title);	           
	            getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
	            getHelper().enterClickAndLeave(new MouseEventData(this, editButton));       
	            find(JPanel.class, "editPanel");
	            JTextField firstNameField = (JTextField) find(JTextField.class,
	                    "firstNameField");
	            JTextField lastNameField = (JTextField) find(JTextField.class,
	                    "lastNameField");
	            JTextField dateOfBirthField = (JTextField) find(JTextField.class,
	                    "dateOfBirthField");  
	            assertEquals("George", firstNameField.getText());
	            assertEquals("Bush", lastNameField.getText());
	            getHelper().sendString(
	                    new StringEventData(this, firstNameField, firstName));
	            getHelper().sendString(
	                    new StringEventData(this, lastNameField, lastName));
	            DateFormat formatter = DateFormat.getDateInstance();
	            String date = formatter.format(now);
	            getHelper().sendString(
	                    new StringEventData(this, dateOfBirthField, date));
	            JButton cancelButton = (JButton) find(JButton.class, "cancelButton");
	            getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));
	            find(JPanel.class, "browsePanel");
	            table = (JTable) find(JTable.class, "userTable");
	            assertEquals(2, table.getRowCount());
	            mockUserDao.verify();
	            
	        } catch (Exception e) {
	            fail(e.toString());
	        }
	}
	
	@Test
	public void testEditUser() {
		try {
			User expectedUser = new User(new Long(1000), "George", "Bush", new Date());
			mockUserDao.expect("update", expectedUser);
			List users = new ArrayList(this.users);
			mockUserDao.expectAndReturn("findAll", users);
			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			JButton editButton = (JButton) find(JButton.class, "editButton");
			getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
			getHelper().enterClickAndLeave(new MouseEventData(this, editButton));
			find(JPanel.class, "editPanel");
			JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
			JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
			JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
			getHelper().sendString(new StringEventData(this, firstNameField, "1"));
			getHelper().sendString(new StringEventData(this, lastNameField, "1"));
			JButton okButton = (JButton) find(JButton.class, "okButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
			find(JPanel.class, "browsePanel");
			table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			mockUserDao.verify();
		} catch (Exception e) {
			fail(e.toString());
		}
	}
	
	@Test
	public void testDeleteUser() {
        try {
            User expectedUser = new User(new Long(1000), "George", "Bush", new Date());
            mockUserDao.expect("delete", expectedUser);
            mockUserDao.expectAndReturn("findAll", users);   
            JTable table = (JTable) find(JTable.class, "userTable");
            assertEquals(1, table.getRowCount());
            JButton deleteButton = (JButton) find(JButton.class, "deleteButton");
            getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
            getHelper().enterClickAndLeave(new MouseEventData(this, deleteButton));
            find(JPanel.class, "browsePanel");
            table = (JTable) find(JTable.class, "userTable");
            assertEquals(0, table.getRowCount());
            mockUserDao.verify();
        } catch (Exception e) {
            fail(e.toString());
        }
}
	
	@Test
	public void testDetailsUser() {
		try {
			mockUserDao.expectAndReturn("findAll", users);
			JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			JButton detailsButton = (JButton) find(JButton.class, "detailsButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, detailsButton));
			String title = "Edit user";
			findDialog(title);
			getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
			getHelper().enterClickAndLeave(new MouseEventData(this, detailsButton));
			find(JPanel.class, "detailsPanel");
			JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
			JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
			JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
			assertEquals("George", firstNameField.getText());
			assertEquals("Bush", lastNameField.getText());
			JButton backButton = (JButton) find(JButton.class, "backButton");
			getHelper().enterClickAndLeave(new MouseEventData(this, backButton));
			find(JPanel.class, "browsePanel");
			table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
			mockUserDao.verify();

		} catch (Exception e) {
			fail(e.toString());
		}
}

	 private void findDialog(String title) {
			JDialog dialog;
			DialogFinder dFinder = new DialogFinder(title);
			dialog = (JDialog) dFinder.find();
			assertNotNull("Could not find dialog '" + title + "'", dialog);
			getHelper().disposeWindow(dialog, this);
	}
	 
	private void fillField(String firstName, String lastName, Date now) {
		JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
		JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
		JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
		getHelper().sendString(new StringEventData(this, firstNameField, firstName));
		getHelper().sendString(new StringEventData(this, lastNameField, lastName));
		DateFormat formatter = DateFormat.getDateInstance();
		String date = formatter.format(now);
		getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
	}
}
