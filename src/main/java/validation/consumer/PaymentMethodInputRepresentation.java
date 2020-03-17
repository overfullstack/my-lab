package validation.consumer;

import lombok.Value;
import validation.lib.ConnectInputRepresentation;

/**
 * gakshintala created on 2/20/20.
 */
@Value
public class PaymentMethodInputRepresentation implements ConnectInputRepresentation {
    ID id;
}
