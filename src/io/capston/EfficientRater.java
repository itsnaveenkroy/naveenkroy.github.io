package io.capston;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EfficientRater implements Rater
{

    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	private String myID; //a unique String ID for this rater (rater who does the rating)
    //private ArrayList<Rating> myRatings; //an ArrayList of Rating object
	
	/**
	 * @param String - key is the movie id
	 * @param Rating is the value of the movie
	 */
	private HashMap<String, Rating> myRatings;
	
    public EfficientRater  (String id) 
    {
        myID = id;
        myRatings = new HashMap<>();
    }
    /**
     * @Parameters- item- IMDB ID of the movie being rated
     */
    @Override
    public void addRating(String item, double rating) 
    {
           myRatings.put(item, new Rating(item, rating));
    }

    /**
     * @Parameters- item- IMDB ID of the movie being rated
     */
    @Override
    public boolean hasRating(String item) 
    {
    	if(myRatings.containsKey(item)) return true;
    	/*
    	 * otherwise
    	 */
        return false;
    }
	/**
	 * 
	 * @return rater id
	 */
    @Override
    public String getID() 
    {
        return myID;
    }

    /**
     * @Parameters- item- IMDB ID of the movie being rated
     */
    @Override
    public double getRating(String item) 
    {
    	if(myRatings.containsKey(item)) return myRatings.get(item).getValue();
    	return -1;
    }
    /**
     * 
     * @return total number of Rating user (rater) has rated
     */
    @Override
    public int numRatings() 
    {
        return myRatings.size();
    }

    /** 
     * @return list of items which has been rated by the rater (user)
     */
    @Override
    public ArrayList<String> getItemsRated() 
    {
        ArrayList<String> list = new ArrayList<>(myRatings.keySet());        
        return list;
    }
    
    //get the hashmap
    public HashMap<String, Rating> getRaterMap()
    {
    	return myRatings;
    }
    

	@Override
	public String toString() {
		return "Rater [myID=" + myID + ", myRatings=" + myRatings + "]";
	}
	
}
