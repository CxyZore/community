package com.cxy.community.mapper;

import com.cxy.community.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author 小陈
 * @Date 2020/11/3 23:12
 * @Version 1.0
 * 操作管理 --> 留言管理、推荐管理、评论管理、关注收藏管理
 */
@Mapper
public interface FuncMapper {
// 留言管理
    /**
     * 获取所有留言信息
     * @return 所有留言信息
     */
    List<Message> getAllMessage();

    /**
     * 删除指定留言
     * @param id 要删除的留言id
     */
    void removeMessage(String id);

    /**
     * 留言回复
     * @param id 要回复的留言Id
     * @param content 回复内容
     */
    void reply(@Param("id") String id, @Param("content") String content);
    
// 推荐管理
    /**
     * 获取特别推荐
     * @return 特别推荐内容
     */
    List<Recommend> getSuperRecommend();

    /**
     * 获取文章/话题推荐
     * @param type 类型，文章还是话题
     * @return 文章热度前五
     */
    List<Recommend> getRecommend(String type);


    /**
     * 新增特别推荐
     * @param id 特别推荐（文章/话题)ID
     * @param sort 排序等级
     */
    void addSuperRecommend(@Param("id") String id, @Param("sort") String sort);

    /**
     * 修改推荐排序
     * @param id 修改的对象
     * @param sort 修改后的位次
     * @param type 推荐表类型
     */
    void changeSort(@Param("id") String id, @Param("sort") String sort, @Param("type") String type);

    /**
     * 删除推荐
     * @param id 删除对象Id
     */
    void removeRecommend(String id);

// 评论/关注/收藏管理

    /**
     * 获取所有评论内容
     * @return 获取所有评论内容
     */
    List<Comments> getAllComments();

    void removeComments(@Param("account") String account,@Param("oppositeSide") String oppositeSide);

    /**
     * 获取所有关注信息
     * @return 获取所有关注信息
     */
    List<Attention> getAllAttention();

    void removeAttention(@Param("id") String id,@Param("otherAccount") String otherAccount);

    /**
     * 获取所有收餐
     * @return 获取所有收藏信息
     */
    List<Collect> getAllCollect();

    void removeCollect (@Param("account") String account,@Param("arcticID") String arcticID);





}
