package ga.overfullstack;

import static java.util.function.Function.identity;

import io.vavr.Function2;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import kotlin.Pair;
import kotlin.collections.MapsKt;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MapLab {
	@Test
	@DisplayName("map merge")
	void mapMerge() {
		final var map1 = MapsKt.mutableMapOf(new Pair<>("a", 1), new Pair<>("b", 2));
		map1.merge(
				"c",
				3,
				(oldValue, newValue) -> {
					System.out.println("Shouldn't be called for new Key");
					return oldValue + newValue;
				});
	}

	@Test
	void testMapLab() {
		final var bsId = List.of("a", "b", "c");
		final var bsBPIs = Map.of("a", List.of(1, 2, 3), "b", List.of(4, 5, 6), "c", List.of(7, 8, 9));
		final var bsBPIList =
				bsBPIs.entrySet().stream()
						.map(
								entry ->
										entry.getValue().stream()
												.collect(Collectors.toMap(identity(), ignore -> entry.getKey())))
						.flatMap(map -> map.entrySet().stream())
						.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
		System.out.println(bsBPIList);
	}

	@Test
	void testRemoveKeySet() {
		final var mutableMap = new HashMap<String, String>();
		mutableMap.put("a", "A");
		mutableMap.put("b", "B");
		mutableMap.put("c", "C");
		final var list = List.of("a", "b", "c");
		list.forEach(mutableMap::remove);
		System.out.println(mutableMap);
	}

	@Test
	void lambdaAsKey() {
		final var bean = new FakeBean() {};
		final Function2<Integer, String, Integer> getInt = bean::getInt;
		FakeBean.map.put(getInt, new IllegalArgumentException("For getInt"));
		System.out.println(FakeBean.map.get(getInt));
	}

	@Test
	@DisplayName("MapsKt with allowed null Value")
	void mapsKtWithNullValue() {
		System.out.println(MapsKt.mapOf(new Pair<>("a", null)));
	}

	interface FakeBean {
		Map<? super Object, ? super Exception> map = new HashMap<>();

		default int getInt(int a, String b) {
			return 1;
		}

		default String getIntString(int a) {
			return "a";
		}
	}
}
