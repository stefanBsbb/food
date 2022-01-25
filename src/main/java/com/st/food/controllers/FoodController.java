package com.st.food.controllers;

import java.util.List;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.st.food.models.Category;
import com.st.food.models.Food;
import com.st.food.repositories.CategoryRepository;
import com.st.food.repositories.FoodRepository;


@Controller
public class FoodController {

	@Autowired
	FoodRepository foodRepository;
	@Autowired
	CategoryRepository categoryRepository;

	
    @GetMapping("/food")
    public String index(Model model) {
        model.addAttribute("food", foodRepository.findAll());
        return "Food/index";
    }
    
    @GetMapping("/foodform")
    public String showSignUpForm(Model model) {
    	List<Category> categories = categoryRepository.findAll();
    	model.addAttribute("categories", categories);
    	model.addAttribute("food", new Food());
        return "Food/add-food";
    }

    @PostMapping("/addfood")
    public String addFood(@Valid Food food, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "Food/add-food";
        }

        foodRepository.save(food);
        model.addAttribute("foods", foodRepository.findAll());
        return "redirect:/foods";
    }

    @GetMapping("/food/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
    	Food food = foodRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid food Id:" + id));
    	List<Category> categories = categoryRepository.findAll();
    	model.addAttribute("categories", categories);
        model.addAttribute("food", food);
        return "Food/update-food";
    }

    @PostMapping("/food/update/{id}")
    public String updateFood(@PathVariable("id") long id, @Valid Food food,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
        	food.setId(id);
            return "Food/update-food";
        }

        foodRepository.save(food);
        model.addAttribute("foods", foodRepository.findAll());
        return "redirect:/foods";
    }

    @GetMapping("/food/delete/{id}")
    public String deleteFood(@PathVariable("id") long id, Model model) {
    	Food food = foodRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid food Id:" + id));
        foodRepository.delete(food);
        model.addAttribute("foods", foodRepository.findAll());
        return "redirect:/foods";
    }
}
