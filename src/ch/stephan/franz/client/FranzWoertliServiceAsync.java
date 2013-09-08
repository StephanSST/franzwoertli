package ch.stephan.franz.client;

import ch.stephan.franz.shared.UserStatsCO;
import ch.stephan.franz.shared.WoertliCO;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>FranzWoertliService</code>.
 */
public interface FranzWoertliServiceAsync {
  void saveUser(String aUserName, String aPassword, String aEmail, AsyncCallback<Void> aCallback);

  void loginUser(String aUserName, String aPassword, AsyncCallback<Void> aCallback);

  void loadUserStat(AsyncCallback<UserStatsCO> aCallback);

  void loadNextWoertli(AsyncCallback<WoertliCO> aCallback);

  void checkInput(Long aWoertliId, String aGuess, Boolean aGeschlecht, AsyncCallback<String> aCallback);

  void saveAllWoertli(String aJsonStream, AsyncCallback<Void> aCallback);

  void deleteWoertli(Integer aUnite, AsyncCallback<Void> aCallback);

  void sendPassword(String aUserName, AsyncCallback<Void> aCallback);

}
