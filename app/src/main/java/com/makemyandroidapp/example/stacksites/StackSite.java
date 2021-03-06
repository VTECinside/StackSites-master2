package com.makemyandroidapp.example.stacksites;


/*
 * Data object that holds all of our information about a StackExchange Site.
 */
public class StackSite {

	private String name;
	private String link;
	private String about;
	private String about2;
	private String about3;
	private String imgUrl;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}

	public String getAbout3() {
		return about3;
	}
	public void setAbout3(String about3) {
		this.about3 = about3;
	}

	public String getAbout2() {return about2;}
	public void setAbout2(String about2) {
		this.about2 = about2;
	}

	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	@Override
	public String toString() {
		return "StackSite [name=" + name + ", link=" + link + ", about="
				+ about + ", imgUrl=" + imgUrl + "]";
	}
}
