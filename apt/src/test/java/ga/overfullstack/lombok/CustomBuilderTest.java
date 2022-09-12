package ga.overfullstack.lombok;

import ga.overfullstack.lombok.custombuilder.Message;
import org.junit.jupiter.api.Test;

import java.util.List;

class CustomBuilderTest {

  @Test
  void customMessageBuilder1() {
    final var msg = Message.builder()
        .sender("user@somedomain.com")
        .recipient("someuser@otherdomain.com")
        .text("How are you today?")
        .files(List.of("file1", "file2"))
        .build();
    System.out.println(msg);
  }

  @Test
  void customMessageBuilder2() {
    final var msg = Message.builder()
        .sender("user@somedomain.com")
        .recipient("someuser@otherdomain.com")
        .file("file1")
        .file("file2")
        .build();
    System.out.println(msg);
  }

  @Test
  void customMessageBuilderWithVarargs() {
    final var msg = Message.builder()
        .sender("user@somedomain.com")
        .recipient("someuser@otherdomain.com")
        .files("file1", "file2")
        .build();
    System.out.println(msg);
  }

}
