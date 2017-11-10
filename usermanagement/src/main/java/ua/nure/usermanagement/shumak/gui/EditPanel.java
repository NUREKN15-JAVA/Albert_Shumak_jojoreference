package ua.nure.usermanagement.shumak.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import javax.swing.JOptionPane;

import ua.nure.usermanagement.shumak.User;
import ua.nure.usermanagement.shumak.db.DatabaseException;

public class EditPanel extends AddPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9222257158515651817L;
	private User user;

	public EditPanel(MainFrame parent) {
		super(parent);
		setName("editPanel"); //$NON-NLS-1$
	}

	protected void doAction(ActionEvent e) throws ParseException {
		System.out.println(user);
		if ("ok".equalsIgnoreCase(e.getActionCommand())) { //$NON-NLS-1$
			user.setFirstName(getFirstNameField().getText());
			user.setLastName(getLastNameField().getText());
			DateFormat format = DateFormat.getDateInstance();
			try {
				Date date = format.parse(getDateOfBirthField().getText());
				user.setDateOfBirthd(date);
			} catch (ParseException e0) {
				getDateOfBirthField().setBackground(Color.RED);
				throw e0;
			}
			try {
				parent.getDao().update(user);
			} catch (DatabaseException e0) {
				JOptionPane.showMessageDialog(this, e0.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
			}
		}
	}

	public void setUser(User user) {
		DateFormat format = DateFormat.getDateInstance();
		this.user = user;
		getFirstNameField().setText(user.getFirstName());
		getLastNameField().setText(user.getLastName());
		getDateOfBirthField().setText(format.format(user.getDateOfBirthd()));
	}
}
