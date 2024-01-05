package io.capston;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestFirstRatings 
{
	//main method
	public static void main(String[] args)
	{
		long start = System.currentTimeMillis();
		
		FirstRatings firstRating = new FirstRatings();
		String path = "data/ratedmoviesfull.csv";
		ArrayList<Movie> movieList = firstRating.loadMovies(path);
		
		//total number of movies:
		//System.out.println("Total number of movies: " + movieList.size());
		
		//movieList.stream().forEach(m -> System.out.println(m));
		
		@SuppressWarnings("unused")
		int numberOfComedyMovies = firstRating.getNumberOfMoveisBasedOnGenre("Comedy", movieList);
		
		//System.out.println("total numberOfComedyMovies: " + numberOfComedyMovies);

		@SuppressWarnings("unused")
		int totalNumberOfMoviesLongerThan150Minute = firstRating.getNumberOfMoveisLongerThanGivenTIme(150, movieList);
		//System.out.println("totalNumberOfMoviesLongerThan150Minute: " + totalNumberOfMoviesLongerThan150Minute);

		HashMap<String, Integer> maximumNumberOfMoviesByDirector = firstRating.getMaximumNumberOfMoviesByDirector(movieList);
		for(String key : maximumNumberOfMoviesByDirector.keySet())
		{
			//System.out.println(key +  " " + maximumNumberOfMoviesByDirector.get(key));
		}
		//System.out.println(maximumNumberOfMoviesByDirector);
		
		String path2 = "data/ratings.csv";
		ArrayList<EfficientRater> raterList = firstRating.loadRaters(path2);
		//raterList.forEach(r -> System.out.println(r.getID()+ " " + r.numRatings() + " " + r.getItemsRated()));
	
		//find out number of ratings for a particular rater
		int number = firstRating.getNumberOfRating("193", path2);
		System.out.println("ratings: " + number);
		
		Map<String, Integer> maxNumRatMap = firstRating.getMaximumNumberOfRating(path2);
		for(Map.Entry<String, Integer> entry : maxNumRatMap.entrySet())
		{
			System.out.println(entry);
		}
		
		//read number of rater
		int numberOfRater = firstRating.getNumberOfRatersOfAMovie("1798709", path2);
		System.out.println(numberOfRater);
		
		//get the total number of all different movies
		int numberOfTotalDiffMovies = firstRating.getNumOfDifferentMovies(path2);
		System.out.println("numberOfTotalDiffMovies: " + numberOfTotalDiffMovies);
		

		long totalTime = System.currentTimeMillis() - start;
		System.out.println("total time: " + totalTime);
	}

}
