package com.example.tripandroidproject.Presenter.RequestCode;

import android.content.Context;

import com.example.tripandroidproject.Contract.RequestCode.RequestCodeContract;
import com.example.tripandroidproject.Model.Firebase.FirebaseRequestCodeModel;


public class RequestCodePresenter implements RequestCodeContract.IRequestCodePresenter {
    RequestCodeContract.IRequestCodeView iRequestCodeView;
    FirebaseRequestCodeModel firebaseRequestCodeModel;
    public RequestCodePresenter(RequestCodeContract.IRequestCodeView iRequestCodeView) {
        this.iRequestCodeView = iRequestCodeView;
        firebaseRequestCodeModel = new FirebaseRequestCodeModel(this);
    }

    @Override
    public void getRequestCode() {
        firebaseRequestCodeModel.getRequestCode();
    }

    @Override
    public void updateRequestCode(int requestCode) {
        firebaseRequestCodeModel.updateRequestCode(requestCode);

    }

    @Override
    public void onSucess(int requestCode) {
        if(requestCode == 0)
        {
            firebaseRequestCodeModel.updateRequestCode(1);
            firebaseRequestCodeModel.getRequestCode();
        }
        else {
            iRequestCodeView.setRequestCodeInSharedPreference(requestCode);
        }
    }

}
