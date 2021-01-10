package top.lete114.service;

import top.lete114.entity.User;

import java.util.Date;

/**
 * @author Lete乐特
 * @createDate 2020- 11-05 10:48
 */
public interface UserService {

    User selUser();

    int ModifyWebSite(
            String title,
            String background,
            String description,
            String subtitle,
            String favicon,
            Date running_time,
            String notice);

    int ModifyPerson(
            String avatar,
            String author,
            String email,
            String icp);

    int ModifyBasic(
            String name,
            String author);

    int ModifyPassword(String password);

    // 保存About
    String saveAbout(String about);
}
