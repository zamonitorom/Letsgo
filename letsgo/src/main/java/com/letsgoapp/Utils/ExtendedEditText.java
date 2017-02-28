package com.letsgoapp.Utils;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by kyupetrov on 27.02.2017.
 */

public class ExtendedEditText extends EditText {
    public ArrayList<TextWatcher> mListeners = null;

    public ExtendedEditText(Context ctx) {
        super(ctx);
    }

    public ExtendedEditText(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
    }

    public ExtendedEditText(Context ctx, AttributeSet attrs, int defStyle) {
        super(ctx, attrs, defStyle);
    }

    @Override
    public void addTextChangedListener(TextWatcher watcher) {
        if (mListeners == null) {
            mListeners = new ArrayList<>();
        }

        mListeners.add(watcher);

        super.addTextChangedListener(watcher);
    }

    public void setTextChangedListener(TextWatcher watcher) {
        clearTextChangedListeners();
        addTextChangedListener(watcher);
    }

    @Override
    public void removeTextChangedListener(TextWatcher watcher) {
        if (mListeners != null) {
            int i = mListeners.indexOf(watcher);
            if (i >= 0) {
                mListeners.remove(i);
            }
        }

        super.removeTextChangedListener(watcher);
    }

    public void clearTextChangedListeners() {
        if (mListeners != null) {
            for (TextWatcher watcher : mListeners) {
                super.removeTextChangedListener(watcher);
            }

            mListeners.clear();
            mListeners = null;
        }
    }

}