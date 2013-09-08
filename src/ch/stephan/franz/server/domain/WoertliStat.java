package ch.stephan.franz.server.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

@Entity(name = "WoertliStat")
@NamedQueries({ @NamedQuery(name = WoertliStat.COUNT_STATS_BY_USERID, query = WoertliStat.COUNT_STATS_BY_USERID_SQL),
    @NamedQuery(name = WoertliStat.FIND_STATS_BY_USERID, query = WoertliStat.FIND_STATS_BY_USERID_SQL),
    @NamedQuery(name = WoertliStat.FIND_STATS_BY_USERID_AND_WOERTLIID, query = WoertliStat.FIND_STATS_BY_USERID_AND_WOERTLIID_SQL) })
public class WoertliStat implements PersistableEntity, Serializable {
  private static final long serialVersionUID = 1L;

  public static final String COUNT_STATS_BY_USERID = "WoertliStat.countByUserId";
  public static final String COUNT_STATS_BY_USERID_SQL = "select count(ws.statId) from WoertliStat ws where ws.correct = :" + WoertliStat.CORRECT
      + " and ws.userId = :" + WoertliStat.USERID;
  public static final String FIND_STATS_BY_USERID = "WoertliStat.findByUserId";
  public static final String FIND_STATS_BY_USERID_SQL = "select ws from WoertliStat ws where ws.userId = :" + WoertliStat.USERID;
  public static final String FIND_STATS_BY_USERID_AND_WOERTLIID = "WoertliStat.findByUserIdAndWoertliId";
  public static final String FIND_STATS_BY_USERID_AND_WOERTLIID_SQL = "select ws from WoertliStat ws where ws.userId = :" + WoertliStat.USERID
      + " and ws.woertliId = :" + WoertliStat.WOERTLIID;

  public static final String USERID = "userId";
  public static final String WOERTLIID = "woertliId";
  public static final String CORRECT = "correct";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long statId;
  private Long userId;
  private Long woertliId;
  private Integer correct;
  @Transient
  private User user;
  @Transient
  private Woertli woertli;

  public WoertliStat() {
    super();
  }

  public Long getStatId() {
    return statId;
  }

  public Integer getCorrect() {
    return correct;
  }

  public void setCorrect(Integer aCorrect) {
    correct = aCorrect;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User aUser) {
    user = aUser;
    userId = aUser.getUserId();
  }

  public Woertli getWoertli() {
    return woertli;
  }

  public void setWoertli(Woertli aWoertli) {
    woertli = aWoertli;
    woertliId = aWoertli.getWoertliId();
  }

  public Long getUserId() {
    return userId;
  }

  public Long getWoertliId() {
    return woertliId;
  }

}
