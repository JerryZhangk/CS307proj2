package entity;

public class reply2 {
    int reply1Id;
    String replyContent;
    int replyStar;
    String replyAuthor;
    public String count(){
        return String.format("select count(*) from reply2;\n");
    }
    public String insert(int reply2Id,int reply1Id,String replyContent, String replyAuthor){
        return String.format("insert into reply2\n" +
                        "values(%d,%d,'%s',0,'%s');", reply2Id, reply1Id
                , replyContent, replyAuthor);
    }
    public String up(int replyId){
        return String.format("update reply2 set reply_star = reply_star +1" +
                " where auto = %d;",replyId);
    }
}
