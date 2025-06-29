package ga.overfullstack;

import static com.google.common.truth.Truth.assertThat;
import static kotlin.system.TimingKt.measureTimeMillis;

import java.util.Map;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class KtLab {
	@Test
	@DisplayName("append to null")
	void appendToNull() {
		final var list = CollectionsKt.plus(null, "a");
		Assertions.assertNotNull(list);
	}

	@Test
	@DisplayName("Merge Maps with plus")
	void mergeMapsWithPlus() {
		final var map1 = Map.of("a", "1", "b", "21");
		final var map2 = Map.of("b", "22", "c", "3");
		System.out.println(String.join(",", MapsKt.plus(map1, map2).values()));
		System.out.println(String.join(",", MapsKt.plus(map2, map1).values()));
	}

	@Test
	@DisplayName("measureTimeInMillis")
	void measureTimeInMillis() {
		final var time =
				measureTimeMillis(
						() -> {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								throw new RuntimeException(e);
							}
							return Unit.INSTANCE;
						});
		assertThat(time).isGreaterThan(1000);
	}
}
