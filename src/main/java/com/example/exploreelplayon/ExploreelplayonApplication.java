package com.example.exploreelplayon;

import java.io.IOException;
import java.io.FileInputStream;
import java.util.List;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.exploreelplayon.model.Difficulty;
import com.example.exploreelplayon.model.Region;
import com.example.exploreelplayon.services.TourPackageService;
import com.example.exploreelplayon.services.TourService;

@SpringBootApplication
public class ExploreelplayonApplication implements CommandLineRunner {
	
	@Value("${exploreelplayon.importfile}")
	private String importFile;
	@Autowired
	private TourService tourService;
	@Autowired
	private TourPackageService tourPackageService;
	
	
	public static void main(String[] args)  {
		SpringApplication.run(ExploreelplayonApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception{
		createTourPackages();
		
		long numOfPackages = this.tourPackageService.total();
		
		createTours("ExploreElPlayon.json");
		
	}

	
	/**
     * Initialize all the known tour packages
     */
	private void createTourPackages(){
        tourPackageService.createTourPackage("HJ", "hiking in the Jungle");
        tourPackageService.createTourPackage("AF", "Andinas Falls");
        tourPackageService.createTourPackage("HC", "Hot Springs in the Countryside");
        tourPackageService.createTourPackage("RB", "Rafting the Long River");
        tourPackageService.createTourPackage("FB", "From Miradores to Rio Blanco");
        tourPackageService.createTourPackage("RN", "Ride Cycle till La Negreña");
        tourPackageService.createTourPackage("NW", "Nature Watch");
        tourPackageService.createTourPackage("SP", "Snowboard At Playon's style");
        tourPackageService.createTourPackage("TP", "Taste of El Playón");
    }
	
	
	 /**
     * Create tour entities from an external file
     */
    private void createTours(String fileToImport) throws IOException {
        TourFromFile.read(fileToImport).forEach(
        		importedTour -> tourService.createTour(importedTour.getTitle(),
                    importedTour.getDescription(),
                    importedTour.getBlurb(),
                    importedTour.getPrice(),
                    importedTour.getLength(),
                    importedTour.getBullets(),
                    importedTour.getKeywords(),
                    importedTour.getPackageType(),
                    importedTour.getDifficulty(),
                    importedTour.getRegion()));
    }

    /**
     * Helper class to process ExploreElPlayon.json
     */
    private static class TourFromFile {
        //fields
        private String packageType, title, description, blurb, price, length,
                bullets, keywords, difficulty, region;
        //reader
        static List<TourFromFile> read(String fileToImport) throws IOException {
            return new ObjectMapper().setVisibility(FIELD, ANY).
                    readValue(new FileInputStream(fileToImport), new TypeReference<List<TourFromFile>>() {});
        }
        
        protected TourFromFile(){}

        String getPackageType() { return packageType; }

        String getTitle() { return title; }

        String getDescription() { return description; }

        String getBlurb() { return blurb; }

        Integer getPrice() { return Integer.parseInt(price); }

        String getLength() { return length; }

        String getBullets() { return bullets; }

        String getKeywords() { return keywords; }

        Difficulty getDifficulty() { return Difficulty.valueOf(difficulty); }

        Region getRegion() { return Region.findByLabel(region); }
    }


}
