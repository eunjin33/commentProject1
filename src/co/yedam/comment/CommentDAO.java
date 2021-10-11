package co.yedam.comment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.yedam.common.DAO;

public class CommentDAO extends DAO {

	private static CommentDAO singleton = new CommentDAO();

	private CommentDAO() {
	}

	public static CommentDAO getInstance() { // CommentDAO라고하는 getInstance()를 만들겠다
		return singleton;
	}

	// 글목록
	// List<Comment>으로 리턴한다
	public List<Comment> getCommentList() {
		// DB연결 connect
		connect();
		List<Comment> list = new ArrayList<>();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from comments order by 1");
			// rs에 값을 담아서 while문 돌면서 목록을 빼는 과정
			// 최종적으로 list에 다 담는다
			while (rs.next()) {
				Comment cmt = new Comment();
				cmt.setId(rs.getString("id"));
				cmt.setContent(rs.getString("content"));
				cmt.setName(rs.getString("name"));
				list.add(cmt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		// 최종적으로 list를 리턴
		return list;
	}

	// 글입력 1)현재id 2)id+1 값으로 글등록 3)현재id값 변경
	public Comment insertComment(Comment comment) {
		connect();
		int currId = 0;
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select value from id_repository where name='COMMENT'");
			//
			if (rs.next()) {
				currId = rs.getInt("value");
			}
			currId++;
			psmt = conn.prepareStatement("insert into comments values(?,?,?)");
			psmt.setInt(1, currId);
			psmt.setString(2, comment.getName());
			psmt.setString(3, comment.getContent());
			int r = psmt.executeUpdate();
			System.out.println(r + "건 입력");

			psmt = conn.prepareStatement("update id_repository set value=? where name='COMMENT'");
			psmt.setInt(1, currId);
			r = psmt.executeUpdate();
			System.out.println(r + "건 변경");

			// 정상적으로 처리되면 comment을 리턴
			conn.commit();
			comment.setId(String.valueOf(currId));
			return comment;

			// 오류가 나면 null을 리턴
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return null;
		} finally {
			disconnect();
		}

	}

	// 글수정
	public Comment updateComment(Comment comment) {
		connect();
		String sql = "update comments set name=?, content=? where id=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, comment.getName());
			psmt.setString(2, comment.getContent());
			psmt.setString(3, comment.getId());
			int r = psmt.executeUpdate();
			System.out.println(r + "건 변경");
			return comment;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			disconnect();
		}
	}

	// 글삭제
	public String deleteComment(String id) {
		connect();
		try {
			psmt = conn.prepareStatement("delete from comments where id=?");
			psmt.setString(1, id);
			int r = psmt.executeUpdate();
			System.out.println(r + "건 삭제");
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			disconnect();
		}
	}
}