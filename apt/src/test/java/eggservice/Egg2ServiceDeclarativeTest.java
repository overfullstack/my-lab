package eggservice;

import org.immutables.value.Value;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

class Egg2ServiceDeclarativeTest {

  private static final Logger logger = LoggerFactory.getLogger(Egg2ServiceDeclarativeTest.class);

  @Test
  void eggServiceDeclarative() {
    var eggs =
        List.of( // TODO 13/06/21 gopala.akshintala: Need to get an Either.RIGHT here
            ImmutableEgg2.of(1, LocalDate.of(2021, 6, 6)),
            ImmutableEgg2.of(11, LocalDate.of(2021, Calendar.JUNE, 6)),
            ImmutableEgg2.of(2, LocalDate.of(2021, Calendar.JUNE, 13)),
            ImmutableEgg2.of(22, LocalDate.of(2021, Calendar.JUNE, 13)),
            ImmutableEgg2.of(3, LocalDate.of(2021, Calendar.JUNE, 16)));
    var eggGroupByExpiryDate =
        eggs.stream()
            .filter(egg -> egg.localDate().getDayOfMonth() < 15)
            .collect(Collectors.groupingBy(Egg2::localDate));
    var failures =
        eggGroupByExpiryDate.entrySet().stream()
            .flatMap(
                entry -> {
                  final var eggGroupWithSameExpiryDate = entry.getValue();
                  final var firstEggFromGroup = eggGroupWithSameExpiryDate.get(0);
                  final var ageOfAllEggsInGroup = diffFromLayingDate(firstEggFromGroup.id());
                  final var expiryDateForAllInGroup = entry.getKey();
                  var msg =
                      "Egg Age: "
                          + ageOfAllEggsInGroup
                          + " Expiry should be before: "
                          + LocalDate.of(
                          expiryDateForAllInGroup.getYear(),
                          expiryDateForAllInGroup.getMonth(),
                          15);
                  return eggGroupWithSameExpiryDate.stream()
                      .map(ignore -> ImmutableFailure.of(msg));
                });
    logger.debug("!!! Failures: ---\n" + failures);
  }

  private static long diffFromLayingDate(int eggId) {
    return Period.between(LocalDate.now(), getEggLayingDateFromDB(eggId)).getDays();
  }

  private static LocalDate getEggLayingDateFromDB(int eggId) {
    return LocalDate.now().minus(1, ChronoUnit.MONTHS);
  }

  @Value.Style(allParameters = true)
  @Value.Immutable
  interface Egg2 {
    int id();

    LocalDate localDate();
  }

  @Value.Style(allParameters = true)
  @Value.Immutable
  interface Failure {
    String msg();
  }
}
