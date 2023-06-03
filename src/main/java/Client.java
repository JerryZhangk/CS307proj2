import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class Client {
    public static int index = 999;

    public void test() {
        final String QUIT = "quit";
        final String DEFAULT_SERVER_HOST = "127.0.0.1";
        final int DEFAULT_SERVER_PORT = 8099;
        Socket socket = null;

        BufferedWriter writer = null;
        BufferedReader reader = null;
        BufferedReader consoleReader = null;
        try {
            // 创建socket
            socket = new Socket(DEFAULT_SERVER_HOST, DEFAULT_SERVER_PORT);

            // 创建IO流
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );

            // 等待用户输入信息
            consoleReader = new BufferedReader(new InputStreamReader(System.in));
            Random random = new Random();
            int a = random.nextInt(10000);
            JSONObject object = new JSONObject();
            object.put("command", "1");
            object.put("name", "namwwe" + a);
            object.put("phone", "phonweee" + a);
            writer.write(object.toString() + "\n");
            writer.flush();
            System.out.println(object.toString());
            String msg = reader.readLine();
            System.out.println(msg);
            JSONObject reply = new JSONObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                writer.close(); //关闭之前还会flush一次
                socket.close();
                reader.close();
                consoleReader.close();
                System.out.println("关闭socket");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        String name;
        final String QUIT = "quit";
        final String DEFAULT_SERVER_HOST = "127.0.0.1";
        final int DEFAULT_SERVER_PORT = 8099;
        Socket socket = null;

        BufferedWriter writer = null;
        BufferedReader reader = null;
        BufferedReader consoleReader = null;


        try {
            // 创建socket
            socket = new Socket(DEFAULT_SERVER_HOST, DEFAULT_SERVER_PORT);

            // 创建IO流
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );

            // 等待用户输入信息
            consoleReader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.println("--------------------------------------------------------------\n" +
                        "---            what do you want to do :                    ---\n" +
                        "---                 1. register                            ---\n" +
                        "---                  2. login                              ---\n" +
                        "--------------------------------------------------------------");
                if (consoleReader.readLine().equals("1")) {
                    System.out.println("your name?");
                    JSONObject object = new JSONObject();
                    name = consoleReader.readLine();
                    System.out.println("what is your phone num?");
                    String phone = consoleReader.readLine();
                    object.put("command", "1");
                    object.put("name", name);
                    object.put("phone", phone);
                    writer.write(object.toString() + "\n");
                    writer.flush();
//                    System.out.println(object.toString());
                    String msg = reader.readLine();
                    System.out.println(msg);
                    JSONObject reply = new JSONObject(msg);
                    if (reply.getString("command").equals("成功注册")) {
                        break;
                    }
                    System.out.println("有重复名字用户，请改名字");
                } else {
                    JSONObject object = new JSONObject();
                    System.out.println("your name?");
                    name = consoleReader.readLine();
                    object.put("command", "2");
                    object.put("name", name);
                    writer.write(object.toString() + "\n");
                    writer.flush();
                    String msg = reader.readLine();
                    System.out.println(msg);
                    JSONObject reply = new JSONObject(msg);
                    if (reply.getString("command").equals("go go go")) {
                        break;
                    }
                    System.out.println("please check your name carefully");
                    System.out.println(msg);
                }
            }
            System.out.println("成功进入！");
            while (true) {
                System.out.println(--------------------------------------------------------------
                        ---           you want to check something?                 ---
                        ---                 3. 查看帖子                              ---
                        ---                 4. 看看自己的                            ---
                        ---                 5. 发布帖子                              ---
                        ---                 6. 对于帖子进行操作                       ---
                        ---                 7. 回复                                 ---
                        ---                 8. 对于其他作者                           ---
                        ---                 9. 点赞回复                              ---
                        ---                10. 修改个人信息                           ---
                        ---                11. 搜索帖子                              ---
                        ---                12. 通过文章内容进行搜索                    ---
                        --------------------------------------------------------------);
                String input = consoleReader.readLine();
                if (input.equals("3")) {
                    JSONObject object = new JSONObject();
                    object.put("command", "3");
                    object.put("name", name);
                    writer.write(object.toString() + "\n");
                    writer.flush();
                    System.out.println(object);
                    System.out.println("check them into server");
                } else if (input.equals("11")) {
                    System.out.println("你要搜什么： 1 时间限制  2 目录限制 3 同时限制");
                    String str = consoleReader.readLine();
                    if (str.equals("1")) {
                        System.out.println("开始时间");
                        String begin = consoleReader.readLine();
                        System.out.println("结束时间");
                        String end = consoleReader.readLine();
                        JSONObject object = new JSONObject();
                        object.put("command", "11.1");
                        object.put("beginTime", begin);
                        object.put("endTime", end);
                        writer.write(object.toString() + "\n");
                        writer.flush();
                    }else if (str.equals("2")){
                        System.out.println("你想要搜的目录");
                        String cate = consoleReader.readLine();
                        JSONObject object = new JSONObject();
                        object.put("command", "11.2");
                        object.put("cate", cate);
                        writer.write(object.toString() + "\n");
                        writer.flush();
                    } else if (str.equals("3")) {
                        System.out.println("开始时间");
                        String begin = consoleReader.readLine();
                        System.out.println("结束时间");
                        String end = consoleReader.readLine();
                        System.out.println("你想要搜的目录");
                        String cate = consoleReader.readLine();
                        JSONObject object = new JSONObject();
                        object.put("command", "11.3");
                        object.put("beginTime", begin);
                        object.put("endTime", end);
                        object.put("cate", cate);
                        writer.write(object.toString() + "\n");
                        writer.flush();
                    }

                } else if (input.equals("10")) {
                    System.out.println("your new name?");
                    JSONObject object = new JSONObject();
                    String newName = consoleReader.readLine();
                    object.put("command", "10");
                    object.put("name", name);
                    object.put("newName", newName);
                    writer.write(object.toString() + "\n");
                    writer.flush();
                } else if (input.equals("9")) {
                    JSONObject object = new JSONObject();
                    System.out.println("点赞回复1  还是点赞回复2");
                    String str = consoleReader.readLine();
                    if (str.equals("1")) {
                        object.put("command", "9.1");
                    } else if (str.equals("2")) {
                        object.put("command", "9.2");
                    }
                    System.out.println("你想点赞第几号回复");
                    String id = consoleReader.readLine();
                    object.put("name", name);
                    object.put("replyId", id);
                    writer.write(object.toString() + "\n");
                    writer.flush();
                    System.out.println(object);
                } else if (input.equals("6")) {
                    System.out.println("--------------------------------------------------------------\n" +
                            "---           what do you want to do?                      ---\n" +
                            "---                 1. 点赞                                 ---\n" +
                            "---                 2. 收藏                                 ---\n" +
                            "---                 3. 转发                                 ---\n" +
                            "--------------------------------------------------------------");
                    String command = consoleReader.readLine();
                    System.out.println("please type the index of post: ");
                    String postId = consoleReader.readLine();
                    if (command.equals("1")) {
                        JSONObject object = new JSONObject();
                        object.put("command", "6.1");
                        object.put("name", name);
                        object.put("id", postId);
                        writer.write(object.toString() + "\n");
                        writer.flush();
                        System.out.println(object);
                        System.out.println("成功");
                    } else if (command.equals("2")) {
                        JSONObject object = new JSONObject();
                        object.put("command", "6.2");
                        object.put("name", name);
                        object.put("id", postId);
                        writer.write(object.toString() + "\n");
                        writer.flush();
                        System.out.println(object);
                        System.out.println("成功");
                    } else if (command.equals("3")) {
                        JSONObject object = new JSONObject();
                        object.put("command", "6.3");
                        object.put("name", name);
                        object.put("id", postId);
                        writer.write(object.toString() + "\n");
                        writer.flush();
                        System.out.println(object);
                        System.out.println("成功");
                    }
                } else if (input.equals("4")) {//看看自己的
                    System.out.println("--------------------------------------------------------------\n" +
                            "---          what do you want ?                            ---\n" +
                            "---                 1. 看看自己发布帖子                       ---\n" +
                            "---                 2. 看看自己回复的帖子                     ---\n" +
                            "---                 3. 看看自己点赞的帖子                     ---\n" +
                            "---                 4. 看看自己收藏的帖子                     ---\n" +
                            "---                 5. 看看自己分享的帖子                     ---\n" +
                            "--------------------------------------------------------------");
                    String command = consoleReader.readLine();
                    if (command.equals("1")) {// 自己发布的帖子
                        JSONObject object = new JSONObject();
                        object.put("command", "4.1");
                        object.put("name", name);
                        writer.write(object.toString() + "\n");
                        writer.flush();
                        System.out.println(object);
                    } else if (command.equals("2")) {// 自己回复的帖子
                        JSONObject object = new JSONObject();
                        object.put("command", "4.2");
                        object.put("name", name);
                        writer.write(object.toString() + "\n");
                        writer.flush();
                        System.out.println(object);
                    } else if (command.equals("3")) {
                        JSONObject object = new JSONObject();
                        object.put("command", "4.3");
                        object.put("name", name);
                        writer.write(object.toString() + "\n");
                        writer.flush();
                        System.out.println(object);
                    } else if (command.equals("4")) {
                        JSONObject object = new JSONObject();
                        object.put("command", "4.4");
                        object.put("name", name);
                        writer.write(object.toString() + "\n");
                        writer.flush();
                        System.out.println(object);
                    } else if (command.equals("5")) {
                        JSONObject object = new JSONObject();
                        object.put("command", "4.5");
                        object.put("name", name);
                        writer.write(object.toString() + "\n");
                        writer.flush();
                        System.out.println(object);
                    }
                } else if (input.equals("5")) {
                    JSONObject object = new JSONObject();
                    System.out.println("please write your title:");
                    String title = consoleReader.readLine();
                    System.out.println("please type your thoughts");
                    String content = consoleReader.readLine();
                    System.out.println("please type your city");
                    String city = consoleReader.readLine();
                    System.out.println("please type the tags, 以&作为分割");
                    String tags = consoleReader.readLine();
                    String[] array = tags.split("&");
                    JSONArray jsonArrayTags = new JSONArray();
                    for (int i = 0; i < array.length; i++) {
                        jsonArrayTags.put(array[i]);
                    }
                    System.out.println("what kind of post you want to send? 1. normal  2. anonymous");
                    String res = consoleReader.readLine();
                    if (res.equals("1")) {
                        object.put("command", "5.1");
                    } else {
                        object.put("command", "5.2");
                    }
                    object.put("tags", jsonArrayTags);
                    object.put("city", city);
                    object.put("title", title);
                    object.put("name", name);
                    object.put("content", content);
                    writer.write(object.toString() + "\n");
                    writer.flush();
                    System.out.println(object);
                } else if (input.equals("7")) {
                    System.out.println("you want to reply : 1 post ; 2 reply");
                    String command = consoleReader.readLine();
                    if (command.equals("1")) {
                        //回复帖子
                        JSONObject object = new JSONObject();
                        System.out.println("please type the post id");
                        String postId = consoleReader.readLine();
                        object.put("postId", postId);
                        object.put("name", name);
                        object.put("command", "7.1");
                        System.out.println("what do you want to say?");
                        String content = consoleReader.readLine();
                        object.put("content", content);
                        writer.write(object.toString() + "\n");
                        writer.flush();
                        System.out.println(object);
                    } else if (command.equals("2")) {
                        JSONObject object = new JSONObject();
                        System.out.println("please type the reply id");
                        String postId = consoleReader.readLine();
                        object.put("replyId", postId);
                        object.put("name", name);
                        object.put("command", "7.2");
                        System.out.println("what do you want to say?");
                        String content = consoleReader.readLine();
                        object.put("content", content);
                        writer.write(object.toString() + "\n");
                        writer.flush();
                        System.out.println(object);
                    }
                } else if (input.equals("8")) {
                    System.out.println("what do you want to do? 1. 关注其他作者 2.查看自己关注作者的列表 3:取消对于某个作者的关注");
                    String command = consoleReader.readLine();
                    if (command.equals("1")) {
                        JSONObject object = new JSONObject();
                        System.out.println("type the id of other author");
                        String authorId = consoleReader.readLine();
                        object.put("command", "8.1");
                        object.put("name", name);
                        object.put("targetId", authorId);
                        writer.write(object.toString() + "\n");
                        writer.flush();
                        System.out.println(object);
                    } else if (command.equals("2")) {
                        JSONObject object = new JSONObject();
                        object.put("command", "8.2");
                        object.put("name", name);
                        writer.write(object.toString() + "\n");
                        writer.flush();
                        System.out.println(object);
                    } else if (command.equals("3")) {
                        JSONObject object = new JSONObject();
                        System.out.println("type the id of other author");
                        String authorId = consoleReader.readLine();
                        object.put("command", "8.3");
                        object.put("name", name);
                        object.put("targetId", authorId);
                        writer.write(object.toString() + "\n");
                        writer.flush();
                        System.out.println(object);
                    }
                } else
                    // 发送消息给服务器


                    // 读取服务器返回的消息
//                String msg = reader.readLine();
//                System.out.println(msg);

                    // 查看用户是否退出
                    if (QUIT.equalsIgnoreCase(input)) {
                        break;
                    }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close(); //关闭之前还会flush一次
                socket.close();
                reader.close();
                consoleReader.close();
                System.out.println("关闭socket");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
 