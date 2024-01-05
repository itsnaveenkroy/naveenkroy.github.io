package io.capston;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author MD AL MAMUNUR RASHID
 * 
 * This class runs the program in web page to recommend a movie list
 * 
 *
 */
public class RecommendationRunner implements Recommender
{

	@Override
	public List<String> getItemsToRate() 
	{
		List<String> movieListToRate = new ArrayList<>();
		
		//create a random object
		Random random = new Random();
		
		//get the movie list
		ArrayList<String> fullMovieList = MovieDatabase.filterBy(new TrueFilter());
		
		
		
		//randomly choose 15 movies to be rated by user
		for(int i = 0; i < 15; i++)
		{
			int movieID = random.nextInt(fullMovieList.size());
			
			String randomLyChosenMoveiID = fullMovieList.get(movieID);
			movieListToRate.add(randomLyChosenMoveiID);
			
			
			//to prevent the added to appear again in the list,
			//remove it after adding to movieListToRate
			fullMovieList.remove(movieID);	
		}
		
		return movieListToRate;
	}

	@Override
	public void printRecommendationsFor(String webRaterID) 
	{
		
	}
	public void printSimilarRatings(String raterID)
	{	
		MovieDatabase.initialize("ratedmoviesfull.csv");
		RaterDatabase.initialize("ratings.csv");
		
		FourthRatings  fr = new FourthRatings();
		//my program only recommend movies which has minimal 5 raters and raters goes in to top 100
		
		int topSimilar = 100;
		int minimalRaters = 5;
		
		//list of the movies which has been rated by the user
		List<String> ratedMovieList = getItemsToRate();
		
		ArrayList<Rating> similarList = fr.getSimilarRatings(raterID, topSimilar, minimalRaters);
		
		if(similarList.size() == 0) 
		{
			System.out.println("Sorry, There is no movie to recommend you.");
			return;
		}
	
		//counter keeps trace how many movie has been recommenced
		int counter = 0; 
		for(Rating rating : similarList)
			{
				if(counter == 10) return;
			
				String mvID = rating.getItem();
				
				//suggest movies which has not been rated the user
				if(!ratedMovieList.contains(mvID))
					{
						System.out.println(MovieDatabase.getTitle(mvID));
						counter++;
					}//end of if block
			} //end of for loop
		
	}//end of the method
}
