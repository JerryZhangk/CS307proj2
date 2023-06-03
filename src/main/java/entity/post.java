package entity;

import java.sql.Date;

public class post {
    public int postId;
    public String authorId;
    public String authorName;
    public String title;
    public String content;
    public String reply_content;
    public Date date;
    public String count(){
        return String.format("select count(*) from post1" +
                "union select count(*) from post2" +
                "union select count(*) from post3;");
    }
    public String insert(int postID, String authorID, String title , String content){
       return String.format("insert into post3\n" +
                "values (%d,'%s',now(),'%s','%s');", postID, authorID, title, content);
    }
    public String detectByName(String name){
        String sql;
        if (name.length() < 5){
            sql = String.format("select * from post1 join author1_5 a on post.author_id = a.author_id where author_name ='%s'" +
                    "union select * from post2 join author1_5 a on post2.author_id = a.author_id where author_name ='%s';\n" +
                    "union select * from post3 join author1_5 a on post2.author_id = a.author_id where author_name ='%s'\n;", name,name,name);
        } else if (name.length() >10) {
            sql = String.format("select * from post1 join author5_10 a on post.author_id = a.author_id where author_name ='%s'" +
                    "union select * from post2 join author5_10 a on post2.author_id = a.author_id where author_name ='%s';\n" +
                    "union select * from post3 join author5_10 a on post2.author_id = a.author_id where author_name ='%s'\n;", name,name,name);
        } else {
            sql = String.format("select * from post1 join author10_15 a on post.author_id = a.author_id where author_name ='%s'" +
                    "union select * from post2 join author10_15 a on post2.author_id = a.author_id where author_name ='%s';\n" +
                    "union select * from post3 join author10_15 a on post2.author_id = a.author_id where author_name ='%s'\n;", name,name,name);
        }
        return sql;
    }


    public String detectByTime(String begin, String end){
        return String.format("select * from post1 where  post_time>='%s' and post_time <='%s' " +
                "union select * from post2 where  post_time>='%s' and post_time <='%s' " +
                "union select * from post3 where  post_time>='%s' and post_time <='%s';\n",begin,end,begin,end,begin,end);
    }
    public String detectByCate(String cate){
        return String.format("select * from post1 join postcategories p on post1.post_id = p.post_id where category = '%s'  " +
                "union   select * from post2 join postcategories p on post2.post_id = p.post_id where category = '%s' " +
                " union select * from post3 join postcategories p on post3.post_id = p.post_id where category = '%s' ;\n",cate,cate,cate);
    }
    public String detectByBoth(String begin, String end,String cate){
        return String.format("select * from post1 join (select post_id from postcategories where  category = '%s') x on x.post_id = post1.post_id where post_time>='%s' and post_time <='%s' \n" +
                "union select * from post2 join (select post_id from postcategories where  category = '%s') x on x.post_id = post2.post_id where post_time>='%s' and post_time <='%s' \n" +
                "union select * from post3 join (select post_id from postcategories where  category = '%s') x on x.post_id = post3.post_id where post_time>='%s' and post_time <='%s' ;\n",
                cate,begin,end,cate,begin,end,cate,begin,end);
    }



    public String String2format() {
        return String.format("post_id : " + postId + "\n" +
                "author_id : " + authorId + "\n" +
                "title : " + title + "\n" +
                "content : " + content + "\n" +
                "author_name : " + authorName + "\n"
                + "post_time : " + date + "\n"
                + "reply_content : " + reply_content + "\n");
    }

    public String String4format() {
        return String.format
                ("post_id: " + postId + "\n" +
                        "author_id : " + authorId + "\n" +
                        "title : " + title + "\n" +
                        "content : " + content + "\n" +
                        "post_time : " + date + "\n");
    }
    public String sharePost(String name){
        return String.format("select * from post1 join share_name l on post.post_id = l.post_id where author_share_id = '%s'" +
                "union select * from post2 join share_name l on post.post_id = l.post_id where author_share_id = '%s'" +
                "union select * from post3 join share_name l on post.post_id = l.post_id where author_share_id = '%s';", name,name,name);
    }
    public String lookAll(){
        return String.format("select * from post1 " +
                         "union select * from post2 " +
                         "union select * from post3;");
    }
    public String favoritePost(String name){
        return String.format("select * from post1 join favorite_name l on post.post_id = l.post_id where author_share_id = '%s'" +
                "union select * from post2 join favorite_name l on post.post_id = l.post_id where author_share_id = '%s'" +
                "union select * from post3 join favorite_name l on post.post_id = l.post_id where author_share_id = '%s';", name,name,name);
    }
    public String likePost(String name){
        return String.format("select * from post1 join like_name l on post.post_id = l.post_id where author_share_id = '%s'" +
                "union select * from post2 join like_name l on post.post_id = l.post_id where author_share_id = '%s'" +
                "union select * from post3 join like_name l on post.post_id = l.post_id where author_share_id = '%s';", name,name,name);
    }
    public String replyPost(String name){
        return String.format("select * from post1 join reply1 r on post.post_id = r.post_id where reply_author = '%s'" +
                "union select * from post2 join reply1 r on post.post_id = r.post_id where reply_author = '%s'" +
                "union select * from post3 join reply1 r on post.post_id = r.post_id where reply_author = '%s';", name,name,name);
    }


    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReply_content() {
        return reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
