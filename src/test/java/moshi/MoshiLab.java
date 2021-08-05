package moshi;

import org.junit.jupiter.api.Test;
import utils.Utils;

class MoshiLab {

  @Test
  void testReadJson() {
    System.out.println(Utils.readFileFromTestResource("bean.json"));
  }
}

