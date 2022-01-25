
package com.st.food.controllers;

import com.st.food.models.Category;
import com.st.food.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;

/**
 *
 * @author sayby
 */
@Controller
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;    

	
    @GetMapping("/categories")
    public String index(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "Categories/index";
    }
    
    @GetMapping("/categoryform")
    public String showSignUpForm(Category category) {
        return "Categories/add-category";
    }

    @PostMapping("/addcategory")
    public String addCategory(@Valid Category category, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "Categories/add-category";
        }

        categoryRepository.save(category);
        model.addAttribute("categories", categoryRepository.findAll());
        return "redirect:/categories";
    }

    @GetMapping("/category/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
    	Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));

        model.addAttribute("category", category);
        return "Categories/update-category";
    }

    @PostMapping("/category/update/{id}")
    public String updateCategory(@PathVariable("id") long id, @Valid Category category,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
        	category.setId(id);
            return "Categories/update-category";
        }

        categoryRepository.save(category);
        model.addAttribute("categories", categoryRepository.findAll());
        return "redirect:/categories";
    }

    @GetMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable("id") long id, Model model) {
    	Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
        categoryRepository.delete(category);
        model.addAttribute("categories", categoryRepository.findAll());
        return "redirect:/categories";
    }
}
