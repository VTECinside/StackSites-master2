package com.makemyandroidapp.example.stacksites;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import android.content.Context;
import android.util.Log;

public class SitesXmlPullParser {

	static final String KEY_SITE = "item";
	static final String KEY_NAME = "title";
	static final String KEY_LINK = "link";
	static final String KEY_ABOUT = "description";
	static final String KEY_IMAGE_URL = "image";

	public static List<StackSite> getStackSitesFromFile(Context ctx) {

		// List of StackSites that we will return
		List<StackSite> stackSites;
		stackSites = new ArrayList<StackSite>();

		// temp holder for current StackSite while parsing
		StackSite curStackSite = null;
		// temp holder for current text value while parsing
		String curText = "";

		try {
			// Get our factory and PullParser
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser xpp = factory.newPullParser();

			// Open up InputStream and Reader of our file.
			FileInputStream fis = ctx.openFileInput("StackSites.xml");
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

			// point the parser to our file.
			xpp.setInput(reader);

			// get initial eventType
			int eventType = xpp.getEventType();


			/* We will parse the XML content looking for the "<title>" tag which appears inside the "<item>" tag.
         * However, we should take in consideration that the rss feed name also is enclosed in a "<title>" tag.
         * As we know, every feed begins with these lines: "<channel><title>Feed_Name</title>...."
         * so we should skip the "<title>" tag which is a child of "<channel>" tag,
         * and take in consideration only "<title>" tag which is a child of "<item>"
         *
         * In order to achieve this, we will make use of a boolean variable.
         */
			boolean insideItem = false;

            int countItem = 0;

            // Loop through pull events until we reach END_DOCUMENT
			while (eventType != XmlPullParser.END_DOCUMENT) {
				// Get the current tag
				String tagname = xpp.getName();

				// React to different event types appropriately
				switch (eventType) {
				case XmlPullParser.START_TAG:
					if (tagname.equalsIgnoreCase(KEY_SITE)) {
						// If we are starting a new <site> block we need
						//a new StackSite object to represent it
						curStackSite = new StackSite();
						insideItem = true;
						++countItem;
						/*
                        ///////// アイテム数カウント
                        Log.d("countItem","is " + ++countItem);
                        ////////
                        */
					}
					break;

				case XmlPullParser.TEXT:
					//grab the current text so we can use it in END_TAG event
					curText = xpp.getText();
					break;

				case XmlPullParser.END_TAG:
					if (tagname.equalsIgnoreCase(KEY_SITE) && insideItem) {
						// if </site> then we are done with current Site
						// add it to the list.
						stackSites.add(curStackSite);
					} else if (tagname.equalsIgnoreCase(KEY_NAME) && insideItem) {
						// if </name> use setName() on curSite
						curStackSite.setName(curText);
					} else if (tagname.equalsIgnoreCase(KEY_LINK) && insideItem) {

						///////// CDATA要素取得＆ログ出力
                        /*
						int token = xpp.nextToken();
						while(token!=XmlPullParser.CDSECT){
							token = xpp.nextToken();
						}
						String cdata = xpp.getText();
						Log.i("Info", cdata);
						*/
						/////////


						// if </link> use setLink() on curSite
						curStackSite.setLink(curText);
					} else if (tagname.equalsIgnoreCase(KEY_ABOUT) && insideItem) {

                        Document doc = Jsoup.parse(curText);
                        doc.select("description");
                        String curText2 = Jsoup.parse(curText).select("img").first().attr("src");

                        curStackSite.setImgUrl(curText2);
                        // if </about> use setAbout() on curSite
                        Element table = doc.select("table").get(0);
                        Elements rows = table.select("div");
                        //Log.i("Info", rows.get(0).text());
                        //Log.i("Info", rows.get(2).text());

//						for (int i=0; i<rows.size(); i++) {
//							Log.i("Info", rows.get(i).text());
//						}
                        curStackSite.setAbout(rows.get(0).text());
                        curStackSite.setAbout2(rows.get(1).text());
                        //curStackSite.setAbout(rows.get(1).text());
						/*curStackSite.setAbout("countItem is " + countItem);

						*/

                        /*
					} else if (tagname.equalsIgnoreCase(KEY_IMAGE_URL) && insideItem) {
						// if </image> use setImgUrl() on curSite
						curStackSite.setImgUrl(curText);*/
					}else if (tagname.equalsIgnoreCase("rx:AuctionType") && insideItem) {

                        curStackSite.setAbout3("AuctionType: " + curText);
                    }
					break;

				default:
					break;
				}
				//move on to next iteration
				eventType = xpp.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// return the populated list.
		return stackSites;
	}
}
