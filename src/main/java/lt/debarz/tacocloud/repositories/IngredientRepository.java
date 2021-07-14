package lt.debarz.tacocloud.repositories;

import lt.debarz.tacocloud.entities.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
