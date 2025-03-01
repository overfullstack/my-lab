package ga.overfullstack;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MilestoneLab {
  @Test
  @DisplayName("milestone")
  void milestone() {
    final var milestoneBTU = 1;
    final var originalBS = 2;
    final var amendmentBS = 3;
    final var cancellationBS = 4;
    final var nonNullCancellationDate = 5;
    final var nonNullAccomplishmentDate = 6;
    final var dateBasedBMPI = 7;
    final var eventBasedBMPI = 8;
    final var unAccomplishedBMPI = 9;
    final var readyForInvoicingBMPI = 10;
    final var nonNullNBD = 11;
    final var nbdEqualsCancellationDate = 12;
    final var bmpisBeyondCancellationDate = 13;
    final var nonMilestoneBTU = 14;

    final var cNBDEqualsCancellationDate = "(" + nonNullNBD + " AND " + nonNullCancellationDate + " AND " + nbdEqualsCancellationDate + ")";
    final var cancelledBSWithDateBasedBMPIBeyondCancellation = "(" + cNBDEqualsCancellationDate + " AND " + dateBasedBMPI + " AND " + nonNullAccomplishmentDate + " AND " + bmpisBeyondCancellationDate + ")";
    final var cancelledBSWithUnaccomplishedEventBasedBMPI = "(" + cNBDEqualsCancellationDate + " AND " + eventBasedBMPI + " AND " + unAccomplishedBMPI + ")";
    final var filterCriteriaArrangement =
        "(" + nonMilestoneBTU + " OR ("
            + milestoneBTU + " AND ((" +
            + originalBS + " AND (" +
            "(" + cancelledBSWithDateBasedBMPIBeyondCancellation + " OR " + cancelledBSWithUnaccomplishedEventBasedBMPI + ") OR (" + readyForInvoicingBMPI + ")" +
            "))" +
            " OR " + amendmentBS +
            " OR " + cancellationBS + ")))";
    // (14 OR (1 AND ((2 AND ((((11 AND 5 AND 12) AND 7 AND 6 AND 13) OR ((11 AND 5 AND 12) AND 8 AND 9)) OR (10))) OR 3 OR 4)))
    assertEquals("(14 OR (1 AND ((2 AND ((((11 AND 5 AND 12) AND 7 AND 6 AND 13) OR ((11 AND 5 AND 12) AND 8 AND 9)) OR (10))) OR 3 OR 4)))", filterCriteriaArrangement);
  }
}
