package com.zl.sys.common;

import cn.hutool.poi.excel.editors.TrimEditor;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeNode {
    private Integer id;
    @JsonProperty("parentId")
    private Integer pid;
    private String title;
    private String icon;
    private String href;
    private Boolean spread;
    private List<TreeNode> children =new ArrayList<TreeNode>();

//    private String checkArr;//0代表补选中，1代表选中

    public TreeNode(Integer id, Integer pid, String title, String icon, String href, Boolean spread) {
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.icon = icon;
        this.href = href;
        this.spread = spread;
    }

    /**
     *  dtree的数据格式
     * @param id
     * @param pid
     * @param title
     * @param spread
     **/
    public TreeNode(Integer id, Integer pid, String title, Boolean spread) {
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.spread = spread;
    }

    /**
     *  dtree复选树的数据格式
     * @param id
     * @param pid
     * @param title
     * @param spread
     * @param checkArr
     **/
//    public TreeNode(Integer id, Integer pid, String title, Boolean spread, String checkArr) {
//        this.id = id;
//        this.pid = pid;
//        this.title = title;
//        this.spread = spread;
//        this.checkArr = checkArr;
//    }
}
