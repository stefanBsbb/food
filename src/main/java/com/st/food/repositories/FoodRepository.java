package com.st.food.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.st.food.models.Food;

public interface FoodRepository extends JpaRepository<Food, Long>{

}
