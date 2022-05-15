package ga.overfullstack;

import org.junit.jupiter.api.Test;
import org.revcloud.ReVoman;

class ReVomanTest {
  private static final String TEST_RESOURCES_PATH = "src/test/resources/";

  @Test
  void traveler() {
    final var pokemon = ReVoman.revUp(TEST_RESOURCES_PATH + "Traveler.postman_collection.json");
    System.out.println(pokemon);
  }
}
