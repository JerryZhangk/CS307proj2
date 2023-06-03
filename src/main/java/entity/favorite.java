package entity;

public class favorite {
    public String count(){
        return String.format("select count(*) from favorite_name;");
    }
    public String insert(int cnt, String postId ,String name){
        return String.format("INSERT INTO favorite_name " +
                "VALUES (%d,%s,'%s');", cnt, postId, name);
    }
}
