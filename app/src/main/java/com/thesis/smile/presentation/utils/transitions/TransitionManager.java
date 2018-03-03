package com.thesis.smile.presentation.utils.transitions;

import android.app.Activity;

public class TransitionManager {

    public static TransitionManager create() {
        return new TransitionManager(DEFAULT_TRANSITION);
    }

    public static TransitionManager create(ActivityTransition transition) {
        return new TransitionManager(transition);
    }

    private static final ActivityTransition DEFAULT_TRANSITION = new NoTransition();

    private ActivityTransition transition;

    private TransitionManager(ActivityTransition transition) {
        this.transition = transition;
    }

    public TransitionManager setTransition(ActivityTransition transition) {
        this.transition = transition;
        return this;
    }

    public ActivityTransition getTransition() {
        return transition;
    }

    /**
     * Must be called in onCreate and before calling super.
     *
     * <pre class="prettyprint">
     * protected void onCreate(Bundle savedInstanceState) {
     *     transitionManager.enterTransition(this);
     *     super.onCreate(savedInstanceState);
     * }
     * </pre>
     *
     * @param activity The activity where we want to override the transitions.
     */
    public void enterTransition(Activity activity) {
        activity.overridePendingTransition(transition.getStartEnterAnim(), transition.getStartExitAnim());
    }

    /**
     * Must be called in onFinish.
     *
     * <pre class="prettyprint">
     * public void finish() {
     *     super.finish();
     *     transitionManager.exitTransition(this);
     * }
     * </pre>
     *
     * @param activity The activity where we want to override the transitions.
     */
    public void exitTransition(Activity activity) {
        activity.overridePendingTransition(transition.getFinishEnterAnim(), transition.getFinishExitAnim());
    }
}
