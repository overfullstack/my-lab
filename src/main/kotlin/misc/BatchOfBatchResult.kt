package misc

import io.vavr.control.Either

/**
 * This is an inline wrapper to represent `BatchOfBatchFailure`
 */
@JvmInline
value class BatchOfBatchFailure<FailureT>(val failure: Either<FailureT?, List<FailureT?>>) {
  fun getContainerFailure(): FailureT? = if (failure.isLeft) failure.left else null
  fun getBatchMemberFailures(): List<FailureT?> = if (failure.isRight) failure.get() else emptyList()
}
