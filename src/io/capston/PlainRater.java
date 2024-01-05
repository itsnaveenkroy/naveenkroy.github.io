package io.capston;

import java.util.ArrayList;


public class PlainRater implements Rater
{

    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	private String myID; //a unique String ID for this rater (rater who does the rating)
    private ArrayList<Rating> myRatings; //an ArrayList of Rating object

    public PlainRater (String id) 
    {
        myID = id;
        myRatings = new ArrayList<Rating>();
    }
    /**
     * @Parameters- item- IMDB ID of the movie being rated
     */
    @Override
    public void addRating(String item, double rating) 
    {
        myRatings.add(new Rating(item,rating));
    }

    /**
     * @param Rating
     */
    public ArrayList<Rating> getRatingList()
    {
    	 return myRatings;
    }
    /**
     * @Parameters- item- IMDB ID of the movie being rated
     */
    @Override
    public boolean hasRating(String item) 
    {
        for(int k=0; k < myRatings.size(); k++)
        {
            if (myRatings.get(k).getItem().equals(item))
            {
                return true;
            }
        }
        
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
        for(int k=0; k < myRatings.size(); k++){
            if (myRatings.get(k).getItem().equals(item)){
                return myRatings.get(k).getValue();
            }
        }
        
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
        ArrayList<String> list = new ArrayList<String>();
        for(int k=0; k < myRatings.size(); k++)
        {
            list.add(myRatings.get(k).getItem());
        }
        
        return list;
    }
	@Override
	public String toString() {
		return "Rater [myID=" + myID + ", myRatings=" + myRatings + "]";
	}
}

