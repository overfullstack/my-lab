package cyclops;

import com.aol.cyclops.vavr.hkt.FutureKind;
import com.aol.cyclops2.hkt.Higher;
import cyclops.monads.VavrWitness;
import io.vavr.concurrent.Future;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KleisliVavrLab {

    @Test
    void withKleisli() {

    }

    interface GenericWork<W> {
        Higher<W, String> get();

        Higher<W, Boolean> save(String data);
    }

    class AsyncWork implements GenericWork<VavrWitness.future> {

        private ExecutorService ex = Executors.newFixedThreadPool(2);

        @Override
        public FutureKind<String> get() {
            return FutureKind.widen(Future.of(ex, () -> "load data asynchronously"));
        }

        @Override
        public FutureKind<Boolean> save(String data) {
            return FutureKind.widen((Future.of(ex, () -> true)));
        }
    }


}
