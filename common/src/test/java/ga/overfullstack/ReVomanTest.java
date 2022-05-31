package ga.overfullstack;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import org.revcloud.ReVoman;

class ReVomanTest {
  private static final String TEST_RESOURCES_PATH = "src/test/resources/";

  @Test
  void traveler() {
    final var pokemon = ReVoman.revUp(TEST_RESOURCES_PATH + "Traveler.postman_collection.json");
    System.out.println(pokemon);
  }

  @Test
  void absolutePath() throws URISyntaxException {
    final var res = getClass().getClassLoader().getResource("Traveler.postman_collection.json");
    final var file = Paths.get(res.toURI()).toFile();
    final var absolutePath = file.getAbsolutePath();
    System.out.println(absolutePath);
  }

  @Test
  void jarPath() throws URISyntaxException {
    System.out.println(ReVoman.class
        .getProtectionDomain()
        .getCodeSource()
        .getLocation()
        .toURI()
        .getPath());
  }
}
