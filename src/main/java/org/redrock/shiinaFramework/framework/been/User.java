package org.redrock.frameworkDemo.framework.been;

import lombok.Data;
import org.redrock.frameworkDemo.framework.annotation.Ingore;

@Data
public class User {
    @Ingore
    private boolean isAdmin = false;
    @Ingore
    private int level = -1;
    private String username;
    private String password;
}
