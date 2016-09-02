package com.rubo.dfer.assistant.widget.pullswipe;

/**
 * User: lgd(1973140289@qq.com)
 * Date: 2016-08-29
 * Function:
 */
interface LoadingProgress {
    float getProgress();
    void setProgress(float progress);
    void startProgress();
    void resetProgress();
}
