package entity;

public class like {
    public String count(){
        return String.format("select count(*) from like_name;");
    }
    public String insert(int cnt,String postId,String name){
        return String.format("INSERT INTO like_name " +
                "VALUES (%d,%s,'%s');", cnt, postId, name);
    }
}
