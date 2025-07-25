package ga.overfullstack.immutables;

import ga.overfullstack.immutable.Kick;
import ga.overfullstack.immutable.StepConfig;
import java.util.Set;
import kotlin.collections.ArraysKt;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ImmutablesTest {
	@Test
	void simpleBuilder() {
		final var person = Person1.configure().age(33).isEmployed(false).isMarried(true).done();
		System.out.println(person);
	}

	@Test
	void usingFrom() {
		final var person1 =
				Person1.configure().age(33).isEmployed(false).isMarried(true).hobbies("cricket").done();
		final var person2 =
				Person1.configure().age(33).isEmployed(false).isMarried(true).hobbies("music").done();
		final var person3 = Person1.configure().from(person1).from(person2).done();
		System.out.println(person3);
	}

	@Test
	@DisplayName("Expressive factory methods")
	void expressiveFactoryMethods() {
		final var point = Point.origin();
		System.out.println(point);
	}

	@Test
	@DisplayName("Private Immutable Impl")
	void privateImmutableImpl() {
		new PersonBuilder().name("Jim Boe").address("P.O. box 0001, Lexington, KY").build();
	}

	@Test
	@DisplayName("Nested Builder")
	void nestedBuilder() {
		final var kick =
				Kick.configure()
						.stepConfig(
								StepConfig.configure()
										.folder("root")
										.nest(
												StepConfig.configure()
														.folder("folder-1")
														.runOnlyStep("step-1")
														.nest(
																StepConfig.configure()
																		.folder("folder-11")
																		.skipStep("step-11")
																		.done())
														.done())
										.done())
						.off();
		System.out.println(kick.stepConfig());
	}

	@Test
	@DisplayName("Derived attributes")
	void derivedAttributes() {
		final var kick = Kick.configure().hooks(Set.of(Set.of("a"), Set.of("b"))).off();
		System.out.println(kick.hooksFlattened());
	}

	@Test
	@DisplayName("Copy from Config")
	void copyFromConfig() {
		final var kick =
				Kick.configure().templatePathsInOdr("path").hooks(Set.of(Set.of("a"), Set.of("b"))).off();
		final var kickCopy = // Replace attributes
				kick.withTemplatePathsInOrder("pathCopy").withHooks(Set.of("c"));
		System.out.println(kickCopy);
	}

	@Test
	@DisplayName("Arrays kt")
	void arraysKt() {
		final var kick =
				Kick.configure().templatePathsInOdr("path").hooks(Set.of(Set.of("a"), Set.of("b"))).off();
		final var arr = new Kick[] {kick, kick};
		final var templatePaths = String.join(", ", ArraysKt.flatMap(arr, Kick::templatePathsInOrder));
		System.out.println(templatePaths);
	}
}
