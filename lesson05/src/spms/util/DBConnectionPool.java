package spms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class DBConnectionPool {

    private String url;
    private String username;
    private String password;

    // Connection 객체를 보관할 ArrayList
    private ArrayList<Connection> connList = new ArrayList<>();

    // DB 커넥션 생성에 필요한 값을 매개변수로 받음
    public DBConnectionPool(String driver, String url, String username, String password) throws Exception {
        this.url = url;
        this.username = username;
        this.password = password;

        Class.forName(driver);
    }

    public Connection getConnection() throws Exception {
        if (connList.size() > 0) {
            Connection conn = connList.get(0);
            // DB 커넥션 객체도 일정 시간이 지나면 서버와의 연결이 끊어지기 때문에 유효성 체크 후 반환
            if (conn.isValid(10)) {
                return conn;
            }
        }
        // ArrayList에 보관된 객체가 없다면, DriverManager를 통해 새로 만들어 반환
        return DriverManager.getConnection(url, username, password);
    }

    // Connection 객체를 쓰고 난 다음에 커넥션 풀에 반환
    public void returnConnection(Connection conn) throws Exception {
        connList.add(conn);
    }

    public void closeAll() {
        for (Connection conn : connList) {
            try {
                conn.close();
            } catch (Exception ignored) {}
        }
    }
}
