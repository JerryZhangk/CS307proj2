package entity;

public class follow {
    String authorID;
    String authorFollowName;

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public void setAuthorFollowName(String authorFollowName) {
        this.authorFollowName = authorFollowName;
    }
    public String count(){
        return  String.format("select count(*) from follow_name;\n");
    }
    public String insert(int followId,String targetID,String authorFollowName){
        return String.format("insert into follow_name values (%d,'%s','%s');",
                followId, targetID, authorFollowName);
    }
    public String lookUp(String  name){
        return String.format("select * from follow_name where author_follow_id ='%s';\n",name);
    }
    public String delete(String name,String targetID){
        return String.format("delete from follow_name where author_follow_id = '%s' and author_id ='%s';",
                name, targetID);
    }
}
