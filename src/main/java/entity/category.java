package entity;

public class category {
    int autoCategoryId;
    int postId;
    String category;

    public int getAutoCategoryId() {
        return autoCategoryId;
    }

    public int getPostId() {
        return postId;
    }

    public String getCategory() {
        return category;
    }
    public String count(){
        return String.format("select count(*) from postcategories;\n");
    }
    public String insert(int tagsCnt,int postId,String tag){
        return String.format("insert into postcategories\n" +
                "values (%d,%d,'%s');", tagsCnt, postId, tag);
    }
}
