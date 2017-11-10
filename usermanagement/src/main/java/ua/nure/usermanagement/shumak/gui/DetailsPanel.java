package ua.nure.usermanagement.shumak.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.DateFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ua.nure.usermanagement.shumak.User;
import ua.nure.usermanagement.shumak.util.Messages;

public class DetailsPanel extends AddPanel {
	private MainFrame parent;
	private JPanel buttonPanel;
	private JPanel fieldPanel;
	private JButton okButton;
	private JTextField dateOfBirthField;
	private JTextField lastNameField;
	private JTextField firstNameField;
	private User user;

	public DetailsPanel(MainFrame parent) {
		super(parent);
		this.parent = parent;
		initialize();
	}

	private void initialize() {
		this.setName("detailsPanel"); //$NON-NLS-1$
		this.setLayout(new BorderLayout());
		this.add(getFieldPanel(), BorderLayout.NORTH);
		this.add(getButtonPanel(), BorderLayout.SOUTH);
	}

	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.add(getOkButton());
		}
		return buttonPanel;
	}

	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.setText(ua.nure.usermanagement.shumak.gui.Messages.getString("DetailsPanel.ok")); //$NON-NLS-1$
			okButton.setName("okButton"); //$NON-NLS-1$
			okButton.setActionCommand("ok"); //$NON-NLS-1$
			okButton.addActionListener(this);
		}
		return okButton;
	}

	private JPanel getFieldPanel() {
		if (fieldPanel == null) {
			fieldPanel = new JPanel();
			fieldPanel.setLayout(new GridLayout(3, 2));
			addLabeledField(fieldPanel, ua.nure.usermanagement.shumak.gui.Messages.getString("DetailsPanel.first_name"), getFirstNameField()); //$NON-NLS-1$
			addLabeledField(fieldPanel, ua.nure.usermanagement.shumak.gui.Messages.getString("DetailsPanel.last_name"), getLastNameField()); //$NON-NLS-1$
			addLabeledField(fieldPanel, ua.nure.usermanagement.shumak.gui.Messages.getString("DetailsPanel.date_of_birth"), getDateOfBirthField()); //$NON-NLS-1$
		}
		return fieldPanel;
	}

	protected JTextField getDateOfBirthField() {
		if (dateOfBirthField == null) {
			dateOfBirthField = new JTextField();
			dateOfBirthField.setName("dateOfBirthField"); //$NON-NLS-1$
		}
		return dateOfBirthField;
	}

	protected JTextField getLastNameField() {
		if (lastNameField == null) {
			lastNameField = new JTextField();
			lastNameField.setName("lastNameField"); //$NON-NLS-1$
		}
		return lastNameField;
	}

	private void addLabeledField(JPanel panel, String labelText, JLabel textField) {
		JLabel label = new JLabel(labelText);
		label.setLabelFor(textField);
		panel.add(label);
		panel.add(textField);

	}

	protected JTextField getFirstNameField() {
		if (firstNameField == null) {
			firstNameField = new JTextField();
			firstNameField.setName("firstNameField"); //$NON-NLS-1$
		}
		return firstNameField;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("ok".equalsIgnoreCase(e.getActionCommand())) { //$NON-NLS-1$
			this.setVisible(false);
		}
		this.setVisible(false);
		parent.showBrowsePanel();
	}

	public void setUser(User user) {
		DateFormat format = DateFormat.getDateInstance();
		this.user = user;
		getFirstNameField().setText(user.getFirstName());
		getLastNameField().setText(user.getLastName());
		getDateOfBirthField().setText(format.format(user.getDateOfBirthd()));
	}
}
