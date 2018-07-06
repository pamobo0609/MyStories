package com.challenge.hufsy.mystories.app;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MyStories
 * <p>
 * Created by Jose Monge on 7/6/18.
 * <p>
 */
public class FileUtil {

    private static final String VIDEO_PATTERN = "([^\\s]+(\\.(?i)(3gp|mp4|mkv))$)";

    private AtomicReference<Context> context;
    private Pattern pattern;

    public FileUtil(@NonNull Context context) {
        this.context = new AtomicReference<>(context);
        this.pattern = Pattern.compile(VIDEO_PATTERN);
    }

    public File createImageFile(String imageFileName) throws IOException {

        final File storageDir = context.get().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    public String getFileName(String absolutePath) {
        return absolutePath.substring(absolutePath.lastIndexOf("/") + 1).split("\\.")[0];
    }

    public String getFileExtension(Uri uri) {
        final ContentResolver contentResolver = context.get().getContentResolver();
        final MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public String getPath(Uri uri) {
        final String[] projection = {MediaStore.Video.Media.DATA};
        final Cursor cursor = context.get().getContentResolver().query(uri, projection, null, null, null);
        if (null == cursor) {
            return null;
        } else {
            final int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
    }

    public boolean isAVideo(@NonNull String fileName) {
        final Matcher matcher = pattern.matcher(fileName);
        return matcher.matches();
    }

}
