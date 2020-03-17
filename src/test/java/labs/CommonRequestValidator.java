package labs;

import validation.consumer.ID;
import validation.lib.ConnectInputRepresentation;
import validation.lib.RequestValidation;
import validation.lib.ValidationFailure;

import java.util.Optional;
import java.util.function.Function;

import static validation.lib.ValidationFailure.FAILURE;
import static validation.lib.ValidationFailure.SUCCESS;

/**
 * gakshintala created on 2/20/20.
 */
public class CommonRequestValidator<InputRepresentationT extends ConnectInputRepresentation> {
    static Function<String, ValidationFailure> validate = gatewayId -> SUCCESS;

    public RequestValidation<InputRepresentationT> getGatewayIdValidatorForId(
            Function<? super InputRepresentationT, ID> getPaymentGatewayId) {
        return getGatewayIdValidator(inputRepresentation ->
                Optional.of(inputRepresentation).map(getPaymentGatewayId).map(ID::getValue).get());
    }

    public RequestValidation<InputRepresentationT> getGatewayIdValidatorForId2(
            Function<? super InputRepresentationT, ID> getPaymentGatewayId) {

        return getGatewayIdValidator(inputRepresentation -> {
            final var gatewayId = getPaymentGatewayId.apply(inputRepresentation);
            return gatewayId == null ? null : gatewayId.getValue();
        });
    }

    public RequestValidation<InputRepresentationT> getGatewayIdValidator(
            Function<? super InputRepresentationT, String> getPaymentGatewayId) {
        return inputRepresentation -> Optional.of(inputRepresentation)
                .map(getPaymentGatewayId)
                .filter(gatewayId -> !gatewayId.isEmpty())
                .map(ignore -> SUCCESS)
                .orElseGet(() -> FAILURE);
    }

    public RequestValidation<InputRepresentationT> getGatewayIdValidator2(
            Function<? super InputRepresentationT, ID> getPaymentGatewayId) {
        return inputRepresentation -> Optional.of(inputRepresentation)
                .map(getPaymentGatewayId)
                .map(ID::getValue)
                .filter(gatewayId -> !gatewayId.isEmpty())
                .map(validate)
                .orElseGet(() -> FAILURE);
    }
}
