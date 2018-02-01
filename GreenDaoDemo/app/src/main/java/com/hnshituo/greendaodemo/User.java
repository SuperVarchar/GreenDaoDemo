package com.hnshituo.greendaodemo;

import com.google.gson.annotations.Expose;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/2/1.
 */

@Entity
public class User {
    /**
     * 用户编号
     */
    @Id
    @Expose
    public String id;

    /**
     * 登录名
     */
    @Expose
    public String loginName;

    /**
     * 密码
     */

    @Expose
    public String password;

    /**
     * 用户姓名
     */

    @Expose
    public String username;

    /**
     * 组织ID
     */

    @Expose
    public String deptId;

    /**
     * 组织部门名称
     */

    @Expose
    public String deptName;

    /**
     * 性别
     */

    @Expose
    public String sex;

    /**
     * 年龄
     */

    @Expose
    public String age;

    /**
     * IP地址
     */

    @Expose
    public String ip;

    /**
     * 邮箱
     */

    @Expose
    public String email;

    /**
     * 地址
     */

    @Expose
    public String address;

    /**
     * 电话
     */

    @Expose
    public String phone;
    /**
     * 手机
     **/

    @Expose
    public String telephone;

    /**
     * 照片
     */

    @Expose
    public String image;

    /**
     * 照片
     */

    @Expose
    public String logo;

    /**
     * 加入公司日期
     */

    @Expose
    public String joinDate;

    /**
     * 生日
     */

    @Expose
    public String birthday;

    /**
     * 籍贯
     */

    @Expose
    public String nativePlace;

    /**
     * 政治面貌
     */

    @Expose
    public String political;

    /**
     * 职务
     */

    @Expose
    public String duty;

    /**
     * 职称
     */

    @Expose
    public String title;

    /**
     * 文化程度
     */

    @Expose
    public String education;

    /**
     * 专业
     */

    @Expose
    public String profession;

    /**
     * 毕业院校
     */

    @Expose
    public String graduateSchool;

    /**
     * 是否登录（0-未登录；1-已登录）
     */

    @Expose
    public String islogin;

    /**
     * 用户状态（0-启用；1-停用）
     */

    @Expose
    public String status;

    /**
     * 创建部门
     */

    @Expose
    public String createDeptId;

    /**
     * 创建人
     */

    @Expose
    public String createMan;

    /**
     * 创建时间
     */

    @Expose
    public String createTime;

    /**
     * 修改人
     */

    @Expose
    public String updateMan;

    /**
     * 修改时间
     */

    @Expose
    public String updateTime;

    /**
     * 移动设备
     */

    @Expose
    public String device;


    @Expose
    public String memo;

    //签名

    @Expose
    public String individuality_signature;


    @Expose
    public String token;


    @Expose
    public String positions;
    //1 在职 2离职

    @Expose
    public String valid_flag;

    @Generated(hash = 1642305594)
    public User(String id, String loginName, String password, String username,
            String deptId, String deptName, String sex, String age, String ip,
            String email, String address, String phone, String telephone,
            String image, String logo, String joinDate, String birthday,
            String nativePlace, String political, String duty, String title,
            String education, String profession, String graduateSchool,
            String islogin, String status, String createDeptId, String createMan,
            String createTime, String updateMan, String updateTime, String device,
            String memo, String individuality_signature, String token,
            String positions, String valid_flag) {
        this.id = id;
        this.loginName = loginName;
        this.password = password;
        this.username = username;
        this.deptId = deptId;
        this.deptName = deptName;
        this.sex = sex;
        this.age = age;
        this.ip = ip;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.telephone = telephone;
        this.image = image;
        this.logo = logo;
        this.joinDate = joinDate;
        this.birthday = birthday;
        this.nativePlace = nativePlace;
        this.political = political;
        this.duty = duty;
        this.title = title;
        this.education = education;
        this.profession = profession;
        this.graduateSchool = graduateSchool;
        this.islogin = islogin;
        this.status = status;
        this.createDeptId = createDeptId;
        this.createMan = createMan;
        this.createTime = createTime;
        this.updateMan = updateMan;
        this.updateTime = updateTime;
        this.device = device;
        this.memo = memo;
        this.individuality_signature = individuality_signature;
        this.token = token;
        this.positions = positions;
        this.valid_flag = valid_flag;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeptId() {
        return this.deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return this.age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLogo() {
        return this.logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getJoinDate() {
        return this.joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNativePlace() {
        return this.nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getPolitical() {
        return this.political;
    }

    public void setPolitical(String political) {
        this.political = political;
    }

    public String getDuty() {
        return this.duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEducation() {
        return this.education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getProfession() {
        return this.profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getGraduateSchool() {
        return this.graduateSchool;
    }

    public void setGraduateSchool(String graduateSchool) {
        this.graduateSchool = graduateSchool;
    }

    public String getIslogin() {
        return this.islogin;
    }

    public void setIslogin(String islogin) {
        this.islogin = islogin;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateDeptId() {
        return this.createDeptId;
    }

    public void setCreateDeptId(String createDeptId) {
        this.createDeptId = createDeptId;
    }

    public String getCreateMan() {
        return this.createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateMan() {
        return this.updateMan;
    }

    public void setUpdateMan(String updateMan) {
        this.updateMan = updateMan;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getDevice() {
        return this.device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getIndividuality_signature() {
        return this.individuality_signature;
    }

    public void setIndividuality_signature(String individuality_signature) {
        this.individuality_signature = individuality_signature;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPositions() {
        return this.positions;
    }

    public void setPositions(String positions) {
        this.positions = positions;
    }

    public String getValid_flag() {
        return this.valid_flag;
    }

    public void setValid_flag(String valid_flag) {
        this.valid_flag = valid_flag;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", deptId='" + deptId + '\'' +
                ", deptName='" + deptName + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", ip='" + ip + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", telephone='" + telephone + '\'' +
                ", image='" + image + '\'' +
                ", logo='" + logo + '\'' +
                ", joinDate='" + joinDate + '\'' +
                ", birthday='" + birthday + '\'' +
                ", nativePlace='" + nativePlace + '\'' +
                ", political='" + political + '\'' +
                ", duty='" + duty + '\'' +
                ", title='" + title + '\'' +
                ", education='" + education + '\'' +
                ", profession='" + profession + '\'' +
                ", graduateSchool='" + graduateSchool + '\'' +
                ", islogin='" + islogin + '\'' +
                ", status='" + status + '\'' +
                ", createDeptId='" + createDeptId + '\'' +
                ", createMan='" + createMan + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateMan='" + updateMan + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", device='" + device + '\'' +
                ", memo='" + memo + '\'' +
                ", individuality_signature='" + individuality_signature + '\'' +
                ", token='" + token + '\'' +
                ", positions='" + positions + '\'' +
                ", valid_flag='" + valid_flag + '\'' +
                '}';
    }
}
