package entity;

public class reply1 {
    int auto;
    int postID;
    String replyContent;
    int replyStar;
    String replyAuthor;
    public String count(){
        return String.format("select count(*) from reply1;\n");
    }
    public String insert(int replyId,int postID, String replyContent,String replyAuthor){
        return String.format("insert into reply1\n" +
                        "values(%d,%d,'%s',0,'%s');", replyId,postID
                , replyContent,replyAuthor);
    }
    public String up(int replyId){
        return String.format("update reply1 set reply_star = reply_star +1" +
               " where auto = %d;",replyId);
    }
}
