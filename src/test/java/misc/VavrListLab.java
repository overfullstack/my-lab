package misc;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

class VavrListLab {

  private static final Logger logger = LoggerFactory.getLogger(VavrListLab.class);

  @Test
  Tuple2<List<Integer>, List<Integer>> listPair() {
    var list1 = List.of(1,2,3);
    var list2 = List.of(1,2,3);
    return Tuple.of(list1, list2);
  }

  @Test
  void optionalTest() {
    Optional.empty().or(() -> null);
  }
  
  @Test
  void zipit2() {
    try {
      var list1 = List.of(1,2,3);
      var list2 = List.of(1,2,3);
      list1.zip(list2);
    } catch(NullPointerException e) {
      throw e;
    } catch(IllegalArgumentException e) {
      throw e;
    } catch(IndexOutOfBoundsException e) {
      throw e;
    }
  }
}
