package com.lanyuan.testspringboot.pojo;

import java.io.Serializable;
import java.util.List;

public class Role implements Serializable {
    private Integer id;

    private String rolename;

    private static final long serialVersionUID = 1L;

    private List<User> adminList;

    public List<User> getAdminList() {
        return adminList;
    }

    public void setAdminList(List<User> adminList) {
        this.adminList = adminList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename == null ? null : rolename.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", rolename=").append(rolename);
        sb.append(", adminList=").append(adminList);
        sb.append("]");
        return sb.toString();
    }
}