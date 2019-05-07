package cyclops;

import com.oath.cyclops.hkt.DataWitness.future;
import cyclops.arrow.Kleisli;
import cyclops.control.Future;
import cyclops.instances.control.FutureInstances;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

public class KleisliLab {

    @Test
    void withKleisli() {
        var findUpdate =
                findNextId().forEachK3(this::loadName,
                        (id, name) -> updateName(id, name),
                        (id, name, success) -> logIfFail(id, name, success));

    }

    public Kleisli<Try, DAO, Long> findNextId() {
        return null;
    }

    public Kleisli<Try, DAO, String> loadName(long id) {
        return null;
    }

    public Kleisli<Try, DAO, Boolean> updateName(long id, String name) {
        return null;
    }

    public Future<Boolean> logIfFail(long id, String name, boolean success) {
        return null;
    }

    public Kleisli<future, DAO, Data> load(long id) {
        return Kleisli.of(FutureInstances.monad(), DAO -> DAO.load(id));
    }

    public Kleisli<future, DAO, Boolean> save(long id, Data data) {
        return Kleisli.of(FutureInstances.monad(), DAO -> DAO.save(id, new Data()));
    }

    private interface DAO {
        Future<Data> load(long id);

        Future<Boolean> save(long id, Data data);
    }

    private static class Data {

    }
}
