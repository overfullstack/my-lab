package eggservice;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;

class EggServiceTest2 {
  private static final Logger logger = LoggerFactory.getLogger(EggServiceTest2.class);

  @Test
  void eggService2() {
    var sharedMap = new HashMap<Integer, Failure>();
    var eggs =
        Arrays.asList(
            new Egg(1, new GregorianCalendar(2021, Calendar.JUNE, 6).getTime()),
            new Egg(11, new GregorianCalendar(2021, Calendar.JUNE, 6).getTime()),
            new Egg(2, new GregorianCalendar(2021, Calendar.JUNE, 13).getTime()),
            new Egg(22, new GregorianCalendar(2021, Calendar.JUNE, 13).getTime()),
            new Egg(3, new GregorianCalendar(2021, Calendar.JUNE, 16).getTime()));
    var expiredEggs = new ArrayList<Egg>();
    for (var egg : eggs) {
      if (egg.getDate().getDate() < 15) {
        expiredEggs.add(egg);
      }
    }
    stampFailureMsg1(expiredEggs, sharedMap);
    logger.debug("!!! Failures: ---\n" + sharedMap);

    var expiredGroupToLog = groupEggsByDate(expiredEggs);
    logger.debug("Expired Eggs: ---\n" + expiredGroupToLog);
  }

  /**
   * There is no difference from Functional approach
   */
  @Test
  void eggService3() {
    var sharedMap = new HashMap<Integer, Failure>();
    var eggs =
        Arrays.asList(
            new Egg(1, new GregorianCalendar(2021, Calendar.JUNE, 6).getTime()),
            new Egg(11, new GregorianCalendar(2021, Calendar.JUNE, 6).getTime()),
            new Egg(2, new GregorianCalendar(2021, Calendar.JUNE, 13).getTime()),
            new Egg(22, new GregorianCalendar(2021, Calendar.JUNE, 13).getTime()),
            new Egg(3, new GregorianCalendar(2021, Calendar.JUNE, 16).getTime()));

    var eggGroupByExpiryDate = groupEggsByDate(eggs);
    logger.debug("Eggs Group by date: ---\n" + eggGroupByExpiryDate);

    for (var entry : eggGroupByExpiryDate.entrySet()) {
      final var eggGroupPerDate = entry.getValue();
      final var expiryDateForAllInGroup = entry.getKey();
      if (expiryDateForAllInGroup.getDate() < 15 && !eggGroupPerDate.isEmpty()) {
        final var firstEggFromGroup = eggGroupPerDate.get(0);
        var ageOfAllEggsInGroup = diffFromLayingDate(firstEggFromGroup.getId());
        stampFailureMsg2(eggGroupPerDate, ageOfAllEggsInGroup, sharedMap);
      }
    }
    logger.debug("!!! Failures: ---\n" + sharedMap);
  }

  private static Map<Date, List<Egg>> groupEggsByDate(List<Egg> eggs) {
    var group = new HashMap<Date, List<Egg>>();
    for (var egg : eggs) {
      var date = egg.getDate();
      var list = group.get(date);
      if (list == null) {
        list = new ArrayList<>();
      }
      list.add(egg);
      group.put(date, list);
    }
    return group;
  }

  private long diffFromLayingDate(int eggId) {
    final var diffInMillis = new Date().getTime() - queryEggLayingDateFromDB(eggId).getTime();
    return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
  }

  public Date getEggLayingDate1(int eggId) {
    return queryEggLayingDateFromDB(eggId);
  }

  private static Map<Integer, Date> eggLayingDateCacheById = new HashMap<>();

  public Date getEggLayingDate(int eggId) {
    return eggLayingDateCacheById.computeIfAbsent(eggId, this::queryEggLayingDateFromDB);
  }

  @Test
  void eggTest() {
    hasEggRotten(1);
    System.out.println(calculateEggAge(1, new Date()));
  }

  boolean hasEggRotten(int eggId) {
    var layingDate = getEggLayingDate(eggId);
    if (layingDate.getDate() < 15) {
      // It's just logging, let's reuse the same Date obj for month and year
      layingDate.setDate(15);
      logger.info("Laying should be before: " + layingDate);
      return true;
    }
    return false;
  }

  long calculateEggAge(int eggId, Date today) {
    return today.getDate() - getEggLayingDate(eggId).getDate();
  }

  @Test
  void printEggAge() {
    var eggId = 0;
    if (hasEggRotten(eggId)) {
      final var age = calculateEggAge(eggId, new Date());
      System.out.println(age);
    }
  }

  private Date queryEggLayingDateFromDB(int eggId) {
    return new GregorianCalendar(2021, Calendar.JUNE, 1).getTime();
  }

  private static void stampFailureMsg2(
      List<Egg> expiredEggs, long ageOfAllEggsInGroup, Map<Integer, Failure> sharedMap) {
    for (var egg : expiredEggs) {
      final var date = egg.getDate();
      date.setDate(15); // To capture current month
      var msg = "Egg Age: " + ageOfAllEggsInGroup + " Expiry should be before: " + date;
      sharedMap.put(egg.getId(), new Failure(msg));
    }
  }

  private void stampFailureMsg1(List<Egg> eggs, Map<Integer, Failure> sharedMap) {
    for (var egg : eggs) {
      var ageOfAllEggsInGroup = diffFromLayingDate(egg.getId());
      final var date = egg.getDate();
      date.setDate(15); // Why to create a new Date object!?
      /*var dateToPrint = new GregorianCalendar();
      dateToPrint.setDate(15);*/
      var msg = "Egg Age: " + ageOfAllEggsInGroup + " Expiry should be before: " + date;
      sharedMap.put(egg.getId(), new Failure(msg));
    }
  }

  private static class Egg {
    private int id;
    private Date date;

    private Egg(int id, Date date) {
      this.id = id;
      this.date = date;
    }

    public int getId() {
      return id;
    }

    public Date getDate() {
      return date;
    }

    @Override
    public String toString() {
      return "Egg{" + "id=" + id + ", date=" + date + '}';
    }
  }

  private static class Failure {
    String msg;

    private Failure(String msg) {
      this.msg = msg;
    }

    @Override
    public String toString() {
      return "Failure{" + "msg='" + msg + '\'' + '}';
    }
  }
}
