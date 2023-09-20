package com.erproject.busgo.views.serviceTracking;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.erproject.busgo.BuildConfig;
import com.erproject.busgo.R;
import com.erproject.busgo.data.data.request.fbRegistration.FbConnectedUser;
import com.erproject.busgo.data.data.simpleData.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.erproject.busgo.base.StaticFields.MAX_TIME_UPDATE;
import static com.erproject.busgo.base.StaticFields.MIDDLE_TIME_UPDATE;

public class StartTrackService extends Service
        implements LocationEngineCallback<LocationEngineResult> {
    public static final String MODEL_EXTRAS = "MODEL_EXTRAS";
    public static final String USER_ID_EXTRAS = "USER_ID_EXTRAS";

    private static final int NOTIFICATION_ID = 1001;
    private final String CHANNEL_ID = "CHANNEL_ID";

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

    private UserModel mTrackingUser;
    private String mUserId;
    private DatabaseReference mDatabase;

    private LocationEngine mLocationEngine;
    private LocationEngineRequest request = new LocationEngineRequest.Builder(MIDDLE_TIME_UPDATE)
            .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
            .setMaxWaitTime(MAX_TIME_UPDATE).build();

    public StartTrackService() {
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void copyFromNewObjectIfNeed(UserModel userModel) {
        if (userModel == null || userModel.getUser() == null) return;
        if (userModel.getUser().getIsTracking() != mTrackingUser.getUser().getIsTracking()) {
            mTrackingUser.getUser().setIsTracking(userModel.getUser().getIsTracking());
        }
        if (!userModel.getUser().getPhone().equals(mTrackingUser.getUser().getPhone())) {
            mTrackingUser.getUser().setPhone(userModel.getUser().getPhone());
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    @SuppressWarnings("MissingPermission")
    public void onCreate() {
        super.onCreate();
        mLocationEngine = LocationEngineProvider.getBestLocationEngine(this);
        mLocationEngine.requestLocationUpdates(request, this, getMainLooper());
    }

    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mTrackingUser = intent.getParcelableExtra(MODEL_EXTRAS);
        mUserId = intent.getStringExtra(USER_ID_EXTRAS);

        this.mDatabase.child(BuildConfig.START_PATH).child(mUserId).child(BuildConfig.MAP_USER_PATH)
                .child(mTrackingUser.getName()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel userModel = new UserModel();
                userModel.setName(dataSnapshot.getKey());
                userModel.setUser(dataSnapshot.getValue(FbConnectedUser.class));
                copyFromNewObjectIfNeed(userModel);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        startForeground();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTracking();
    }

    private void startForeground() {
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(), 0);

        Notification notification =
                new NotificationCompat.Builder(this, CHANNEL_ID).setOngoing(true)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(getString(R.string.string_track_is_running)).build();

        createNotificationChannel();

        startForeground(NOTIFICATION_ID, notification);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "my_chanel_0001";
            String description = "my_chanel_0001 description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void stopTracking() {
        if (mLocationEngine != null && mTrackingUser != null) {
            mLocationEngine.removeLocationUpdates(this);
            FbConnectedUser user = mTrackingUser.getUser();
            user.setLatitude(0d);
            user.setLongitude(0d);
            user.setLastTimeActive("");
            user.setIsUsed(false);

            updateUserInFirebase(user);
        }
    }

    private void updateUserInFirebase(FbConnectedUser user) {
        HashMap<String, Object> mMap = new HashMap<>();
        mMap.put(mTrackingUser.getName(), user);
        mDatabase.child(BuildConfig.START_PATH).child(mUserId).child(BuildConfig.MAP_USER_PATH)
                .updateChildren(mMap);
    }

    @Override
    public void onSuccess(LocationEngineResult result) {
        if (mTrackingUser == null) return;
        FbConnectedUser user = mTrackingUser.getUser();
        Location location = result.getLastLocation();
        if (location != null && user != null) {
            user.setLatitude(location.getLatitude());
            user.setLongitude(location.getLongitude());
            user.setLastTimeActive(sdf.format(new Date()));
            updateUserInFirebase(user);
        }
    }

    @Override
    public void onFailure(@NonNull Exception exception) {

    }
}
