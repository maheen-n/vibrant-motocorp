package com.motorcorp.application.modules.user.controller;

import com.motorcorp.application.modules.user.models.PasswordUpdate;
import com.motorcorp.application.modules.user.models.UserBasicView;
import com.motorcorp.application.modules.user.models.UserCreateRequest;
import com.motorcorp.application.modules.user.models.UserFilter;
import com.motorcorp.application.modules.user.models.UserUpdateRequest;
import com.motorcorp.application.modules.user.models.UserView;
import com.motorcorp.application.modules.user.services.UserService;
import com.motorcorp.exceptions.ControllerException;
import com.motorcorp.models.BaseFilter;
import com.motorcorp.models.PaginatedList;
import com.motorcorp.models.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<UserView> create(@RequestBody @Valid UserCreateRequest userCreateRequest,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Request contains validation errors");
            throw new ControllerException(bindingResult);
        }
        UserView userView = userService.create(userCreateRequest);
        return ResponseModel.of(userView);
    }

    @GetMapping("{id}")
    public ResponseModel<UserView> get(@PathVariable("id") Long id) {
        UserView userView = userService.get(id);
        return ResponseModel.of(userView);
    }

    @GetMapping("{id}/info")
    public ResponseModel<UserBasicView> getBasicInfo(@PathVariable("id") Long id) {
        UserBasicView userView = userService.getBasicInfo(id);
        return ResponseModel.of(userView);
    }

    @PostMapping("search")
    public ResponseModel<PaginatedList<UserView>> getAllUsers(@Valid @RequestBody UserFilter params) {
        PaginatedList<UserView> userView = userService.get(params);
        return ResponseModel.of(userView);
    }

    @PutMapping("{id}")
    public ResponseModel<UserView> update(@PathVariable("id") Long id, @Valid @RequestBody UserUpdateRequest updateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Request contains validation errors");
            throw new ControllerException(bindingResult);
        }
        UserView userView = userService.update(id, updateRequest);
        return ResponseModel.of(userView);
    }

    @DeleteMapping("{id}")
    public ResponseModel<Long> delete(@PathVariable("id") Long id) {
        UserView userView = userService.delete(id);
        return ResponseModel.of(userView.getId());
    }

    @PostMapping("{id}/password")
    public ResponseModel<Long> updatePassword(@PathVariable("id") Long id,
                                              @Valid @RequestBody PasswordUpdate update,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Request contains validation errors");
            throw new ControllerException(bindingResult);
        }
        UserView userView = userService.updatePassword(id,update);
        return ResponseModel.of(userView.getId());
    }

}
