package com.share.model.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.share.common.annotations.exceptions.DatabaseException;
import com.share.common.annotations.exceptions.LoginAlreadyExistsException;
import com.share.model.db.DBCon;

@Named
@SessionScoped
public class NewUserDAO implements Serializable {  

	@Inject
	private DBCon dbcon;
	/**
	 * 
	 */
	private static final long serialVersionUID = -5035823934131184074L;

	private final static Logger LOG = LoggerFactory.getLogger(NewUserDAO.class);
	
	public void addNewUser(final String username, final String password, final String firstName, final String lastName) throws DatabaseException, LoginAlreadyExistsException {
		LOG.info("login");
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = dbcon.getConnection();
			
			ps = con.prepareStatement(
					"SELECT username FROM users WHERE username= ? ");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) // found
			{
				LOG.error("Username " + username + " already used ");
				throw new LoginAlreadyExistsException("The login " + username + " is already used");
			}
			
			ps = con.prepareStatement(
					"INSERT INTO users (username, password, firstname, lastname) values (?, ?, ?, ?) ");
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, firstName);
			ps.setString(4, lastName);
			
			ps.executeQuery();
		} 
		catch (SQLException ex) {
			LOG.error("Error in addNewUser() -->" + ex.getMessage());
			throw new DatabaseException("Internal error");            
		} finally {
			dbcon.closeConnection(con);
		}
	}
}