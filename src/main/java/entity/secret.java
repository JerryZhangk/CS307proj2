package entity;

import java.util.Date;

public class secret {
    int postID;
    String authorID;
    Date postTime;
    String content;
    String title;

    public String insert(int postID, String authorID, String title , String content){
        return String.format("insert into secret\n" +
                "values (%d,'%s',now(),'%s','%s');", postID, authorID, title, content);
    }
}
