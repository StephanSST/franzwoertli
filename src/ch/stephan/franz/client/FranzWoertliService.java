package ch.stephan.franz.client;

import ch.stephan.franz.shared.UserStatsCO;
import ch.stephan.franz.shared.WoertliCO;
import ch.stephan.franz.shared.exception.SecurityException;
import ch.stephan.franz.shared.exception.UserHandlingException;
import ch.stephan.franz.shared.exception.WoertliInkonsistencyException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("service")
public interface FranzWoertliService extends RemoteService {
  void saveUser(String aUserName, String aPassword, String aEmail) throws UserHandlingException;

  void loginUser(String aUserName, String aPassword) throws SecurityException;

  UserStatsCO loadUserStat();

  WoertliCO loadNextWoertli() throws WoertliInkonsistencyException;

  String checkInput(Long aWoertliId, String aGuess, Boolean aGeschlecht) throws WoertliInkonsistencyException;

  void saveAllWoertli(String aJsonStream);

  void deleteWoertli(Integer aUnite);

  void sendPassword(String aUserName) throws SecurityException, UserHandlingException;
}
