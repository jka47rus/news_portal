//package com.example.news_portal.mapper;
//
//import com.example.news_portal.dto.request.UserRequest;
//import com.example.news_portal.dto.response.UserListResponse;
//import com.example.news_portal.dto.response.UserResponse;
//import com.example.news_portal.model.User;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.ReportingPolicy;
//
//import java.util.List;
//import java.util.UUID;
//
////@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
//public interface UserMapper {
//    UserResponse userToResponse(User user);
//    User fromRequestToUser(UserRequest request);
////    @Mapping(source = "userId", target = "id")
//    User requestToUser(UUID id, UserRequest request);
//
////    UserListResponse userListToUserListResponse(List<User> users);
//
//}
