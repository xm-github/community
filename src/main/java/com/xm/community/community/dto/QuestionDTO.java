package com.xm.community.community.dto;

import com.xm.community.community.model.User;
import lombok.Data;

/**
 * @author XM
 * @version 1.0
 * @data 2020-06-22
 * @description QuestionDTO
 */
@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    /* private Long gmt_create;
     private Long gmt_modified;*/
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer LikeCount;
    private User user;
}
