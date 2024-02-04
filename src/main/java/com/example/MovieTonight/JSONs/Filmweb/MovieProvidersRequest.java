package com.example.MovieTonight.JSONs.Filmweb;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MovieProvidersRequest {
    @SerializedName("vodProvider")
    private int vodProvider;

    @SerializedName("id")
    private int id;

    @SerializedName("film")
    private int film;

    @SerializedName("start")
    private String start;

    @SerializedName("end")
    private String end;

    @SerializedName("link")
    private String link;

    @SerializedName("externalId")
    private String externalId;

    @SerializedName("status")
    private int status;

    @SerializedName("externalTitle")
    private String externalTitle;

    @SerializedName("payments")
    private List<Payment> payments;

    // Dodajemy getter-y i setter-y

    public int getVodProvider() {
        return vodProvider;
    }

    public void setVodProvider(int vodProvider) {
        this.vodProvider = vodProvider;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFilm() {
        return film;
    }

    public void setFilm(int film) {
        this.film = film;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getExternalTitle() {
        return externalTitle;
    }

    public void setExternalTitle(String externalTitle) {
        this.externalTitle = externalTitle;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public static class Payment {
        @SerializedName("filmVod")
        private int filmVod;

        @SerializedName("accessType")
        private int accessType;

        @SerializedName("currency")
        private int currency;

        @SerializedName("price")
        private int price;

        @SerializedName("buy")
        private boolean buy;

        @SerializedName("rent")
        private boolean rent;

        @SerializedName("subscription")
        private boolean subscription;

        @SerializedName("free")
        private boolean free;

        @SerializedName("link")
        private String link;  // Dodany link do obiektu Payment

        // Dodajemy getter-y i setter-y

        public int getFilmVod() {
            return filmVod;
        }

        public void setFilmVod(int filmVod) {
            this.filmVod = filmVod;
        }

        public int getAccessType() {
            return accessType;
        }

        public void setAccessType(int accessType) {
            this.accessType = accessType;
        }

        public int getCurrency() {
            return currency;
        }

        public void setCurrency(int currency) {
            this.currency = currency;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public boolean isBuy() {
            return buy;
        }

        public void setBuy(boolean buy) {
            this.buy = buy;
        }

        public boolean isRent() {
            return rent;
        }

        public void setRent(boolean rent) {
            this.rent = rent;
        }

        public boolean isSubscription() {
            return subscription;
        }

        public void setSubscription(boolean subscription) {
            this.subscription = subscription;
        }

        public boolean isFree() {
            return free;
        }

        public void setFree(boolean free) {
            this.free = free;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
    public static List<MovieProvidersRequest> fromJsonArray(String jsonArray) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<MovieProvidersRequest>>(){}.getType();
        return gson.fromJson(jsonArray, listType);
    }
}
