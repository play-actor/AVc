package com.hfad.AVc.ui;

import android.content.Context;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class SendWorker extends Worker {
    public SendWorker(
            Context context,
            WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {

        // Do the work here--in this case, upload the images.
        ;

        // Indicate whether the work finished successfully with the Result
        return Result.success();
    }
}
