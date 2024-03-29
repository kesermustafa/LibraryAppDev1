package com.lib.exception.message;

public class ErrorMessage {





    public final static String PRINCIPAL_FOUND_MESSAGE = "User not found";
    public final static String BOOK_IS_NOT_AVAILABLE_MESSAGE = "The book is currently in user";
    public final static String BOOK_IS_NOT_LOANABLE_MESSAGE = "The book is close to loan";
    public final static String BOOK_IS_NOT_HAVE_PERMISSON = "You do not have a permission to loan";

    public final static String SCORE_IS_NOT_ENOUGH = "Your score does not between -2 and 2";

    public final static String IMAGE_USED_MESSAGE = "ImageFile is used by another car";

    public final static String PASSWORD_NOT_FOUNT_EXCEPTION = "Your password could not be verified";
    public final static String RESOURSE_NOT_FOUND_EXCEPTION = "User with email : %s not found";

    public final static String USER_NOT_FOUNT_EXCEPTION = "User with id : %s not found";
    public final static String BOOK_NOT_FOUNT_EXCEPTION = "Book with id : %s not found";
    public final static String LOAN_NOT_FOUNT_EXCEPTION = "Loan with id : %s not found";
    public final static String CATEGORY_NOT_FOUNT_EXCEPTION = "Category with id : %s not found";
    public final static String ROLE_NOT_FOUNT_EXCEPTION = "Role : %s not found";
    public final static String EMAIL_ALREADY_EXIST_MESSAGE = "Email : %s already exist";
    public final static String ISBN_ALREADY_EXIST_MESSAGE = "ISBN : %s already exist";
    public final static String NOT_PERMITTED_METHOD_MESSAGE = "You don't have any permission to this data";
    public final static String JWTTOKEN_ERROR_MESSAGE = "JWT Token Validation Error : %s";
    public final static String IMAGE_NOT_FOUND_MESSAGE = "Image with id : %s not found";
    public final static String EXCEL_REPORT_ERROR_MESAGE = "Error occured while gererating excel report";
    public final static String UNAUTHRIZED_FOUND_MESSAGE = "You are not authorized to delete this user ";
    public final static String NOT_DELETED_METHOD_MESSAGE = "The book cannot be deleted as it is not returned by the member. ";


}
