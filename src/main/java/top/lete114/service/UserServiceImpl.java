package top.lete114.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.lete114.entity.User;
import top.lete114.mapper.UserMapper;

import java.util.Date;

/**
 * @author Lete乐特
 * @createDate 2020- 11-05 10:49
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired(required=false)
    public UserMapper mapper;

    @Override
    public User selUser() {
        return mapper.selUser();
    }

    @Override
    public int ModifyWebSite(
            String title,
            String background,
            String description,
            String subtitle,
            String favicon,
            Date running_time,String notice) {
        return mapper.ModifyWebSite(title,background,description,subtitle,favicon,running_time,notice);
    }

    @Override
    public int ModifyPerson(String avatar, String author, String email, String icp) {
        return mapper.ModifyPerson(avatar,author,email,icp);
    }

    @Override
    public int ModifyBasic(String name, String author) {
        return mapper.ModifyBasic(name,author);
    }

    @Override
    public int ModifyPassword(String password) {
        return mapper.ModifyPassword(password);
    }

    @Override
    public String saveAbout(String about) {
        int result = mapper.saveAbout(about);
        if(result>0)return "success";
        return "error";
    }
}
