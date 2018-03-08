package cn.com.chinalife.ecdata.entity;

/**
 * Created by xiexiangyu on 2018/3/8.
 */
public class Auth {
    private String roleName;
    private String resourceId;
    private String resourceName;
    private String resourcePath;


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public String toString() {
        return "Auth{" +
                "roleName='" + roleName + '\'' +
                ", resourceId='" + resourceId + '\'' +
                ", resourceName='" + resourceName + '\'' +
                ", resourcePath='" + resourcePath + '\'' +
                '}';
    }
}
