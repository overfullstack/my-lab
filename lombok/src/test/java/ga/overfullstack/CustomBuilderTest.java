package ga.overfullstack;

import ga.overfullstack.custombuilder.Message;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

class CustomBuilderTest {

  @Test
  public void givenBuilderWithCustomSetter_TestTextOnly() {
    Message message = Message.builder()
        .sender("user@somedomain.com")
        .recipient("someuser@otherdomain.com")
        .text("How are you today?")
        .<String>files(List.of("1"))
        .build();
  }

  @Test
  public void givenBuilderWithCustomSetter_TestFileOnly() {
    Message message = Message.builder()
        .sender("user@somedomain.com")
        .recipient("someuser@otherdomain.com")
        .file(new File("/path/to/file"))
        .build();
  }

}
