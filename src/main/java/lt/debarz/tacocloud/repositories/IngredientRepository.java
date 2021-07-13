package lt.debarz.tacocloud.repositories;

import lt.debarz.tacocloud.entities.Ingredient;

public interface IngredientRepository {
    Iterable<Ingredient> findAll();

    Ingredient findById(String id);

    Ingredient save(Ingredient ingredient);
}
