package ch.stephan.franz.server.domain;

public enum Geschlecht {
  Maennlich("m"), Weiblich("f");

  private final String value;

  private Geschlecht(String aValue) {
    value = aValue;
  }

  public String getValue() {
    return value;
  }

  public static Geschlecht valueOfGeschlecht(String aValue) {
    for (Geschlecht current : values()) {
      if (current.getValue().equals(aValue)) {
        return current;
      }
    }
    throw new IllegalArgumentException("No enum constant " + Geschlecht.class.getName() + "." + aValue);
  }

}
