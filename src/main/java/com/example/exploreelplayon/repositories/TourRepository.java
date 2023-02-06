package com.example.exploreelplayon.repositories;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.exploreelplayon.model.Tour;
import com.example.exploreelplayon.model.Difficulty;
import com.example.exploreelplayon.model.Region;

public interface TourRepository extends CrudRepository<Tour,Integer>{
	
	Optional<Tour> findByTitle(String tittle);
	
	List<Tour> findByPrice(Integer price);
	
	Collection<Tour> findByDifficulty(Difficulty difficulty);
	
	List<Tour> findByTourPackageCodeAndRegion(String code, Region region);
	
	List<Tour> findByRegionIn(List<Region> regions);
	
	List<Tour> findByPriceLessThan(Integer maxPrice);
	
	List<Tour> findByKeywordContains(String keyword);
	
	List<Tour> findByTourPackageCodeAndBulletsLike( String code, String searchString);
	
	List<Tour> findByTourPackageCodeAndDifficultyAndRegionAndPricelessThan(String code, Difficulty difficulty, Region region, Integer maxPrice);
	
	@Query("Select t from Tour t where t.tourPackage.code = ?1 " + 
		   "and t.difficulty = ?2 and t.region = ?3 and t.price = ?4")
	List<Tour> lookupTour(String code, Difficulty difficulty, Region region, Integer maxPrice);
	
}


 