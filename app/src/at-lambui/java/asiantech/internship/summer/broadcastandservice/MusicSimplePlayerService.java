package asiantech.internship.summer.broadcastandservice;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;

public class MusicSimplePlayerService extends Service {
    private Handler mHandeler;
    private Runnable mRunnable;
    private simplePlayer mSimplePlayer;
    private static final int TIME_DELAY = 500;

    public MusicSimplePlayerService() {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                Intent durationintent = new Intent(BroadCastReceiverAndServiceActivity.DURATION_ACTION);
                durationintent.putExtra(BroadCastReceiverAndServiceActivity.DURATION_KEY,mSimplePlayer.mCurrentPosition);


            }
        };

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
