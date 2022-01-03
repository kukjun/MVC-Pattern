package spms.dao;

import spms.vo.Member;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PsqlMemberDao implements MemberDao{

    // DataSource 를 위한 인스턴스 변수와 세터 메소드 추가
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public List<Member> selectList() throws Exception {
        String query = "SELECT mno, mname, email, cre_date" +
                " from members" +
                " order by mno";
        Connection connection = dataSource.getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            ArrayList<Member> members = new ArrayList<>();

            while (rs.next()) {
                members.add(new Member()
                        .setNo(rs.getInt("mno"))
                        .setName(rs.getString("mname"))
                        .setEmail(rs.getString("email"))
                        .setCreateDate(rs.getDate("cre_date")));
            }
            return members;
        }

    }

    // 회원 등록
    public int insert(Member member) throws Exception {
        String query = "INSERT INTO members (email, pwd, mname, cre_date, mod_date)" +
                " VALUES (?, ?, ?, now(), now())";
        int success;
        Connection connection = dataSource.getConnection();

        try (
                PreparedStatement pstmt = connection.prepareStatement(query)
        ) {
            pstmt.setString(1, member.getEmail());
            pstmt.setString(2, member.getPassword());
            pstmt.setString(3, member.getName());
            success = pstmt.executeUpdate();
        }
        return success;
    }

    // 회원 삭제
    public int delete(int no) throws Exception {
        String query = "DELETE" +
                " FROM members" +
                " WHERE mno=?";
        int success;
        Connection connection = dataSource.getConnection();

        try (
                PreparedStatement pstmt = connection.prepareStatement(query)
        ) {
            pstmt.setInt(1, no);
            success = pstmt.executeUpdate();
        }
        return success;
    }

    // 회원 상세 정보 조회
    public Member selectOne(int no) throws Exception {
        String query = "SELECT mno, email, pwd, mname, cre_date, mod_date" +
                " FROM members" +
                " WHERE mno=?";
        Member returnMember;
        Connection connection = dataSource.getConnection();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, no);
            ResultSet rs = pstmt.executeQuery();
            rs.next();

            returnMember = new Member()
                    .setNo(rs.getInt("mno"))
                    .setName(rs.getString("mname"))
                    .setEmail(rs.getString("email"))
                    .setCreateDate(rs.getDate("cre_date"));
        }
        return returnMember;
    }

    public int update(Member member) throws Exception {
        int success = 0;
        String query = "update members set email=?, mname=?, mod_date=now() where mno=?";
        Connection connection = dataSource.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, member.getEmail());
            ps.setString(2, member.getName());
            ps.setInt(3, member.getNo());
            success = ps.executeUpdate();
        }
        return success;
    }

    public Member exist(String email, String password) throws Exception {
        Member member = null;
        String query = "select mno, mname, cre_date, mod_date from members" +
                " where email=? and pwd=?";
        Connection connection = dataSource.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                member = new Member()
                        .setName(rs.getString("mname"))
                        .setEmail(email)
                        .setNo(rs.getInt("mno"))
                        .setCreateDate(rs.getDate("cre_date"))
                        .setModifiedDate(rs.getDate("mod_date"))
                        .setPassword(password);
            }
        }

        return member;
    }

}
