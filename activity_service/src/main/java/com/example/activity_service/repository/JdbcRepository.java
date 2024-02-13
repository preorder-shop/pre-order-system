package com.example.activity_service.repository;

import com.example.activity_service.dto.response.NewsFeedDto;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class JdbcRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 뉴스피드에서 조건별 게시글 조회 API
     */
    public List<NewsFeedDto> getPostListByCondition(String userId, String type){ // 인기순으로 정렬함.
        String sql="";
        if(type.equals("all")){ // 모든 게시글
            sql = "select p.id, p.title, p.content , p.userId from Post p "
                    + "left join (select postId ,count(*) as likeCount from LikePost) lp on lp.postId=p.id "
                    + "order by lp.likeCount DESC";
            return jdbcTemplate.query(sql,itemRowMapper());
        }
        if(type.equals("follow")){// 내가 팔로우한 사용자만
            sql = "select p.id, p.title, p.content , p.userId from Post p "
                    + "left join (select postId ,count(*) as likeCount from LikePost) lp on lp.postId=p.id "
                    + "left join (select toUserId, fromUserId from Follow) f on f.fromUserId=? "
                    + "where p.userId=f.toUserId "
                    + "order by lp.likeCount DESC";

            return jdbcTemplate.query(sql,itemRowMapper(),userId);
        }
        return null;

    }

    private RowMapper<NewsFeedDto> itemRowMapper() { // table 에서 가져온 정보를 객체에 mapping
        return ((rs, rowNum) -> {
            NewsFeedDto item = new NewsFeedDto();
            item.setId(rs.getLong("id"));
            item.setWriter(rs.getString("userId"));
            item.setTitle(rs.getString("title"));
            item.setContent(rs.getString("content"));
            return item;
        });
    }
}
