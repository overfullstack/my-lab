package ga.overfullstack.interop;

import java.util.List;
import kotlin.collections.CollectionsKt;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CollectionsKtLab {
	@Test
	@DisplayName("Collections Kt")
	void collectionsKt() {
		System.out.println(CollectionsKt.associateBy(List.of(1, 2, 3, 1), String::valueOf));
	}

	@Test
	@DisplayName("Class Package path")
	void classPackagePath() {
		System.out.println(this.getClass().getResource("").getPath());
	}
}
