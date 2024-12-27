package orderservice.users;


import orderservice.entity.BaseModels;

public class Roles extends BaseModels {
    private String roleType;

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

}
