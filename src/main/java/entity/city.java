package entity;

public class city {
    int autoId ;
    int postId ;
    String cityName;

    public int getAutoId() {
        return autoId;
    }

    public int getPostId() {
        return postId;
    }

    public String getCityName() {
        return cityName;
    }

    public String count(){
        return String.format("select count(*) from postcity;");
    }
    public String insert(int postCity, int postId,String cityName){
        return String.format("insert into postcity\n" +
                "values (%d,%d,'%s');", postCity, postId, cityName);
    }
}
