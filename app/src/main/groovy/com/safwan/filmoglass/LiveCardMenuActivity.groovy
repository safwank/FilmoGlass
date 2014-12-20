package com.safwan.filmoglass

import android.app.Activity
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import groovy.transform.CompileStatic

/**
 * A transparent {@link Activity} displaying a "Stop" options menu to remove the {@link LiveCard}.
 */
@CompileStatic
class LiveCardMenuActivity extends Activity {

  @Override
  void onAttachedToWindow() {
    super.onAttachedToWindow()
    // Open the options menu right away.
    openOptionsMenu()
  }

  @Override
  boolean onCreateOptionsMenu(Menu menu) {
    menuInflater.inflate(R.menu.live_card, menu)
    true
  }

  @Override
  boolean onOptionsItemSelected(MenuItem item) {
    switch (item.itemId) {
      case R.id.action_stop:
        // Stop the service which will unpublish the live card.
        stopService(new Intent(this, LiveCardService.class))
        true
      default:
        super.onOptionsItemSelected(item)
    }
  }

  @Override
  void onOptionsMenuClosed(Menu menu) {
    super.onOptionsMenuClosed(menu)
    // Nothing else to do, finish the Activity.
    finish()
  }

}
