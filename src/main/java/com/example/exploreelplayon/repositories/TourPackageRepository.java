package com.example.exploreelplayon.repositories;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.example.exploreelplayon.model.TourPackage;

public interface TourPackageRepository extends CrudRepository<TourPackage , String> {
	Optional<TourPackage> findByName(String name);
}
