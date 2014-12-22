package com.safwan.filmoglass

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.RemoteViews
import com.google.android.glass.timeline.LiveCard
import com.google.android.glass.timeline.LiveCard.PublishMode
import groovy.transform.CompileStatic

@CompileStatic
class LiveCardService extends Service {

  private static final String LIVE_CARD_TAG = "LiveCardService"

  private LiveCard mLiveCard

  @Override
  IBinder onBind(Intent intent) {
    null
  }

  @Override
  int onStartCommand(Intent intent, int flags, int startId) {
    if (mLiveCard == null) {
      initializeLiveCard()
    } else {
      mLiveCard.navigate()
    }
    START_STICKY
  }

  private void initializeLiveCard() {
    mLiveCard = new LiveCard(this, LIVE_CARD_TAG)
    mLiveCard.setViews(new RemoteViews(packageName, R.layout.live_card))
    displayOptionsMenuWhenTapped(mLiveCard)
  }

  private void displayOptionsMenuWhenTapped(LiveCard mLiveCard) {
    def menuIntent = new Intent(this, LiveCardMenuActivity.class)
    mLiveCard.setAction(PendingIntent.getActivity(this, 0, menuIntent, 0))
    mLiveCard.publish(PublishMode.REVEAL)
  }

  @Override
  void onDestroy() {
    if (mLiveCard?.isPublished()) {
      mLiveCard.unpublish()
      mLiveCard = null
    }
    super.onDestroy()
  }

}
