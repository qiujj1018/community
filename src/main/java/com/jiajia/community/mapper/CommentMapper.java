package com.jiajia.community.mapper;

import com.jiajia.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment(parent_id,commentator,gmt_create,gmt_modified,content,type) values(#{parentId},#{commentator},#{gmtCreate},#{gmtModified},#{content},#{type})")
    public void insert(Comment comment);

    @Select("select * from comment where parent_id=#{parentId}")
    Comment getById(Long parentId);
}
