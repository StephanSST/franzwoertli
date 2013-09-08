package ch.stephan.franz.shared;

import java.io.Serializable;

public class WoertliCO implements Serializable {
  private static final long serialVersionUID = 1L;

  private Long woertliId;
  private String germanText;
  private Boolean geschlecht;

  public WoertliCO() {
    super();
  }

  public Long getWoertliId() {
    return woertliId;
  }

  public void setWoertliId(Long woertliId) {
    this.woertliId = woertliId;
  }

  public String getGermanText() {
    return germanText;
  }

  public void setGermanText(String germanText) {
    this.germanText = germanText;
  }

  public Boolean getGeschlecht() {
    return geschlecht;
  }

  public void setGeschlecht(Boolean geschlecht) {
    this.geschlecht = geschlecht;
  }

}
