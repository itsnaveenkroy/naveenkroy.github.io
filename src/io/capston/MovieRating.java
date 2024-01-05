package io.capston;

public class MovieRating implements Comparable<MovieRating>
{
	private double rating;
	private String movieName;
	
	MovieRating(double rating, String mvName)
	{
		this.rating = rating;
		this.movieName = mvName;
	}

	public double getRating()
	{
		return rating;
	}
	public String getMovieName()
	{
		return movieName;
	}
	@Override
	public int compareTo(MovieRating movieRating) 
	{
		if(this.rating < movieRating.rating) return -1;
		if(this.rating > movieRating.rating) return 1;

		return 0;
	}
	
	@Override
	public String toString()
	{
		return rating +" " + movieName;
	}
}
