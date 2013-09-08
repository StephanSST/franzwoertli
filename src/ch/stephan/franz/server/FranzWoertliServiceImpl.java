package ch.stephan.franz.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import ch.stephan.franz.client.FranzWoertliService;
import ch.stephan.franz.server.domain.Geschlecht;
import ch.stephan.franz.server.domain.User;
import ch.stephan.franz.server.domain.Woertli;
import ch.stephan.franz.server.domain.WoertliStat;
import ch.stephan.franz.server.facade.WoertliDatabaseFacadeBean;
import ch.stephan.franz.shared.UserStatsCO;
import ch.stephan.franz.shared.WoertliCO;
import ch.stephan.franz.shared.exception.SecurityException;
import ch.stephan.franz.shared.exception.UserHandlingException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class FranzWoertliServiceImpl extends RemoteServiceServlet implements FranzWoertliService {

  private static final String UID = "UID";

  private static final Logger LOG = Logger.getLogger(FranzWoertliServiceImpl.class.getName());
  private static final String USER_ID = "uid";
  private static final String USER_NAME = "uName";
  private static final String WOERTLI_CACHE = "woertli";
  private static final Random RANDOMIZER = new Random();;

  private WoertliDatabaseFacadeBean woertliDatabaseFacade = new WoertliDatabaseFacadeBean();

  @Override
  public void saveAllWoertli(String jsonStream) {
    try {
      List<Woertli> woertliList = WoertliParser.parseStream(jsonStream);
      for (Woertli woertli : woertliList) {
        woertliDatabaseFacade.persist(woertli);
      }
      LOG.info("Es wurden " + woertliList.size() + " Woertli gespeichert.");

      connectUserToWoertli(woertliDatabaseFacade.findAllUser(), woertliList);

    } catch (Exception ex) {
      LOG.error("Fehler beim Speichern der Woertli", ex);
    }
  }

  @Override
  public void loginUser(String userName, String password) throws SecurityException {
    User user = woertliDatabaseFacade.findUserByName(userName);
    if (user == null) {
      throw new SecurityException("User [" + userName + "] wurde nicht gefunden.");
    } else if (!user.getPassword().equals(password)) {
      throw new SecurityException("Das Passwort für User [" + userName + "] ist falsch.");
    }
    LOG.info("User [" + user.getUserName() + "] mit Id [" + user.getUserId() + "] erfolgreich eingeloggt.");

    getThreadLocalRequest().getSession().setAttribute(USER_ID, user.getUserId());
    getThreadLocalRequest().getSession().setAttribute(USER_NAME, user.getUserName());
    getThreadLocalRequest().getSession().removeAttribute(WOERTLI_CACHE);

    user.setLastLogin(new Date());
    woertliDatabaseFacade.merge(user);
  }

  @Override
  public void saveUser(String aUserName, String aPassword, String aEmail) throws UserHandlingException {
    if (!EMailVerifier.isValidEmail(aEmail)) {
      throw new UserHandlingException("Die EMail-Adresse [" + aEmail + "] ist ungültig.");
    }
    if (woertliDatabaseFacade.findUserByName(aUserName) != null) {
      throw new UserHandlingException("Es existiert bereits ein Benutzer [" + aUserName + "].");
    }

    User tempUser = new User();
    tempUser.setUserName(aUserName);
    tempUser.setPassword(aPassword);
    tempUser.setLastLogin(new Date());
    tempUser.setEmail(aEmail);
    User newUser = woertliDatabaseFacade.persist(tempUser);
    LOG.info("Neuer Benutzer [" + newUser.getUserName() + "] mit Id [" + newUser.getUserId() + "] gespeichert.");

    connectUserToWoertli(Arrays.asList(newUser), woertliDatabaseFacade.findAllWoertli());

    getThreadLocalRequest().getSession().setAttribute(USER_ID, newUser.getUserId());
    getThreadLocalRequest().getSession().setAttribute(USER_NAME, aUserName);
  }

  @Override
  public UserStatsCO loadUserStat() {
    int[] groupedCounts = woertliDatabaseFacade.findAllStatsOfUser(getCurrentUserId());

    UserStatsCO userStatsCO = new UserStatsCO();
    userStatsCO.setUserName(getCurrentUserName());
    userStatsCO.setNewWords(String.valueOf(groupedCounts[0]));
    userStatsCO.setOneRight(String.valueOf(groupedCounts[1]));
    userStatsCO.setTwoRight(String.valueOf(groupedCounts[2]));
    userStatsCO.setThreeRight(String.valueOf(groupedCounts[3]));
    userStatsCO.setFourRight(String.valueOf(groupedCounts[4]));
    userStatsCO.setFiveRight(String.valueOf(groupedCounts[5]));
    userStatsCO.setOneWrong(String.valueOf(groupedCounts[6]));
    userStatsCO.setUnites(getUnitesString());

    return userStatsCO;

  }

  @Override
  public WoertliCO loadNextWoertli() {
    Woertli woertli = getNextWoertliStat().getWoertli();

    WoertliCO woertliCO = new WoertliCO();
    woertliCO.setGermanText(woertli.getGermanText());
    woertliCO.setWoertliId(woertli.getWoertliId());
    woertliCO.setGeschlecht(getGeschlechtBoolean(woertli.getGeschlecht()));
    return woertliCO;
  }

  @Override
  public String checkInput(Long aWoertliId, String aGuess, Boolean aGeschlecht) {
    WoertliStat woertliStat = woertliDatabaseFacade.findWoertliStatByUserIdAndWoertliId(getCurrentUserId(), aWoertliId);
    Woertli woertli = woertliDatabaseFacade.findById(Woertli.class, aWoertliId);

    LOG.info("Woertli mit id [" + aWoertliId + "]: [" + aGuess + "] <--> [" + woertli.getFranzText() + "]");
    String result = null;
    int factor = 0;
    if (checkWoertli(woertli, aGuess, aGeschlecht)) {
      factor = +1;
    } else {
      result = woertli.getFranzText();
      factor = -1;
    }

    setNewCorrect(woertliStat, factor);
    woertliDatabaseFacade.merge(woertliStat);

    return result;
  }

  @Override
  public void deleteWoertli(Integer aUnite) {
    try {
      woertliDatabaseFacade.deleteWoertliByUnite(aUnite);
    } catch (Exception ex) {
      LOG.error("Fehler beim Löschen der Woertli für Unité " + aUnite + ".", ex);
    }
  }

  @Override
  public void sendPassword(String aUserName) throws SecurityException, UserHandlingException {
    User user = woertliDatabaseFacade.findUserByName(aUserName);
    if (user == null) {
      throw new SecurityException("User [" + aUserName + "] wurde nicht gefunden.");
    }
    MailSenderUtil.sendMail(user);
  }

  private void connectUserToWoertli(List<User> allUsers, List<Woertli> allWoertli) {
    try {
      int counter = 0;
      for (User user : allUsers) {
        for (Woertli woertli : allWoertli) {
          WoertliStat woertliStat = new WoertliStat();
          woertliStat.setCorrect(0);
          woertliStat.setUser(user);
          woertliStat.setWoertli(woertli);
          woertliDatabaseFacade.persist(woertliStat);
          counter++;
        }
      }

      LOG.info("Es werden " + counter + " WoertliStat geschrieben.");
    } catch (Exception ex) {
      LOG.error("Fehler beim Speichern der Woertli", ex);
    }
  }

  private boolean checkWoertli(Woertli aWoertli, String aGuess, Boolean aGeschlecht) {
    boolean richtigGeschrieben = false;
    if (aWoertli.getFranzText().equals(aGuess)) {
      richtigGeschrieben = true;
    }

    boolean richtigGeschlecht = false;
    Boolean geschlechtBoolean = getGeschlechtBoolean(aWoertli.getGeschlecht());
    if (aGeschlecht == null) {
      if (geschlechtBoolean == null) {
        richtigGeschlecht = true;
      }
    } else {
      richtigGeschlecht = aGeschlecht.equals(geschlechtBoolean);
    }

    return richtigGeschrieben && richtigGeschlecht;
  }

  private Boolean getGeschlechtBoolean(Geschlecht aGeschlecht) {
    if (Geschlecht.Maennlich.equals(aGeschlecht)) {
      return Boolean.TRUE;
    } else if (Geschlecht.Weiblich.equals(aGeschlecht)) {
      return Boolean.FALSE;
    } else {
      return null;
    }
  }

  private String getUnitesString() {
    HashSet<Integer> distinct = new HashSet<Integer>();
    for (Woertli woertli : woertliDatabaseFacade.findAllWoertli()) {
      distinct.add(woertli.getUnite());
    }
    StringBuilder builder = new StringBuilder();
    for (Integer unite : distinct) {
      builder.append(unite).append(", ");
    }
    builder.setLength(builder.length() - 2);
    return builder.toString();
  }

  private Long getCurrentUserId() {
    return (Long) getThreadLocalRequest().getSession().getAttribute(USER_ID);
  }

  private String getCurrentUserName() {
    return (String) getThreadLocalRequest().getSession().getAttribute(USER_NAME);
  }

  private WoertliStat getNextWoertliStat() {
    @SuppressWarnings("unchecked")
    List<WoertliStat> woertliCache = (List<WoertliStat>) getThreadLocalRequest().getSession().getAttribute(WOERTLI_CACHE);
    if ((woertliCache == null) || (woertliCache.isEmpty())) {
      Long userId = getCurrentUserId();

      woertliCache = new ArrayList<WoertliStat>();
      List<WoertliStat> allWoertli = new ArrayList<WoertliStat>();
      allWoertli.addAll(woertliDatabaseFacade.findAllWoertliStatByUserId(userId));
      for (int i = 0; (i < 10 && !allWoertli.isEmpty()); i++) {
        int random = RANDOMIZER.nextInt(allWoertli.size());
        WoertliStat woertliStat = allWoertli.remove(random);
        woertliCache.add(woertliStat);
      }
    }
    WoertliStat head = woertliCache.remove(0);
    getThreadLocalRequest().getSession().setAttribute(WOERTLI_CACHE, woertliCache);
    return head;
  }

  private void setNewCorrect(WoertliStat aWoertliStat, int aFactor) {
    int correct = aWoertliStat.getCorrect() + aFactor;
    if (correct < -1) {
      correct = -1;
    }
    aWoertliStat.setCorrect(correct);
  }

  @Override
  protected void service(HttpServletRequest aRequest, HttpServletResponse aResponse) throws ServletException, IOException {
    try {
      String userName = (String) aRequest.getSession().getAttribute(USER_NAME);
      if (userName == null) {
        userName = "unknown";
      }
      MDC.put(UID, userName);
      super.service(aRequest, aResponse);
    } finally {
      MDC.remove(UID);
    }
  }
}
