package io.capston;

public class DirectorsFilter implements Filter 
{
	String[] directors;
	
	public DirectorsFilter(String directors)
	{
		this.directors = directors.split(",");
	}
	@Override
	public boolean satisfies(String id) 
	{
		for(int i = 0; i < directors.length; i++)
		{
			if(MovieDatabase.getDirector(id).contains(directors[i].trim())) return true;
		}
		return false;
	}

}
