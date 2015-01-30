/*
 * Copyright (C) 2011 Mats Hofman <http://matshofman.nl/contact/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.matshofman.saxrssreader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class RssItem implements Comparable<RssItem>, Parcelable {

	private RssFeed feed;
	private String title;
	private String link;
	private Date pubDate;
	private String description;
	private String content;
    private Date published; // Atom
    private Date updated; // Atom
    private String id; // Atom
    private String summary; // Atom
    private String author; // Atom

	public RssItem() {
		
	}
	
	public RssItem(Parcel source) {
		
		Bundle data = source.readBundle();
		title = data.getString("title");
		link = data.getString("link");
		pubDate = (Date) data.getSerializable("pubDate");
		description = data.getString("description");
		content = data.getString("content");
		feed = data.getParcelable("feed");
        published = (Date) data.getSerializable("published");
        updated = (Date) data.getSerializable("updated");
        id = data.getString("id");
        summary = data.getString("summary");
        author = data.getString("author");
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		Bundle data = new Bundle();
		data.putString("title", title);
		data.putString("link", link);
		data.putSerializable("pubDate", pubDate);
		data.putString("description", description);
		data.putString("content", content);
		data.putParcelable("feed", feed);
        data.putSerializable("published", published);
        data.putSerializable("updated", updated);
        data.putString("id", id);
        data.putString("summary", summary);
        data.putString("author", author);
		dest.writeBundle(data);
	}
	
	public static final Parcelable.Creator<RssItem> CREATOR = new Parcelable.Creator<RssItem>() {
		public RssItem createFromParcel(Parcel data) {
			return new RssItem(data);
		}
		public RssItem[] newArray(int size) {
			return new RssItem[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}
	
	public RssFeed getFeed() {
		return feed;
	}

	public void setFeed(RssFeed feed) {
		this.feed = feed;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public void setPubDate(String pubDate) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
			this.pubDate = dateFormat.parse(pubDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

    public void setPublished(Date published) { this.published = published; }

    public void setPublished(String published) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
            this.published = dateFormat.parse(published);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getPublished() { return published; }

    public void setUpdated(Date updated) { this.updated = updated; }

    public void setUpdated(String updated) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
            this.updated = dateFormat.parse(updated);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getUpdated() { return updated; }

    public void setId(String id) { this.id = id; }

    public String getId() { return id; }

    public void setSummary(String summary) { this.summary = summary; }

    public String getSummary() { return summary; }

    public void setAuthor(String author) { this.author = author; }

    public String getAuthor() { return author; }

	@Override
	public int compareTo(RssItem another) {
		if(getPubDate() != null && another.getPubDate() != null) {
			return getPubDate().compareTo(another.getPubDate());
		} else { 
			return 0;
		}
	}
}
