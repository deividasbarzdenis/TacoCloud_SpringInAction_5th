package lt.debarz.tacocloud.api;

import org.springframework.hateoas.Resources;

import java.util.List;

public class TacoResources extends Resources<TacoResource> {

    public TacoResources(List<TacoResource> tacoResources) {
        super(tacoResources);
    }
}
