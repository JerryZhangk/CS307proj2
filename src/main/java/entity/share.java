package entity;

public class share {
    public String count(){
        return String.format("select count(*) from share_name;");
    }
    public String  insert(int cnt , String postID,String name){
        return String.format("INSERT INTO share_name " +
                "VALUES (%d,%s,'%s');", cnt, postID, name);
    }
}
