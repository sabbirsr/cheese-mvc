package bd.ac.seu.cheesemvc.controller;

import bd.ac.seu.cheesemvc.models.Category;
import bd.ac.seu.cheesemvc.models.Cheese;
import bd.ac.seu.cheesemvc.models.data.CategoryDao;
import bd.ac.seu.cheesemvc.models.data.CheeseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by R@kib Hasan Sabbir
 */


@Controller
@RequestMapping("")
public class CheeseController {

    @Autowired
    private CheeseDao cheeseDao;

    @Autowired
    private CategoryDao categoryDao;

    // Request path: /cheese
    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("List1", cheeseDao.findAll());
        model.addAttribute("title", "My List");

        return "Cheese/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCheeseForm(Model model) {
        model.addAttribute("title", "Add To List");
        model.addAttribute(new Cheese());
       model.addAttribute("categories", categoryDao.findAll());
        return "Cheese/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddCheeseForm(@ModelAttribute  @Valid Cheese newCheese,
                                       Errors errors, Model model,@RequestParam int categoryId) {


        if(errors.hasErrors()){
            model.addAttribute("title", "Add To List");
            model.addAttribute("categories", categoryDao.findAll());
            return "Cheese/add";
        }

            Category cat =categoryDao.findOne(categoryId);
                newCheese.setCategory(cat);
                cheeseDao.save(newCheese);
                return "redirect:";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveCheeseForm(Model model) {
        model.addAttribute("List1", cheeseDao.findAll());
        model.addAttribute("title", "Remove List");
        return "Cheese/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveCheeseForm(@RequestParam int[] Ids) {

        for (int id : Ids) {
            cheeseDao.delete(id);
        }

        return "redirect:";
    }
}
