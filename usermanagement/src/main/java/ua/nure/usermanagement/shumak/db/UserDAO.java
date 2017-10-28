package ua.nure.usermanagement.shumak.db;

import java.util.Collection;
import ua.nure.usermanagement.shumak.User;

/**
 *   Interface for User class with implemented DAO pattern with all CRUD operations.
 *
 *   @see ua.nure.usermanagement.shumak.db.HsqldbUserDAO
 */

public interface UserDAO {

	/**
     *    Add user to db users table and return new user's id from db.
     *
     *    @param user user object, all fields must be filled except id
     *    @return copy of instance of user from db with id given from db
     *   @throws DatabaseException when any error occurs in db
     */
	
    User create(User user) throws DatabaseException;

    /**
     *    Update user from db according to id.
     *
     *    @param user user object to be updated
     *   @throws DatabaseException when any error occurs in db
     */
    
    void update(User user) throws DatabaseException;

    /**
     *    Delete user from db according to id.
     *
     *    @param user user object to be deleted
     *   @throws DatabaseException when any error occurs in db
     */
    
    void delete(User user) throws DatabaseException;
    /**
     *    Find user in db according to id.
     *
     *    @param id user's id to be found in db
     *    @return user object from db according to specified id but null in case of missing
     *   @throws DatabaseException when any error occurs in db
     */
    
    User find(Long id) throws DatabaseException;

    /**
     *    Find all users from db.
     *
     *    @return list of all users from db but null in case of missing
     *   @throws DatabaseException when any error occurs in db
     */
    
    Collection<User> findAll() throws DatabaseException;

    void setConnectionFactory(ConnectionFactory connectionFactory);
}

