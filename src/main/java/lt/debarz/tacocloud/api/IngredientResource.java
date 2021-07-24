package lt.debarz.tacocloud.api;

import lombok.Getter;
import lt.debarz.tacocloud.entities.Ingredient;
import lt.debarz.tacocloud.entities.Ingredient.Type;
import org.springframework.hateoas.ResourceSupport;


public class IngredientResource extends ResourceSupport {
    @Getter
    private String name;

    @Getter
    private Type type;

    public IngredientResource(Ingredient ingredient) {
        this.name = ingredient.getName();
        this.type = ingredient.getType();
    }
}
