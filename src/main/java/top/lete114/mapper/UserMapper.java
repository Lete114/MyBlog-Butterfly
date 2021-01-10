package top.lete114.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.lete114.entity.User;

import java.util.Date;

/**
 * @author Lete乐特
 * @createDate 2020- 11-05 10:52
 */
@Mapper
public interface UserMapper {
    // 查询用户信息
    User selUser();

    // 修改用户信息
    int ModifyWebSite(
            @Param("title")String title,
            @Param("background")String background,
            @Param("description")String description,
            @Param("subtitle")String subtitle,
            @Param("favicon")String favicon,
            @Param("running_time") Date running_time,
            @Param("notice")String notice);

    // 修改用户信息
    int ModifyPerson(
            @Param("avatar")String avatar,
            @Param("author")String author,
            @Param("email")String email,
            @Param("icp")String icp);

    // 修改基本信息
    int ModifyBasic(
            @Param("name")String name,
            @Param("author")String author);

    // 修改密码
    int ModifyPassword(@Param("password")String password);

    // 保存About
    int saveAbout(String about);
}
