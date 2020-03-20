package com.example.tripandroidproject.Contract.SignUp;

import com.example.tripandroidproject.Contract.Base.IBase;
import com.example.tripandroidproject.View.UserDetails;

public class SignUpContract {
    public interface ISignUpView{
        void showMessage(Boolean result);
    }
    public interface ISignUpPresenter extends IBase {
        void onSendData(UserDetails userDetails);
    }
    public interface ISignUpModel{
        void signUp(UserDetails userDetails);
    }
}
