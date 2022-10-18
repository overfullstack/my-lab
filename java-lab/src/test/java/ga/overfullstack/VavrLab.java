package ga.overfullstack;

import io.vavr.control.Either;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class VavrLab {

  @Test
  void either() {
    final var right = Optional.of(Either.<Integer, Integer>left(1));
    System.out.println(right.map(etr -> etr.map(v -> v == 1).getOrElse(false)).orElse(false));
  }
}
