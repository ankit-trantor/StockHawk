package com.udacity.stockhawk.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import timber.log.Timber;

/**
 * Created by sarabjeet on 4/4/17.
 */

public class WidgetRemoteViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Timber.d("******************Service Initiated********************");
        return new WidgetFactory(getApplicationContext(), intent);
    }
}
