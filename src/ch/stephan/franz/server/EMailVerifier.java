package ch.stephan.franz.server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EMailVerifier {

  private static final String EMAIL_PATTERN_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
  private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_PATTERN_STRING);

  /**
   * EMail Adresse validieren.
   * 
   * @see 
   *      {http://www.mkyong.com/regular-expressions/how-to-validate-email-address
   *      -with-regular-expression/}
   * @param aEmail
   *          Die EMail Adresse, welche geprüft werden soll.
   * @return Das Resultat der Validierung.
   */
  public static boolean isValidEmail(String aEmail) {
    Matcher matcher = EMAIL_PATTERN.matcher(aEmail);
    return matcher.matches();
  }

}
