package io.capston;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.csv.CSVRecord;

import edu.duke.FileResource;

public class FirstRatings 
{
	//method loadMovies - this method load the movie data to a ArrayList
	public ArrayList<Movie> loadMovies(String fileName)
	{
		ArrayList<Movie> movieList = new ArrayList<>();
		
		FileResource fr = new FileResource(fileName);


		for(CSVRecord record : fr.getCSVParser())
		{
			String id = record.get("id");
			String title = record.get("title");
			String year = record.get("year");
			String genres = record.get("genre");
			String director = record.get("director");
			String country = record.get("country");
			String poster = record.get("poster");
			int minutes = Integer.parseInt(record.get("minutes"));
			
			movieList.add(new Movie(id, title, year, genres, director, country, poster, minutes));
			
		}
		
		return movieList;
	}//end of method
	int getNumberOfMoveisBasedOnGenre(String genre, ArrayList<Movie> movieList)
	{
		int number = 0;
		for(Movie m:  movieList)
		{
			if(m.getGenres().indexOf(genre) != -1)
			{
				number++;
			}
		}
		return number;
	}
	
	int getNumberOfMoveisLongerThanGivenTIme(int time, ArrayList<Movie> movieList)
	{
		int number = 0;
		for(Movie m:  movieList)
		{
			if(m.getMinutes() > time)
			{
				number++;
			}
		}
		return number;
	}//end of method getNumberOfMoveisLongerThanGivenTIme
	
	HashMap<String, Integer> getMaximumNumberOfMoviesByDirector(ArrayList<Movie> movieList)
	{
		HashMap<String, Integer> maxNumMovDir = new HashMap<>();
		HashMap<String, Integer> dirMapNum = new HashMap<>();
		for(Movie m : movieList)
		{
			//if one has more than ode directory, split the director name
			String[] directorNames = m.getDirector().split(",");
			for(int i = 0; i < directorNames.length; i++)
			{
				if(!dirMapNum.containsKey(directorNames[i].trim()))
				{
					dirMapNum.put(directorNames[i].trim(), 1);
				}
				else
				{
					//get value
					int value = dirMapNum.get(directorNames[i].trim());
					dirMapNum.put(directorNames[i].trim(), value + 1);
				}
			}
		}
		//get maximum number movie of a director
		int maxNumMovie = dirMapNum.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
				.get().getValue();
				
		//now find the maxNumMovi director
		for(String key : dirMapNum.keySet())
		{
			if(dirMapNum.get(key) >= maxNumMovie) maxNumMovDir.put(key, dirMapNum.get(key));
		}
		return maxNumMovDir;
	}
	
	/*
	 * This method processes every record from the CSV file whose name is filename, a file of raters and their ratings, 
	 * and return an ArrayList of type Rater with all the rater data from the file.
	 */
	public ArrayList<EfficientRater> loadRaters(String fileName)
	{
		ArrayList<EfficientRater> raterList = new ArrayList<>();
		HashMap<String, ArrayList<Rating>> map = createRatermap(fileName);
		
		for(Map.Entry<String, ArrayList<Rating>> entry : map.entrySet())
			{
				String id = entry.getKey();
				ArrayList<Rating> ratList = entry.getValue();
				EfficientRater rater = new EfficientRater(id);
				
				for(Rating rating : ratList)
				{
					String mv_id = rating.getItem();
					double mv_rating = rating.getValue();
					rater.addRating(mv_id, mv_rating);
				}
				raterList.add(rater);
			}
		return raterList;
	}
	
	//helper method to read data from the rating file and store them
	private HashMap<String, ArrayList<Rating>> createRatermap(String fileName)
	{
		
		HashMap<String, ArrayList<Rating>> map = new HashMap<>();
		
		FileResource fileResource = new FileResource(fileName);
		
		for(CSVRecord record : fileResource.getCSVParser())
		{
			String raterID = record.get("rater_id");
			String movie_id = record.get("movie_id");
			double rating = Double.parseDouble(record.get("rating"));

			if(!map.containsKey(raterID))
			{
				ArrayList<Rating> list = new ArrayList<>();
				list.add(new Rating(movie_id, rating));
				map.put(raterID, list);
			}
			else
			{
				//get ArrayList of the key
				ArrayList<Rating> list = map.get(raterID);
				list.add(new Rating(movie_id, rating));
				map.put(raterID, list);
			}//end of inner if block

		}//end for loop
		
		return map;
	}
	
	//method to find out number of ratings for a particular rater
	public int getNumberOfRating(String rater_id, String path)
	{
		HashMap<String, ArrayList<Rating>> map = createRatermap(path);
		int number = map.get(rater_id).size();
		
		return number;
	}
	//this method find maximum number of ratings by any rater
	public Map<String, Integer> getMaximumNumberOfRating(String path)
	{
		Map<String, Integer> map = new HashMap<>();
		
		HashMap<String, ArrayList<Rating>> ratingMap = createRatermap(path);
		
//		Map.Entry<String, ArrayList<Rating>> entry = null;
//		
//		for(Map.Entry<String, ArrayList<Rating>> e : ratingMap.entrySet())
//		{
//			if((entry == null) || (e.getValue().size() > entry.getValue().size())) entry = e;
//		}

		int maxNumRat = ratingMap.entrySet().stream().max((entry1, entry2) -> entry1.getValue().size() > entry2.getValue().size() ? 1 : -1)
				.get().getValue().size();	
		
		for(Map.Entry<String, ArrayList<Rating>> e : ratingMap.entrySet())
		{
			if(e.getValue().size() >= maxNumRat) map.put(e.getKey(), e.getValue().size());
		}
		return map;
	}
	
	//find the number of ratings a particular movie has
	public int getNumberOfRatersOfAMovie(String movieID, String fileName)
	{
		int numberOfRaters = 0;
		HashMap<String, ArrayList<Rating>> map = createRatermap( fileName);
		for(Map.Entry<String, ArrayList<Rating>> entry : map.entrySet())
		{
			ArrayList<Rating> arrList = entry.getValue();
			for(Rating r : arrList)
			{
				if(r.getItem().equals(movieID)) numberOfRaters++;
			}
		}
		return numberOfRaters;
	}
	//this method reads the total number of different movies rated by all raters
	public int getNumOfDifferentMovies(String fileName)
	{
		int numberOfDifferentMoves = 0;
		//HashMap<String, Integer> map = new HashMap<>();
		ArrayList<String> movieList = new ArrayList<>();
		FileResource fileResource = new FileResource(fileName);
		
		for(CSVRecord record : fileResource.getCSVParser())
		{
			String movieID = record.get("movie_id");
			
			if(!movieList.contains(movieID)) 
			{
				movieList.add(movieID);
				numberOfDifferentMoves++;
			}
		}
		return numberOfDifferentMoves;
	}
}//end of class
