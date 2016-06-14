/*
ebayDemo - Demo of how to call the eBay API and display the results on an Android device
Imported from TurboGrafx16Collector - Mobile application to manage a collection of TurboGrafx-16 games
Copyright (C) 2010 Hugues Johnson

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See
the GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software 
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package com.makemyandroidapp.example.stacksites;

import java.util.ArrayList;
import java.util.Collections;

public class SearchResult{
    public final static int RESULT_SUCCESS=0;
    public final static int RESULT_ERROR=1;
    private int resultCode;
    private Exception error;
    private ArrayList<Listing> listings;

    public SearchResult(){
        this.listings=new ArrayList<Listing>();
    }

    public void append(SearchResult toAppend){
        this.listings.addAll(toAppend.getListings());
    }

    public Exception getError(){
        return error;
    }
    public void setError(Exception error){
        this.error=error;
    }
    public ArrayList<Listing> getListings(){
        return listings;
    }
    public void setListings(ArrayList<Listing> listings){
        this.listings=listings;
    }

    public int getResultCode(){
        return resultCode;
    }

    public void setResultCode(int resultCode){
        this.resultCode=resultCode;
    }

    public void sort(){
        Collections.sort(this.listings);
    }
}