package kg.tutorialapp.hw_42

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RemoteViews

class MainActivity : AppCompatActivity() {

    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationChannel: NotificationChannel
    private lateinit var builder: Notification.Builder
    private val channelId = "app.notification"
    private val description = "Test notification"
    private lateinit var btnNotification: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setup()
    }

    private fun setup() {
        btnNotification = findViewById(R.id.btn_notification)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        btnNotification.setOnClickListener {
            val intent = Intent (this, NotificationActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                    this,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT)
            RemoteViews(packageName, R.layout.activity_notification)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.BLUE
                notificationChannel.enableVibration(false)
                notificationManager.createNotificationChannel(notificationChannel)

                builder = Notification.Builder(this, channelId)
                        .setContentTitle("Notification")
                        .setContentText("Notification is a kind of message, alert, or status of an application.")
                        .setSmallIcon(R.drawable.bell)
                        .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.bell))
                        .setContentIntent(pendingIntent)
            } else {
                builder = Notification.Builder(this)
                        .setContentTitle("Notification")
                        .setContentText("Notification is a kind of message, alert, or status of an application.")
                        .setSmallIcon(R.drawable.bell)
                        .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.bell))
                        .setContentIntent(pendingIntent)
            }
            notificationManager.notify(123, builder.build())
        }
    }
}