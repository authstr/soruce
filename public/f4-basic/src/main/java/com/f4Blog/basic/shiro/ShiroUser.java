package com.f4Blog.basic.shiro;


import java.io.Serializable;
import java.util.List;

public class ShiroUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户主键ID
     */
    private Integer id;

    /**
     * 账号
     */
    private String username;

    /**
     * 角色集
     */
    private List<Integer> roleList;

    /**
     * 角色名称集
     */
    private List<String> roleNames;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Integer> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Integer> roleList) {
        this.roleList = roleList;
    }

    public List<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(List<String> roleNames) {
        this.roleNames = roleNames;
    }
}
