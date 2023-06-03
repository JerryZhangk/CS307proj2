import entity.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Server {
    static Statement stmt;
    static Connection con;
    static ResultSet resultSet;

    public static void main(String[] args) {
        stmt = null;
        final String QUIT = "quit";

        final int DEFAULT_PORT = 8099;
        ServerSocket serverSocket = null;
        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            // 绑定监听端口
            serverSocket = new ServerSocket(DEFAULT_PORT);
            System.out.println("启动服务器，监听服务器本地端口" + DEFAULT_PORT);

            while (true) {
                // 等待客户端连接
                Socket socket = serverSocket.accept();
                System.out.println("客户端[" + socket.getInetAddress() + ":" + socket.getPort() + "]已连接");

                reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );

                writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())
                );

                ConnectionPool connectionPool = new ConnectionPool("jdbc:postgresql://localhost:5432/postgres",
                        "postgres", "20030115Zjl", 5, 10);
                try {
                    // 从连接池获取连接
                    con = connectionPool.getConnection();

                    // 创建Statement对象
                    stmt = con.createStatement();

                    // 处理结果集

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    // 释放资源
                    if (resultSet != null) {
                        try {
                            resultSet.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (stmt != null) {
                        try {
                            stmt.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (con != null) {
                        // 将连接放回连接池
                        connectionPool.releaseConnection(con);
                    }
                }
                System.out.println("Opened database successfully");


                String msg = null;
                while ((msg = reader.readLine()) != null) {
                    System.out.println("msg: " + msg);

                    JSONObject object = new JSONObject(msg);
                    // 读取客户端发送的消息
                    System.out.println("客户端[" + socket.getInetAddress() + ":" + socket.getPort() + "]: " + msg);
                    if (object.get("command").equals("3")) {
                        ResultSet resultSet = null;
                        //look all
                        post p = new post();
                        System.out.println(p.lookAll());
                        resultSet = excute2(p.lookAll());
                        if (!resultSet.next()) {
                            System.out.println("没有任何帖子！");
                        } else {
                            System.out.println("look successfully!");
                        }
                        while (resultSet.next()) {
                            p.setAuthorId(resultSet.getString("author_id"));
                            p.setPostId(resultSet.getInt("post_id"));
                            p.setTitle(resultSet.getString("title"));
                            p.setContent(resultSet.getString("content"));
                            p.setDate(resultSet.getDate("post_time"));
                            System.out.println(p.String4format());
                        }
                        System.out.println("look all things");
                    } else if (object.get("command").equals("6.1")) {
                        ResultSet resultSet = null;
                        like like = new like();
                        String postId = object.getString("id");
                        resultSet = excute2(like.count());
                        int cnt = 0;
                        while (resultSet.next()) {
                            cnt = resultSet.getInt("count") + 1;
                        }
                        String name = object.getString("name");
                        System.out.println(like.insert(cnt, postId, name));
                        excute(like.insert(cnt, postId, name));
                        //点赞帖子
                    } else if (object.get("command").equals("6.2")) {
                        favorite favorite = new favorite();
                        ResultSet resultSet = null;
                        resultSet = excute2(favorite.count());
                        int cnt = 0;
                        while (resultSet.next()) {
                            cnt = resultSet.getInt("count") + 1;
                        }
                        String postId = object.getString("id");
                        String name = object.getString("name");
                        System.out.println(favorite.insert(cnt, postId, name));
                        excute(favorite.insert(cnt, postId, name));
                        //收藏帖子
                    } else if (object.get("command").equals("6.3")) {
                        ResultSet resultSet = null;
                        share share = new share();
                        resultSet = excute2(share.count());
                        int cnt = 0;
                        while (resultSet.next()) {
                            cnt = resultSet.getInt("count") + 1;
                        }
                        //转发帖子
                        String postId = object.getString("id");
                        String name = object.getString("name");
                        System.out.println(share.insert(cnt,postId,name));
                        excute(share.insert(cnt,postId,name));
                    } else if (object.get("command").equals("4.1")) {
                        ResultSet resultSet = null;
                        post post = new post();
                        String name = object.getString("name");
                        //look myself
                        resultSet = excute2(post.detectByName(name));
                        System.out.println("look myself successfully!");
                        while (resultSet.next()) {
                            post.setAuthorId(resultSet.getString("author_id"));
                            post.setPostId(resultSet.getInt("post_id"));
                            post.setDate(resultSet.getDate("post_time"));
                            post.setTitle(resultSet.getString("title"));
                            post.setContent(resultSet.getString("content"));
                            System.out.println(post.String4format());
                        }
                        System.out.println("自己发布的全部帖子");
                        //自己发布的帖子
                    } else if (object.get("command").equals("4.2")) {
                        ResultSet resultSet = null;
                        post post = new post();
                        String name = object.getString("name");
                        //look myself
                        System.out.println(post.replyPost(name));
                        resultSet = excute2(post.replyPost(name));
                        post p = new post();
                        while (resultSet.next()) {
                            p.setAuthorId(resultSet.getString("author_id"));
                            p.setPostId(resultSet.getInt("post_id"));
                            p.setDate(resultSet.getDate("post_time"));
                            p.setTitle(resultSet.getString("title"));
                            p.setAuthorName(resultSet.getString("reply_author"));
                            p.setContent(resultSet.getString("content"));
                            p.setReply_content(resultSet.getString("reply_content"));
                            System.out.println(p.String2format());
                        }
                        System.out.println("上面是自己回复的全部帖子");
                        //自己回复的帖子
                    } else if (object.get("command").equals("4.3")) {
                        ResultSet resultSet = null;
                        post post = new post();
                        String name = object.getString("name");
                        //看自己点赞的帖子
                        System.out.println(post.likePost(name));
                        resultSet = excute2(post.likePost(name));
                        post p = new post();
                        while (resultSet.next()) {
                            p.setAuthorId(resultSet.getString("author_id"));
                            p.setPostId(resultSet.getInt("post_id"));
                            p.setDate(resultSet.getDate("post_time"));
                            p.setTitle(resultSet.getString("title"));
                            p.setContent(resultSet.getString("content"));
                            System.out.println(p.String4format());
                        }
                        System.out.println("上面是自己点赞的全部帖子");
                    } else if (object.get("command").equals("4.4")) {
                        ResultSet resultSet = null;
                        post post = new post();
                        String name = object.getString("name");
                        //看自己点赞的帖子
                        System.out.println(post.favoritePost(name));
                        resultSet = excute2(post.favoritePost(name));
                        post p = new post();
                        while (resultSet.next()) {
                            p.setAuthorId(resultSet.getString("author_id"));
                            p.setPostId(resultSet.getInt("post_id"));
                            p.setDate(resultSet.getDate("post_time"));
                            p.setTitle(resultSet.getString("title"));
                            p.setContent(resultSet.getString("content"));
                            System.out.println(p.String4format());
                        }
                        System.out.println("上面是自己收藏的全部帖子");
                    } else if (object.get("command").equals("4.5")) {
                        post post = new post();
                        ResultSet resultSet = null;
                        String name = object.getString("name");
                        //看自己点赞的帖子
                        System.out.println(post.sharePost(name));
                        resultSet = excute2(post.sharePost(name));
                        post p = new post();
                        while (resultSet.next()) {
                            p.setAuthorId(resultSet.getString("author_id"));
                            p.setPostId(resultSet.getInt("post_id"));
                            p.setDate(resultSet.getDate("post_time"));
                            p.setTitle(resultSet.getString("title"));
                            p.setContent(resultSet.getString("content"));
                            System.out.println(p.String4format());
                        }
                        System.out.println("上面是自己分享的全部帖子");
                    } else if (object.get("command").equals("9.1")) {
                        reply1 reply1 = new reply1();
                        excute(reply1.up(Integer.parseInt(object.getString("replyId"))));
                    } else if (object.get("command").equals("9.2")) {
                        reply2 reply2 = new reply2();
                        excute(reply2.up(Integer.parseInt(object.getString("replyId"))));
                    } else if (object.get("command").equals("10")) {
                        author a = new author();
                        excute(a.upDateName(object.getString("newName"),object.getString("name")));
                    } else if (object.get("command").equals("11.1")) {
                        post p = new post();
                        ResultSet resultSet = excute2(p.detectByTime(object.getString("beginTime"), object.getString("endTime") ));
                        while (resultSet.next()) {
                            p.setAuthorId(resultSet.getString("author_id"));
                            p.setPostId(resultSet.getInt("post_id"));
                            p.setDate(resultSet.getDate("post_time"));
                            p.setTitle(resultSet.getString("title"));
                            p.setContent(resultSet.getString("content"));
                            System.out.println(p.String4format());
                        }
                    } else if (object.get("command").equals("11.2")) {
                        post p = new post();
                        ResultSet resultSet = excute2(p.detectByCate(object.getString("cate")));
                        while (resultSet.next()) {
                            p.setAuthorId(resultSet.getString("author_id"));
                            p.setPostId(resultSet.getInt("post_id"));
                            p.setDate(resultSet.getDate("post_time"));
                            p.setTitle(resultSet.getString("title"));
                            p.setContent(resultSet.getString("content"));
                            System.out.println(p.String4format());
                        }
                    } else if (object.get("command").equals("11.3")) {
                        post p = new post();
                        ResultSet resultSet = excute2(p.detectByBoth(object.getString("beginTime"), object.getString("endTime"),object.getString("cate")));
                        while (resultSet.next()) {
                            p.setAuthorId(resultSet.getString("author_id"));
                            p.setPostId(resultSet.getInt("post_id"));
                            p.setDate(resultSet.getDate("post_time"));
                            p.setTitle(resultSet.getString("title"));
                            p.setContent(resultSet.getString("content"));
                            System.out.println(p.String4format());
                        }
                    } else if (object.get("command").equals("5.1")) {
                        String authorId = null;
                        author author = new author();
                        ResultSet resultSet = excute2(author.findIDByName(object.getString("name")));
                        while (resultSet.next()) {
                            authorId = resultSet.getString("author_id");
                        }
                        int postId = 0;
                        post post = new post();
                        resultSet = excute2(post.count());
                        while (resultSet.next()) {
                            postId = resultSet.getInt("count") + 1;
                        }
                        excute(post.insert(postId,authorId,object.getString("title"),object.getString("content")));
                        int postCity = 0;
                        city city = new city();
                        resultSet = excute2(city.count());
                        while (resultSet.next()) {
                            postCity = resultSet.getInt("count") + 1;
                        }
                        excute(city.insert(postCity,postId,object.getString("city")));
                        category category = new category();
                        JSONArray tem = object.getJSONArray("tags");
                        for (int i = 0; i < tem.length(); i++) {
                            int tagsCnt = 0;
                            resultSet = excute2(category.count());
                            while (resultSet.next()) {
                                tagsCnt = resultSet.getInt("count") + 1;
                            }
                            excute(category.insert(tagsCnt,postId,tem.getString(i)));
                        }
                    } else if (object.get("command").equals("5.2")) {
                        String authorId = null;
                        author author = new author();
                        ResultSet resultSet = excute2(author.findIDByName(object.getString("name")));
                        while (resultSet.next()) {
                            authorId = resultSet.getString("author_id");
                        }
                        int postId = 0;
                        post post = new post();
                        secret secret = new secret();
                        resultSet = excute2(post.count());
                        while (resultSet.next()) {
                            postId = resultSet.getInt("count") + 1;
                        }
                        excute(secret.insert(postId,authorId,object.getString("title"),object.getString("content")));
                        int postCity = 0;
                        city city = new city();
                        resultSet = excute2(city.count());
                        while (resultSet.next()) {
                            postCity = resultSet.getInt("count") + 1;
                        }
                        excute(city.insert(postCity,postId,object.getString("city")));
                        category category = new category();
                        JSONArray tem = object.getJSONArray("tags");
                        for (int i = 0; i < tem.length(); i++) {
                            int tagsCnt = 0;
                            resultSet = excute2(category.count());
                            while (resultSet.next()) {
                                tagsCnt = resultSet.getInt("count") + 1;
                            }
                            excute(category.insert(tagsCnt,postId,tem.getString(i)));
                        }
                    } else if (object.get("command").equals("7.1")) {
                        reply1 reply1 = new reply1();
                        int replyId = 0;
                        ResultSet resultSet  = excute2(reply1.count());
                        while (resultSet.next()) {
                            replyId = resultSet.getInt("count") + 1;
                        }
                        excute(reply1.insert(replyId,Integer.parseInt(object.getString("postId")),object.getString("content"), object.getString("name")));
                    } else if (object.get("command").equals("7.2")) {
                        reply2 reply2 = new reply2();
                        int reply2Id = 0;
                        ResultSet resultSet  = excute2(reply2.count());
                        while (resultSet.next()) {
                            reply2Id = resultSet.getInt("count") + 1;
                        }
                        excute(reply2.insert(reply2Id,Integer.parseInt(object.getString("replyId")), object.getString("content"), object.getString("name")));
                    } else if (object.get("command").equals("8.1")) {
                        follow follow = new follow();
                        ResultSet resultSet = excute2(follow.count());;
                        int followId = 0;
                        while (resultSet.next()) {
                            followId = resultSet.getInt("count") + 1;
                        }
                        excute(follow.insert(followId,object.getString("targetId"), object.getString("name")));

                    } else if (object.get("command").equals("8.2")) {
                        follow follow = new follow();
                        ResultSet resultSet = excute2(follow.lookUp(object.getString("name")));
                        String authorId = null;
                        String authorFollowName = null;
                        while (resultSet.next()) {
                            authorId = resultSet.getString("author_id");
                            authorFollowName = resultSet.getString("author_follow_id");
                            System.out.println(authorFollowName + " follows " + authorId);
                        }
                    } else if (object.get("command").equals("8.3")) {
                        follow follow = new follow();
                        excute(follow.delete(object.getString("name"),object.getString("targetId")));
                    } else if (object.get("command").equals("1")) {
                        author author = new author();
                        String name = object.getString("name");
                        ResultSet set = excute2(author.duplicate(name));
                        if (!set.next()) {
                            JSONObject object1 = new JSONObject();
                            author.setAuthorName(object.getString("name"));
                            author.setPhone(object.getString("phone"));
                            System.out.println(author.insert(author.getAuthorName(), author.getPhone()));
                            excute(author.insert(author.getAuthorName(), author.getPhone()));
                            object1.put("command", "成功注册");
                            System.out.println(object1);
                            writer.write(object1.toString() + "\n");
                            writer.flush();

                        } else {
                            JSONObject object1 = new JSONObject();
                            object1.put("command", "有重复名字用户，请改名字");
                            writer.write(object1.toString() + "\n");
                            writer.flush();
                            System.out.println(object1);
                        }
                    } else if (object.get("command").equals("2")) {
                        author author = new author();
                        author.setAuthorName(object.getString("name"));
                        ResultSet resultSet = excute2(author.duplicate(author.getAuthorName()));
                        author.setAuthorName(object.getString("name"));
                        if (!resultSet.next()) {
                            JSONObject object1 = new JSONObject();
                            object1.put("command", "no user");
                            writer.write(object1.toString() + "\n");
                            writer.flush();
                            System.out.println(object1);
                        } else {
                            JSONObject object1 = new JSONObject();
                            object1.put("command", "go go go");
                            writer.write(object1.toString() + "\n");
                            writer.flush();
                            System.out.println(object1);
                        }
                    }
                    // 回复客户发送的消息
//                    writer.write("服务器已收到: " + msg + "\n");
                    //防止消息遗留到本地缓冲区，保证马上发送出去
                    writer.flush();

                    // 查看客户端是否退出
                    if (QUIT.equalsIgnoreCase(msg)) {
                        System.out.println("客户端[" + socket.getInetAddress() + ":" + socket.getPort() + "]已断开连接");
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("resultSet has question!");
            throw new RuntimeException(e);
        } finally {
            try {
                serverSocket.close();
                reader.close();
                writer.close();
                System.out.println("关闭serverSocket");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public static synchronized void excute(String sql) {
        try {
            if (con != null) {
                stmt = con.createStatement();
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized ResultSet excute2(String sql) {
        ResultSet resultSet = null;
        try {
            if (con != null) {
                stmt = con.createStatement();
                resultSet = stmt.executeQuery(sql);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }


}

