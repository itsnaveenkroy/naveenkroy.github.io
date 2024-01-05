package io.capston;

/**
 * RaterWeight class to present a rater id and his weight related to a given rater
 * 
 * @author MD AL MAMUNUR RASHID (Mamun)
 *
 */
public class RaterWeight implements Comparable<RaterWeight>
{
	private String raterID; //store the rater ID
	private double raterWeight; //store the rater weight
	
	//constructor
	public RaterWeight(String id, double weight)
	{
		this.raterID = id;
		this.raterWeight = weight;
	}
	
	//get raterID
	public String getRaterID()
	{
		return raterID;
	}
	
	//get raterWeight
	public double getRaterWeight()
	{
		return raterWeight;
	}

	//sort the RaterWeight in descending order by weight
	@Override
	public int compareTo(RaterWeight otherRaterWeight) 
	{
		if(this.raterWeight > otherRaterWeight.raterWeight) return 1;
		if(this.raterWeight < otherRaterWeight.raterWeight) return -1;
		
		return 0;
	}
	
	@Override
	public String toString()
	{
		return raterID + " " + raterWeight;
	}
}
