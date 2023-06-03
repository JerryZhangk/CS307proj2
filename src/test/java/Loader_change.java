
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import org.json.JSONObject;

public class Loader_change {
    private static final int BATCH_SIZE1 = 10;
    private static final int BATCH_SIZE2 = 10;
    private static Connection con = null;

    private static PreparedStatement stmt_PostCategories = null;
    private static PreparedStatement stmt_author1 = null;
    private static PreparedStatement stmt_author2 = null;
    private static PreparedStatement stmt_author3 = null;
    private static PreparedStatement stmt_PostCity = null;
    private static PreparedStatement stmt_post1 = null;
    private static PreparedStatement stmt_post2 = null;
    private static PreparedStatement stmt_post3 = null;
    private static PreparedStatement stmt_other_author1 = null;
    private static PreparedStatement stmt_other_author2 = null;
    private static PreparedStatement stmt_other_author3 = null;
    private static PreparedStatement stmt_follow = null;
    private static PreparedStatement stmt_favorite = null;
    private static PreparedStatement stmt_share = null;
    private static PreparedStatement stmt_like = null;
    private static PreparedStatement stmt_reply_1 = null;
    private static PreparedStatement stmt_reply_2 = null;

    private static PreparedStatement stmt_Register = null;
    private static long author_id_register = 0;


    private static String reply1 = null;
    private static Integer number_auto_category_first = 1;

    private static final long MIN_TIMESTAMP = 1020952210000L; // "2002-05-10 10:10:10"的时间戳
    private static final long MAX_TIMESTAMP = 1652242210000L; // "2022-05-10 10:10:10"的时间戳

