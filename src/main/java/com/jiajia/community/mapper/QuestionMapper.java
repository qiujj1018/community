package com.jiajia.community.mapper;

import com.jiajia.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,tag,creator) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{tag},#{creator})")
    void create(Question question);

    @Select("select * from question limit #{offent},#{size}")
    List<Question> list(@Param("offent") Integer offent, @Param("size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator=#{userId} limit #{offent},#{size}")
    List<Question> listByUserId(@Param("userId") Long userId, @Param("offent") Integer offent, @Param("size") Integer size);

    @Select("select count(1) from question where creator=#{userId}")
    Integer countByUserId(@Param("userId") Long userId);

    @Select("select * from question where id=#{id}")
    Question getById(@Param("id") Long id);

    @Update("update question set title=#{title},description=#{description},gmt_modified=#{gmtModified},tag=#{tag} where id=#{id}")
    Integer update(Question question);

    @Update("update question set view_count=#{viewCount}")
    void updateViewCount(Question updateQuestion);

    @Update("update question set view_count=view_count+#{viewCount} where id=#{id}")
    void inView(Question updateQuestion);

    @Update("update question set comment_count=comment_count+#{commentCount} where id=#{id}")
    int inCommentCount(Question resord);
}