package ch.stephan.franz.server.facade;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMFService {
  private static final EntityManagerFactory EM_FACTORY = Persistence.createEntityManagerFactory("transactions-optional");

  public static EntityManagerFactory get() {
    return EM_FACTORY;
  }

}