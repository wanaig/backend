package com.naixue.entity;

import jdk.jfr.Category;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class GiftCard implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String content;
    private String img;
    private Integer showType;
    private String bannerActivityName;
    private Long bannerActivityId;
    private List<Category> categoryList;
}
