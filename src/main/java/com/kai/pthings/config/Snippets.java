package com.kai.pthings.config;

import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Snippets {
    //user
    public static final String FOUNDED = "FOUNDED";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String USER_FOUND = "User found";
    public static final String YOU_DONT_HAVE_PERMISSION = "You don't have permission!";
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";
    public static final String NOT_NULL = "User must be not null";
    public static final String EMAIL_ALREADY_TAKEN = "This email already taken!";
    public static final String USERNAME_ALREADY_TAKEN = "This username already taken!";
    public static final String USER_LOGGED_IN = "User logged in";
    public static final String USER_DO_NOT_LOGIN = "User do not login";
    public static final String USER_CREATE_SUCCESSFULLY = "User create successfully";
    public static final String USER_EDITED = "User edited";
    public static final String PASSWORD_UPDATED = "Your password updated";
    public static final String UPLOAD_PROFILE_IMAGE_SUCCESS = "Upload profile image success";
    public static final String UPLOAD_IMAGE_SUCCESS = "Upload image success";
    public static final String NOT_PERMISSION = "You do not permission";
    public static final String HAVE_ERROR = "Have an error. Try again";
    public static final String COMMENT_UPDATED = "Comment updated";
    public static final String COMMENT_ADDED = "Comment added success";
    public static final String ADD_NEW_COMMENT_FAILED = "Add new comment failed, error: %s";
    public static final String STATUS = "status";
    public static final String UPDATE_COMMENT_FAILED = "Update failed";
    //role
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    public static final String ROLE_MOD = "ROLE_MOD";
    public static final String ROLE_EXIST = "Create role %s failed, this role already exist!";
    public static final String BRAND_EXIST = "Create brand %s failed, this brand is already exist";

    public static final String TAG_EXIST = "This tag already exist";
    public static final String CREATE_ROLE_SUCCESS = "The role %s create successfully";
    public static final String ACCOUNT_ACTIVE_SUCCESS = "Account activate success";
    public static final String ACCOUNT_ACTIVE_FAILED = "Account activate failed";

    //time
    public static final String TIME_PATTERN = "dd/MM/yy hh:mm:ss aa";

    //file
    public static final String CANNOT_INITIALIZE_STORAGE = "Cannot initialize storage";
    public static final String FAILED_STORE_EMPTY_FILE = "Failed to store empty file";
    public static final String YOU_CAN_ONLY_UPLOAD_IMAGE = "You can only upload image file";
    public static final String SIZE_UPLOAD_FILE = "Image must be <= 5mb";
    public static final String CANNOT_STORE_OUSIDE = "Cannot store file ouside current directory";
    public static final String STORE_FILE_FAILED = "Failed to store the file";

    //diary
    public static final String DIARY_NOT_FOUND = "Diary not found";
    public static final String DIARY_DELETED = "Diary deleted";
    public static final String DIARY_EDITED = "Diary edited";
    public static final String LIST_DIARY_BY = "List diary by %s";
    public static final String UNTITLED_DIARY = "Untitled Diary";
    public static final String DIARY_CREATE_SUCCESS = "Diary create successfully";
    public static final String DIARY_FOUND = "Diary found";

    //token
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final long EXPIRATION_TIME = 900_000;
    public static final long EXPIRATION_TIME_REFRESH = 2_700_000;
    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "refresh_token";

    //authen
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String SECRET_CODE = "546A576E5A7234753778217A25432A462D4A614E645267556B58703273357638";
    public static final String ROLES = "roles";

    //sort

    public static final String A_Z = "title_a_z";
    public static final String Z_A = "title_z_a";
    public static final String CREATED_AT_ASC = "created_at_asc";
    public static final String CREATED_AT_DESC = "created_at_desc";
    public static final String LAST_EDITED_DESC = "last_edited";
    public static final String CREATED_AT = "created_at";
    public static final String TITLE = "title";

    //image
    public static final String NONAME = "noname";

    //vendor
    public static final String VENDOR = "vendor";
    public static final String CATEGORY = "category";
    public static final String PRODUCT = "product";
    public static final String SAVE_VENDOR = "Save new vendor success";
    public static final String VENDOR_EXIST = "Vendor already exist";
    public static final String PRODUCT_EXIST = "Product already exsit";

    //save success
    public static final String SAVE_SUCCESS = "Save new %s success";
    public static final String CATEGORY_EXIST ="Category already exist";
    public static final String UPDATED_SUCCESS = "Updated %s success";
    public static final String DELETED_SUCCESS = "Delete %s success";
    public static final String NOT_FOUND = "%s with id %s not found";

    //mail
    public static final String ACCOUNT_CREATED_SUCCESS = "Your account create successfully!";

    //type
    public static final String TYPE_REGISTER = "register";
    public static final String TYPE_ACTIVE = "active";


    public static String convertMeta(String str) {
        str = str.replaceAll("à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ", "a");
        str = str.replaceAll("è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ", "e");
        str = str.replaceAll("ì|í|ị|ỉ|ĩ", "i");
        str = str.replaceAll("ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ", "o");
        str = str.replaceAll("ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ", "u");
        str = str.replaceAll("ỳ|ý|ỵ|ỷ|ỹ", "y");
        str = str.replaceAll("đ", "d");

        str = str.replaceAll("À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ằ|Ắ|Ặ|Ẳ|Ẵ", "A");
        str = str.replaceAll("È|É|Ẹ|Ẻ|Ẽ|Ê|Ề|Ế|Ệ|Ể|Ễ", "E");
        str = str.replaceAll("Ì|Í|Ị|Ỉ|Ĩ", "I");
        str = str.replaceAll("Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ", "O");
        str = str.replaceAll("Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ", "U");
        str = str.replaceAll("Ỳ|Ý|Ỵ|Ỷ|Ỹ", "Y");
        str = str.replaceAll("Đ", "D");
        str = str.replaceAll("[^a-zA-Z0-9]+", "-");
        str = str.endsWith("-") ? str.substring(0, str.length() - 1) : str;
        return str.toLowerCase();
    }

    public static String getCurrentDateTime(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(Snippets.TIME_PATTERN);
        return sdf.format(date);
    }

}
