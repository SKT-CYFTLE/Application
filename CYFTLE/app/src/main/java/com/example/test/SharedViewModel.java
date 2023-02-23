package com.example.test;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> stt = new MutableLiveData<>();
    private final MutableLiveData<String> story = new MutableLiveData<>();
    private final MutableLiveData<String> text2 = new MutableLiveData<>();
    private final MutableLiveData<String> text3 = new MutableLiveData<>();
    private final MutableLiveData<String> text4 = new MutableLiveData<>();
    private final MutableLiveData<String> text5 = new MutableLiveData<>();
    private final MutableLiveData<String> image2 = new MutableLiveData<>();
    private final MutableLiveData<String> image3 = new MutableLiveData<>();
    private final MutableLiveData<String> image4 = new MutableLiveData<>();
    private final MutableLiveData<String> image5 = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<String>> arr = new MutableLiveData<>();

    public LiveData<String> getStt() {
        return stt;
    }

    public void setStt(String str) {
        stt.setValue(str);
    }

    public LiveData<String> getStory() {
        return story;
    }

    public void setStory(String str) {
        story.setValue(str);
    }
    public LiveData<String> getText2() {
        return text2;
    }

    public void setText2(String str) {
        text2.setValue(str);
    }
    public LiveData<String> getText3() {
        return text3;
    }

    public void setText3(String str) {
        text3.setValue(str);
    }
    public LiveData<String> getText4() {
        return text4;
    }

    public void setText4(String str) {
        text4.setValue(str);
    }
    public LiveData<String> getText5() {
        return text5;
    }

    public void setText5(String str) {
        text5.setValue(str);
    }

    public LiveData<String> getImage2() {
        return image2;
    }
    public void setImage2(String str) {
        image2.setValue(str);
    }
    public LiveData<String> getImage3() {
        return image3;
    }
    public void setImage3(String str) {
        image3.setValue(str);
    }
    public LiveData<String> getImage4() {
        return image4;
    }
    public void setImage4(String str) {
        image4.setValue(str);
    }
    public LiveData<String> getImage5() {
        return image5;
    }
    public void setImage5(String str) {
        image5.setValue(str);
    }
    public LiveData<ArrayList<String>> getArr() {
        return arr;
    }
    public void setArr(ArrayList<String> ar) {
        arr.setValue(ar);
    }
}
