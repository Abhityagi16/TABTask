package in.appmetric.tabtask.data.models;

/**
 * Created by abhishektyagi on 19/08/17.
 */

public class Thumbnail {
    private static final String THUMBNAIL_SIZE = "/portrait_xlarge";
    private String path, extension;


    public void setPath(String path) {
        this.path = path;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getCompletePath() {
        return path + THUMBNAIL_SIZE + "." + extension;
    }
}
