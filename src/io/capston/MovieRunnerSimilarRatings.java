package io.capston;

import java.util.ArrayList;
import java.util.Collections;


public class MovieRunnerSimilarRatings 
{
	private static FourthRatings  fr;
		
	//static block to load data at the beginning at the program 
	static
	{
		fr = new FourthRatings();
		
		//set up the movie database
		MovieDatabase.initialize("ratedmoviesfull.csv");
		RaterDatabase.initialize("ratings.csv");

	}//end of static block
	
	public void printSimilarRatings(String raterID,  int topSimilar, int minimalRaters)
	{	
		ArrayList<Rating> similarList = fr.getSimilarRatings(raterID, topSimilar, minimalRaters);
		for(Rating rating : similarList)
		{
			String mvID = rating.getItem();
			System.out.println("Movie id: " + mvID + " Title: "+ MovieDatabase.getTitle(mvID) + " " + rating.getValue());
		}

		System.out.println("size: " + similarList.size());
	}
	
	public void printSimilarRatingsByYearAfterAndMinutes(String raterID,  int topSimilar, 
			int minimalRaters, int minMinute, int maxMinute, int year)
	{	
		Filter minFilter = new MinutesFilter(minMinute, maxMinute);
		Filter yearFilter = new YearAfterFilter(year);
		
		AllFilters allFilter = new AllFilters();
		
		allFilter.addFilter(minFilter);
		allFilter.addFilter(yearFilter);
		
		ArrayList<Rating> similarList = fr.getSimilarRatingsByFilter(raterID, topSimilar, minimalRaters, allFilter);
		
		for(Rating rating : similarList)
		{
			String mvID = rating.getItem();
			System.out.println("Movie id: " + mvID + " Title: "+ MovieDatabase.getTitle(mvID) + " " 
			+ rating.getValue() + " " + MovieDatabase.getDirector(mvID));
		}
		
		System.out.println("size: " + similarList.size());
	}
	public void printSimilarRatingsByGenreAndMinutes(String raterID,  int topSimilar, 
			int minimalRaters, int minMinute, int maxMinute, String genre)
	{	
		Filter minFilter = new MinutesFilter(minMinute, maxMinute);
		Filter genreFilter = new GenreFilter(genre);
		
		AllFilters allFilter = new AllFilters();
		
		allFilter.addFilter(minFilter);
		allFilter.addFilter(genreFilter);
		
		ArrayList<Rating> similarList = fr.getSimilarRatingsByFilter(raterID, topSimilar, minimalRaters, allFilter);
		
		for(Rating rating : similarList)
		{
			String mvID = rating.getItem();
			System.out.println("Movie id: " + mvID + " Title: "+ MovieDatabase.getTitle(mvID) + " " 
			+ rating.getValue() + " " + MovieDatabase.getDirector(mvID));
		}
		
		System.out.println("size: " + similarList.size());
	}
	
	public void printSimilarRatingsByDirector(String raterID,  int topSimilar, int minimalRaters, String directors)
	{	
		Filter filter = new DirectorsFilter(directors);
		
		ArrayList<Rating> similarList = fr.getSimilarRatingsByFilter(raterID, topSimilar, minimalRaters, filter);
		
		for(Rating rating : similarList)
		{
			String mvID = rating.getItem();
			System.out.println("Movie id: " + mvID + " Title: "+ MovieDatabase.getTitle(mvID) + " " 
			+ rating.getValue() + " " + MovieDatabase.getDirector(mvID));
		}
		
		System.out.println("size: " + similarList.size());
	}
	
	
	public void printSimilarRatingsByGenre(String raterID,  int topSimilar, int minimalRaters, String genre)
	{	
		Filter genreFilter = new GenreFilter(genre);
		
		ArrayList<Rating> similarList = fr.getSimilarRatingsByFilter(raterID, topSimilar, minimalRaters, genreFilter);
		
		for(Rating rating : similarList)
		{
			String mvID = rating.getItem();
			System.out.println("Movie id: " + mvID + " Title: "+ MovieDatabase.getTitle(mvID) + " " + rating.getValue() + " " + MovieDatabase.getGenres(mvID));
		}
		
		System.out.println("size: " + similarList.size());
		
	} //end of the method 
	
}//end of main class
