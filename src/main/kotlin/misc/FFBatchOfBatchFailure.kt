package misc

import io.vavr.control.Either

/**
 * This is an inline wrapper to represent `FFBatchOfBatchFailure`
 */
@JvmInline
value class FFBatchOfBatchFailure<FailureT>(private val failure: Either<FailureT?, List<FailureT?>>) {
  val containerFailure: FailureT?
    get() = if (failure.isLeft) failure.left else null
  val batchMemberFailures: List<FailureT?>
    get() = if (failure.isRight) failure.get() else emptyList()
}
