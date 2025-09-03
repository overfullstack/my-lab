package ga.overfullstack;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StreamLab {
	@Test
	@DisplayName("allMatch on emptyList")
	void allMatchOnEmptyList() {
		System.out.println(Stream.of().allMatch(x -> true));
	}

  @Test
  @DisplayName("reduce")
  void reduce() {
    System.out.println(Stream.of(1, 2, 3).reduce((sum, num) -> { // `n-1` iterations only
      System.out.println(sum + ", " + num);
      return sum + num;
    }));
  }
}