    public static void main(String[] args) throws ParseException {
        load();

    }
    //不用改了
    private static void openDB(Properties prop) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
            System.exit(1);
        }
        String url = "jdbc:postgresql://" + prop.getProperty("host") + "/" +
                prop.getProperty("database");
        try {
            con = DriverManager.getConnection(url, prop);
            if (con != null) {
                System.out.println("Successfully connected to the database "
                        + prop.getProperty("database") + " as " + prop.getProperty("user"));
                con.setAutoCommit(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private static void closeDB() {
        if (con != null) {
            try {
                if (stmt_author1 != null) {
                    stmt_author1.close();
                }
                con.close();
                con = null;
            } catch (Exception ignored) {
            }
        }
    }

    private static Properties loadDBUser() {
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(new FileInputStream("D:\\weixindownloads\\WeChat Files\\wxid_5r2ydhbty61322\\FileStorage\\File\\2023-05\\CSdatabase\\src\\main\\resources\\dbUser.properties")));
            return properties;
        } catch (IOException e) {
            System.err.println("can not find db user file");
            throw new RuntimeException(e);
        }
    }

    private static List<String> loadJsonFile(String string) {
        try {
            return Files.readAllLines(Path.of(string));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //第一张表
    public static void setPostCategories() {
        try {
            stmt_PostCategories = con.prepareStatement("INSERT INTO public.PostCategories (post_id, category) " +
                    "VALUES (?,?);");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
    }

    private static void loadPostCategories(JSONObject myJsonObject) {
        if (con != null) {
            try {
                stmt_PostCategories.setInt(1, myJsonObject.getInt("Post ID"));
                for (int i = 0; i < myJsonObject.getJSONArray("Category").length(); i++) {
                    stmt_PostCategories.setString(2, myJsonObject.getJSONArray("Category").get(i).toString());
                    stmt_PostCategories.executeUpdate();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    //第二张表
    public static void setPrepareAuthor() {
        try {
            stmt_author1 = con.prepareStatement("INSERT INTO author1_5 (author_id, author_name, registration_time, phone) " +
                    "VALUES (?,?,?,?);");
            stmt_author2 = con.prepareStatement("INSERT INTO author5_10 (author_id, author_name, registration_time, phone) " +
                    "VALUES (?,?,?,?);");
            stmt_author3 = con.prepareStatement("INSERT INTO author10_15 (author_id, author_name, registration_time, phone) " +
                    "VALUES (?,?,?,?);");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
    }

    private static void loadAuthor1(JSONObject myJsonObject) {
        if (con != null) {
            try {
                stmt_author1.setString(1, myJsonObject.getString("Author's ID"));
                stmt_author1.setString(2, myJsonObject.getString("Author"));
                stmt_author1.setTimestamp(3, Timestamp.valueOf((myJsonObject.getString("Author Registration Time"))));
                stmt_author1.setString(4, myJsonObject.getString("Author's Phone"));
                stmt_author1.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private static void loadAuthor2(JSONObject myJsonObject) {
        if (con != null) {
            try {
                stmt_author2.setString(1, myJsonObject.getString("Author's ID"));
                stmt_author2.setString(2, myJsonObject.getString("Author"));
                stmt_author2.setTimestamp(3, Timestamp.valueOf((myJsonObject.getString("Author Registration Time"))));
                stmt_author2.setString(4, myJsonObject.getString("Author's Phone"));
                stmt_author2.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private static void loadAuthor3(JSONObject myJsonObject) {
        if (con != null) {
            try {
                stmt_author3.setString(1, myJsonObject.getString("Author's ID"));
                stmt_author3.setString(2, myJsonObject.getString("Author"));
                stmt_author3.setTimestamp(3, Timestamp.valueOf((myJsonObject.getString("Author Registration Time"))));
                stmt_author3.setString(4, myJsonObject.getString("Author's Phone"));
                stmt_author3.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    //第三张表
    public static void setPostCity() {
        try {
            stmt_PostCity = con.prepareStatement("INSERT INTO public.PostCity (post_id,city) " +
                    "VALUES (?,?);");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
    }

    private static void loadPostCity(JSONObject myJsonObject) {
        if (con != null) {
            try {
                stmt_PostCity.setInt(1, myJsonObject.getInt("Post ID"));
                String[] lineData = myJsonObject.getString("Posting City").split(", ");
                for (int i = 0; i < lineData.length; i++) {
                    stmt_PostCity.setString(2, lineData[i]);
                    stmt_PostCity.executeUpdate();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    //第四张表
    public static void setPost() {
        try {
            stmt_post1 = con.prepareStatement("INSERT INTO public.post1 (post_id,author_id,post_time,title,content) " +
                    "VALUES (?,?,?,?,?);");
            stmt_post2 = con.prepareStatement("INSERT INTO public.post2 (post_id,author_id,post_time,title,content) " +
                    "VALUES (?,?,?,?,?);");
            stmt_post3 = con.prepareStatement("INSERT INTO public.post3 (post_id,author_id,post_time,title,content) " +
                    "VALUES (?,?,?,?,?);");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
    }

    private static void loadPost1(JSONObject myJsonObject) {
        if (con != null) {
            try {
                stmt_post1.setInt(1, myJsonObject.getInt("Post ID"));
                stmt_post1.setString(2, myJsonObject.getString("Author's ID"));
                stmt_post1.setTimestamp(3, Timestamp.valueOf((myJsonObject.getString("Posting Time"))));
                stmt_post1.setString(4, myJsonObject.getString("Title"));
                stmt_post1.setString(5, myJsonObject.getString("Content"));
                stmt_post1.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private static void loadPost2(JSONObject myJsonObject) {
        if (con != null) {
            try {
                stmt_post2.setInt(1, myJsonObject.getInt("Post ID"));
                stmt_post2.setString(2, myJsonObject.getString("Author's ID"));
                stmt_post2.setTimestamp(3, Timestamp.valueOf((myJsonObject.getString("Posting Time"))));
                stmt_post2.setString(4, myJsonObject.getString("Title"));
                stmt_post2.setString(5, myJsonObject.getString("Content"));
                stmt_post2.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private static void loadPost3(JSONObject myJsonObject) {
        if (con != null) {
            try {
                stmt_post3.setInt(1, myJsonObject.getInt("Post ID"));
                stmt_post3.setString(2, myJsonObject.getString("Author's ID"));
                stmt_post3.setTimestamp(3, Timestamp.valueOf((myJsonObject.getString("Posting Time"))));
                stmt_post3.setString(4, myJsonObject.getString("Title"));
                stmt_post3.setString(5, myJsonObject.getString("Content"));
                stmt_post3.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    //更新第二张表
    public static void resetPrepareAuthor() {
        try {
            stmt_other_author1 = con.prepareStatement("INSERT INTO public.author1_5 (author_id, author_name,registration_time) " +
                    "VALUES (?,?,?);");
            stmt_other_author2 = con.prepareStatement("INSERT INTO public.author5_10 (author_id, author_name,registration_time) " +
                    "VALUES (?,?,?);");
            stmt_other_author3 = con.prepareStatement("INSERT INTO public.author10_15 (author_id, author_name,registration_time) " +
                    "VALUES (?,?,?);");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
    }

    public static String generateRandomTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Random random = new Random();
        long offset = MIN_TIMESTAMP + (long) (random.nextDouble() * (MAX_TIMESTAMP - MIN_TIMESTAMP)); // 随机生成一个在时间范围内的时间偏移量
        String randomTimestamp = dateFormat.format(new Date(offset)); // 将时间戳格式化为字符串
        return randomTimestamp;
    }


    private static void reloadAuthor_follow1(JSONObject myJsonObject) {
        if (con != null) {
            try {
                for (int i = 0; i < myJsonObject.getJSONArray("Authors Followed By").length(); i++) {
                    stmt_other_author1.setString(1, myJsonObject.getString("Author's ID") + "_" + myJsonObject.getJSONArray("Authors Followed By").get(i).toString() + "_" + "1");
                    stmt_other_author1.setString(2, myJsonObject.getJSONArray("Authors Followed By").get(i).toString());
                    String randomTimestamp = generateRandomTimestamp();
                    stmt_other_author1.setTimestamp(3, Timestamp.valueOf(randomTimestamp));
                    stmt_other_author1.executeUpdate();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private static void reloadAuthor_follow2(JSONObject myJsonObject) {
        if (con != null) {
            try {
                for (int i = 0; i < myJsonObject.getJSONArray("Authors Followed By").length(); i++) {
                    stmt_other_author2.setString(1, myJsonObject.getString("Author's ID") + "_" + myJsonObject.getJSONArray("Authors Followed By").get(i).toString() + "_" + "1");
                    stmt_other_author2.setString(2, myJsonObject.getJSONArray("Authors Followed By").get(i).toString());
                    String randomTimestamp = generateRandomTimestamp();
                    stmt_other_author2.setTimestamp(3, Timestamp.valueOf(randomTimestamp));
                    stmt_other_author2.executeUpdate();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private static void reloadAuthor_follow3(JSONObject myJsonObject) {
        if (con != null) {
            try {
                for (int i = 0; i < myJsonObject.getJSONArray("Authors Followed By").length(); i++) {
                    stmt_other_author3.setString(1, myJsonObject.getString("Author's ID") + "_" + myJsonObject.getJSONArray("Authors Followed By").get(i).toString() + "_" + "1");
                    stmt_other_author3.setString(2, myJsonObject.getJSONArray("Authors Followed By").get(i).toString());
                    String randomTimestamp = generateRandomTimestamp();
                    stmt_other_author3.setTimestamp(3, Timestamp.valueOf(randomTimestamp));
                    stmt_other_author3.executeUpdate();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private static void reloadAuthor_favorite1(JSONObject myJsonObject) {
        if (con != null) {
            try {
                for (int i = 0; i < myJsonObject.getJSONArray("Authors Who Favorited the Post").length(); i++) {
                    stmt_other_author1.setString(1, myJsonObject.getString("Author's ID") + "_" + myJsonObject.getJSONArray("Authors Who Favorited the Post").get(i).toString() + "_" + "2");
                    stmt_other_author1.setString(2, myJsonObject.getJSONArray("Authors Who Favorited the Post").get(i).toString());
                    String randomTimestamp = generateRandomTimestamp();
                    stmt_other_author1.setTimestamp(3, Timestamp.valueOf(randomTimestamp));
                    stmt_other_author1.executeUpdate();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private static void reloadAuthor_favorite2(JSONObject myJsonObject) {
        if (con != null) {
            try {
                for (int i = 0; i < myJsonObject.getJSONArray("Authors Who Favorited the Post").length(); i++) {
                    stmt_other_author2.setString(1, myJsonObject.getString("Author's ID") + "_" + myJsonObject.getJSONArray("Authors Who Favorited the Post").get(i).toString() + "_" + "2");
                    stmt_other_author2.setString(2, myJsonObject.getJSONArray("Authors Who Favorited the Post").get(i).toString());
                    String randomTimestamp = generateRandomTimestamp();
                    stmt_other_author2.setTimestamp(3, Timestamp.valueOf(randomTimestamp));
                    stmt_other_author2.executeUpdate();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private static void reloadAuthor_favorite3(JSONObject myJsonObject) {
        if (con != null) {
            try {
                for (int i = 0; i < myJsonObject.getJSONArray("Authors Who Favorited the Post").length(); i++) {
                    stmt_other_author3.setString(1, myJsonObject.getString("Author's ID") + "_" + myJsonObject.getJSONArray("Authors Who Favorited the Post").get(i).toString() + "_" + "2");
                    stmt_other_author3.setString(2, myJsonObject.getJSONArray("Authors Who Favorited the Post").get(i).toString());
                    String randomTimestamp = generateRandomTimestamp();
                    stmt_other_author3.setTimestamp(3, Timestamp.valueOf(randomTimestamp));
                    stmt_other_author3.executeUpdate();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private static void reloadAuthor_share1(JSONObject myJsonObject) {
        if (con != null) {
            try {
                for (int i = 0; i < myJsonObject.getJSONArray("Authors Who Shared the Post").length(); i++) {
                    stmt_other_author1.setString(1, myJsonObject.getString("Author's ID") + "_" + myJsonObject.getJSONArray("Authors Who Shared the Post").get(i).toString() + "_" + "3");
                    stmt_other_author1.setString(2, myJsonObject.getJSONArray("Authors Who Shared the Post").get(i).toString());
                    String randomTimestamp = generateRandomTimestamp();
                    stmt_other_author1.setTimestamp(3, Timestamp.valueOf(randomTimestamp));
                    stmt_other_author1.executeUpdate();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private static void reloadAuthor_share2(JSONObject myJsonObject) {
        if (con != null) {
            try {
                for (int i = 0; i < myJsonObject.getJSONArray("Authors Who Shared the Post").length(); i++) {
                    stmt_other_author2.setString(1, myJsonObject.getString("Author's ID") + "_" + myJsonObject.getJSONArray("Authors Who Shared the Post").get(i).toString() + "_" + "3");
                    stmt_other_author2.setString(2, myJsonObject.getJSONArray("Authors Who Shared the Post").get(i).toString());
                    String randomTimestamp = generateRandomTimestamp();
                    stmt_other_author2.setTimestamp(3, Timestamp.valueOf(randomTimestamp));
                    stmt_other_author2.executeUpdate();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private static void reloadAuthor_share3(JSONObject myJsonObject) {
        if (con != null) {
            try {
                for (int i = 0; i < myJsonObject.getJSONArray("Authors Who Shared the Post").length(); i++) {
                    stmt_other_author3.setString(1, myJsonObject.getString("Author's ID") + "_" + myJsonObject.getJSONArray("Authors Who Shared the Post").get(i).toString() + "_" + "3");
                    stmt_other_author3.setString(2, myJsonObject.getJSONArray("Authors Who Shared the Post").get(i).toString());
                    String randomTimestamp = generateRandomTimestamp();
                    stmt_other_author3.setTimestamp(3, Timestamp.valueOf(randomTimestamp));
                    stmt_other_author3.executeUpdate();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    private static void reloadAuthor_like1(JSONObject myJsonObject) {
        if (con != null) {
            try {
                for (int i = 0; i < myJsonObject.getJSONArray("Authors Who Liked the Post").length(); i++) {
                    stmt_other_author1.setString(1, myJsonObject.getString("Author's ID") + "_" + myJsonObject.getJSONArray("Authors Who Liked the Post").get(i).toString() + "_" + "4");
                    stmt_other_author1.setString(2, myJsonObject.getJSONArray("Authors Who Liked the Post").get(i).toString());
                    String randomTimestamp = generateRandomTimestamp();
                    stmt_other_author1.setTimestamp(3, Timestamp.valueOf(randomTimestamp));
                    stmt_other_author1.executeUpdate();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private static void reloadAuthor_like2(JSONObject myJsonObject) {
        if (con != null) {
            try {
                for (int i = 0; i < myJsonObject.getJSONArray("Authors Who Liked the Post").length(); i++) {
                    stmt_other_author2.setString(1, myJsonObject.getString("Author's ID") + "_" + myJsonObject.getJSONArray("Authors Who Liked the Post").get(i).toString() + "_" + "4");
                    stmt_other_author2.setString(2, myJsonObject.getJSONArray("Authors Who Liked the Post").get(i).toString());
                    String randomTimestamp = generateRandomTimestamp();
                    stmt_other_author2.setTimestamp(3, Timestamp.valueOf(randomTimestamp));
                    stmt_other_author2.executeUpdate();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private static void reloadAuthor_like3(JSONObject myJsonObject) {
        if (con != null) {
            try {
                for (int i = 0; i < myJsonObject.getJSONArray("Authors Who Liked the Post").length(); i++) {
                    stmt_other_author3.setString(1, myJsonObject.getString("Author's ID") + "_" + myJsonObject.getJSONArray("Authors Who Liked the Post").get(i).toString() + "_" + "4");
                    stmt_other_author3.setString(2, myJsonObject.getJSONArray("Authors Who Liked the Post").get(i).toString());
                    String randomTimestamp = generateRandomTimestamp();
                    stmt_other_author3.setTimestamp(3, Timestamp.valueOf(randomTimestamp));
                    stmt_other_author3.executeUpdate();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    //第五张表
    public static void setFollow() {
        try {
            stmt_follow = con.prepareStatement("INSERT INTO public.follow_name (author_id, author_follow_id) " +
                    "VALUES (?,?);");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
    }

    private static void loadFollow(JSONObject myJsonObject) {
        if (con != null) {
            try {
                stmt_follow.setString(1, myJsonObject.getString("Author's ID"));
                for (int i = 0; i < myJsonObject.getJSONArray("Authors Followed By").length(); i++) {
                    stmt_follow.setString(2, myJsonObject.getJSONArray("Authors Followed By").get(i).toString());
                    stmt_follow.executeUpdate();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    //第六张表
    public static void setFavorite() {
        try {
            stmt_favorite = con.prepareStatement("INSERT INTO public.favorite_name (post_id, author_favorite_id) " +
                    "VALUES (?,?);");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
    }

    private static void loadFavorite(JSONObject myJsonObject) {
        if (con != null) {
            try {
                stmt_favorite.setInt(1, myJsonObject.getInt("Post ID"));
                for (int i = 0; i < myJsonObject.getJSONArray("Authors Who Favorited the Post").length(); i++) {
                    stmt_favorite.setString(2, myJsonObject.getJSONArray("Authors Who Favorited the Post").get(i).toString());
                    stmt_favorite.executeUpdate();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    //第七张表
    public static void setShare() {
        try {
            stmt_share = con.prepareStatement("INSERT INTO public.share_name (post_id, author_share_id) " +
                    "VALUES (?,?);");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
    }

    private static void loadShare(JSONObject myJsonObject) {
        if (con != null) {
            try {
                stmt_share.setInt(1, myJsonObject.getInt("Post ID"));
                for (int i = 0; i < myJsonObject.getJSONArray("Authors Who Shared the Post").length(); i++) {
                    stmt_share.setString(2, myJsonObject.getJSONArray("Authors Who Shared the Post").get(i).toString());
                    stmt_share.executeUpdate();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    //第八张表
    public static void setLike() {
        try {
            stmt_like = con.prepareStatement("INSERT INTO public.like_name (post_id, author_like_id) " +
                    "VALUES (?,?);");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
    }

    private static void loadLike(JSONObject myJsonObject) {
        if (con != null) {
            try {
                stmt_like.setInt(1, myJsonObject.getInt("Post ID"));
                for (int i = 0; i < myJsonObject.getJSONArray("Authors Who Liked the Post").length(); i++) {
                    stmt_like.setString(2, myJsonObject.getJSONArray("Authors Who Liked the Post").get(i).toString());
                    stmt_like.executeUpdate();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    //第九张表
    public static void setReply1() {
        try {
            stmt_reply_1 = con.prepareStatement("INSERT INTO public.reply1 (post_id,reply_content,reply_star,reply_author) " +
                    "VALUES (?,?,?,?);");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
    }

    private static void loadReply1(JSONObject myJsonObject) {
        if (con != null) {
            try {
                stmt_reply_1.setInt(1, myJsonObject.getInt("Post ID"));
                stmt_reply_1.setString(2, myJsonObject.getString("Reply Content"));
                stmt_reply_1.setInt(3, myJsonObject.getInt("Reply Stars"));
                stmt_reply_1.setString(4, myJsonObject.getString("Reply Author"));
                if (!Objects.equals(reply1, myJsonObject.getString("Reply Content"))) {
                    stmt_reply_1.executeUpdate();
                }
                reply1 = myJsonObject.getString("Reply Content");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    //第十张表
    public static void setReply2() {
        try {
            stmt_reply_2 = con.prepareStatement("INSERT INTO public.reply2 (auto_first,reply_content,reply_star,reply_author) " +
                    "VALUES (?,?,?,?);");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
    }

    private static void loadReply2(JSONObject myJsonObject) {
        if (con != null) {
            try {
                if (!Objects.equals(reply1, myJsonObject.getString("Reply Content"))) {
                    stmt_reply_2.setInt(1, number_auto_category_first);
                    number_auto_category_first++;
                }
                reply1 = myJsonObject.getString("Reply Content");
                stmt_reply_2.setString(2, myJsonObject.getString("Secondary Reply Content"));
                stmt_reply_2.setInt(3, myJsonObject.getInt("Secondary Reply Stars"));
                stmt_reply_2.setString(4, myJsonObject.getString("Secondary Reply Author"));
                stmt_reply_2.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    //全局影响方法
    public static void drop() {
        Statement stmt0;
        if (con != null) {
            try {
                stmt0 = con.createStatement();
                stmt0.executeUpdate("drop table if exists postcategories;\n" +
                        "                        drop table if exists PostCity;\n" +
                        "                        drop table if exists follow_name;\n" +
                        "                        drop table if exists favorite_name;\n" +
                        "                        drop table if exists share_name;\n" +
                        "                        drop table if exists like_name;\n" +
                        "                        drop table if exists reply2;\n" +
                        "                        drop table if exists reply1;\n" +
                        "                        drop table if exists post1;\n" +
                        "                        drop table if exists post2;\n" +
                        "                        drop table if exists post3;\n" +
                        "                        drop table if exists author1_5;\n" +
                        "                        drop table if exists author5_10;\n" +
                        "                        drop table if exists author10_15;\n");
                con.commit();
                stmt0.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static void resetAllTable() {
        Statement stmt0;
        if (con != null) {
            try {
                stmt0 = con.createStatement();
                stmt0.executeUpdate("create table if not exists author1_5\n" +
                        "(\n" +
                        "    author_id        varchar  not null primary key,\n" +
                        "    author_name       varchar,\n" +
                        "    registration_time date,\n" +
                        "    phone            varchar unique\n" +
                        ");\n" +
                        "\n" +
                        "create table if not exists author5_10\n" +
                        "(\n" +
                        "    author_id        varchar  not null primary key,\n" +
                        "    author_name       varchar,\n" +
                        "    registration_time date,\n" +
                        "    phone            varchar unique\n" +
                        ");\n" +
                        "\n" +
                        "create table if not exists author10_15\n" +
                        "(\n" +
                        "    author_id        varchar  not null primary key,\n" +
                        "    author_name       varchar,\n" +
                        "    registration_time date,\n" +
                        "    phone            varchar unique\n" +
                        ");\n" +
                        "\n" +
                        "create table if not exists post1\n" +
                        "(\n" +
                        "    post_id   int not null primary key,\n" +
                        "    author_id varchar ,\n" +
                        "    post_time date,\n" +
                        "    title    varchar,\n" +
                        "    content  text\n" +
                        ");\n" +
                        "\n" +
                        "create table if not exists post2\n" +
                        "(\n" +
                        "    post_id   int not null primary key,\n" +
                        "    author_id varchar ,\n" +
                        "    post_time date,\n" +
                        "    title    varchar,\n" +
                        "    content  text\n" +
                        ");\n" +
                        "\n" +
                        "create table if not exists post3\n" +
                        "(\n" +
                        "    post_id   int not null primary key,\n" +
                        "    author_id varchar ,\n" +
                        "    post_time date,\n" +
                        "    title    varchar,\n" +
                        "    content  text\n" +
                        ");\n" +
                        "create table if not exists PostCategories\n" +
                        "    (\n" +
                        "        auto_category serial not null,\n" +
                        "        post_id   int ,\n" +
                        "        category varchar,\n" +
                        "        primary key (auto_category)\n" +
                        ");\n" +
                        "alter sequence postcategories_auto_category_seq restart with 1;\n" +
                        "\n" +
                        "\n" +
                        "create table if not exists PostCity\n" +
                        "    (\n" +
                        "        auto_PostCity serial not null,\n" +
                        "        post_id   int ,\n" +
                        "        city varchar,\n" +
                        "        primary key (auto_PostCity)\n" +
                        ");\n" +
                        "alter sequence postcity_auto_PostCity_seq restart with 10000;\n" +
                        "\n" +
                        "-- todo\n" +
                        "create table if not exists follow_name\n" +
                        "    (\n" +
                        "        auto_follow serial not null,\n" +
                        "        author_id   varchar ,\n" +
                        "        author_follow_id varchar,\n" +
                        "        primary key (auto_follow)\n" +
                        ");\n" +
                        "alter sequence follow_name_auto_follow_seq restart with 20000;\n" +
                        "\n" +
                        "create table if not exists favorite_name\n" +
                        "    (\n" +
                        "        auto_favorite serial not null,\n" +
                        "        post_id   int ,\n" +
                        "        author_favorite_id varchar,\n" +
                        "        primary key (auto_favorite)\n" +
                        ");\n" +
                        "alter sequence favorite_name_auto_favorite_seq restart with 1;\n" +
                        "\n" +
                        "create table if not exists share_name\n" +
                        "    (\n" +
                        "        auto_share serial not null,\n" +
                        "        post_id   int  ,\n" +
                        "        author_share_id varchar,\n" +
                        "        primary key (auto_share)\n" +
                        ");\n" +
                        "alter sequence share_name_auto_share_seq restart with 1;\n" +
                        "\n" +
                        "create table if not exists like_name\n" +
                        "    (\n" +
                        "        auto_liked serial not null,\n" +
                        "        post_id   int ,\n" +
                        "        author_like_id varchar,\n" +
                        "        primary key (auto_liked)\n" +
                        ");\n" +
                        "alter sequence like_name_auto_liked_seq restart with 1;\n" +
                        "\n" +
                        "create table if not exists reply1\n" +
                        "    (\n" +
                        "        auto serial not null,\n" +
                        "        post_id   int,\n" +
                        "        reply_content varchar,\n" +
                        "        reply_star int,\n" +
                        "        reply_author varchar,\n" +
                        "        primary key (auto)\n" +
                        ");\n" +
                        "alter sequence reply1_auto_seq restart with 1;\n" +
                        "\n" +
                        "create table if not exists reply2\n" +
                        "    (\n" +
                        "        auto serial not null,\n" +
                        "        auto_first int,\n" +
                        "        reply_content varchar,\n" +
                        "        reply_star int,\n" +
                        "        reply_author varchar,\n" +
                        "        primary key (auto)\n" +
                        "    );\n" +
                        "alter sequence reply2_auto_seq restart with 1;"
                );
                con.commit();
                stmt0.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static void setPreAllTable() {
        setPostCategories();
        setPrepareAuthor();
        resetPrepareAuthor();
        setPostCity();
        setPost();
        setFollow();
        setFavorite();
        setShare();
        setLike();
        setReply1();
        setReply2();
    }

    public static int function(int cnt, List<String> lines, PreparedStatement stmt, int BATCH_SIZE) {
        try {
            int line_number = 1;
            while ((lines.get(line_number).length() >= 4) && (lines.get(line_number).charAt(4) == '{')) {
                StringBuilder temp = new StringBuilder();
                temp.append(lines.get(line_number).charAt(4));
                line_number++;
                while (lines.get(line_number).charAt(4) != '}') {
                    temp.append(lines.get(line_number));
                    line_number++;
                }
                temp.append(lines.get(line_number).charAt(4));
                line_number++;
                JSONObject myJsonObject = new JSONObject(temp.toString());
                if (stmt == stmt_PostCategories) {
                    loadPostCategories(myJsonObject);
                } else if (stmt == stmt_author1 && myJsonObject.getString("Author").length()<5) {
                    loadAuthor1(myJsonObject);
                }else if (stmt == stmt_author2 && myJsonObject.getString("Author").length() >= 5 & myJsonObject.getString("Author").length() <10) {
                    loadAuthor2(myJsonObject);
                }else if (stmt == stmt_author3 && myJsonObject.getString("Author").length() >= 10) {
                    loadAuthor3(myJsonObject);
                } else if (stmt == stmt_PostCity) {
                    loadPostCity(myJsonObject);
                } else if (stmt == stmt_post1 && Timestamp.valueOf((myJsonObject.getString("Posting Time"))).toLocalDateTime().toLocalDate().isBefore(LocalDate.of(2010,1,1))) {
                    loadPost1(myJsonObject);
                }else if (stmt == stmt_post2 && Timestamp.valueOf((myJsonObject.getString("Posting Time"))).toLocalDateTime().toLocalDate().isAfter(LocalDate.of(2010,1,1)) && Timestamp.valueOf((myJsonObject.getString("Posting Time"))).toLocalDateTime().toLocalDate().isBefore(LocalDate.of(2020,1,1))) {
                    loadPost2(myJsonObject);
                }else if (stmt == stmt_post3 && Timestamp.valueOf((myJsonObject.getString("Posting Time"))).toLocalDateTime().toLocalDate().isAfter(LocalDate.of(2020,1,1))) {
                    loadPost3(myJsonObject);
                } else if (stmt == stmt_other_author1 && myJsonObject.getString("Author").length()<5) {
                    reloadAuthor_follow1(myJsonObject);
                    reloadAuthor_favorite1(myJsonObject);
                    reloadAuthor_share1(myJsonObject);
                    reloadAuthor_like1(myJsonObject);
                }else if (stmt == stmt_other_author2 && myJsonObject.getString("Author").length() >= 5 & myJsonObject.getString("Author").length() <10) {
                    reloadAuthor_follow2(myJsonObject);
                    reloadAuthor_favorite2(myJsonObject);
                    reloadAuthor_share2(myJsonObject);
                    reloadAuthor_like2(myJsonObject);
                }else if (stmt == stmt_other_author3 && myJsonObject.getString("Author").length() >= 10) {
                    reloadAuthor_follow3(myJsonObject);
                    reloadAuthor_favorite3(myJsonObject);
                    reloadAuthor_share3(myJsonObject);
                    reloadAuthor_like3(myJsonObject);
                } else if (stmt == stmt_follow) {
                    loadFollow(myJsonObject);
                } else if (stmt == stmt_favorite) {
                    loadFavorite(myJsonObject);
                } else if (stmt == stmt_share) {
                    loadShare(myJsonObject);
                } else if (stmt == stmt_like) {
                    loadLike(myJsonObject);
                } else if (stmt == stmt_reply_1) {
                    loadReply1(myJsonObject);
                } else if (stmt == stmt_reply_2) {
                    loadReply2(myJsonObject);
                }
                if ((cnt % BATCH_SIZE == 0) & (cnt != 0)) {
                    stmt.executeBatch();
//                    System.out.println("insert " + BATCH_SIZE + " data successfully!");
                    stmt.clearBatch();
                }
                cnt++;
                if (line_number >= lines.size()) {
                    break;
                }
            }
            if (cnt % BATCH_SIZE != 0) {
                stmt.executeBatch();
//                System.out.println("insert " + cnt % BATCH_SIZE + " data successfully!");
            }
            con.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cnt;
    }

    public static void load(){
        Properties prop = loadDBUser();
        String json1 = "D:\\json文件\\posts.json";
        String json2 = "D:\\json文件\\replies.json";
        List<String> lines1 = loadJsonFile(json1);
        List<String> lines2 = loadJsonFile(json2);
        int cnt = 0;
        long start = System.currentTimeMillis();
        // Empty target table
        openDB(prop);
        drop();
        resetAllTable();
        closeDB();
        //开始记录时间
        openDB(prop);
        setPreAllTable();
        //StringBuilder拼接，线程不安全，速度最快（10000条记录大概0毫秒）,可以在论文中对比

        cnt = function(cnt, lines1, stmt_author1, BATCH_SIZE1);
        cnt = function(cnt, lines1, stmt_author2, BATCH_SIZE1);
        cnt = function(cnt, lines1, stmt_author3, BATCH_SIZE1);
        cnt = function(cnt, lines1, stmt_post1, BATCH_SIZE1);
        cnt = function(cnt, lines1, stmt_post2, BATCH_SIZE1);
        cnt = function(cnt, lines1, stmt_post3, BATCH_SIZE1);
        cnt = function(cnt, lines1, stmt_PostCategories, BATCH_SIZE1);
        cnt = function(cnt, lines1, stmt_PostCity, BATCH_SIZE1);
        cnt = function(cnt, lines1, stmt_other_author1, BATCH_SIZE1);
        cnt = function(cnt, lines1, stmt_other_author2, BATCH_SIZE1);
        cnt = function(cnt, lines1, stmt_other_author3, BATCH_SIZE1);
        cnt = function(cnt, lines1, stmt_follow, BATCH_SIZE1);
        cnt = function(cnt, lines1, stmt_favorite, BATCH_SIZE1);
        cnt = function(cnt, lines1, stmt_share, BATCH_SIZE1);
        cnt = function(cnt, lines1, stmt_like, BATCH_SIZE1);
        cnt = function(cnt, lines2, stmt_reply_1, BATCH_SIZE2);
        cnt = function(cnt, lines2, stmt_reply_2, BATCH_SIZE2);
        closeDB();
        long end = System.currentTimeMillis();
        System.out.println(cnt + " records successfully loaded");
        System.out.println("Loading speed : " + (cnt * 1000L) / (end - start) + " records/s");
        System.out.println("Loading time : " + (end - start) * 1.00 / (1000L) + "s");

    }


}


