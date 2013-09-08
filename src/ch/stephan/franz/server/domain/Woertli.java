package ch.stephan.franz.server.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity(name = "Woertli")
@NamedQueries({ @NamedQuery(name = Woertli.FIND_ALL, query = Woertli.FIND_ALL_SQL),
    @NamedQuery(name = Woertli.DELETE_BY_UNITE, query = Woertli.DELETE_BY_UNITE_SQL) })
public class Woertli implements PersistableEntity, Serializable {
  private static final long serialVersionUID = 1L;

  public static final String FIND_ALL = "Woertli.findAll";
  public static final String FIND_ALL_SQL = "SELECT w FROM Woertli w";
  public static final String DELETE_BY_UNITE = "Woertli.deleteByUnite";
  public static final String DELETE_BY_UNITE_SQL = "DELETE FROM Woertli w WHERE w.unite = :" + Woertli.UNITE;
  public static final String UNITE = "unite";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long woertliId;
  private String germanText;
  private String franzText;
  private Integer unite;
  private String otherSource;

  private Geschlecht geschlecht;

  public Woertli() {
    super();
  }

  public Long getWoertliId() {
    return woertliId;
  }

  public String getGermanText() {
    return germanText;
  }

  public void setGermanText(String aGermanText) {
    germanText = aGermanText;
  }

  public String getFranzText() {
    return franzText;
  }

  public void setFranzText(String aFranzText) {
    franzText = aFranzText;
  }

  public Integer getUnite() {
    return unite;
  }

  public void setUnite(Integer aUnite) {
    unite = aUnite;
  }

  public String getOtherSource() {
    return otherSource;
  }

  public void setOtherSource(String aOtherSource) {
    otherSource = aOtherSource;
  }

  public Geschlecht getGeschlecht() {
    return geschlecht;
  }

  public void setGeschlecht(Geschlecht aGeschlecht) {
    geschlecht = aGeschlecht;
  }

}
