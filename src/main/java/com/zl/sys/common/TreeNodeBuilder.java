package com.zl.sys.common;

import java.util.ArrayList;
import java.util.List;

public class TreeNodeBuilder {
    public  static List<TreeNode> build(List<TreeNode> treeNodes,Integer topPid){
        List<TreeNode> nodes=new ArrayList<>();
        for(TreeNode node1:treeNodes){
            if(topPid==node1.getPid()){
                nodes.add(node1);
            }
            for(TreeNode node2:treeNodes){
                if(node1.getId()==node2.getPid()){
                    node1.getChildren().add(node2);
                }
            }
        }
        return nodes;

    }
}
