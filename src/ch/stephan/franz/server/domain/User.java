package ch.stephan.franz.server.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity(name = "User")
@NamedQueries({ @NamedQuery(name = User.FIND_ALL, query = User.FIND_ALL_SQL), @NamedQuery(name = User.FIND_BY_NAME, query = User.FIND_BY_NAME_SQL) })
public class User implements PersistableEntity, Serializable {
  private static final long serialVersionUID = 1L;

  public static final String FIND_ALL = "User.findAll";
  public static final String FIND_ALL_SQL = "SELECT u FROM User u";
  public static final String FIND_BY_NAME = "User.findByName";
  public static final String FIND_BY_NAME_SQL = "SELECT u FROM User u WHERE u.userName = :" + User.USERNAME;
  public static final String USERNAME = "userName";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;
  private String userName;
  private String password;
  private String email;
  private Date lastLogin;

  public User() {
    super();
  }

  public Long getUserId() {
    return userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String aUserName) {
    userName = aUserName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String aPassword) {
    password = aPassword;
  }

  public Date getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(Date aLastLogin) {
    lastLogin = aLastLogin;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String aEmail) {
    email = aEmail;
  }

}
