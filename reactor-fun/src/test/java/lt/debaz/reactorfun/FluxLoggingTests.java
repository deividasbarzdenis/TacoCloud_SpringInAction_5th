package lt.debaz.reactorfun;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class FluxLoggingTests {

    @Test
    public void logSimple(){
        Flux<String> beltColor = Flux
                .just("white", "yellow", "orange", "purple", "blue")
                .log();
        beltColor.subscribe();

    }

    @Test
    public void logMapping(){
        Flux<String> beltColor = Flux
                .just("white", "yellow", "orange", "purple", "blue")
                .map(String::toUpperCase)
                .log();
        beltColor.subscribe();
    }

    @Test
    public void logFlatMap() throws Exception{
        Flux<String> beltColor = Flux
                .just("white", "yellow", "orange", "purple", "blue")
                .flatMap(cb -> Mono.just(cb)
                        .map(c -> c.toUpperCase())
                        .log()
                        .subscribeOn(Schedulers.parallel())
                );
        beltColor.subscribe();
        Thread.sleep(3000);
    }
}
