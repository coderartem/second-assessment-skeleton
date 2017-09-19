package com.cooksys.secondassessment.twitterapi.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.secondassessment.twitterapi.dto.UserDto;
import com.cooksys.secondassessment.twitterapi.entity.Users;

@Mapper(componentModel="spring")
public interface UserMapper {
	
	UserDto userToUserDto(Users user);
	List<UserDto> usersToUsersDto(List<Users> users);

}
