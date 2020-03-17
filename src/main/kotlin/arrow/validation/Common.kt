package arrow.validation

import arrow.core.Nel

sealed class ValidationError(val msg: String) {
    data class DoesNotContain(val value: String) : ValidationError("Did not contain $value")
    data class MaxLength(val value: Int) : ValidationError("Exceeded length of $value")
    data class NotAnEmail(val reasons: Nel<ValidationError>) : ValidationError("Not a valid email")
}

sealed class FFType {
    data class NewFormField1(val label: String, val value: String) : FFType()
    data class FormField2(val label: String, val value: String) : FFType()
}

data class FormField(val label: String, val value: String)
data class FormField22(val label: String, val value: String)
data class Email(val value: String)
