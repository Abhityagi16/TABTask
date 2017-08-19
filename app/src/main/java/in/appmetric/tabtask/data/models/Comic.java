package in.appmetric.tabtask.data.models;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by abhishektyagi on 14/08/17.
 */

public class Comic {
    private int id;
    private String title, description;
    private int pageCount;
    private AuthorWrapper creators;
    private Thumbnail thumbnail;
    private List<Price> prices;




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public AuthorWrapper getCreators() {
        return creators;
    }

    public void setCreators(AuthorWrapper creators) {
        this.creators = creators;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public Price lowestPrice() {
        Price minPrice = prices.get(0);
        for(Price p: prices) {
            if (p.getPrice() < minPrice.getPrice()) minPrice = p;

        }
        return minPrice;
    }
}
