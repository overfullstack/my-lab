import org.junit.jupiter.api.Test;

public class PolyglotLab {
  @Test
  public void testPolyglotLab() {
    final var pm = new PostmanAPI();
    pm.environment.set("foo", "bar");
  }
}
