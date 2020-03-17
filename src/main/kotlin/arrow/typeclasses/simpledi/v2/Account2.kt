/* gakshintala created on 3/6/20 */
package arrow.typeclasses.simpledi.v2

import arrow.fx.IO
import arrow.fx.IOResult
import arrow.fx.extensions.fx

data class Account(val balance: Int)
data class AccountEntity(val balance: Int)

interface Data {
    fun fetchAccount(): IO<Nothing, AccountEntity>
}

class InMemoryData : Data {

    fun retrofit(callback: (IOResult<Nothing, AccountEntity>) -> Unit): Unit =
            callback(IOResult.Success(AccountEntity(100)))

    override fun fetchAccount(): IO<Nothing, AccountEntity> =
            IO.async { callback ->
                retrofit(callback)
            }
}

interface Domain {
    // This functions is tied to two contexts Domain and Data.
    fun <R> R.getProcessedAccount(): IO<Nothing, Account> where R : Data
}

class DefaultDomain : Domain {
    override fun <R> R.getProcessedAccount(): IO<Nothing, Account> where R : Data =
            fetchAccount().map { Account(it.balance) }
}

interface UI {
    fun <R> R.present(): IO<Nothing, Unit> where R : Domain, R : Data
}

class DefaultUI : UI {
    override fun <R> R.present(): IO<Nothing, Unit> where R : Domain, R : Data =
            IO.fx<Unit> {
                val account = getProcessedAccount().bind()
                !effect { println(account) }
            }
}

class Module(
        data: Data = InMemoryData(),
        domain: Domain = DefaultDomain(),
        val ui: UI = DefaultUI()
) : Data by data, UI by ui, Domain by domain {
    companion object {
        val defaultInstance = Module()
    }
}

fun main() {
    with(Module.defaultInstance) {
        present().unsafeRunSyncEither()
    }

}
