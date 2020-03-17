package arrow.typeclasses.simplediv1

import arrow.core.right
import arrow.fx.IO
import arrow.fx.IOResult
import arrow.fx.extensions.fx

data class Account(val balance: Int)
data class AccountEntity(val balance: Int)

/** User algebras **/
interface UI {
    fun present(): IO<Nothing, Unit>
}

interface Domain {
    fun getProcessedAccount(): IO<Nothing, Account>
}

interface Data {
    fun fetchAccount(): IO<Nothing, AccountEntity>
}

/** interpreters */
object InMemoryData : Data {

    fun retrofit(callback: (IOResult<Nothing, AccountEntity>) -> Unit): Unit =
            callback(IOResult.Success(AccountEntity(100)))

    override fun fetchAccount(): IO<Nothing, AccountEntity> =
            IO.async { callback ->
                retrofit(callback)
            }
}

class DefaultDomain(val data: Data) : Domain {
    override fun getProcessedAccount(): IO<Nothing, Account> =
            data.fetchAccount().map { Account(it.balance) }
}

class DefaultUI(val domain: Domain) : UI {
    override fun present(): IO<Nothing, Unit> =
            IO.fx<Unit> {
                val account = domain.getProcessedAccount().bind()
                !effect { println(account) }
            }
}

class Module(
        data: Data = InMemoryData,
        domain: Domain = DefaultDomain(data),
        val ui: UI = DefaultUI(domain)
) {
    companion object {
        val defaultInstance = Module()
    }
}

fun main() {
    Module.defaultInstance.ui.present().unsafeRunSyncEither()

}
