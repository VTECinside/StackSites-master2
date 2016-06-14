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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;

public class EbayInvoke{
    private static String appID;
    private static String ebayURL;
    private Resources resources;

    public EbayInvoke(Context context){
        this.resources=context.getResources();
        if(Build.PRODUCT.toLowerCase().indexOf("sdk")>-1){
			/* 
			the sandbox URLs are pretty useless as they only return a success code but no results
			if you really want to use them then swap out the next two lines
			appID=this.resources.getString(R.string.ebay_appid_sandbox);
			ebayURL=this.resources.getString(R.string.ebay_wsurl_sandbox);
			*/
            appID=this.resources.getString(R.string.ebay_appid_production);
            ebayURL=this.resources.getString(R.string.ebay_wsurl_production);
        }else{
            appID=this.resources.getString(R.string.ebay_appid_production);
            ebayURL=this.resources.getString(R.string.ebay_wsurl_production);
        }
    }

    public String search(String keyword) throws Exception{
        String jsonResponse=null;
        jsonResponse=invokeEbayRest(keyword);
        if((jsonResponse==null)||(jsonResponse.length()<1)){
            throw(new Exception("No result received from invokeEbayRest("+keyword+")"));
        }
        return(jsonResponse);
    }

    private String getRequestURL(String keyword){
        CharSequence requestURL=TextUtils.expandTemplate(this.resources.getString(R.string.ebay_request_template),ebayURL,appID,keyword);
        return(requestURL.toString());
    }

    private String invokeEbayRest(String keyword) throws Exception{
        String result=null;
        HttpClient httpClient=new DefaultHttpClient();
        HttpGet httpGet=new HttpGet(this.getRequestURL(keyword));
        HttpResponse response=httpClient.execute(httpGet);
        HttpEntity httpEntity=response.getEntity();
        if(httpEntity!=null){
            InputStream in=httpEntity.getContent();
            BufferedReader reader=new BufferedReader(new InputStreamReader(in));
            StringBuffer temp=new StringBuffer();
            String currentLine=null;
            while((currentLine=reader.readLine())!=null){
                temp.append(currentLine);
            }
            result=temp.toString();
            in.close();
        }
        return(result);
    }
}