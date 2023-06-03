package entity;

import java.util.Date;

public class author {
    String authorID;
    String authorName;
    Date registerTime;
    String phone;

    public String duplicate(String name) {
        if (name.length() < 5) {
            return String.format("select * from author1_5 where author_name = '%s'", name);
        } else if (name.length() < 10) {
            return String.format("select * from author5_10 where author_name = '%s'", name);
        } else return String.format("select * from author10_15 where author_name = '%s'", name);
    }

    public String upDateName(String newName ,String name ){
        if (name.length()<5 && newName.length()<5){
            return String.format("update author1_5 set author_name = '%s' where author_name = '%s';",newName,name);
        }else if (name.length()<10 && newName.length()<10){
            return String.format("update author5_10 set author_name = '%s' where author_name = '%s';",newName,name);
        }else if (name.length()>10 && newName.length()>10){
            return String.format("update author10_15 set author_name = 'dsds' where author_name = 'gold_net';",newName,name);
        }
        if (name.length()<5 ){
            if ( newName.length()>=5&& newName.length()<10){
                return String.format("INSERT INTO author5_10 SELECT * FROM author1_5 WHERE author_name = '%s';\n" +
                        "delete from author1_5 where author_name = '%s';\n" +
                        "update author5_10 set author_name = '%s' where author_name = 'east_kill';\n",name,name,newName);
            }else if (newName.length()>=10){
                return String.format("INSERT INTO author10_15 SELECT * FROM author1_5 WHERE author_name = '%s';\n" +
                        "delete from author1_5 where author_name = '%s';\n" +
                        "update author10_15 set author_name = '%s' where author_name = 'east_kill';\n",name,name,newName);
            }
        }
        if (name.length()<10&& name.length()>=5){
            if (newName.length()<5){
                return String.format("INSERT INTO author1_5 SELECT * FROM author5_10 WHERE author_name = '%s';\n" +
                        "delete from author5_10 where author_name = '%s';\n" +
                        "update author1_5 set author_name = '%s' where author_name = 'east_kill';\n",name,name,newName);
            }else if (newName.length()>=10){
                return String.format("INSERT INTO author10_15 SELECT * FROM author5_10 WHERE author_name = '%s';\n" +
                        "delete from author5_10 where author_name = '%s';\n" +
                        "update author10_15 set author_name = '%s' where author_name = 'east_kill';\n",name,name,newName);
            }
        }
        if (name.length()>=10){
            if(newName.length() <5 ){
                return String.format("INSERT INTO author1_5 SELECT * FROM author10_15 WHERE author_name = '%s';\n" +
                        "delete from author10_15 where author_name = '%s';\n" +
                        "update author1_5 set author_name = '%s' where author_name = 'east_kill';\n",name,name,newName);
            }else if (newName.length()>=5 && newName.length() < 10){
                return String.format("INSERT INTO author5_10 SELECT * FROM author10_15 WHERE author_name = '%s';\n" +
                        "delete from author10_15 where author_name = '%s';\n" +
                        "update author5_10 set author_name = '%s' where author_name = 'east_kill';\n",name,name,newName);
            }
        }return null;
    }

    public String insert(String name, String phone) {
        if (name.length() < 5) {
            return String.format(
                    "begin;\n" +
                    "LOCK TABLE author1_5 IN ACCESS EXCLUSIVE MODE;\n" +
                    "--查询\n" +
                    "INSERT INTO author1_5 (author_id, author_name, registration_time, phone) " +
                    "VALUES ('%s','%s',now(),'%s');"
                    +"commit" ,
                    "993716198710224381_" + name, name, phone);


        } else if (name.length() < 10) {
            return String.format(
                    "begin;\n" +
                            "LOCK TABLE author5_10 IN ACCESS EXCLUSIVE MODE;\n" +
                            "--查询\n" +
                            "INSERT INTO author5_10 (author_id, author_name, registration_time, phone) " +
                            "VALUES ('%s','%s',now(),'%s');"
                            +"commit" ,
                    "993716198710224381_" + name, name, phone);
        } else return String.format(
                "begin;\n" +
                        "LOCK TABLE author10_15 IN ACCESS EXCLUSIVE MODE;\n" +
                        "--查询\n" +
                        "INSERT INTO author10_25 (author_id, author_name, registration_time, phone) " +
                        "VALUES ('%s','%s',now(),'%s');"
                        +"commit" ,
                "993716198710224381_" + name, name, phone);
    }

    public String findIDByName(String name) {
        String sql;
        if (name.length() < 5) {
            sql = String.format("select * from author1_5 where author_name = '%s';", name);
        } else if (name.length() >= 10) {
            sql = String.format("select * from author10_15 where author_name = '%s';", name);
        } else {
            sql = String.format("select * from author5_10 where author_name = '%s';", name);
        }
        return sql;
    }

    public String getAuthorID() {
        return authorID;
    }

    public String getAuthorName() {
        return authorName;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
