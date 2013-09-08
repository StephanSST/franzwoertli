package ch.stephan.franz.server.facade;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import ch.stephan.franz.server.domain.User;
import ch.stephan.franz.server.domain.Woertli;
import ch.stephan.franz.server.domain.WoertliStat;

public class WoertliDatabaseFacadeBean {
  private static final Logger LOG = Logger.getLogger(WoertliDatabaseFacadeBean.class.getName());

  public <T> T persist(T persistableEntity) {
    EntityManager em = EMFService.get().createEntityManager();
    try {
      em.persist(persistableEntity);
      return persistableEntity;
    } finally {
      em.close();
    }
  }

  public <T> void merge(T persistableEntity) {
    EntityManager em = EMFService.get().createEntityManager();
    try {
      em.merge(persistableEntity);
    } finally {
      em.close();
    }
  }

  public List<Woertli> findAllWoertli() {
    EntityManager em = EMFService.get().createEntityManager();
    try {
      Query query = em.createNamedQuery(Woertli.FIND_ALL);
      @SuppressWarnings("unchecked")
      List<Woertli> resultList = query.getResultList();
      LOG.info("Es wurden " + resultList.size() + " Woertli gefunden.");
      return resultList;
    } finally {
      em.close();
    }
  }

  public List<User> findAllUser() {
    EntityManager em = EMFService.get().createEntityManager();
    try {
      Query query = em.createNamedQuery(User.FIND_ALL);
      @SuppressWarnings("unchecked")
      List<User> resultList = query.getResultList();
      LOG.info("Es wurden " + resultList.size() + " Benutzer gefunden.");
      return resultList;
    } finally {
      em.close();
    }
  }

  public User findUserByName(String userName) {
    EntityManager em = EMFService.get().createEntityManager();
    try {
      Query query = em.createNamedQuery(User.FIND_BY_NAME);
      query.setParameter(User.USERNAME, userName);
      return (User) query.getSingleResult();
    } catch (NoResultException nrex) {
      LOG.warn("User [" + userName + "] nicht gefunden.");
      return null;
    } finally {
      em.close();
    }
  }

  public <T> T findById(Class<T> aClass, Long aId) {
    EntityManager em = EMFService.get().createEntityManager();
    try {
      return em.find(aClass, aId);
    } catch (NoResultException nrex) {
      LOG.warn(aClass.getSimpleName() + " mit id [" + aId + "] nicht gefunden.");
      return null;
    } finally {
      em.close();
    }
  }

  public void deleteWoertliByUnite(Integer unite) {
    EntityManager em = EMFService.get().createEntityManager();
    try {
      Query query = em.createNamedQuery(Woertli.DELETE_BY_UNITE);
      query.setParameter(Woertli.UNITE, unite);
      int anzahl = query.executeUpdate();
      LOG.info("Für Unité " + unite + " wurden " + anzahl + " Woertli gelöscht.");
    } finally {
      em.close();
    }
  }

  public List<WoertliStat> findAllWoertliStatByUserId(Long userId) {
    EntityManager em = EMFService.get().createEntityManager();
    try {
      Query query = em.createNamedQuery(WoertliStat.FIND_STATS_BY_USERID);
      query.setParameter(WoertliStat.USERID, userId);
      @SuppressWarnings("unchecked")
      List<WoertliStat> resultList = query.getResultList();
      enhanceWoertliStatList(resultList);
      return resultList;
    } finally {
      em.close();
    }
  }

  public WoertliStat findWoertliStatByUserIdAndWoertliId(Long userId, Long woertliId) {
    EntityManager em = EMFService.get().createEntityManager();
    try {
      Query query = em.createNamedQuery(WoertliStat.FIND_STATS_BY_USERID_AND_WOERTLIID);
      query.setParameter(WoertliStat.USERID, userId);
      query.setParameter(WoertliStat.WOERTLIID, woertliId);
      WoertliStat woertliStat = (WoertliStat) query.getSingleResult();
      enhanceWoertliStatList(Arrays.asList(woertliStat));
      return woertliStat;
    } finally {
      em.close();
    }
  }

  public int[] findAllStatsOfUser(Long userId) {
    int[] result = new int[7];
    result[0] = countWoertliStatByNumber(userId, 0);
    result[1] = countWoertliStatByNumber(userId, 1);
    result[2] = countWoertliStatByNumber(userId, 2);
    result[3] = countWoertliStatByNumber(userId, 3);
    result[4] = countWoertliStatByNumber(userId, 4);
    result[5] = countWoertliStatByNumber(userId, 5);
    result[6] = countWoertliStatByNumber(userId, -1);
    return result;
  }

  private Integer countWoertliStatByNumber(Long userId, Integer correctNumber) {
    EntityManager em = EMFService.get().createEntityManager();
    try {
      Query query = em.createNamedQuery(WoertliStat.COUNT_STATS_BY_USERID);
      query.setParameter(WoertliStat.CORRECT, correctNumber);
      query.setParameter(WoertliStat.USERID, userId);
      Long count = (Long) query.getSingleResult();
      return count.intValue();
    } finally {
      em.close();
    }
  }

  private void enhanceWoertliStatList(List<WoertliStat> aResultList) {
    for (WoertliStat woertliStat : aResultList) {
      if (woertliStat.getUser() == null) {
        User user = findById(User.class, woertliStat.getUserId());
        woertliStat.setUser(user);
      }
      if (woertliStat.getWoertli() == null) {
        Woertli woertli = findById(Woertli.class, woertliStat.getWoertliId());
        if (woertli == null) {
          throw new IllegalStateException("WoertliStat " + woertliStat.getStatId() + " zeigt auf Woertli " + woertliStat.getWoertliId()
              + ", das nicht existiert.");
        }
        woertliStat.setWoertli(woertli);
      }
    }
  }

}
