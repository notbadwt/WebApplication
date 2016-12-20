package com.application.security.auth;

import com.application.security.exception.AuthenticationException;
import com.application.security.model.PermissionDetails;
import com.application.security.model.RoleDetails;
import com.application.security.model.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class Authentication {

    private UserDetailsService userDetailsService;

    public static List<RoleDetails> roleDetailsList = new CopyOnWriteArrayList<>();

    public static List<PermissionDetails> permissionDetailsList = new CopyOnWriteArrayList<>();

    public static List<String> anonymousList = new CopyOnWriteArrayList<>();

    private static final String ENCODE = "sha";

    public void freshRoleAndPermissionCache() {
        roleDetailsList = new CopyOnWriteArrayList<>();
        permissionDetailsList = new CopyOnWriteArrayList<>();
        anonymousList = new CopyOnWriteArrayList<>();
    }

    public Authentication(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    private UserDetails login(HttpServletRequest request, UserDetails userDetails) {
        HttpSession session = request.getSession();
        session.setAttribute("userDetails", userDetails);
        session.setMaxInactiveInterval(3600);
        return userDetails;
    }

    public UserDetails usernameAndPasswordLogin(HttpServletRequest request, String username, String password) throws AuthenticationException {
        if (username == null || "".equals(username)) {
            throw new AuthenticationException("用户名不能为空");
        }

        UserDetails userDetails = userDetailsService.getUserByUsername(username);
        if (userDetails == null) {
            throw new AuthenticationException("用户不存在");
        }

        if (!comparePassword(userDetails, password)) {
            throw new AuthenticationException("用户名密码不匹配");
        }

        return login(request, userDetails);
    }

    public UserDetails unionIdLogin(HttpServletRequest request, String unionId) throws AuthenticationException {
        if (unionId == null || "".equals(unionId)) {
            throw new AuthenticationException("unionId 不能为空");
        }

        UserDetails userDetails = userDetailsService.getUserByUnionId(unionId);

        if (userDetails == null) {
            throw new AuthenticationException("用户不存在");
        }

        return login(request, userDetails);
    }

    public boolean logout(HttpServletRequest request) throws AuthenticationException {
        try {

            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(0);
            session.setAttribute("userDetails", null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthenticationException("登出异常 : " + e.getMessage());
        }
        return true;
    }

    public boolean hasPermission(HttpServletRequest request) {
        return false;
    }

    public UserDetails getCurrentUser(HttpServletRequest request) {
        return null;
    }

    private boolean comparePassword(UserDetails userDetails, String password) {
        String userPassword = userDetails.getPassword();
        return (userPassword.equals(encodePassword(password, ENCODE)));
    }

    private String encodePassword(String password, String algorithm) {
        byte[] unencodedPassword = {};
        try {
            unencodedPassword = password.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (Exception e) {
            return password;
        }
        md.reset();
        // call the update method one or more times
        // (useful when you don't know the size of your data, eg. stream)
        md.update(unencodedPassword);
        // now calculate the hash
        byte[] encodedPassword = md.digest();
        /*StringBuffer buf = new StringBuffer();
        for (byte anEncodedPassword : encodedPassword) {
            if ((anEncodedPassword & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString(anEncodedPassword & 0xff, 16));
        }
        return buf.toString();*/
        return getFormattedText(encodedPassword);


    }

    private String getFormattedText(byte bytes[]) {
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        for (int j = 0; j < bytes.length; j++) {
            buf.append(HEX_DIGITS[bytes[j] >> 4 & 15]);
            buf.append(HEX_DIGITS[bytes[j] & 15]);
        }

        return buf.toString();
    }

    private final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
}
