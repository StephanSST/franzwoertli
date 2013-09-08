package ch.stephan.franz.shared;

import java.io.Serializable;

public class UserStatsCO implements Serializable {
  private static final long serialVersionUID = 1L;

  private String userName;
  private String newWords;
  private String oneWrong;
  private String oneRight;
  private String twoRight;
  private String threeRight;
  private String fourRight;
  private String fiveRight;
  private String unites;

  public UserStatsCO() {
    super();
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getNewWords() {
    return newWords;
  }

  public void setNewWords(String newWords) {
    this.newWords = newWords;
  }

  public String getOneWrong() {
    return oneWrong;
  }

  public void setOneWrong(String oneWrong) {
    this.oneWrong = oneWrong;
  }

  public String getOneRight() {
    return oneRight;
  }

  public void setOneRight(String oneRight) {
    this.oneRight = oneRight;
  }

  public String getTwoRight() {
    return twoRight;
  }

  public void setTwoRight(String twoRight) {
    this.twoRight = twoRight;
  }

  public String getThreeRight() {
    return threeRight;
  }

  public void setThreeRight(String threeRight) {
    this.threeRight = threeRight;
  }

  public String getFourRight() {
    return fourRight;
  }

  public void setFourRight(String fourRight) {
    this.fourRight = fourRight;
  }

  public String getFiveRight() {
    return fiveRight;
  }

  public void setFiveRight(String fiveRight) {
    this.fiveRight = fiveRight;
  }

  public String getUnites() {
    return unites;
  }

  public void setUnites(String unites) {
    this.unites = unites;
  }

}
