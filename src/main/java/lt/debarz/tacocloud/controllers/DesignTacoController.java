package lt.debarz.tacocloud.controllers;

import lt.debarz.tacocloud.entities.Order;
import lt.debarz.tacocloud.entities.Taco;
import lt.debarz.tacocloud.repositories.OrderRepository;
import lt.debarz.tacocloud.repositories.TacoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityLinks;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="/design",
        produces="application/json")
@CrossOrigin(origins="*")
public class DesignTacoController {

    private TacoRepository tacoRepo;
    private OrderRepository order;

    @Autowired
    EntityLinks entityLinks;

    public DesignTacoController(TacoRepository tacoRepo, OrderRepository order) {
        this.tacoRepo = tacoRepo;
        this.order = order;
    }

    @GetMapping("/recent")
    public Iterable<Taco> recentTacos() {
        PageRequest page = PageRequest.of(
                0, 12, Sort.by("createdAt").descending());
        return tacoRepo.findAll(page).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
        Optional<Taco> optTaco = tacoRepo.findById(id);
        return optTaco
                .map(taco -> new ResponseEntity<>(taco, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody Taco taco) {
        return tacoRepo.save(taco);
    }

//
//    @ModelAttribute(name = "order")
//    public Order order() {
//        return new Order();
//    }
//
//    @ModelAttribute(name = "taco")
//    public Taco taco() {
//        return new Taco();
//    }
//
//    @GetMapping
//    public String showDesignForm(Model model, Principal principal) {
//        List<Ingredient> ingredients = new ArrayList<>();
//        ingredientRepo.findAll().forEach(i -> ingredients.add(i));
//        Type[] types = Ingredient.Type.values();
//        for (Type type : types) {
//            model.addAttribute(type.toString().toLowerCase(),
//                    filterByType(ingredients, type));
//        }
//        String username = principal.getName();
//        User user = userRepo.findByUsername(username);
//        model.addAttribute("user", user);
//        return "design";
//    }
//
//    @PostMapping
//    public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order) {
//        if (errors.hasErrors()) {
//            return "design";
//        }
//        Taco saved = tacoRepository.save(design);
//        order.addDesign(saved);
//        return "redirect:/orders/current";
//    }
//
//    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
//        return ingredients
//                .stream()
//                .filter(x -> x.getType().equals(type))
//                .collect(Collectors.toList());
//    }
}
