package ua.nure.usermanagement.shumak;

import java.util.Calendar;
import java.util.Date;

public class User {
	private Long id;
	private String firstName;
	private String lastName;
	private Date dateOfBirthd;

	public User(User user) {
		this.setDateOfBirthd(user.getDateOfBirthd());
		this.setFirstName(user.getFirstName());
		this.setLastName(user.getLastName());
		this.setId(user.getId());
	}

	public User(Long id, String firstName, String lastName, Date dateOfBirthd) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirthd = dateOfBirthd;
	}

	public User() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirthd() {
		return dateOfBirthd;
	}

	public void setDateOfBirthd(Date dateOfBirthd) {
		this.dateOfBirthd = dateOfBirthd;
	}

	public String getFullName() {
		if (getLastName() == null || getFirstName() == null) {
			throw new IllegalArgumentException("One of neccessary fields, first or last name, is empty");
		}
		String ans = new StringBuilder(getLastName()).append(", ").append(getFirstName()).toString();
		return ans;
	}

	public int getAge() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int currentYear = calendar.get(Calendar.YEAR);
		int currentDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.setTime(getDateOfBirthd());
		int year = calendar.get(Calendar.YEAR);
		int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
		return currentDayOfYear < dayOfYear ? currentYear - year - 1 : currentYear - year;
	}
}
