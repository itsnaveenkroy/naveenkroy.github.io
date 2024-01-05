package io.capston;

import java.util.ArrayList;
import java.util.Collections;



public class MovieRunnerWithFilters 
{
	private static ThirdRatings  tr;
	
	static
	{
		tr = new ThirdRatings();
		
		//set up the movie database
		MovieDatabase.initialize("ratedmoviesfull.csv");

	}

	//main method
	public static void main(String[] args)
	{
		//printing movies having minimal number of raters
//		System.out.println("printing movies having minimal number of raters");
//		printAverageRatings(35);
		
//		System.out.println("\nPrinting movies by year+after filter:\n");
//		printAverageRatingsByYearAfter(20, 2000);
		
//		System.out.println("\nPrinting movies by genre filter:\n");
//		printAverageRatingsByGenre(20, "Comedy");
		
//		System.out.println("\nPrinting movies by minimum minutes and maximum");
//		printAverageRatingsByMinutes(5, 105, 135);
		
//		System.out.println("\nPrinting filted movies by directors: ");
//		printAverageRatingsByDirectors(4, 
//				"Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,"
//				+ "Nora Ephron,Ridley Scott,Sydney Pollack");
		
//		System.out.println("\nPrinting filtered movies by year after and genre:");
//		printAverageRatingsByYearAfterAndGenre(8, 1990, "Drama");

		System.out.println("\nPrinting filtered movires by directors and time range(in minutes)");
		printAverageRatingsByDirectorsAndMinutes(3, 
				"Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack", 90, 180);
	
	}
	
	//this method print the list of movies with average rating
	public static void printAverageRatings(int minimalRaters)
	{
		System.out.println("total numbe of raters: " + tr.getRaterSize());
		
		//print total number of movies in the movie database
		System.out.println("Total movies in database: " + MovieDatabase.size());
		
		ArrayList<MovieRating> movieRatingList =  createMoveRatingList(minimalRaters, tr);
		//sort the movieRatingList in ascending order
		Collections.sort(movieRatingList);
		
		System.out.printf("There are %d movies who has more than or equal %d raters.%n",
				movieRatingList.size(), minimalRaters);
		
		//print the list
		movieRatingList.stream().forEach(item -> System.out.println(item));
		
		
	}
	//this method prints movie list which has been released a given year or later
	public static void printAverageRatingsByYearAfter(int minimalRatings, int year)
	{
		ArrayList<Rating> moviesByYearFiter = 
				tr.getAverageRatingsByFilter(minimalRatings, new YearAfterFilter(year));
	

		Collections.sort(moviesByYearFiter);
		
		moviesByYearFiter.forEach(m -> 
		System.out.println(
				m.getValue() + " " + MovieDatabase.getYear(m.getItem()) 
				+ " "+ MovieDatabase.getTitle(m.getItem())));
		System.out.printf("found %d movies%n", moviesByYearFiter.size());
	}//end of the method
	
	//this method filter and print movies by genre
	public static void printAverageRatingsByGenre(int minimalRatings, String genre)
	{
		ArrayList<Rating> moviesByGenreFiter = 
				tr.getAverageRatingsByFilter(minimalRatings, new GenreFilter(genre));

		Collections.sort(moviesByGenreFiter);
		
		moviesByGenreFiter.forEach( m ->
				System.out.println(
				m.getValue() + " " +  MovieDatabase.getTitle(m.getItem())
						+" " + MovieDatabase.getGenres(m.getItem())));
		
		System.out.printf("found %d movies%n", moviesByGenreFiter.size());


	}
	//this method filter and print movies by given time range(in minute)
	public static void printAverageRatingsByMinutes(int minimalRatings, int minMinutes, int maxMinutes)
	{
		ArrayList<Rating> moviesByMinuteFiter = 
				tr.getAverageRatingsByFilter(minimalRatings, new MinutesFilter(minMinutes, maxMinutes));

		Collections.sort(moviesByMinuteFiter);
		
		moviesByMinuteFiter.forEach( m ->
				System.out.println(
				m.getValue() + " time: " + MovieDatabase.getMinutes(m.getItem()) 
				+ " " +  MovieDatabase.getTitle(m.getItem())));	
		
		System.out.printf("found %d movies%n", moviesByMinuteFiter.size());


	}
	//this method filter and print movies by director name
	public static void printAverageRatingsByDirectors(int minimalRatings, String directors)
	{
		ArrayList<Rating> moviesByMinuteFiter = 
				tr.getAverageRatingsByFilter(minimalRatings, new DirectorsFilter(directors));

		Collections.sort(moviesByMinuteFiter);
		
		moviesByMinuteFiter.forEach( 
					m -> System.out.println
					(
						m.getValue() +" " 
						+  MovieDatabase.getTitle(m.getItem()) +" "
						+ MovieDatabase.getDirector(m.getItem())
					));	
		
		System.out.printf("found %d movies%n", moviesByMinuteFiter.size());
	}
	
	//this method filtered movies having minimal raters by year and genre and print them
	public static void printAverageRatingsByYearAfterAndGenre(int minimalRaters, int year, String genre)
	{
		//create an allFilters object
		AllFilters allFilters = new AllFilters();
		
		//add filter to the filters
		allFilters.addFilter(new YearAfterFilter(year));
		allFilters.addFilter(new GenreFilter(genre));
		
		ArrayList<Rating> movieListByFilter = 
				tr.getAverageRatingsByFilter(minimalRaters, allFilters);
		
		movieListByFilter.forEach
				( 
					m -> System.out.println
					(
						  m.getValue() +" "
						+ MovieDatabase.getYear(m.getItem()) + " "
						+ MovieDatabase.getTitle(m.getItem()) +" "
						+ MovieDatabase.getGenres(m.getItem())
				));	
	
		System.out.printf("found %d movies%n", movieListByFilter.size());

	}

	public static void printAverageRatingsByDirectorsAndMinutes(int minimalRaters, String directors, 
			int minMinutes, int maxMinutes)
	{
		//create an allFilters object
		AllFilters allFilters = new AllFilters();
		
		//add filter to the filters
		allFilters.addFilter(new DirectorsFilter(directors));
		allFilters.addFilter(new MinutesFilter(minMinutes, maxMinutes));
		
		ArrayList<Rating> movieListByFilter = 
				tr.getAverageRatingsByFilter(minimalRaters, allFilters);
		
		movieListByFilter.forEach
				( 
					m -> System.out.println
					(
						  m.getValue() +
						  " time: "+ MovieDatabase.getMinutes(m.getItem()) + " "
						+ MovieDatabase.getTitle(m.getItem()) +" "
						+ MovieDatabase.getDirector(m.getItem())
				));	
	
	   System.out.printf("found %d movies%n", movieListByFilter.size());

	}

	private static ArrayList<MovieRating> createMoveRatingList(int minimalsRaters, ThirdRatings thirdRatings)
	{
		ArrayList<MovieRating> movieRatingList = new ArrayList<>();
		
		ArrayList<Rating> list = thirdRatings.getAverageRatingsOfAllMovies(minimalsRaters);
		for(Rating rating : list)
		{
			String mvName = MovieDatabase.getTitle(rating.getItem());
			double mvRating = rating.getValue();
			
			//add element to the movieRatingList
			movieRatingList.add(new MovieRating(mvRating, mvName));
		}

		return movieRatingList;
	}

}
