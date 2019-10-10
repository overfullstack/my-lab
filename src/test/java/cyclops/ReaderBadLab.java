package cyclops;

import cyclops.control.Future;
import cyclops.control.Reader;
import org.junit.jupiter.api.Test;


public class ReaderBadLab {

    public Reader<DAO, Future<Long>> findNextId() {
        return null;
    }

    @Test
    void withoutKleisli() {
        // This shows how this code turns complex when you use Reader, where Kleisli should be used.
        Reader<DAO, Future<Boolean>> load10 =
                loadName(10l).forEach2(nameF -> dao -> nameF.<Boolean>flatMap(name -> updateName(10, name + "suffix").apply(dao)),
                        (nameF, successF) -> successF.flatMap(success -> nameF.flatMap(name -> logIfFail(10, name, success))));
    }

    public Reader<DAO, Future<String>> loadName(long id) {
        return null;
    }

    public Reader<DAO, Future<Boolean>> updateName(long id, String name) {
        return null;
    }

    public Future<Boolean> logIfFail(long id, String name, boolean success) {
        return null;
    }

    private static class DAO {
    }
}
