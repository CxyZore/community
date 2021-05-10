package com.cxy.community.mapper;

import com.cxy.community.entity.Content;
import com.cxy.community.entity.Label;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author 小陈
 * @Date 2020/11/3 22:55
 * @Version 1.0
 * 内容管理 --> 文章/话题管理 、 标签管理
 */
@Mapper
public interface ContentMapper {

// 内容管理 --> 文章/话题管理

    /**
     * 获取内容列表
     * @return 内容列表
     */
    List<Content> getAllContent();

    List<Content> getContent(String type,String select);

    /**
     * 修改内容审核通过状态
     * @param state 审核通过状态
     * @param id 修改对象Id
     */
    void changeContent(@Param("state") String state, @Param("id") String id);

    /**
     * 删除内容
     * @param id 内容ID
     */
    void removeContent(String id);

// 标签管理
    /**
     * 获取所有标签
     * @return 所有标签信息
     */
    List<Label> getAllLabel();

    /**
     * 新增标签
     * @param label 新标签
     */
    void addLabel(Label label);

    /**
     * 删除标签
     * @param id 要删除的标签Id
     */
    void removeLabel(String id);

    /**
     * 修改标签
     * @param label 修改后的标签
     */
    void changeLabel(Label label);

    /**
     * 置顶
     * @param id 置顶标签的ID
     */
    void toTop(String id);
}

