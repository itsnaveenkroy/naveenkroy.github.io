package io.capston;

import java.util.ArrayList;

public class SecondRatings 
{
	private ArrayList<Movie> myMovies;
	private ArrayList<EfficientRater> myRater;

	public SecondRatings()
	{
        this("data/ratedmoviesfull.csv", "data/ratings.csv");
	}

	public SecondRatings(String string1, String string2) 
	{
		FirstRatings fr = new FirstRatings();
		myMovies = fr.loadMovies(string1);
		myRater = fr.loadRaters(string2);
	}
	
	
	/**
	 * @return the myMovies
	 */
	public ArrayList<Movie> getMyMovies() {
		return myMovies;
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
		ArrayList<Movie> myMovies = getMyMovies();
		
		//create rating list
		ArrayList<Rating>  ratingList = new ArrayList<>();
		
		//get the average rating of a movie
		for(Movie mv : myMovies)
		{
			String mvID = mv.getID();
			
				double avgRating =  getAverageByID(mvID, minimalRaters);
				if (avgRating > 0.0) ratingList.add(new Rating(mvID, avgRating));			
		}
		return ratingList;
	}
	
	// returns the tile of given movie ID
	public String getTitle(String mvID)
	{
		for(Movie mv : myMovies)
		{
			if(mv.getID().equals(mvID)) return mv.getTitle();
		}
		return "No tile with the given movie ID has found";
	}
	// returns the movie ID of given movie tile
	public String getID(String title)
	{
		for(Movie mv : myMovies)
		{
			if(mv.getTitle().equals(title)) return mv.getID();
		}
		return "No tile with the given movie ID has found";
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

	
	//returns the number of movies that were read in and stored in the ArrayList of type Movie.
	public int getMovieSize()
	{
		return myMovies.size();
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
}
