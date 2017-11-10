package ua.nure.usermanagement.shumak.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ua.nure.usermanagement.shumak.User;

public class UserTableModel extends AbstractTableModel {

	private List users = null;
	private String[] COLUMN_NAMES = { Messages.getString("UserTableModel.id"), Messages.getString("UserTableModel.first_name"), Messages.getString("UserTableModel.last_name") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	private Class[] COLUMN_CLASSES = { Long.class, String.class, String.class };

	public UserTableModel(Collection users) {
		super();
		this.users = new ArrayList(users);
	}

	@Override
	public int getRowCount() {
		return users.size();
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		User user = (User) users.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return user.getId();
		case 1:
			return user.getFirstName();
		case 2:
			return user.getLastName();
		}
		return null;
	}

	public Class getColumnClass(int columnIndex) {
		return COLUMN_CLASSES[columnIndex];
	}

	public String getColumnName(int column) {
		return COLUMN_NAMES[column];
	}

	public User getUser(int index) {
		return (User) users.get(index);
	}

	public void addUsers(Collection users) {
		this.users.addAll(users);

	}

	public void clearUsers() {
		this.users = new ArrayList();
	}

}
