package com.example.myloginmvvm.uploadprogress;

public interface ProgressListener {
        void onProgress(long hasWrittenLen, long totalLen, boolean hasFinish);
    }
