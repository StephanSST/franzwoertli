package ch.stephan.franz.server;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import ch.stephan.franz.server.domain.Geschlecht;
import ch.stephan.franz.server.domain.Woertli;

public class WoertliParser {

  /**
   * Parse't einen Json Stream in eine Liste von Woertli.
   */
  public static List<Woertli> parseStream(String jsonStream) {
    List<Woertli> result = new ArrayList<Woertli>();
    try {
      JSONParser parser = new JSONParser();

      JSONArray jsonArray = (JSONArray) parser.parse(jsonStream);
      for (Object current : jsonArray) {
        JSONObject jsonWoertli = (JSONObject) ((JSONObject) current).get("Woertli");
        Woertli woertli = new Woertli();
        woertli.setFranzText((String) jsonWoertli.get("franzText"));
        woertli.setGermanText((String) jsonWoertli.get("germanText"));
        woertli.setOtherSource((String) jsonWoertli.get("otherSource"));
        woertli.setUnite(((Long) jsonWoertli.get("unite")).intValue());
        determineGeschlecht(woertli);
        result.add(woertli);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return result;
  }

  private static void determineGeschlecht(Woertli aWoertli) {
    Pattern GESCHLECHT_PATTERN = Pattern.compile("\\s\\(.\\)$");
    Matcher matcher = GESCHLECHT_PATTERN.matcher(aWoertli.getFranzText());
    if (matcher.find()) {
      String geschlecht = matcher.group(0);
      aWoertli.setFranzText(matcher.replaceAll(""));
      aWoertli.setGeschlecht(Geschlecht.valueOfGeschlecht(geschlecht.substring(2, 3)));
    }
  }

}
