package com.green.thirdproject.score.score.module;

import com.green.fefu.exception.CustomException;
import com.green.fefu.security.AuthenticationFacade;
import com.green.fefu.security.MyUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.green.fefu.exception.LJH.LjhErrorCode.SCORE_INSERT_POST;

@Component
@RequiredArgsConstructor
public class RoleCheckerImpl implements Check {
    private final AuthenticationFacade authenticationFacade;

//    @Autowired
//    public RoleCheckerImpl(AuthenticationFacade authenticationFacade) {
//        this.authenticationFacade = authenticationFacade;
//    }

    @Override
    public void checkTeacherRole() {
        MyUser user = authenticationFacade.getLoginUser();
        String userRole = user.getRole();
        if (!userRole.equals("ROLE_TEACHER")) {
            throw new CustomException(SCORE_INSERT_POST);
        }
    }
}
