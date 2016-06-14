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

import java.io.InputStream;
import java.net.URL;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListingArrayAdapter extends ArrayAdapter<Listing>{
    private final static String TAG="ListingArrayAdapter";
    private Context context;
    private SearchResult listings;
    private int resourceId;
    private Drawable images[];

    public ListingArrayAdapter(Context context,int resourceId,SearchResult listings){
        super(context,resourceId,listings.getListings());
        this.images=new Drawable[listings.getListings().size()];
        this.context=context;
        this.listings=listings;
        this.resourceId=resourceId;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        LayoutInflater inflater=(LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout=inflater.inflate(resourceId,null);
        Listing listing=this.listings.getListings().get(position);
        TextView titleField=(TextView)layout.findViewById(R.id.listviewitem_title);
        titleField.setText(listing.getTitle());
        TextView priceField=(TextView)layout.findViewById(R.id.listviewitem_price);
        priceField.setText("Current price: "+listing.getCurrentPrice());
        TextView shippingField=(TextView)layout.findViewById(R.id.listviewitem_shipping);
        shippingField.setText("Shipping: "+listing.getShippingCost());
        if(listing.getListingUrl()!=null){
            try{
                URL url=new URL(listing.getImageUrl());
                InputStream is=(InputStream)url.getContent();
                Drawable image=Drawable.createFromStream(is,"src");
                ImageView imageView=new ImageView(context);
                imageView=(ImageView)layout.findViewById(R.id.listviewitem_image);
                imageView.setImageDrawable(image);
                this.images[position]=image;
            }catch(Exception x){
                Log.e(TAG,"getView - in if(listing.getListingUrl()!=null)",x);
            }
        }
        return(layout);
    }

    public Drawable getImage(int position){
        return(this.images[position]);
    }
}