package io.capston;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * 
 * @author MD AL MAMUNUR RASHID (Mamun)
 * @date 28-10-2018
 */
public class FourthRatings 
{
	//returns the number of raters that were read in and stored in the ArrayList of type Rater
	public int getRaterSize()
	{
		return RaterDatabase.getRaters().size();
	}
	
	//print rater list
	public void printRaterArray()
	{
		for(Rater r : RaterDatabase.getRaters())
			System.out.println(r);
	}	
	
	//this method return similar movies fulfilling filters
	public ArrayList<Rating> getSimilarRatingsByFilter(String raterID, int numSimilarRaters, int minimalRates, Filter filterCriteria)
	{
		//get a top rated list equal to numSimilarRaters, in top rate list the weight rate is located as value
		ArrayList<RaterWeight> topRaterList = createTopSimilarRaterList(raterID, numSimilarRaters);
		
		//get the top rater rated movieList
		ArrayList<String> topRaterRatedMovieListWithMinimaToplRating = getTopRaterRatedMovieListWithMinimaTopRating(topRaterList, minimalRates);
		
		ArrayList<Rating> ratingList = new ArrayList<>();
		
		//get a filtered movieList
		ArrayList<String> filteredMovieList = MovieDatabase.filterBy(filterCriteria);
		
		//loop to get filtered movie list
		for(String mvID : topRaterRatedMovieListWithMinimaToplRating)
		{	
			if(filteredMovieList.contains(mvID))
				{
					double avgRating = calculateSingleMovieSimilarWeightedAverage(mvID, topRaterList);
					
					//add the movieID and it's average rating to the list as a Rating object (instance)
					ratingList.add(new Rating(mvID, avgRating));
				}
		}
		
		//sorting the list in descending order
		Collections.sort(ratingList, Collections.reverseOrder());
		
		return ratingList;

	}//end of the method getSimilarRatingsByFilter
	
	/**
	 * 
	 * @param raterID
	 * @param numSimilarRaters
	 * @param minimalRates
	 * 
	 * this method returns an ArrayList of type Rating, of movies and their weighted average 
	 * ratings using only the top numSimilarRaters with positive ratings and 
	 * including only those movies that have at least minimalRaters ratings from 
	 * those most similar raters (not just minimalRaters ratings overall)
	 * 
	 */
	public ArrayList<Rating> getSimilarRatings(String raterID, int numSimilarRaters, int minimalRates)
	{
		
		//get a top rated list equal to numSimilarRaters, in top rate list the weight rate is located as value
		ArrayList<RaterWeight> topRaterList = createTopSimilarRaterList(raterID, numSimilarRaters);
				
		//get the top rater rated movieList
		ArrayList<String> topRaterRatedMovieListWithMinimaToplRating = getTopRaterRatedMovieListWithMinimaTopRating(topRaterList, minimalRates);
		
		//rating list to return
		ArrayList<Rating> ratingList = new ArrayList<>();
		
		for(String mvID : topRaterRatedMovieListWithMinimaToplRating)
		{	
			double avgRating = calculateSingleMovieSimilarWeightedAverage(mvID, topRaterList);
			
			//add the movieID and it's average rating to the list as a Rating object (instance)
			ratingList.add(new Rating(mvID, avgRating));
		}
		
		//sorting the list in descending order
		Collections.sort(ratingList, Collections.reverseOrder());
		
		return ratingList;
	} //end of the method getSimilarRatings
	
	//helper method calculate each single movie's similar average
	private double calculateSingleMovieSimilarWeightedAverage(String mvID, ArrayList<RaterWeight> topRaterList)
	{
		int totalRater = 0;
		
		double totalRating = 0.0;
		
		for(RaterWeight topRater : topRaterList)
		{
			//get rater id
			String raterID = topRater.getRaterID();
			
			//get rater from rater database
			Rater rater = RaterDatabase.getRater(raterID);
			
			//get moviesRated by this rater
			ArrayList<String> moviesRated = rater.getItemsRated();
			
			if(moviesRated.contains(mvID))
			{
				//get rater weight
				double raterWeight = topRater.getRaterWeight();
				
				//get rater rate for the movie from RaterDatabase
				double raterRate = rater.getRating(mvID);

				totalRating += raterRate * raterWeight;
				totalRater++;
			} //end of if block
		} //end of for loop
		
		return totalRating / totalRater;
	}//end of the method calculateSingleMovieSimilarWeightedAverage
	
