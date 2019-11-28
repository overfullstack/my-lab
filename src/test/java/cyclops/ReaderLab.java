package cyclops;

import cyclops.control.Reader;
import org.junit.jupiter.api.Test;


public class ReaderLab {

    @Test
    void normalComposition() {
        var r = loadName(10)
                .flatMap(s -> updateName(10, s)
                        .map(success -> logIfFail(10, s, success)));
    }

    public Reader<DAO, String> loadName(long id) {
        return null;
    }

    public Reader<DAO, Boolean> updateName(long id, String name) {
        return null;
    }

    public boolean logIfFail(long id, String name, boolean success) {
        return true;
    }

    @Test
    void forComposition() {
        var updateName1 = loadName(10)
                .forEach2(
                        name -> updateName(10, name),
                        (name, success) -> logIfFail(10, name, success)
                );

        var updateName2 = findNextId()
                .forEach3(
                        id -> loadName(id),
                        (id, name) -> updateName(id, name),
                        (id, name, success) -> logIfFail(id, name, success)
                );
    }

    public Reader<DAO, Long> findNextId() {
        return null;
    }

    private static class DAO {
    }
}
