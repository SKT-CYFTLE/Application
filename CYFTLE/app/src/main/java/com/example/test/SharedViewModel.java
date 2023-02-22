package com.example.test;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> translate = new MutableLiveData<>();
    private final MutableLiveData<String> story = new MutableLiveData<>();

    public LiveData<String> getTranslate() {
        return translate;
    }

    public void setTranslate(String str) {
        translate.setValue(str);
    }

    public LiveData<String> getStory() {
        return story;
    }

    public void setStory(String str) {
        story.setValue(str);
    }
}
