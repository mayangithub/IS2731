package models.user;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author hab81
 * This class establishes connection with
 * database, and operates data in tables. 
 */
public class UserDBManager {

    private final String databaseURL;
    private final String dbUserName;
    private final String dbPassword;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    public UserDBManager() {
        databaseURL = "jdbc:mysql://localhost:3306/final_project";
        dbUserName = "is2731";
        dbPassword = "is2731";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(databaseURL, dbUserName, dbPassword);   
        }
        catch(ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserDBManager.class.getName()).log(Level.SEVERE, null, ex);
        }		
    }
	
    /*
    * Query if user is stored in database with only userName. 
    */
    public User queryUser(String userName) {
    	User userResult = null;
        String sql = "SELECT * FROM users WHERE user_name = ?";
	try {
            this.statement = connection.prepareStatement(sql);
            this.statement.setString(1, userName);
            this.resultSet = this.statement.executeQuery();

            if(this.resultSet.next()){
                userResult = new User();              
		userResult.setUserName(this.resultSet.getString("user_name"));
                userResult.setHashedPassword(this.resultSet.getString("hashed_password"));
		userResult.setEmail(this.resultSet.getString("email"));
                userResult.setHashedAnswer(this.resultSet.getString("hashed_answer"));
                userResult.setIsActivated(this.resultSet.getInt("is_activated"));
            }
            statement.close();
        }
        catch(SQLException ex) {
            Logger.getLogger(UserDBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
	return userResult;
    }
    
    /*
    * Query if user/role pair is stored in database with only userName. 
    */
    public List<UserRole> queryUserRole(String userName) {
        List<UserRole> userRoleList = new ArrayList<>();
    	UserRole userRoleResult;
        String sql = "SELECT * FROM users_roles WHERE user_name = ?";
	try {
            this.statement = connection.prepareStatement(sql);
            this.statement.setString(1, userName);
            this.resultSet = this.statement.executeQuery();

            while(this.resultSet.next()){
                userRoleResult = new UserRole();              
		userRoleResult.setUserName(this.resultSet.getString("user_name"));
                userRoleResult.setRoleName(this.resultSet.getString("role_name"));
                userRoleList.add(userRoleResult);
            }
            statement.close();
        }
        catch(SQLException ex) {
            Logger.getLogger(UserDBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
	return userRoleList;
    }
    
    /*
    * Query if user is stored in database with userName and password. 
    */
    public User queryUserWithPassword(String userName, String password) {
    	User userResult = null;
        String sql = "SELECT * FROM users WHERE user_name = ? AND hashed_password = ?";
        String hashedPassword = UserManager.encryptText(password);
	try {
            this.statement = connection.prepareStatement(sql);
            this.statement.setString(1, userName);
            this.statement.setString(2, hashedPassword);
            this.resultSet = this.statement.executeQuery();

            if(this.resultSet.next()){
                userResult = new User();
                userResult.setUserName(this.resultSet.getString("user_name"));
                userResult.setHashedPassword(this.resultSet.getString("hashed_password"));
		userResult.setEmail(this.resultSet.getString("email"));
                userResult.setHashedAnswer(this.resultSet.getString("hashed_answer"));
                userResult.setIsActivated(this.resultSet.getInt("is_activated"));
            }
            this.statement.close();
        }
        catch(SQLException ex) {
            Logger.getLogger(UserDBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
	return userResult;
    }
    
    /*
    * Query if user is stored in database with userName and hashed answer. 
    */
    public User queryUserWithAnswer(String userName, String securityAnswer) {
    	User userResult = null;
        String sql = "SELECT * FROM users WHERE user_name = ? AND hashed_answer = ?";
        String hashedAnswer = UserManager.encryptText(securityAnswer);
	try {
            this.statement = connection.prepareStatement(sql);
            this.statement.setString(1, userName);
            this.statement.setString(2, hashedAnswer);
            this.resultSet = this.statement.executeQuery();

            if(this.resultSet.next()){
                userResult = new User();
                userResult.setUserName(this.resultSet.getString("user_name"));
                userResult.setHashedPassword(this.resultSet.getString("hashed_password"));
		userResult.setEmail(this.resultSet.getString("email"));
                userResult.setHashedAnswer(this.resultSet.getString("hashed_answer"));
                userResult.setIsActivated(this.resultSet.getInt("is_activated"));
            }
            this.statement.close();
        }
        catch(SQLException ex) {
            Logger.getLogger(UserDBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
	return userResult;
    }
	
    /*
    * Query all users in database.
    */
    public List<User> queryAllUsers() {
    	List<User> userList = new ArrayList<>();
	User user;
        String sql = "SELECT * FROM users";
	try {	
            this.statement = connection.prepareStatement(sql);
            this.resultSet = this.statement.executeQuery();
            while(this.resultSet.next()) {
		user = new User();
                user.setUserName(this.resultSet.getString("user_name"));
                user.setHashedPassword(this.resultSet.getString("hashed_password"));
		user.setEmail(this.resultSet.getString("email"));
                user.setHashedAnswer(this.resultSet.getString("hashed_answer"));
                user.setIsActivated(this.resultSet.getInt("is_activated"));
		userList.add(user);
            }	
            this.statement.close();
	} 
        catch(SQLException ex) {
            Logger.getLogger(UserDBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userList;
    }
    
    /*
    * Query all users/roles in database.
    */
    public List<UserRole> queryAllUsersRoles() {
    	List<UserRole> userRoleList = new ArrayList<>();
	UserRole userRole;
        String sql = "SELECT * FROM users_roles";
	try {	
            this.statement = connection.prepareStatement(sql);
            this.resultSet = this.statement.executeQuery();
            while(this.resultSet.next()) {
		userRole = new UserRole();
                userRole.setUserName(this.resultSet.getString("user_name"));
                userRole.setRoleName(this.resultSet.getString("role_name"));
		userRoleList.add(userRole);
            }	
            this.statement.close();
	} 
        catch(SQLException ex) {
            Logger.getLogger(UserDBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userRoleList;
    }
	
    /*
    * Add new user
    */
    public boolean addUser(User newUser) {
        boolean addResult = false;
	String sql = "INSERT INTO users"
                    + "(user_name, hashed_password, email, hashed_answer, is_activated) VALUES"
                    + "(? , ? , ? , ? , ?)";
    	try {			
            this.statement = connection.prepareStatement(sql);
            this.statement.setString(1, newUser.getUserName());
            this.statement.setString(2, newUser.getHashedPassword());
            this.statement.setString(3, newUser.getEmail());    
            this.statement.setString(4, newUser.getHashedAnswer());    
            this.statement.setInt(5, newUser.getIsActivated());    
            this.statement.executeUpdate();
            addResult = true;
            this.statement.close();
	}
        catch(SQLException ex) {
            Logger.getLogger(UserDBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return addResult; 
    }
    
    /*
    * Add new user/role pair
    */
    public boolean addUserRole(String userName, String roleName) {
        boolean addResult = false;
	String sql = "INSERT INTO users_roles"
                    + "(user_name, role_name) VALUES"
                    + "(? , ?)";
    	try {			
            this.statement = connection.prepareStatement(sql);
            this.statement.setString(1, userName);
            this.statement.setString(2, roleName);
            this.statement.executeUpdate();
            addResult = true;
            this.statement.close();
	}
        catch(SQLException ex) {
            Logger.getLogger(UserDBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return addResult; 
    }
    
    /*
    * Update user's information
    */
    public boolean updateUser(User user) {
    	boolean updateResult = false;
	String sql = "UPDATE users SET hashed_password = ? , email = ? , hashed_answer = ?, is_activated = ? WHERE user_name = ?";
    	try {			
            this.statement = connection.prepareStatement(sql);
            this.statement.setString(1, user.getHashedPassword());
            this.statement.setString(2, user.getEmail());
            this.statement.setString(3, user.getHashedAnswer());
            this.statement.setInt(4, user.getIsActivated());
            this.statement.setString(5, user.getUserName());
            this.statement.executeUpdate();
            updateResult = true;
            this.statement.close();
	}
        catch(SQLException ex) {
            Logger.getLogger(UserDBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return updateResult; 
    }
    
    /*
    * Update user/role pair
    */
    public boolean updateUserRole(String oldUserName, String oldRoleName, String newUserName, String newRoleName) {
    	boolean updateResult = false;
	String sql = "UPDATE users_roles SET user_name = ? , role_name = ? WHERE user_name = ? and role_name = ?";
    	try {			
            this.statement = connection.prepareStatement(sql);
            this.statement.setString(1, oldUserName);
            this.statement.setString(2, oldRoleName);
            this.statement.setString(3, newUserName);
            this.statement.setString(4, newRoleName);
            
            this.statement.executeUpdate();
            updateResult = true;
            this.statement.close();
	}
        catch(SQLException ex) {
            Logger.getLogger(UserDBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return updateResult; 
    }
    
    /*
    * Delete user
    */
    public boolean deleteUser(User currentUser) {
        boolean deleteResult = false;
	String sql = "DELETE FROM users WHERE user_name = ?";
    	try {			
            this.statement = connection.prepareStatement(sql);
            this.statement.setString(1, currentUser.getUserName());      
            this.statement.executeUpdate();
            deleteResult = true;
            this.statement.close();
	}
        catch(SQLException ex) {
            Logger.getLogger(UserDBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return deleteResult; 
    }
    
    /*
    * Delete user/role pair
    */
    public boolean deleteUserRole(String userName, String roleName) {
        boolean deleteResult = false;
        String sql;
        if(roleName == null)
            sql = "DELETE FROM users_roles WHERE user_name = ?";
        else
            sql = "DELETE FROM users_roles WHERE user_name = ? and role_name = ?";
    	try {			
            this.statement = connection.prepareStatement(sql);
            this.statement.setString(1, userName); 
            this.statement.setString(2, roleName);
            this.statement.executeUpdate();
            deleteResult = true;
            this.statement.close();
	}
        catch(SQLException ex) {
            Logger.getLogger(UserDBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return deleteResult; 
    }
    
    /* 
    * Close database connection.
    */
    public boolean closeConnection() {
        try {
            connection.close();
            return true;
        } 
        catch(SQLException ex) {
            Logger.getLogger(UserDBManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}