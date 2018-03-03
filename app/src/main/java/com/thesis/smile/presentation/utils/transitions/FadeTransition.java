package com.thesis.smile.presentation.utils.transitions;

import com.thesis.smile.R;

public class FadeTransition extends ActivityTransition {

    public FadeTransition() {
        super(R.anim.enter_fade, R.anim.stay, R.anim.stay, R.anim.exit_fade);
    }
}

