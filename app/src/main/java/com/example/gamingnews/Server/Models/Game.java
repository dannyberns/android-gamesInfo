package com.example.gamingnews.Server.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Game  implements Parcelable {


    // this class will be as a template for the data that we are going to parse

    private String name;
    private int id;
    private double aggregated_rating;
    private String summary;
    private String image;
    private String url;
    private Cover cover;
    private List<InvolvedCompanies> involved_companies;
    private List<Genre> genres;
    private List<Video> videos;
    private List<Platform> platforms;
    private List<ReleaseDate> release_dates;
    private List<Screenshot> screenshots;
    private List<Expansion> expansions;
    private Collection collection;
    private double rating;
    private int rating_count;
    private int aggregated_rating_count;


    public Game(String name, int id, double aggregated_rating, String summary, String image, String url, Cover cover, List<InvolvedCompanies> involved_companies, List<Genre> genres, List<Video> videos, List<Platform> platforms, List<ReleaseDate> release_dates, List<Screenshot> screenshots, List<Expansion> expansions, Collection collection, double rating, int rating_count, int aggregated_rating_count) {
        this.name = name;
        this.id = id;
        this.aggregated_rating = aggregated_rating;
        this.summary = summary;
        this.image = image;
        this.url = url;
        this.cover = cover;
        this.involved_companies = involved_companies;
        this.genres = genres;
        this.videos = videos;
        this.platforms = platforms;
        this.release_dates = release_dates;
        this.screenshots = screenshots;
        this.expansions = expansions;
        this.collection = collection;
        this.rating = rating;
        this.rating_count = rating_count;
        this.aggregated_rating_count = aggregated_rating_count;
    }

    protected Game(Parcel in) {
        name = in.readString();
        id = in.readInt();
        aggregated_rating = in.readDouble();
        summary = in.readString();
        image = in.readString();
        url = in.readString();
        genres = in.createTypedArrayList(Genre.CREATOR);
        platforms = in.createTypedArrayList(Platform.CREATOR);
        screenshots = in.createTypedArrayList(Screenshot.CREATOR);
        rating = in.readDouble();
        rating_count = in.readInt();
        aggregated_rating_count = in.readInt();
        involved_companies = in.createTypedArrayList(InvolvedCompanies.CREATOR);
        expansions = in.createTypedArrayList(Expansion.CREATOR);
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public String getSummary() {
        return summary;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public double getAggregated_rating() {
        return aggregated_rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAggregated_rating(double aggregated_rating) {
        this.aggregated_rating = aggregated_rating;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms;
    }

    public List<ReleaseDate> getRelease_dates() {
        return release_dates;
    }

    public void setRelease_dates(List<ReleaseDate> release_dates) {
        this.release_dates = release_dates;
    }

    public int getAggregated_rating_count() {
        return aggregated_rating_count;
    }

    public void setAggregated_rating_count(int aggregated_rating_count) {
        this.aggregated_rating_count = aggregated_rating_count;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getRating_count() {
        return rating_count;
    }

    public void setRating_count(int rating_count) {
        this.rating_count = rating_count;
    }

    public List<Screenshot> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(List<Screenshot> screenshots) {
        this.screenshots = screenshots;
    }

    public List<InvolvedCompanies> getInvolved_companies() {
        return involved_companies;
    }

    public void setInvolved_companies(List<InvolvedCompanies> involved_companies) {
        this.involved_companies = involved_companies;
    }

    public List<Expansion> getExpansions() {
        return expansions;
    }

    public void setExpansions(List<Expansion> expansions) {
        this.expansions = expansions;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeDouble(aggregated_rating);
        dest.writeString(summary);
        dest.writeString(image);
        dest.writeString(url);
        dest.writeTypedList(genres);
        dest.writeTypedList(platforms);
        dest.writeTypedList(screenshots);
        dest.writeTypedList(involved_companies);
        dest.writeTypedList(expansions);
        dest.writeDouble(rating);
        dest.writeInt(rating_count);
        dest.writeInt(aggregated_rating_count);
    }
}
