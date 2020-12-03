package ru.bimlibik.pixabaygallery

import android.app.WallpaperManager
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.squareup.picasso.Picasso
import kotlinx.coroutines.coroutineScope
import java.io.IOException

class WallpaperWorker(
    context: Context, workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        try {
            val wallpaperManager = WallpaperManager.getInstance(applicationContext)
            val displayMetrics = applicationContext.resources.displayMetrics
            val url = inputData.getString(IMAGE_URL)
            val bitmap = Picasso.with(applicationContext)
                .load(url)
                .resize(displayMetrics.widthPixels, displayMetrics.heightPixels)
                .centerCrop()
                .get()

            wallpaperManager.setBitmap(bitmap)
            Result.success()
        } catch (e: IOException) {
            Result.failure()
        }

    }


    companion object {
        const val IMAGE_URL = "large image url"
    }
}