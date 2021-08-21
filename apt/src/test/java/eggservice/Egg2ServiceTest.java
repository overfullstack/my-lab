package eggservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("deprecation")
class Egg2ServiceTest {

  private static final Logger logger = LoggerFactory.getLogger(Egg2ServiceTest.class);

  /**
   * I can't represent the same in FP. Plus I can't avoid `diffCache`.
   */
  @Test
  void eggService1() {
    var failureMap = new HashMap<Integer, Failure>();
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
    stampFailureMsg1(expiredEggs, failureMap);
    // stampFailureMsg2(expiredEggs, failureMap);
    logger.debug("!!! Failures: ---\n" + failureMap);
  }

  private static void stampFailureMsg1(List<Egg> eggs, Map<Integer, Failure> failureMap) {
    for (var egg : eggs) {
      var eggAge = diffFromLayingDate(egg.getId());
      final var date = egg.getDate();
      date.setDate(15); // Why to create a new Date object!?
      /*var dateToPrint = new GregorianCalendar();
      dateToPrint.setDate(15);*/
      var msg = "Egg Age: " + eggAge + ". Expiry should be before: " + date;
      failureMap.put(egg.getId(), new Failure(msg));
    }
  }

  private static long diffFromLayingDate(int eggId) {
    final var diffInMillis = new Date().getTime() - getEggLayingDateFromDB(eggId).getTime();
    return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
  }

  private static void stampFailureMsg2(List<Egg> eggs, Map<Integer, Failure> sharedMap) {
    var diffCache = new HashMap<Date, Long>();
    for (var egg : eggs) {
      final var date = egg.getDate();
      var eggAge = diffFromLayingDateOptimized(date, egg.getId(), diffCache);
      date.setDate(15); // Why to create a new Date object!?
      var msg = "Egg Age: " + eggAge + " Expiry should be before: " + date;
      sharedMap.put(egg.getId(), new Failure(msg));
    }
  }

  private static long diffFromLayingDateOptimized(
      Date eggExpiryDate, int eggId, Map<Date, Long> diffCache) {
    if (diffCache.containsKey(eggExpiryDate)) {
      logger.debug("Key found: " + eggExpiryDate);
      return diffCache.get(eggExpiryDate);
    }
    // Compute
    final var diffInMillis = new Date().getTime() - getEggLayingDateFromDB(eggId).getTime();
    final var diff = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
    diffCache.put(eggExpiryDate, diff);
    return diff;
  }

  private static Date getEggLayingDateFromDB(int eggId) {
    return new GregorianCalendar(2021, Calendar.MAY, 1).getTime();
  }

  private static class Egg {
    private int id;
    private Date date;

    public Egg(int id, Date date) {
      this.id = id;
      this.date = date;
    }

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public Date getDate() {
      return date;
    }

    public void setDate(Date date) {
      this.date = date;
    }
  }

  private static class Failure {
    String msg;

    public Failure(String msg) {
      this.msg = msg;
    }

    public String getMsg() {
      return msg;
    }

    public void setMsg(String msg) {
      this.msg = msg;
    }
  }
}
