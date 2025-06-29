package ga.overfullstack.lombok.generics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ValidationConfigTest {
	@Test
	@DisplayName("Validator and Failure types")
	void validatorAndFailureTypes() {
		final var validationConfig = ValidationConfig.<String, Integer>toValidate();
		System.out.println(validationConfig);
	}
}
