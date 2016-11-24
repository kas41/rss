package com.example.rss.model;

public class Event {

    private String mTitle;
    private String mLink;
    private String mImageURL;

    public String getTitle() {
        return mTitle;
    }

    public String getLink() {
        return mLink;
    }

    public String getImageURL() {
        return mImageURL;
    }

    public static class Builder{
        private String mTitle;
        private String mLink;
        private String mImageURL;
        private String mDate;

        public Builder(){}

        public Builder title(String title){
            mTitle = title;
            return this;
        }

        public Builder link(String link){
            mLink = link;
            return this;
        }

        public Builder image(String img){
            mImageURL = img;
            return this;
        }

        public Event create(){
            return new Event(this);
        }
    }

    private Event(Builder builder){
        mTitle = builder.mTitle;
        mLink = builder.mLink;
        mImageURL = builder.mImageURL;
    }
}
