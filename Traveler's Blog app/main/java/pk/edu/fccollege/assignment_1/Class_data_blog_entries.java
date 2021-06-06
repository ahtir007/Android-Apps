package pk.edu.fccollege.assignment_1;

public class Class_data_blog_entries {

    private  String heading;
    private String  author;
    private String  short_des;
    private  String long_des;
    private  String photo_name;
    private float rating;

    private int photo_resid;

    public Class_data_blog_entries(String heading, String author, String short_des, String long_des, String photo_name, float rating, int photo_resid) {
        this.heading = heading;
        this.author = author;
        this.short_des = short_des;
        this.long_des = long_des;
        this.photo_name = photo_name;
        this.rating = rating;
        this.photo_resid = photo_resid;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getLong_des() {
        return long_des;
    }

    public void setLong_des(String long_des) {
        this.long_des = long_des;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getShort_des() {
        return short_des;
    }

    public void setShort_des(String short_des) {
        this.short_des = short_des;
    }

    public String getPhoto_name() {
        return photo_name;
    }

    public void setPhoto_name(String photo_name) {
        this.photo_name = photo_name;
    }

    public int getPhoto_resid() {
        return photo_resid;
    }

    public void setPhoto_resid(int photo_resid) {
        this.photo_resid = photo_resid;
    }
}
