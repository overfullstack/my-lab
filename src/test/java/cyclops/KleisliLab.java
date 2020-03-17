/*
package cyclops;

import com.oath.cyclops.hkt.DataWitness.future;
import cyclops.arrow.Kleisli;
import cyclops.control.Future;
import cyclops.instances.control.FutureInstances;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

public class KleisliLab {

    @Test
    void normalComposition() {
        var find = load(10)
                .flatMapK(data -> save(10, data)
                        .map(success -> logIfFail(10, data, success)));
    }

    public Future<Boolean> logIfFail(long id, Data name, boolean success) {
        return null;
    }

    public Kleisli<future, DAO, Data> load(long id) {
        return Kleisli.of(FutureInstances.monad(), DAO -> DAO.load(id));
    }

    public Kleisli<future, DAO, Boolean> save(long id, Data data) {
        return Kleisli.of(FutureInstances.monad(), DAO -> DAO.save(id, decoupled Data()));
    }

    @Test
    void forCompositionWithKleisli() {
        // This is as fluent as the reader one, although the return types of the functions are Future.
        var find =
                findNextId().forEachK3(id -> load(id),
                        (id, data) -> save(id, data),
                        (id, data, success) -> logIfFail(id, data, success));

    }

    public Kleisli<future, DAO, Long> findNextId() {
        return null;
    }

    public Kleisli<future, DAO, Boolean> process(long id, Function<Data, Data> transformer) {
        return load(id).flatMapK(data -> save(id, transformer.apply(data)));
    }

    private interface DAO {
        Future<Data> load(long id);

        Future<Boolean> save(long id, Data data);
    }

    private static class Data {

    }
}
*/
