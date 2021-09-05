package lt.debaz.reactorfun;

import lombok.Data;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class FluxTransformingTests {

    @Test
    public void skipAFew() {
        Flux<String> skipFlux = Flux.just(
                        "one", "two", "skip a few", "ninety nine", "one hundred")
                .skip(3);
        StepVerifier.create(skipFlux)
                .expectNext("ninety nine", "one hundred")
                .verifyComplete();
    }

    @Test
    public void skipAFewSeconds() {
        Flux<String> skipFlux = Flux.just(
                        "one", "two", "skip a few", "ninety nine", "one hundred")
                .delayElements(Duration.ofSeconds(1))
                .skip(Duration.ofSeconds(4));

        StepVerifier.create(skipFlux)
                .expectNext("ninety nine", "one hundred")
                .verifyComplete();
    }

    @Test
    public void take() {
        Flux<String> nationalParkFlux = Flux.just(
                        "Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Grand Teton")
                .take(3);
        StepVerifier.create(nationalParkFlux)
                .expectNext("Yellowstone", "Yosemite", "Grand Canyon")
                .verifyComplete();
    }

    @Test
    public void takeForWhile() {
        Flux<String> nationalParkFlux = Flux.just(
                        "Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Grand Teton")
                .delayElements(Duration.ofSeconds(1))
                .take(Duration.ofMillis(3500));

        StepVerifier.create(nationalParkFlux)
                .expectNext("Yellowstone", "Yosemite", "Grand Canyon")
                .verifyComplete();
    }

    @Test
    public void filter() {
        Flux<String> nationalParkFlux = Flux.just(
                        "Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Grand Teton")
                .filter(np -> !np.contains(" "));

        StepVerifier.create(nationalParkFlux)
                .expectNext("Yellowstone", "Yosemite", "Zion")
                .verifyComplete();
    }

    @Test
    public void distinct() {
        Flux<String> animalFlux = Flux.just(
                        "dog", "cat", "bird", "dog", "bird", "anteater")
                .distinct();
        StepVerifier.create(animalFlux)
                .expectNext("dog", "cat", "bird", "anteater")
                .verifyComplete();
    }

    @Test
    public void map() {
        Flux<Player> playerFlux = Flux.just(
                        "Micheal Jordan", "Scottie Pippen", "Steve Kerr")
                .map(this::createPlayer);
        StepVerifier.create(playerFlux)
                .expectNext(new Player("Micheal", "Jordan"))
                .expectNext(new Player("Scottie", "Pippen"))
                .expectNext(new Player("Steve", "Kerr"))
                .verifyComplete();
    }

    @Test
    public void flatMap() {
        Flux<Player> playerFlux = Flux.just(
                        "Micheal Jordan", "Scottie Pippen", "Steve Kerr")
                .flatMap(n -> Flux // also, can be Mono
                        .just(n)
                        .map(this::createPlayer))
                .subscribeOn(Schedulers.parallel());

        List<Player> playerList = Arrays.asList(
                new Player("Micheal", "Jordan"),
                new Player("Scottie", "Pippen"),
                new Player("Steve", "Kerr")
        );

        StepVerifier.create(playerFlux)
                .expectNextMatches(playerList::contains)
                .expectNextMatches(playerList::contains)
                .expectNextMatches(p -> playerList.contains(p))
                .verifyComplete();

    }

    private Player createPlayer(String n) {
        String[] split = n.split("\\s");
        return new Player(split[0], split[1]);
    }

    @Data
    private static class Player {
        private final String firstName;
        private final String lastName;
    }
}
