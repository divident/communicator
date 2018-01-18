package server.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import server.Server;

public class Database {
	private final static Logger LOGGER = Logger.getLogger(Server.class.getName());
	private Connection connection;
	private String serverAddres;
	private String dbUsername;
	private String dbPassword;
	
	public Database() {
		ReadXMLFile rxf = new ReadXMLFile();
		serverAddres = rxf.getDatabaseConf("address");
		dbUsername = rxf.getDatabaseConf("login");
		dbPassword = rxf.getDatabaseConf("passwd");
	}

	public boolean checkUser(String username, String password) {
		connect();
		try {
			String sql = "SELECT USERNAME, PASSWORD FROM USERS " + "WHERE USERNAME = ? AND PASSWORD = ?;";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String user = rs.getString("USERNAME");
				String pass = rs.getString("PASSWORD");
				if (user.equals(username) && pass.equals(password)) {
					return true;
				}
			}
			rs.close();
			pstmt.close();
			connection.close();
		} catch (Exception e) {
			LOGGER.warning("Failed to query database");
			e.printStackTrace();
		}
		return false;
	}

	private void connect() {
		try {
			if (connection == null) {
				connection = DriverManager.getConnection(serverAddres, dbUsername, dbPassword);
			}
		} catch (SQLException e) {
			LOGGER.warning("Failed to connect to database");
		}
	}
}
