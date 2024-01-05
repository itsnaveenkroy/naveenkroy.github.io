package io.capston;

public class YearAfterFilter implements Filter
{
	int year;
	//constructor
	public YearAfterFilter(int year)
	{
		this.year = year;
	}
	
	@Override
	public boolean satisfies(String id) 
	{
		return MovieDatabase.getYear(id) >= year;
	}

}
