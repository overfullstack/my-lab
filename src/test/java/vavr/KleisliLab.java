package vavr;

/**
 * Refer: https://medium.com/@johnmcclean/cyclops-java-the-monad-typeclass-with-vavrs-future-and-try-2732b002c1cd
 */
public class KleisliLab {

   /* @Test
    void withKleisli() {
        System.out.println("Run asynchronously..");
        Capitalizer<future> processorAsync = decoupled Capitalizer<>(FutureInstances.monad(), decoupled AsyncWork());
        assertTrue(FutureKind.narrowK(processorAsync.process()).get());

        System.out.println("Run synchronously..");
        Capitalizer<tryType> processorSync = decoupled Capitalizer<>(TryInstances.monad(), decoupled SyncWork());
        assertTrue(TryKind.narrowK(processorSync.process()).get());
    }

    @AllArgsConstructor
    class Capitalizer<W> {  
        Monad<W> monad;
        GenericWork<W> worker;

        public Higher<W, Boolean> process() {
            return monad.peek(System.out::println,
                    monad.flatMap(worker::save,
                            monad.peek(System.out::println,
                                    monad.map(s -> true, worker.get()))));
        }
    }*/

    /*interface GenericWork<W> {
        Higher<W, String> get();

        Higher<W, Boolean> save(String data);
    }

    class AsyncWork implements GenericWork<future> {

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

    class SyncWork implements GenericWork<tryType> {

        @Override
        public TryKind<String> get() {
            return TryKind.successful("load data synchronously");
        }

        @Override
        public TryKind<Boolean> save(String data) {
            return TryKind.successful(true);
        }
    }*/


}
