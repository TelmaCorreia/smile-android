package com.thesis.smile.presentation.utils.transitions;

import android.support.annotation.AnimRes;

public class ActivityTransition {

    @AnimRes
    private int startEnterAnim;

    @AnimRes
    private int startExitAnim;

    @AnimRes
    private int finishEnterAnim;

    @AnimRes
    private int finishExitAnim;

    public ActivityTransition(@AnimRes int startEnterAnim, @AnimRes int startExitAnim, @AnimRes int finishEnterAnim, @AnimRes int finishExitAnim) {
        this.startEnterAnim = startEnterAnim;
        this.startExitAnim = startExitAnim;
        this.finishEnterAnim = finishEnterAnim;
        this.finishExitAnim = finishExitAnim;
    }

    public int getStartEnterAnim() {
        return startEnterAnim;
    }

    public void setStartEnterAnim(@AnimRes int startEnterAnim) {
        this.startEnterAnim = startEnterAnim;
    }

    public int getStartExitAnim() {
        return startExitAnim;
    }

    public void setStartExitAnim(@AnimRes int startExitAnim) {
        this.startExitAnim = startExitAnim;
    }

    public int getFinishEnterAnim() {
        return finishEnterAnim;
    }

    public void setFinishEnterAnim(@AnimRes int finishEnterAnim) {
        this.finishEnterAnim = finishEnterAnim;
    }

    public int getFinishExitAnim() {
        return finishExitAnim;
    }

    public void setFinishExitAnim(@AnimRes int finishExitAnim) {
        this.finishExitAnim = finishExitAnim;
    }
}
