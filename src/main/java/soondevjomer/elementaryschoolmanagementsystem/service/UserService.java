package soondevjomer.elementaryschoolmanagementsystem.service;

import org.springframework.data.domain.Page;
import soondevjomer.elementaryschoolmanagementsystem.dto.UserDto;

public interface UserService {

    Page<UserDto> getUsers(Integer page, Integer size, String sortField, String sortOrder);

    UserDto getUser(Integer userId);
}
