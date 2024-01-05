package io.capston;

public class MinutesFilter implements Filter
{
	int minMinutes;
	int maxMinutes;
	
	public MinutesFilter(int  minMinutes, int maxMinutes)
	{
		this.minMinutes = minMinutes;
		this.maxMinutes = maxMinutes;
	}

	@Override
	public boolean satisfies(String id) 
	{
		return MovieDatabase.getMinutes(id) >= minMinutes && MovieDatabase.getMinutes(id) <= maxMinutes;
	}

}
