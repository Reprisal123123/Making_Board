package com.fastcampus.ch6.domain;

import java.util.Date;
import java.util.Objects;

public class UserDto {
    private String id; // id
    private String pwd; // 비밀번호
    private String name; // 이름
    private String email; // 이메일
    private Date birth; // 생일
    private String sns; // sns
    private Date reg_date; // 가입일

    public UserDto() {}
    public UserDto(String id, String pwd, String name, String email, Date birth, String sns) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.email = email;
        this.birth = birth;
        this.sns = sns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) && Objects.equals(pwd, userDto.pwd) && Objects.equals(name, userDto.name) && Objects.equals(email, userDto.email) && Objects.equals(birth, userDto.birth) && Objects.equals(sns, userDto.sns);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pwd, name, email, birth, sns);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getSns() {
        return sns;
    }

    public void setSns(String sns) {
        this.sns = sns;
    }

    public Date getReg_date() {
        return reg_date;
    }

    public void setReg_date(Date reg_date) {
        this.reg_date = reg_date;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id='" + id + '\'' +
                ", pwd='" + pwd + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birth=" + birth +
                ", sns='" + sns + '\'' +
                ", reg_date=" + reg_date +
                '}';
    }

}
