package com.dev.nicola.allweather.callback;

/**
 * Created by Nicola on 20/10/2016.
 */

/**
 * This is a useful callback mechanism so we can abstract our AsyncTasks out into separate, re-usable
 * and testable classes yet still retain a hook back into the calling activity. Basically, it'll make classes
 * cleaner and easier to unit test.
 */
public interface AsyncTaskCompleteListener {

    /**
     * Invoked when the AsyncTask has completed its execution.
     */
    public void onTaskComplete();
}
