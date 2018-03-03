package com.thesis.smile.presentation.utils.transitions;

import com.thesis.smile.di.scopes.ActivityScope;

import javax.inject.Inject;

@ActivityScope
public class FragmentTransitionManager {

    private int popAnimation;

    @Inject
    public FragmentTransitionManager() {
        this.popAnimation = 0;
    }

    public boolean hasOverriddenPopAnimation() {
        return popAnimation != 0;
    }

    public int getOverriddenPopAnimation() {
        return popAnimation;
    }

    public void overridePopAnimation(int overridePopAnimation) {
        this.popAnimation = overridePopAnimation;
    }

    public void clearOverriddenPopAnimation() {
        overridePopAnimation(0);
    }
}
