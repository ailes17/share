package com.share.model.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.share.model.db.DBCon;

@Named
@SessionScoped
public class LoginDAO implements Serializable {  

	@Inject
	private DBCon dbcon;
	/**
	 * 
	 */
	private static final long serialVersionUID = -5035823934131184074L;

	private final static Logger LOG = LoggerFactory.getLogger(LoginDAO.class);
	
	public boolean login(String user, String password) {
		LOG.info("login");
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = dbcon.getConnection();
			ps = con.prepareStatement(
					"SELECT username, password FROM users WHERE username= ? and password= ? ");
			ps.setString(1, user);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) // found
			{
				return true;
			}
			else {
				return false;
			}
		} 
		catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Database Error",
					"Unable to connect database"));            
			System.out.println("Error in login() -->" + ex.getMessage());
			return false;
		} finally {
			dbcon.closeConnection(con);
		}
	}
}