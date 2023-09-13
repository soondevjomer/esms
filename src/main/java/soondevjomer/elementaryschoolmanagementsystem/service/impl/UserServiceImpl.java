package soondevjomer.elementaryschoolmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import soondevjomer.elementaryschoolmanagementsystem.dto.UserDto;
import soondevjomer.elementaryschoolmanagementsystem.exception.NotFoundException;
import soondevjomer.elementaryschoolmanagementsystem.security.User;
import soondevjomer.elementaryschoolmanagementsystem.security.UserRepository;
import soondevjomer.elementaryschoolmanagementsystem.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<UserDto> getUsers(Integer page, Integer size, String sortField, String sortOrder) {

        Sort.Direction direction = Sort.Direction.ASC;
        if (sortOrder.equalsIgnoreCase("desc"))
            direction = Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        return userRepository.findAll(pageable)
                .map(this::createUserDtoResponse);
    }

    @Override
    public UserDto getUser(Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(()->new NotFoundException("User", "id", userId.toString()));

        return createUserDtoResponse(user);
    }

    private UserDto createUserDtoResponse(User user) {

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setDateCreated(user.getDateCreated());
        userDto.setRole(user.getRole().toString());

        return userDto;
    }
}