	//helper method to create top RaterList
	private ArrayList<RaterWeight> createTopSimilarRaterList(String raterID, int numSimilarRaters)
	{
		ArrayList<RaterWeight> topSimilarRaterList = new ArrayList<>();

		//get the similar rater list 
		ArrayList<RaterWeight> similarList = getSimilarities(raterID);
	
		for(int i = 0; i < numSimilarRaters; i++) 
			topSimilarRaterList.add(new RaterWeight(similarList.get(i).getRaterID(), (similarList.get(i).getRaterWeight())));
		
		return topSimilarRaterList;
	}//end of createTopSimilarRaterList method

	//helper method to get the movie list with minimal number of rating rated by top raters 
	private ArrayList<String> getTopRaterRatedMovieListWithMinimaTopRating(ArrayList<RaterWeight> topRaterList, int minimalRatings) 
	{
		ArrayList<String> topRaterRatedMovieList = new ArrayList<>();
		
		for(RaterWeight rating : topRaterList)
		{
			//get rating's rater ID
			String raterID = rating.getRaterID();
			
			Rater rater = RaterDatabase.getRater(raterID);
			
			//get movie list rated by the rater
			ArrayList<String> ratedMovieList = rater.getItemsRated();
			
			//get the complete rater list of this movie
			for(String mvID : ratedMovieList) 
				if(!topRaterRatedMovieList.contains(mvID) && movieHasMinimalTopRaters(mvID, topRaterList,  minimalRatings))
					//check if movie has minimal ratings in top raters movies rating number from topRaters
					topRaterRatedMovieList.add(mvID);
		}
		
		//sort array in descending order
		Collections.sort(topRaterRatedMovieList);
		
		return topRaterRatedMovieList;
		
	} //end of the method getTopRaterRatedMovieListWithMinimaToplRating
	
	//check if the movie has minimal number of ratings rated by top raters
	private boolean movieHasMinimalTopRaters(String mvID, ArrayList<RaterWeight> topRaterList, int minimalRatings) 
	{
		//movie's minimal top rating counter
		int raterCounter = 0;
		
		for(RaterWeight rw : topRaterList)
		{
			//get rater id
			String raterID = rw.getRaterID();
			
			//get rater
			Rater rater = RaterDatabase.getRater(raterID);
			
			//check if the rater has a positive rating for the given movie
			double rating = rater.getRating(mvID);
			if(rating > 0.0)
				raterCounter++;
		}

		//if the movie has at least minimal rating, return true
		if(raterCounter >= minimalRatings) return true;
		
		return false;
		
	}//end of the method movieHasMinimalTopRaters
	
	/**
	 * this method returns an ArrayList of type Rating sorted by ratings from 
	 * highest to lowest rating with the highest rating first and only including 
	 * those raters who have a positive similarity rating since those with 
	 * negative values are not similar in any way.
	 */
	private ArrayList<RaterWeight> getSimilarities(String raterID)
	{
		ArrayList<RaterWeight> ratingList = new ArrayList<>();
		
		Rater me = RaterDatabase.getRater(raterID);
		
		for(Rater r : RaterDatabase.getRaters())
		{
			if(!r.getID().equals(raterID)) //make sure the rater and me are not same
			{
				double product = dotProduct(me, r);
				if(product >= 0.0) //only positive product is accepted
				{
					//r.getID() is the rater id
					RaterWeight raterWeight = new RaterWeight(r.getID(), product);
					ratingList.add(raterWeight);
				}//end of inner if block
			
			}//end of outer if block
		}//end of outer for loop
		
		//sort the list descending order
		Collections.sort(ratingList, Collections.reverseOrder());
		
		return ratingList;
		
	}//end of method getSimilarities

	//this method return the dot product (Arater's rating weight comparing to the given rater) 
	//of the ratings of movies, those have been rated by both rater
	private double dotProduct(Rater me, Rater rater)
	{
		double product = 0.0;
		
		//get the movie list
		ArrayList<String> movieList = MovieDatabase.filterBy(new TrueFilter());
		//me rating
		Rater meRater = RaterDatabase.getRater(me.getID());
		Rater otherRater = RaterDatabase.getRater(rater.getID());
		
		for(String movie : movieList)
			if(meRater.hasRating(movie) && otherRater.hasRating(movie))
				product +=  (meRater.getRating(movie)  - 5 ) * (otherRater.getRating(movie) - 5);
		
		return product;
		
	}//end of method dotProduct
}//end of FourthRating class