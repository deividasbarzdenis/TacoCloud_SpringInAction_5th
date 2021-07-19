package lt.debarz.tacocloud.controllers;

import lombok.extern.slf4j.Slf4j;
import lt.debarz.tacocloud.entities.Ingredient;
import lt.debarz.tacocloud.entities.Ingredient.Type;
import lt.debarz.tacocloud.entities.Order;
import lt.debarz.tacocloud.entities.Taco;
import lt.debarz.tacocloud.entities.User;
import lt.debarz.tacocloud.repositories.IngredientRepository;
import lt.debarz.tacocloud.repositories.TacoRepository;
import lt.debarz.tacocloud.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;



import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;
    private TacoRepository tacoRepository;
    private UserRepository userRepo;

    public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository tacoRepository, UserRepository userRepo) {
        this.ingredientRepo = ingredientRepo;
        this.tacoRepository = tacoRepository;
        this.userRepo = userRepo;
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm(Model model, Principal principal) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i -> ingredients.add(i));
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
        String username = principal.getName();
        User user = userRepo.findByUsername(username);
        model.addAttribute("user", user);
        return "design";
    }

    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order) {
        if (errors.hasErrors()) {
            return "design";
        }
        Taco saved = tacoRepository.save(design);
        order.addDesign(saved);
        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
