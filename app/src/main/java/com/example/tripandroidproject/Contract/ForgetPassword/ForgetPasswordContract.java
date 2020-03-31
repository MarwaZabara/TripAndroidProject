package com.example.tripandroidproject.Contract.ForgetPassword;

import com.example.tripandroidproject.Contract.Base.IBase;

public class ForgetPasswordContract {
    public interface IForgetPassView{
        void showMessage(boolean result);
    }
    public interface IForgetPassPresenter extends IBase {
        void getForgetPassword(String email);
    }
    public interface IForgetPassModel {
        void getForgetPassword(String email);
    }
}
