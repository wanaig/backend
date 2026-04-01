package com.naixue.entity;

import lombok.Data;
import org.springframework.ui.context.Theme;

import java.io.Serializable;
import java.util.List;

@Data
public class GiftCardCategory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String activityIds;
    private List<Theme> themesList;
}
