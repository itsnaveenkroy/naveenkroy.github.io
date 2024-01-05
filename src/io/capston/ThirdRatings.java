package io.capston;

import java.util.ArrayList;

public class ThirdRatings 
{
	private ArrayList<EfficientRater> myRater;

	public ThirdRatings()
	{
        this("data/ratings.csv");
	}

	public ThirdRatings(String ratingFile) 
	{
		FirstRatings fr = new FirstRatings();
		myRater = fr.loadRaters(ratingFile);
	}
	/**
	 * @return the myRater
	 */
	public ArrayList<EfficientRater> getMyRater() {
		return myRater;
	}

	public double getAverageByID(String mvID, int minimalRaters)
	{
		if(getTotalRater(mvID) >= minimalRaters) return  getAvgRateOfaGivenMovie(mvID);

		 return 0.0;
	}
	//this method find the average rating for every movie that has been rated by at least minimalRaters raters
	public ArrayList<Rating> getAverageRatingsOfAllMovies(int minimalRaters)
	{
		//get movie list
		ArrayList<String> myMovies = MovieDatabase.filterBy(new TrueFilter());
		
		//create rating list
		ArrayList<Rating>  ratingList = new ArrayList<>();
		
		//get the average rating of a movie
		for(String mvID : myMovies)
		{
				double avgRating =  getAverageByID(mvID, minimalRaters);
				if (avgRating > 0.0) ratingList.add(new Rating(mvID, avgRating));			
		}
		return ratingList;
	}
	
	
	//get the average rating of a movie
	private double getAvgRateOfaGivenMovie(String movieID)
	{
		//loop through the Rater list to calculate the given movie's average rating
		double totalRate = getTotalMovieRating(movieID);
		int totalRater = getTotalRater(movieID);
		
		//return Math.floor((totalRate / totalRater) * 100) / 100;
		
		return totalRate / totalRater;
	}

	//get totalRater of a given movie
	private int getTotalRater(String movieID)
	{
		int totalRater = 0;
		for(EfficientRater er : myRater)
					if(er.hasRating(movieID)) totalRater++;
		
		return totalRater;
	}
	
	//get total rating of a given movie
	private double getTotalMovieRating(String movieID)
	{
		double totalMovieRating = 0.0;

		for(EfficientRater er : myRater)
			if(er.hasRating(movieID)) 
			{
				totalMovieRating += er.getRating(movieID);
			}
		
		return totalMovieRating;
	}

	//returns the number of raters that were read in and stored in the ArrayList of type Rater
	public int getRaterSize()
	{
		return myRater.size();
	}
	
	//print rater list
	public void printRaterArray()
	{
		for(Rater r : myRater)
			System.out.println(r);
	}	
	
	//helper method creates and returns an ArrayList of type Rating of 
	//all the movies that have at least minimalRaters ratings and satisfies the filter criteria
	
	public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria)
	{
		ArrayList<Rating> ratingListByFilter = new ArrayList<>();
		
		//get movies name from the movie database
		ArrayList<String> movieList = MovieDatabase.filterBy(filterCriteria);
		
		//get the rating of each of movie from the movieList
		for(String mvID : movieList)
		{
			double avgRating =  getAverageByID(mvID, minimalRaters);
			if (avgRating > 0.0) ratingListByFilter.add(new Rating(mvID, avgRating));
		}
		return ratingListByFilter;
	}//end of the method
}
