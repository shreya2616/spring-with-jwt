package com.demo.authentication_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.LifecycleState;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Roles {

    private String name;
    private List<String> permissions;
}
